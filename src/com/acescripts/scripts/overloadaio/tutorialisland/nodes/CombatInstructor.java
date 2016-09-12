package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.utility.ConditionalSleep;

import static org.osbot.rs07.script.MethodProvider.random;
import static org.osbot.rs07.script.MethodProvider.sleep;

/**
 * Created by Transporter on 07/08/2016 at 01:48.
 */

public class CombatInstructor extends Node {
    public CombatInstructor(OverloadAIO script) {
        super(script);
    }

    private void openWornInterface() {
        if(script.getTabs().getOpen().equals(Tab.EQUIPMENT)) {
            script.widgets.get(387, 18).interact();
        } else {
            script.getTabs().open(Tab.EQUIPMENT);
        }
    }

    private void equipWeapon(String weaponName, String equipOption) {
        if(script.getInventory().getItem(weaponName) != null) {
            script.getInventory().getItem(weaponName).interact(equipOption);
        }
    }

    private void attackRat() {
        if(!script.myPlayer().isUnderAttack() && !script.myPlayer().isAnimating() && script.myPlayer().getInteracting() == null) {
            if(script.myPosition().equals(new Position(3104, 9509, 0))) {
                methods.walkExact(script, new Position(3107, 9511, 0));
            } else {
                NPC rat = script.getNpcs().npcs.closest(new Filter<NPC>() {
                    @Override
                    public boolean match(NPC npc) {
                        return npc != null && npc.getName().equals("Giant rat") && !npc.isUnderAttack() && (npc.getInteracting() == null || npc.getInteracting().equals(script.myPlayer())) && npc.getAnimation() != 4653;
                    }
                });
                rat.interact("Attack");
                new ConditionalSleep(5000) {
                    @Override
                    public boolean condition() {
                        return script.myPlayer().isAnimating() && script.myPlayer().isMoving();
                    }
                }.sleep();
            }
        }
    }

    private void rangeRat() {
        if(script.getTabs().getOpen().equals(Tab.INVENTORY)) {
            if(script.inventory.contains("Bronze arrow")) {
                script.inventory.getItem("Bronze arrow").interact("Wield");
            } else if(script.inventory.contains("Shortbow")) {
                script.inventory.getItem("Shortbow").interact("Wield");
            } else {
                attackRat();
            }
        } else {
            script.getTabs().open(Tab.INVENTORY);
        }
    }

    private void returnToCombatInstructor() throws InterruptedException {
        NPC npc = script.getNpcs().closest("Combat Instructor");

        if(script.map.canReach(npc)) {
            methods.interactWithNpc("Combat Instructor", "Talk-to");
        } else {
            methods.interactWithObject("Gate", Constants.Objects.COMBAT_INSTRUCTOR_GATE_POSITION, "Open");
            sleep(random(2000, 3000));
        }
    }

    private void talkToCombatInstructor() {
        if (script.widgets.get(84, 1) != null && script.widgets.get(84, 1).isVisible()) {
            script.widgets.get(84, 4).interact();
        } else {
            methods.interactWithNpc("Combat Instructor", "Talk-to");
        }
    }

    @Override
    public void execute() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT,
                Constants.WidgetText.TALK_TO_COMBAT_INSTRUCTOR_START,
                Constants.WidgetText.OPENING_EQUIPMENT,
                Constants.WidgetText.OPENING_WORN_INTERFACE,
                Constants.WidgetText.EQUIPPING_DAGGER,
                Constants.WidgetText.OPENING_COMBAT,
                Constants.WidgetText.ATTACK_RAT_MELEE,
                Constants.WidgetText.TALK_TO_COMBAT_INSTRUCTOR_RANGE,
                Constants.WidgetText.ATTACK_RAT_RANGE,
                Constants.WidgetText.OPEN_COMBAT_INSTRUCTOR_EXIT
        );

        RS2Widget widgetTwo = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT_2,
                Constants.WidgetText.TALK_TO_COMBAT_INSTRUCTOR_DAGGER,
                Constants.WidgetText.EQUIP_BETTER_WEAPON,
                Constants.WidgetText.OPEN_RAT_GATE
        );

        if(widget != null && widget.isVisible()) {
            switch(widget.getMessage()) {
                case Constants.WidgetText.TALK_TO_COMBAT_INSTRUCTOR_START:
                    script.setStatus("Talking to Combat Instructor.");
                    methods.interactWithNpc("Combat Instructor", "Talk-to");
                    break;
                case Constants.WidgetText.OPENING_EQUIPMENT:
                    script.setStatus("Opening Equipment.");
                    script.getTabs().open(Tab.EQUIPMENT);
                    break;
                case Constants.WidgetText.OPENING_WORN_INTERFACE:
                    script.setStatus("Opening Worn Interface.");
                    openWornInterface();
                    break;
                case Constants.WidgetText.EQUIPPING_DAGGER:
                    script.setStatus("Equipping Bronze Dagger.");
                    equipWeapon("Bronze dagger", "Wield");
                    break;
                case Constants.WidgetText.OPENING_COMBAT:
                    script.setStatus("Opening Combat Tab.");
                    script.getTabs().open(Tab.ATTACK);
                    break;
                case Constants.WidgetText.ATTACK_RAT_MELEE:
                    script.setStatus("Attacking Rat.");
                    attackRat();
                    break;
                case Constants.WidgetText.TALK_TO_COMBAT_INSTRUCTOR_RANGE:
                    script.setStatus("Talking to Combat Instructor.");
                    returnToCombatInstructor();
                    break;
                case Constants.WidgetText.ATTACK_RAT_RANGE:
                    script.setStatus("Ranging Rat.");
                    rangeRat();
                    break;
                case Constants.WidgetText.OPEN_COMBAT_INSTRUCTOR_EXIT:
                    script.setStatus("Climbing Ladder.");
                    methods.interactWithObject("Ladder", "Climb-up");
                    break;
            }
        } else if(widgetTwo != null && widgetTwo.isVisible()) {
            switch(widgetTwo.getMessage()) {
                case Constants.WidgetText.TALK_TO_COMBAT_INSTRUCTOR_DAGGER:
                    script.setStatus("Talking to Combat Instructor.");
                    talkToCombatInstructor();
                    break;
                case Constants.WidgetText.EQUIP_BETTER_WEAPON:
                    script.setStatus("Equipping Better Weapons.");
                    equipWeapon("Bronze sword", "Wield");
                    equipWeapon("Wooden shield", "Wield");
                    break;
                case Constants.WidgetText.OPEN_RAT_GATE:
                    script.setStatus("Opening Gate.");
                    methods.interactWithObject("Gate", Constants.Objects.COMBAT_INSTRUCTOR_GATE_POSITION, "Open");
                    break;
            }
        } else {
            methods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.configs.get(406) == 10 || script.configs.get(406) == 11 || script.configs.get(406) == 12;
    }
}