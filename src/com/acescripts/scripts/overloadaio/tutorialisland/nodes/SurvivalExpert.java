package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

/**
 * Created by Transporter on 07/08/2016 at 00:41.
 */

public class SurvivalExpert extends Node {
    public SurvivalExpert(OverloadAIO script) {
        super(script);
    }

    private void makeFire() throws InterruptedException {
        script.setStatus("Making a Fire.");

        if(script.inventory.contains("Logs")) {
            methods.createFire();
        } else {
            methods.interactWithObject("Tree", "Chop down");
        }
    }

    private void cookShrimp() throws InterruptedException {
        script.setStatus("Cooking the Shrimp.");

        if(script.getTabs().getOpen().equals(Tab.INVENTORY)) {
            if(script.inventory.getItem("Raw shrimps") != null && script.inventory.getAmount("Raw shrimps") < 2 && script.inventory.getItem("Burnt shrimp") == null) {
                methods.interactWithNpc("Fishing spot", "Net");
            } else {
                methods.cookShrimp();
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
                    script.setStatus("Talking to Survival Expert.");
                    methods.interactWithNpc("Survival Expert", "Talk-to");
                    break;
                case Constants.WidgetText.OPENING_INVENTORY:
                    script.setStatus("Opening Inventory");
                    script.getTabs().open(Tab.INVENTORY);
                    break;
                case Constants.WidgetText.CHOPPING_TREE:
                    script.setStatus("Chopping Down a Tree.");
                    methods.interactWithObject("Tree", "Chop down");
                    break;
                case Constants.WidgetText.MAKING_FIRE:
                    makeFire();
                    break;
                case Constants.WidgetText.CATCHING_SHRIMP:
                    script.setStatus("Catching some Shrimp.");
                    methods.interactWithNpc("Fishing spot", "Net");
                    break;
                case Constants.WidgetText.COOKING_SHRIMP:
                case Constants.WidgetText.BURNING_SHRIMP:
                    cookShrimp();
                    break;
                case Constants.WidgetText.OPENING_SURVIVAL_GATE:
                    script.setStatus("Opening Gate.");
                    methods.interactWithObject("Gate", "Open");
                    break;
                case Constants.WidgetText.OPENING_SKILLS:
                    script.setStatus("Opening Skills.");
                    script.getTabs().open(Tab.SKILLS);
                    break;
            }
        } else if(widgetTwo != null && widgetTwo.isVisible()) {
            switch(widgetTwo.getMessage()) {
                case Constants.WidgetText.CHECKING_SKILL_STATS:
                    script.setStatus("Talking to Survival Expert.");
                    methods.interactWithNpc("Survival Expert", "Talk-to");
                    break;
            }
        } else {
            methods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.configs.get(406) == 2 || script.configs.get(406) == 3;
    }
}
