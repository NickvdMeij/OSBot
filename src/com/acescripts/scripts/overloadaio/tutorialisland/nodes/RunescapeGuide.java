package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import com.acescripts.scripts.overloadaio.tutorialisland.methods.TutorialIslandMethods;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;

/**
 * Created by Transporter on 06/08/2016 at 23:53.
 */

public class RunescapeGuide extends Node {
    private TutorialIslandMethods tutorialIslandMethods = new TutorialIslandMethods(script);

    public RunescapeGuide(Script script) {
        super(script);
    }

    @Override
    public void execute() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT,
                Constants.WidgetText.START_TUTORIAL,
                Constants.WidgetText.PLAYER_CONTROLS
        );

        RS2Widget widgetTwo = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT_2,
                Constants.WidgetText.PLAYER_CONTROLS,
                Constants.WidgetText.OPEN_FIST_DOOR
        );

        if(widget != null && widget.isVisible()) {
            switch(widget.getMessage()) {
                case Constants.WidgetText.START_TUTORIAL:
                    tutorialIslandMethods.setStatus("Talking to Runescape Guide.");
                    tutorialIslandMethods.interactWithNpc("RuneScape Guide", "Talk-to");
                    break;
                case Constants.WidgetText.PLAYER_CONTROLS:
                    tutorialIslandMethods.setStatus("Opening Settings.");
                    script.getTabs().open(Tab.SETTINGS);
                    break;
            }
        } else if(widgetTwo != null && widgetTwo.isVisible()) {
            switch(widgetTwo.getMessage()) {
                case Constants.WidgetText.PLAYER_CONTROLS:
                    tutorialIslandMethods.setStatus("Talking to Runescape Guide.");
                    tutorialIslandMethods.interactWithNpc("RuneScape Guide", "Talk-to");
                    break;
                case Constants.WidgetText.OPEN_FIST_DOOR:
                    tutorialIslandMethods.setStatus("Opening Door.");
                    tutorialIslandMethods.interactWithObject("Door", "Open");
                    break;
            }
        } else {
            tutorialIslandMethods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.widgets.get(269, 97) == null && script.configs.get(406) == 0;
    }
}
