package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

/**
 * Created by Transporter on 07/08/2016 at 02:14.
 */

public class BrotherBrace extends Node {
    public BrotherBrace(OverloadAIO script) {
        super(script);
    }

    private void talkToNpc() {
        NPC npc = script.getNpcs().closest("Brother Brace");

        if(npc != null && script.map.canReach(npc)) {
            methods.interactWithNpc("Brother Brace", "Talk-to");
        } else {
            if(script.map.canReach(new Position(3128, 3107, 0))) {
                script.walking.walk(new Position(3128, 3107, 0));
            } else {
                Entity object = script.objects.closest("Large door");

                if(object != null && script.myPosition().distance(object) < 5) {
                    methods.interactWithObject("Large door", "Open");
                } else {
                    script.walking.walk(new Position(3129, 3107, 0));
                }
            }
        }
    }

    @Override
    public void execute() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT,
                Constants.WidgetText.TALK_TO_BROTHER_BRACE_START,
                Constants.WidgetText.OPENING_PRAYER,
                Constants.WidgetText.TALK_TO_BROTHER_BRACE_PRAYER,
                Constants.WidgetText.OPENING_FRIENDS,
                Constants.WidgetText.OPENING_IGNORES,
                Constants.WidgetText.TALK_TO_BROTHER_BRACE_IGNORE,
                Constants.WidgetText.OPEN_BROTHER_BRACE_EXIT
        );

        if(widget != null && widget.isVisible()) {
            switch (widget.getMessage()) {
                case Constants.WidgetText.TALK_TO_BROTHER_BRACE_START:
                    script.setStatus("Talking to Brother Brace.");
                    talkToNpc();
                    break;
                case Constants.WidgetText.OPENING_PRAYER:
                    script.setStatus("Opening Prayer Tab.");
                    script.getTabs().open(Tab.PRAYER);
                    break;
                case Constants.WidgetText.TALK_TO_BROTHER_BRACE_PRAYER:
                case Constants.WidgetText.TALK_TO_BROTHER_BRACE_IGNORE:
                    script.setStatus("Talking to Brother Brace.");
                    methods.interactWithNpc("Brother Brace", "Talk-to");
                    break;
                case Constants.WidgetText.OPENING_FRIENDS:
                    script.setStatus("Opening Friends Tab.");
                    script.getTabs().open(Tab.FRIENDS);
                    break;
                case Constants.WidgetText.OPENING_IGNORES:
                    script.setStatus("Opening Ignore Tab.");
                    script.getTabs().open(Tab.IGNORES);
                    break;
                case Constants.WidgetText.OPEN_BROTHER_BRACE_EXIT:
                    script.setStatus("Opening Door.");
                    methods.interactWithObject("Door", Constants.Objects.BROTHER_BRACE_DOOR_POSITION, "Open");
                    break;
            }
        } else {
            methods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.configs.get(406) == 16 || script.configs.get(406) == 17;
    }
}
