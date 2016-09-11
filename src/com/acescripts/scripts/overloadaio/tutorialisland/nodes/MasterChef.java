package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import com.acescripts.scripts.overloadaio.tutorialisland.methods.TutorialIslandMethods;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

/**
 * Created by Transporter on 07/08/2016 at 01:05.
 */

public class MasterChef extends Node {

    private TutorialIslandMethods tutorialIslandMethods = new TutorialIslandMethods(script);

    public MasterChef(Script script) {
        super(script);
    }

    @Override
    public void execute() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT,
                Constants.WidgetText.MAKING_DOUGH,
                Constants.WidgetText.COOKING_DOUGH,
                Constants.WidgetText.OPEN_MASTER_CHEF_ENTRANCE,
                Constants.WidgetText.TALK_TO_MASTER_CHEF
        );

        RS2Widget widgetTwo = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT_2,
                Constants.WidgetText.OPENING_MUSIC,
                Constants.WidgetText.OPEN_MASTER_CHEF_EXIT
        );

        if(widget != null && widget.isVisible()) {
            switch(widget.getMessage()) {
                case Constants.WidgetText.MAKING_DOUGH:
                    tutorialIslandMethods.setStatus("Making Dough.");
                    tutorialIslandMethods.useInventoryItems("Bucket of water", "Pot of flour");
                    break;
                case Constants.WidgetText.COOKING_DOUGH:
                    tutorialIslandMethods.setStatus("Cooking Dough.");
                    tutorialIslandMethods.useItemOnObject("Range", "Bread dough");
                    break;
                case Constants.WidgetText.OPEN_MASTER_CHEF_ENTRANCE:
                    tutorialIslandMethods.setStatus("Opening Door.");
                    tutorialIslandMethods.interactWithObject("Door", "Open");
                    break;
                case Constants.WidgetText.TALK_TO_MASTER_CHEF:
                    tutorialIslandMethods.setStatus("Talking to Master Chef.");
                    tutorialIslandMethods.interactWithNpc("Master Chef", "Talk-to");
                    break;
            }
        } else if(widgetTwo != null && widgetTwo.isVisible()) {
            switch(widgetTwo.getMessage()) {
                case Constants.WidgetText.OPENING_MUSIC:
                    tutorialIslandMethods.setStatus("Opening Music.");
                    script.getTabs().open(Tab.MUSIC);
                    break;
                case Constants.WidgetText.OPEN_MASTER_CHEF_EXIT:
                    tutorialIslandMethods.setStatus("Opening Door.");
                    tutorialIslandMethods.interactWithObject("Door", Constants.Objects.MASTER_CHEF_DOOR_POSITION, "Open");
                    break;
            }
        } else {
            tutorialIslandMethods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.configs.get(406) == 4 || script.configs.get(406) == 5;
    }
}
