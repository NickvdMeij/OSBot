package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import com.acescripts.scripts.overloadaio.tutorialisland.methods.TutorialIslandMethods;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;

/**
 * Created by Transporter on 07/08/2016 at 00:41.
 */

public class SurvivalExpert extends Node {

    private TutorialIslandMethods tutorialIslandMethods = new TutorialIslandMethods(script);

    public SurvivalExpert(Script script) {
        super(script);
    }

    private void makeFire() throws InterruptedException {
        tutorialIslandMethods.setStatus("Making a Fire.");

        if(script.inventory.contains("Logs")) {
            tutorialIslandMethods.createFire();
        } else {
            tutorialIslandMethods.interactWithObject("Tree", "Chop down");
        }
    }

    private void cookShrimp() throws InterruptedException {
        tutorialIslandMethods.setStatus("Cooking the Shrimp.");

        if(script.getTabs().getOpen().equals(Tab.INVENTORY)) {
            if(script.inventory.getItem("Raw shrimps") != null && script.inventory.getAmount("Raw shrimps") < 2 && script.inventory.getItem("Burnt shrimp") == null) {
                tutorialIslandMethods.interactWithNpc("Fishing spot", "Net");
            } else {
                tutorialIslandMethods.cookShrimp();
            }
        } else {
            script.getTabs().open(Tab.INVENTORY);
        }
    }

    @Override
    public void execute() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT,
                Constants.WidgetText.MOVING_AROUND,
                Constants.WidgetText.OPENING_INVENTORY,
                Constants.WidgetText.CHOPPING_TREE,
                Constants.WidgetText.MAKING_FIRE,
                Constants.WidgetText.CATCHING_SHRIMP,
                Constants.WidgetText.COOKING_SHRIMP,
                Constants.WidgetText.BURNING_SHRIMP,
                Constants.WidgetText.OPENING_SURVIVAL_GATE,
                Constants.WidgetText.OPENING_SKILLS
        );

        RS2Widget widgetTwo = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT_2,
                Constants.WidgetText.CHECKING_SKILL_STATS
        );

        if(widget != null && widget.isVisible()) {
            switch(widget.getMessage()) {
                case Constants.WidgetText.MOVING_AROUND:
                    tutorialIslandMethods.setStatus("Talking to Survival Expert.");
                    tutorialIslandMethods.interactWithNpc("Survival Expert", "Talk-to");
                    break;
                case Constants.WidgetText.OPENING_INVENTORY:
                    tutorialIslandMethods.setStatus("Opening Inventory");
                    script.getTabs().open(Tab.INVENTORY);
                    break;
                case Constants.WidgetText.CHOPPING_TREE:
                    tutorialIslandMethods.setStatus("Chopping Down a Tree.");
                    tutorialIslandMethods.interactWithObject("Tree", "Chop down");
                    break;
                case Constants.WidgetText.MAKING_FIRE:
                    makeFire();
                    break;
                case Constants.WidgetText.CATCHING_SHRIMP:
                    tutorialIslandMethods.setStatus("Catching some Shrimp.");
                    tutorialIslandMethods.interactWithNpc("Fishing spot", "Net");
                    break;
                case Constants.WidgetText.COOKING_SHRIMP:
                case Constants.WidgetText.BURNING_SHRIMP:
                    cookShrimp();
                    break;
                case Constants.WidgetText.OPENING_SURVIVAL_GATE:
                    tutorialIslandMethods.setStatus("Opening Gate.");
                    tutorialIslandMethods.interactWithObject("Gate", "Open");
                    break;
                case Constants.WidgetText.OPENING_SKILLS:
                    tutorialIslandMethods.setStatus("Opening Skills.");
                    script.getTabs().open(Tab.SKILLS);
                    break;
            }
        } else if(widgetTwo != null && widgetTwo.isVisible()) {
            switch(widgetTwo.getMessage()) {
                case Constants.WidgetText.CHECKING_SKILL_STATS:
                    tutorialIslandMethods.setStatus("Talking to Survival Expert.");
                    tutorialIslandMethods.interactWithNpc("Survival Expert", "Talk-to");
                    break;
            }
        } else {
            tutorialIslandMethods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.configs.get(406) == 2 || script.configs.get(406) == 3;
    }
}
