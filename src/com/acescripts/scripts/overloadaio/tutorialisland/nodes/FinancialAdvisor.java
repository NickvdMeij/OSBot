package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import org.osbot.rs07.api.ui.RS2Widget;

/**
 * Created by Transporter on 07/08/2016 at 02:06.
 */

public class FinancialAdvisor extends Node {
    public FinancialAdvisor(OverloadAIO script) {
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
                    script.setStatus("Talking to Financial Advisor Interface.");
                    methods.interactWithNpc("Financial Advisor", "Talk-to");
                    break;
                case Constants.WidgetText.OPEN_FINANCIAL_ADVISOR_EXIT:
                    script.setStatus("Opening Door.");
                    methods.interactWithObject("Door", Constants.Objects.FINANCIAL_ADVISOR_DOOR_POSITION, "Open");
                    break;
            }
        } else {
            methods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.configs.get(406) == 15;
    }
}
