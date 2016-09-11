package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import com.acescripts.scripts.overloadaio.tutorialisland.methods.TutorialIslandMethods;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

/**
 * Created by Transporter on 07/08/2016 at 02:06.
 */

public class FinancialAdvisor extends Node {

    private TutorialIslandMethods tutorialIslandMethods = new TutorialIslandMethods(script);

    public FinancialAdvisor(Script script) {
        super(script);
    }

    @Override
    public void execute() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT,
                Constants.WidgetText.TALK_TO_FINANCIAL_ADVISOR_START,
                Constants.WidgetText.OPEN_FINANCIAL_ADVISOR_EXIT
        );

        if(widget != null && widget.isVisible()) {
            switch (widget.getMessage()) {
                case Constants.WidgetText.TALK_TO_FINANCIAL_ADVISOR_START:
                    tutorialIslandMethods.setStatus("Talking to Financial Advisor Interface.");
                    tutorialIslandMethods.interactWithNpc("Financial Advisor", "Talk-to");
                    break;
                case Constants.WidgetText.OPEN_FINANCIAL_ADVISOR_EXIT:
                    tutorialIslandMethods.setStatus("Opening Door.");
                    tutorialIslandMethods.interactWithObject("Door", Constants.Objects.FINANCIAL_ADVISOR_DOOR_POSITION, "Open");
                    break;
            }
        } else {
            tutorialIslandMethods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.configs.get(406) == 15;
    }
}
