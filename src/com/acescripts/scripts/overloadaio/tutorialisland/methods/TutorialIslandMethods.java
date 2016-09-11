package com.acescripts.scripts.overloadaio.tutorialisland.methods;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Constants;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.Arrays;
import java.util.List;

import static org.osbot.rs07.script.MethodProvider.random;
import static org.osbot.rs07.script.MethodProvider.sleep;

/**
 * Created by Transporter on 07/08/2016 at 00:22.
 */

public class TutorialIslandMethods {
    private Script script;

    public TutorialIslandMethods(Script script) {
        this.script = script;
    }

    public void setStatus(String status) {
        OverloadAIO.status = status;
    }

    public void interactWithNpc(String npcName, String interactOption) {
        NPC npc = script.getNpcs().closest(npcName);

        if (npc != null) {
            if(npc.isVisible() && script.myPosition().distance(npc.getPosition()) < 7  && !script.myPlayer().isAnimating() && !script.myPlayer().isMoving()) {
                npc.interact(interactOption);
                new ConditionalSleep(10000) {
                    @Override
                    public boolean condition() {
                        return script.myPlayer().isAnimating() || script.myPlayer().isMoving() || script.myPlayer().isInteracting(npc);
                    }
                }.sleep();
            } else {
                if(script.myPosition().distance(npc.getPosition()) > 5) {
                    script.walking.walk(npc);
                } else {
                    script.getCamera().toPosition(npc.getPosition());
                }
            }
        }
    }

    public void interactWithObject(String objectName, String interactOption) {
        Entity object = script.objects.closest(objectName);

        if(object != null) {
            if(object.isVisible() && !script.myPlayer().isAnimating() && !script.myPlayer().isMoving()) {
                object.interact(interactOption);
                new ConditionalSleep(3500) {
                    @Override
                    public boolean condition() {
                        return script.myPlayer().isAnimating() || script.myPlayer().isMoving();
                    }
                }.sleep();
            } else {
                if(script.myPosition().distance(object.getPosition()) > 5) {
                    script.walking.walk(object);
                } else {
                    script.getCamera().toPosition(object.getPosition());
                }
            }
        }
    }

    public void interactWithObject(String objectName, Position pos, String interactOption) {
        Entity door = script.getObjects().closest(
                obj -> obj.getName().equals(objectName) && obj.getPosition().equals(pos)
        );

        if (door != null) {
            if (door.isVisible() && !script.myPlayer().isAnimating() && !script.myPlayer().isMoving()) {
                door.interact(interactOption);
                new ConditionalSleep(3500) {
                    @Override
                    public boolean condition() {
                        return script.myPlayer().isAnimating() || script.myPlayer().isMoving();
                    }
                }.sleep();
            } else {
                if (script.myPosition().distance(door.getPosition()) > 8) {
                    script.walking.walk(door);
                } else {
                    script.getCamera().toPosition(door.getPosition());
                }
            }
        }
    }

    private boolean canLight(Position p) {
        for (RS2Object current : script.objects.get(p.getX(), p.getY())) {
            if (current != null && current.getPosition().equals(p) && !current.getName().equals("null")) {
                return false;
            }
        }
        return true;
    }

    public boolean walkExact(Script script, Position position) {
        WalkingEvent event = new WalkingEvent(position);
        event.setMinDistanceThreshold(0);
        return script.execute(event).hasFinished();
    }

    public void createFire() throws InterruptedException {
        Area firemakingArea = new Area(3101, 3098, 3104, 3094);

        if(canLight(script.myPosition())) {
            useInventoryItems("Logs", "Tinderbox");
        } else {
            firemakingArea.getPositions().stream().filter(fireMakePos -> !canLight(script.myPosition()) && canLight(fireMakePos) && !script.myPosition().equals(fireMakePos)).forEach(fireMakePos -> {
                walkExact(script, fireMakePos);
                new ConditionalSleep(3500) {
                    @Override
                    public boolean condition() {
                        return script.myPosition().equals(fireMakePos);
                    }
                }.sleep();
            });
        }
    }

    public void cookShrimp() throws InterruptedException {
        Entity fire = script.objects.closest("Fire");

        if (fire != null) {
            if (script.getInventory().isItemSelected()) {
                if (fire.isVisible() && !script.myPlayer().isAnimating() && !script.myPlayer().isMoving()) {
                    fire.interact("Use");
                    sleep(random(1250, 2500));
                } else {
                    script.camera.toPosition(fire.getPosition());
                }
            } else {
                script.getInventory().getItem("Raw shrimps").interact("Use");
                new ConditionalSleep(2000) {
                    @Override
                    public boolean condition() {
                        return script.getInventory().isItemSelected();
                    }
                }.sleep();
            }
        } else {
            if (script.inventory.contains("Logs")) {
                createFire();
            } else {
                interactWithObject("Tree", "Chop down");
            }
        }
    }

    public void useItemOnObject(String objectName, String itemName) throws InterruptedException {
        Entity object = script.objects.closest(objectName);

        if (object != null) {
            script.camera.toPosition(object.getPosition());

            if (script.getInventory().isItemSelected()) {
                if (object.isVisible()) {
                    object.interact("Use");
                    sleep(random(4000, 5000));
                } else {
                    script.camera.toPosition(object.getPosition());
                }
            } else {
                script.getInventory().getItem(itemName).interact("Use");
                new ConditionalSleep(2000) {
                    @Override
                    public boolean condition() {
                        return script.getInventory().isItemSelected();
                    }
                }.sleep();
            }
        }
    }

    public void useInventoryItems(String item1, String item2) {
        if(script.getInventory().isItemSelected()) {
            script.getInventory().getItem(item1).interact("Use");
            new ConditionalSleep(2000) {
                @Override
                public boolean condition() {
                    return script.myPlayer().isAnimating() && script.myPlayer().isMoving();
                }
            }.sleep();
        } else {
            script.getInventory().getItem(item2).interact("Use");
            new ConditionalSleep(2000) {
                @Override
                public boolean condition() {
                    return script.getInventory().isItemSelected();
                }
            }.sleep();
        }
    }

    public void clickContinue() throws InterruptedException {
        setStatus("Clicking Continue.");

        RS2Widget widget = script.getWidgets().getWidgetContainingText("Click here to continue");
        RS2Widget widget2 = script.getWidgets().getWidgetContainingText(Constants.Widgets.CLICK_CONTINUE, "Click to continue");

        if(widget != null && widget.isVisible()) {
            widget.interact();
            sleep(random(250, 750));
        } else if(widget2 != null && widget2.isVisible()) {
            widget2.interact();
            sleep(random(250, 750));
        }
    }
}
