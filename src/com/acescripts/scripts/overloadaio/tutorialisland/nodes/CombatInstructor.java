package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import com.acescripts.scripts.overloadaio.tutorialisland.methods.TutorialIslandMethods;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import static org.osbot.rs07.script.MethodProvider.random;
import static org.osbot.rs07.script.MethodProvider.sleep;

/**
 * Created by Transporter on 07/08/2016 at 01:48.
 */

public class CombatInstructor extends Node {

    private TutorialIslandMethods tutorialIslandMethods = new TutorialIslandMethods(script);

    public CombatInstructor(Script script) {
        super(script);
    }

    private void openWornInterface() {
        if(script.widgets.get(85, 0) != null && script.widgets.get(85, 0).isVisible()) {
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
                tutorialIslandMethods.walkExact(script, new Position(3107, 9511, 0));
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
            tutorialIslandMethods.interactWithNpc("Combat Instructor", "Talk-to");
        } else {
            tutorialIslandMethods.interactWithObject("Gate", Constants.Objects.COMBAT_INSTRUCTOR_GATE_POSITION, "Open");
            sleep(random(2000, 3000));
        }
    }

    private void talkToCombatInstructor() {
        if (script.widgets.get(84, 1) != null && script.widgets.get(84, 1).isVisible()) {
            script.widgets.get(84, 4).interact();
        } else {
            tutorialIslandMethods.interactWithNpc("Combat Instructor", "Talk-to");
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
                    tutorialIslandMethods.setStatus("Talking to Combat Instructor.");
                    tutorialIslandMethods.interactWithNpc("Combat Instructor", "Talk-to");
                    break;
                case Constants.WidgetText.OPENING_EQUIPMENT:
                    tutorialIslandMethods.setStatus("Opening Equipment.");
                    script.getTabs().open(Tab.EQUIPMENT);
                    break;
                case Constants.WidgetText.OPENING_WORN_INTERFACE:
                    tutorialIslandMethods.setStatus("Opening Worn Interface.");
                    openWornInterface();
                    break;
                case Constants.WidgetText.EQUIPPING_DAGGER:
                    tutorialIslandMethods.setStatus("Equipping Bronze Dagger.");
                    equipWeapon("Bronze dagger", "Wield");
                    break;
                case Constants.WidgetText.OPENING_COMBAT:
                    tutorialIslandMethods.setStatus("Opening Combat Tab.");
                    script.getTabs().open(Tab.ATTACK);
                    break;
                case Constants.WidgetText.ATTACK_RAT_MELEE:
                    tutorialIslandMethods.setStatus("Attacking Rat.");
                    attackRat();
                    break;
                case Constants.WidgetText.TALK_TO_COMBAT_INSTRUCTOR_RANGE:
                    tutorialIslandMethods.setStatus("Talking to Combat Instructor.");
                    returnToCombatInstructor();
                    break;
                case Constants.WidgetText.ATTACK_RAT_RANGE:
                    tutorialIslandMethods.setStatus("Ranging Rat.");
                    rangeRat();
                    break;
                case Constants.WidgetText.OPEN_COMBAT_INSTRUCTOR_EXIT:
                    tutorialIslandMethods.setStatus("Climbing Ladder.");
                    tutorialIslandMethods.interactWithObject("Ladder", "Climb-up");
                    break;
            }
        } else if(widgetTwo != null && widgetTwo.isVisible()) {
            switch(widgetTwo.getMessage()) {
                case Constants.WidgetText.TALK_TO_COMBAT_INSTRUCTOR_DAGGER:
                    tutorialIslandMethods.setStatus("Talking to Combat Instructor.");
                    talkToCombatInstructor();
                    break;
                case Constants.WidgetText.EQUIP_BETTER_WEAPON:
                    tutorialIslandMethods.setStatus("Equipping Better Weapons.");
                    equipWeapon("Bronze sword", "Wield");
                    equipWeapon("Wooden shield", "Wield");
                    break;
                case Constants.WidgetText.OPEN_RAT_GATE:
                    tutorialIslandMethods.setStatus("Opening Gate.");
                    tutorialIslandMethods.interactWithObject("Gate", Constants.Objects.COMBAT_INSTRUCTOR_GATE_POSITION, "Open");
                    break;
            }
        } else {
            tutorialIslandMethods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.configs.get(406) == 10 || script.configs.get(406) == 11 || script.configs.get(406) == 12;
    }
}