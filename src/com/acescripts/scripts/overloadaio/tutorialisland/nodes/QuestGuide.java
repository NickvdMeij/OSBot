package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Constants;
import com.acescripts.scripts.overloadaio.framework.Node;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import java.util.Random;

/**
 * Created by Transporter on 07/08/2016 at 01:16.
 */

public class QuestGuide extends Node {
    public QuestGuide(OverloadAIO script) {
        super(script);
    }

    private void performRandomEmote() {
        Random rand = new Random();
        int id = rand.nextInt(22);

        if(script.getTabs().getOpen().equals(Tab.EMOTES)) {
            if(script.widgets.get(216, 1, id) != null) {
                script.widgets.get(216, 1, id).interact();
            }
        } else {
            script.getTabs().open(Tab.EMOTES);
        }
    }

    private void runToNextGuide() {
        Entity door = script.getObjects().closest(
                obj -> obj.getName().equals("Door") && obj.getPosition().equals(Constants.Objects.QUEST_GUIDE_DOOR_POSITION)
        );

        if(door != null && door.isVisible()) {
            methods.interactWithObject("Door", Constants.Objects.QUEST_GUIDE_DOOR_POSITION, "Open");
        } else {
            script.walking.walk(new Position(3086, 3126, 0));
        }
    }

    @Override
    public void execute() throws InterruptedException {
        RS2Widget widget = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT,
                Constants.WidgetText.PERFORM_EMOTE,
                Constants.WidgetText.TALK_TO_QUEST_GUIDE_JOURNAL,
                Constants.WidgetText.OPENING_SETTINGS,
                Constants.WidgetText.TURN_ON_RUN,
                Constants.WidgetText.TALK_TO_QUEST_GUIDE_NORMAL,
                Constants.WidgetText.OPENING_QUESTS,
                Constants.WidgetText.CLIMB_DOWN_LADDER
        );

        RS2Widget widgetTwo = script.getWidgets().getWidgetContainingText(
                Constants.Widgets.CHAT_BOX_ROOT_2,
                Constants.WidgetText.PERFORM_EMOTE,
                Constants.WidgetText.RUN_TO_GUIDE
        );

        if(widget != null && widget.isVisible()) {
            switch(widget.getMessage()) {
                case Constants.WidgetText.PERFORM_EMOTE:
                    script.setStatus("Opening Emotes.");
                    script.getTabs().open(Tab.EMOTES);
                    break;
                case Constants.WidgetText.TALK_TO_QUEST_GUIDE_JOURNAL:
                case Constants.WidgetText.TALK_TO_QUEST_GUIDE_NORMAL:
                    script.setStatus("Talking to Quest Guide.");
                    methods.interactWithNpc("Quest Guide", "Talk-to");
                    break;
                case Constants.WidgetText.OPENING_SETTINGS:
                    script.setStatus("Opening Settings.");
                    script.getTabs().open(Tab.SETTINGS);
                    break;
                case Constants.WidgetText.TURN_ON_RUN:
                    script.setStatus("Turning on Run.");
                    script.widgets.get(261, 65).interact();
                    break;
                case Constants.WidgetText.OPENING_QUESTS:
                    script.setStatus("Opening Quests.");
                    script.getTabs().open(Tab.QUEST);
                    break;
                case Constants.WidgetText.CLIMB_DOWN_LADDER:
                    script.setStatus("Climbing Down Ladder.");
                    methods.interactWithObject("Ladder", "Climb-down");
                    break;
            }
        } else if(widgetTwo != null && widgetTwo.isVisible()) {
            switch(widgetTwo.getMessage()) {
                case Constants.WidgetText.PERFORM_EMOTE:
                    script.setStatus("Performing Random Emote.");
                    performRandomEmote();
                    break;
                case Constants.WidgetText.RUN_TO_GUIDE:
                    script.setStatus("Running To Next Guide.");
                    runToNextGuide();
                    break;
            }
        } else {
            methods.clickContinue();
        }
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.configs.get(406) == 6 || script.configs.get(406) == 7;
    }
}
