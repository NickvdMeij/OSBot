package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.Random;

/**
 * Created by Transporter on 07/08/2016 at 01:25.
 */

public class MiningInstructor extends Node {
    public MiningInstructor(OverloadAIO script) {
        super(script);
    }

    private void talkToMiningNpc() {
        NPC npc = script.getNpcs().closest("Mining Instructor");

        if(npc != null) {
            methods.interactWithNpc("Mining Instructor", "Talk-to");
        } else {
            script.walking.walk(new Position(3080, 9505, 0));
        }
    }

    public void interactWithRocks(int colour, String interactOption) {
        Entity rock = script.getObjects().closest(
                obj -> obj.getName().equals("Rocks") && obj.getDefinition().getModifiedModelColors()[0] == colour
        );

        if(rock != null) {
            if(rock.isVisible() && !script.myPlayer().isAnimating() && !script.myPlayer().isMoving()) {
                rock.interact(interactOption);
                new ConditionalSleep(3500) {
                    @Override
                    public boolean condition() {
                        return script.myPlayer().isAnimating() || script.myPlayer().isMoving();
                    }
                }.sleep();
            } else {
                if(script.myPosition().distance(rock.getPosition()) > 5) {
                    script.walking.walk(rock);
                } else {
                    script.getCamera().toPosition(rock.getPosition());
                }
            }
        }
    }

    private void makeBronzeBar() throws InterruptedException {
        if(script.getTabs().getOpen().equals(Tab.INVENTORY)) {
            Random rand = new Random();
            int n = rand.nextInt(2) + 1;

            if(n == 1) {
                methods.useItemOnObject("Furnace", "Tin ore");
            } else if(n == 2) {
                methods.useItemOnObject("Furnace", "Copper ore");
            }
        } else {
            script.getTabs().open(Tab.INVENTORY);
        }
    }

    private void smithBronzeDagger() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText("What would you like to make?");

        if(widget != null && widget.isVisible()) {
            script.widgets.get(312, 2).interact("Smith 1");
        } else {
            if(!script.myPlayer().isAnimating()) {
                methods.useItemOnObject("Anvil", "Bronze bar");
            }
        }
    }

    @Override
    public void execute() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT,
                Constants.WidgetText.TALK_TO_MINING_INSTRUCTOR_START,
                Constants.WidgetText.PROSPECT_TIN_ORE,
                Constants.WidgetText.PROSPECT_COPPER_ORE,
                Constants.WidgetText.TALK_TO_MINING_INSTRUCTOR_SMELTING,
                Constants.WidgetText.SMELTING,
                Constants.WidgetText.TALK_TO_MINING_INSTRUCTOR_SMITHING,
                Constants.WidgetText.SMITHING_A_DAGGER,
                Constants.WidgetText.OPEN_MINING_INSTRUCTOR_EXIT,
                Constants.WidgetText.MINE_TIN_ORE,
                Constants.WidgetText.MINE_COPPER_ORE
        );

        if(widget != null && widget.isVisible()) {
            switch(widget.getMessage()) {
                case Constants.WidgetText.TALK_TO_MINING_INSTRUCTOR_START:
                case Constants.WidgetText.TALK_TO_MINING_INSTRUCTOR_SMELTING:
                case Constants.WidgetText.TALK_TO_MINING_INSTRUCTOR_SMITHING:
                    script.setStatus("Talking to Mining Instructor.");
                    talkToMiningNpc();
                    break;
                case Constants.WidgetText.PROSPECT_TIN_ORE:
                    script.setStatus("Prospecting Tin Ore.");
                    interactWithRocks(Constants.Objects.TIN_ROCK_COLOUR, "Prospect");
                    break;
                case Constants.WidgetText.PROSPECT_COPPER_ORE:
                    script.setStatus("Prospecting Copper Ore.");
                    interactWithRocks(Constants.Objects.COPPER_ROCK_COLOUR, "Prospect");
                    break;
                case Constants.WidgetText.SMELTING:
                    script.setStatus("Making Bronze Bar.");
                    makeBronzeBar();
                    break;
                case Constants.WidgetText.SMITHING_A_DAGGER:
                    script.setStatus("Smithing Bronze Dagger.");
                    smithBronzeDagger();
                    break;
                case Constants.WidgetText.OPEN_MINING_INSTRUCTOR_EXIT:
                    script.setStatus("Opening Gate.");
                    methods.interactWithObject("Gate", "Open");
                    break;
                case Constants.WidgetText.MINE_TIN_ORE:
                    script.setStatus("Mining Tin.");
                    interactWithRocks(Constants.Objects.TIN_ROCK_COLOUR, "Mine");
                    break;
                case Constants.WidgetText.MINE_COPPER_ORE:
                    script.setStatus("Mining Copper.");
                    interactWithRocks(Constants.Objects.COPPER_ROCK_COLOUR, "Mine");
                    break;
            }
        } else {
            methods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.configs.get(406) == 8 || script.configs.get(406) == 9;
    }
}
