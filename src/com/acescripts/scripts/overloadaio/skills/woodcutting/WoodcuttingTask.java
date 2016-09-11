package com.acescripts.scripts.overloadaio.skills.woodcutting;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Task;
import com.acescripts.scripts.overloadaio.framework.Bank;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import static org.osbot.rs07.script.MethodProvider.random;
import static org.osbot.rs07.script.MethodProvider.sleep;

/**
 * Created by Transporter on 26/07/2016 at 01:23.
 */

public class WoodcuttingTask implements Task {
    private Script script;
    private String toChop;
    private int stopLevel;
    private Area choppingArea;
    private boolean shouldBank = false;

    public WoodcuttingTask(Script script, String toChop, int stopLevel, Area choppingArea, boolean shouldBank) {
        this.script = script;
        this.toChop = toChop;
        this.stopLevel = stopLevel;
        this.choppingArea = choppingArea;
        this.shouldBank = shouldBank;
    }

    private enum State {
        BANK, CHOP, DROP, WALK_TO_TREES, WAIT
    }

    private State getState() {
        Entity tree = script.objects.closest(toChop);

        if(script.inventory.isFull() && shouldBank || !script.inventory.contains(1351)) {
            return State.BANK;
        }

        if(!script.inventory.isFull() && tree != null && !script.myPlayer().isAnimating() && choppingArea.contains(script.myPlayer())) {
            return State.CHOP;
        }

        if(script.inventory.isFull() && !shouldBank) {
            return State.DROP;
        }

        if(!script.inventory.isFull() && !choppingArea.contains(script.myPlayer())) {
            return State.WALK_TO_TREES;
        }
        return State.WAIT;
    }

    public void proceed() throws InterruptedException {
        switch (getState()) {
            case BANK:
                OverloadAIO.status = "Banking";

                Area currentBank = Bank.closestTo(script.myPlayer());

                if(currentBank.contains(script.myPlayer())) {
                    if(script.getBank().isOpen()) {
                        if(script.inventory.contains(1351)) {
                            script.getBank().depositAllExcept(1351);
                            script.getBank().close();
                        } else {
                            if(!script.getInventory().isEmptyExcept(1351)) {
                                script.getBank().depositAll();
                            }
                            script.getBank().withdraw(1351, 1);
                        }
                    } else {
                        script.getBank().open();
                    }
                    sleep(random(1000, 1400));
                } else {
                    script.walking.webWalk(currentBank);
                }
                break;
            case CHOP:
                RS2Object tree = script.getObjects().closest(new Filter<RS2Object>(){
                    @Override
                    public boolean match(RS2Object object) {
                        return object != null && object.getName().equals(toChop) && choppingArea.contains(object.getPosition());
                    }
                });

                OverloadAIO.status = "Chop";

                if (tree != null && tree.isVisible()) {
                    tree.interact("Chop down");
                    new ConditionalSleep(2000) {
                        @Override
                        public boolean condition() {
                            return script.myPlayer().isAnimating() && script.myPlayer().isMoving();
                        }
                    }.sleep();
                } else {
                    if(tree != null) {
                        if (script.myPosition().distance(tree.getPosition()) > 3) {
                            Position spec = new Position(tree.getPosition().getX(), tree.getPosition().getY() - 2, tree.getPosition().getZ());
                            if(!WoodcuttingLocations.EDGEVILLE.getArea().contains(tree)) {
                                script.walking.walk(spec);
                            }
                        }
                        script.camera.toPosition(tree.getPosition());
                    }
                }
                break;
            case DROP:
                OverloadAIO.status = "Drop";
                script.inventory.dropAll("Oak logs");
                break;
            case WALK_TO_TREES:
                OverloadAIO.status = "Walking To Trees";
                script.walking.webWalk(choppingArea);
                break;
            case WAIT:
                OverloadAIO.status = "Waiting...";
                break;
        }
    }

    public boolean isFinished() {
        return script.skills.getDynamic(Skill.WOODCUTTING) >= stopLevel;
    }
}
