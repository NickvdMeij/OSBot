package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.Random;

/**
 * Created by Transporter on 07/08/2016 at 02:20.
 */
public class MagicInstructor extends Node {
    public MagicInstructor(OverloadAIO script) {
        super(script);
    }

    private void talkToNpc() {
        NPC npc = script.getNpcs().closest("Magic Instructor");

        if(npc != null) {
            methods.interactWithNpc("Magic Instructor", "Talk-to");
        } else {
            script.walking.walk(new Position(3141, 3086, 0));
        }
    }

    private void walkToWindStrikeTile(int x, int y) {
        if(!script.myPosition().equals(new Position(x, y, 0))) {
            methods.walkExact(script, new Position(x, y, 0));
            new ConditionalSleep(3500) {
                @Override
                public boolean condition() {
                    return script.myPosition().equals(new Position(x, y, 0));
                }
            }.sleep();
        }
    }

    private void attackChicken() {
        NPC npc = script.getNpcs().closest("Chicken");

        if(npc.isVisible()) {
            script.getMagic().castSpellOnEntity(Spells.NormalSpells.WIND_STRIKE, npc);
        } else {
            script.camera.toPosition(npc.getPosition());
        }
    }

    private void castWindStrike(String npcName) {
        NPC npc = script.getNpcs().closest(npcName);

        if(script.getTabs().getOpen() != null && script.getTabs().getOpen().equals(Tab.MAGIC)) {
            if(npc != null) {
                Random rand = new Random();
                int n = rand.nextInt(3) + 1;

                if(script.myPosition().getY() != 3091) {
                    if(n == 1) {
                        walkToWindStrikeTile(3138, 3091);
                    } else if(n == 2) {
                        walkToWindStrikeTile(3139, 3091);
                    } else if(n == 3) {
                        walkToWindStrikeTile(3140, 3091);
                    }
                } else {
                    attackChicken();
                }
            }
        } else {
            script.getTabs().open(Tab.MAGIC);
        }
    }

    @Override
    public void execute() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT,
                Constants.WidgetText.TALK_TO_MAGIC_INSTRUCTOR_START,
                Constants.WidgetText.OPENING_MAGIC,
                Constants.WidgetText.TALK_TO_MAGIC_INSTRUCTOR_MAGIC
        );

        RS2Widget widgetTwo = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT_2,
                Constants.WidgetText.CAST_WIND_STRIKE
        );

        if(widget != null && widget.isVisible()) {
            switch (widget.getMessage()) {
                case Constants.WidgetText.TALK_TO_MAGIC_INSTRUCTOR_START:
                    script.setStatus("Talking to Magic Instructor.");
                    talkToNpc();
                    break;
                case Constants.WidgetText.OPENING_MAGIC:
                    script.setStatus("Opening Magic Tab.");
                    script.getTabs().open(Tab.MAGIC);
                    break;
                case Constants.WidgetText.TALK_TO_MAGIC_INSTRUCTOR_MAGIC:
                    script.setStatus("Talking to Magic Instructor.");
                    methods.interactWithNpc("Magic Instructor", "Talk-to");
                    break;
            }
        } else if(widgetTwo != null && widgetTwo.isVisible()) {
            switch(widgetTwo.getMessage()) {
                case Constants.WidgetText.CAST_WIND_STRIKE:
                    script.setStatus("Casting Wind Strike.");
                    castWindStrike("Chicken");
                    break;
            }
        } else if(script.getDialogues().isPendingOption()) {
            script.getDialogues().selectOption("Yes.");
        } else {
            methods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        Area tutorialIslandArea = new Area(3059, 3136, 3151, 3059);
        return script.configs.get(406) == 18 || script.configs.get(406) == 19 || script.configs.get(406) == 20 && tutorialIslandArea.contains(script.myPosition());
    }
}
