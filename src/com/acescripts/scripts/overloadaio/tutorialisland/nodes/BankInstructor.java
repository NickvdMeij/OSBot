package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import org.osbot.rs07.api.ui.RS2Widget;

/**
 * Created by Transporter on 07/08/2016 at 02:08.
 */

public class BankInstructor extends Node {
    public BankInstructor(OverloadAIO script) {
        super(script);
    }

    private void openPollBooth() {
        if(script.bank.isOpen()) {
            script.bank.close();
        } else {
            methods.interactWithObject("Poll booth", "Use");
        }
    }

    private void openNextDoor() {
        RS2Widget widget = script.getWidgets().getWidgetContainingText("Old School Content Poll");

        if(widget != null && widget.isVisible()) {
            script.getWidgets().closeOpenInterface();
        } else {
            methods.interactWithObject("Door", Constants.Objects.BANK_INSTRUCTOR_DOOR_POSITION, "Open");
        }
    }

    @Override
    public void execute() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT,
                Constants.WidgetText.OPEN_BANK,
                Constants.WidgetText.OPEN_POLL_BOOTH,
                Constants.WidgetText.OPEN_BANK_INSTRUCTOR_EXIT
        );

        if(widget != null && widget.isVisible()) {
            switch (widget.getMessage()) {
                case Constants.WidgetText.OPEN_BANK:
                    script.setStatus("Opening Bank.");
                    methods.interactWithObject("Bank booth", "Use");
                    break;
                case Constants.WidgetText.OPEN_POLL_BOOTH:
                    script.setStatus("Opening Poll Box.");
                    openPollBooth();
                    break;
                case Constants.WidgetText.OPEN_BANK_INSTRUCTOR_EXIT:
                    script.setStatus("Opening Door.");
                    openNextDoor();
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
        return script.configs.get(406) == 14;
    }
}
