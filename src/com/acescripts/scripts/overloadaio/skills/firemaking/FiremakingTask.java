package com.acescripts.scripts.overloadaio.skills.firemaking;

/**
 * Created by Transporter on 26/07/2016 at 16:47.
 */

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Bank;
import com.acescripts.scripts.overloadaio.framework.Task;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.Condition;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.ArrayList;

import static org.osbot.rs07.script.MethodProvider.random;
import static org.osbot.rs07.script.MethodProvider.sleep;

/**
 * Created by Transporter on 26/07/2016 at 01:23.
 */

public class FiremakingTask implements Task {
    private OverloadAIO script;
    private String logType;
    private int stopLevel;
    private Area firemakingArea;
    private int tinderBoxId = 590;
    private ArrayList<FirePath> paths = new ArrayList<>();

    private Position START_TILE_ONE = new Position(3105, 3250, 0);

    public FiremakingTask(OverloadAIO script, String logType, int stopLevel, Area firemakingArea, ArrayList<FirePath> paths) {
        this.script = script;
        this.logType = logType;
        this.stopLevel = stopLevel;
        this.firemakingArea = firemakingArea;
        this.paths = paths;
    }

    private enum State {
        BANK, FIRE_MAKE, WALK_TO_FM_LOCATION, WAIT
    }

    private State getState() {
        if(!script.inventory.contains(logType) && script.myPlayer().getAnimation() != 733 || !script.inventory.contains(tinderBoxId)) {
            return State.BANK;
        }

        if(script.inventory.contains(logType) && firemakingArea.contains(script.myPlayer())) {
            return State.FIRE_MAKE;
        }

        if(!firemakingArea.contains(script.myPlayer())) {
            return State.WALK_TO_FM_LOCATION;
        }

        return State.WAIT;
    }

    private void lightFire() {
        if(!script.myPlayer().isAnimating() && !script.myPlayer().isMoving()) {
            if(script.getInventory().isItemSelected()) {
                script.getInventory().getItem(logType).interact();
                new ConditionalSleep(1000) {
                    @Override
                    public boolean condition() {
                        return script.myPlayer().isAnimating() && script.myPlayer().isMoving();
                    }
                }.sleep();
            } else {
                script.getInventory().getItem("Tinderbox").interact("Use");
                new ConditionalSleep(1000) {
                    @Override
                    public boolean condition() {
                        return script.getInventory().isItemSelected();
                    }
                }.sleep();
            }
        } else {
            if(script.getInventory().isItemSelected()) {
                script.getInventory().getItem(logType).hover();
                new ConditionalSleep(1000) {
                    @Override
                    public boolean condition() {
                        return script.myPlayer().isAnimating() && script.myPlayer().isMoving();
                    }
                }.sleep();
            } else {
                script.getInventory().getItem("Tinderbox").interact("Use");
                new ConditionalSleep(1000) {
                    @Override
                    public boolean condition() {
                        return script.getInventory().isItemSelected();
                    }
                }.sleep();
            }
        }
    }

    private FirePath getBest() {
        FirePath currentBest = paths.get(0);
        for(FirePath path: paths) {
            if(path.getEmptyTileCount() > currentBest.getEmptyTileCount()) {
                currentBest = path;
            }
        }
        return currentBest;
    }

    private boolean canLight(Position p) {
        for (RS2Object current : script.objects.get(p.getX(), p.getY())) {
            if (current != null && current.getPosition().equals(p) && !current.getName().equals("null")) {
                return false;
            }
        }
        return true;
    }

    private boolean walkExact(Script script, Position position) {
        WalkingEvent event = new WalkingEvent(position);
        event.setMinDistanceThreshold(0);
        return script.execute(event).hasFinished();
    }

    public void proceed() throws InterruptedException {
        switch (getState()) {
            case BANK:
                script.setStatus("Banking");

                Area currentBank = Bank.closestTo(script.myPlayer());

                if(currentBank.contains(script.myPlayer())) {
                    if(script.getBank().isOpen()) {
                        if(script.inventory.contains(tinderBoxId)) {
                            script.getBank().depositAllExcept(tinderBoxId);
                            script.getBank().withdrawAll(logType);
                            script.getBank().close();
                        } else {
                            if(!script.getInventory().isEmptyExcept(tinderBoxId)) {
                                script.getBank().depositAll();
                            }
                            script.getBank().withdraw(tinderBoxId, 1);
                        }
                        sleep(random(1000, 1400));
                    } else {
                        script.getBank().open();
                    }
                } else {
                    script.walking.webWalk(currentBank);
                }
                break;
            case FIRE_MAKE:
                script.setStatus("Making Fire");

                if (!canLight(script.myPosition())) {
                    if(script.getInventory().isItemSelected()) {
                      script.tabs.open(Tab.INVENTORY);
                    } else {
                        if(script.map.canReach(new Position(script.myPosition().getX() - 1, script.myPosition().getY(), script.myPosition().getZ()))) {
                            walkExact(script, new Position(script.myPosition().getX() - 1, script.myPosition().getY(), script.myPosition().getZ()));
                        } else {
                            walkExact(script, getBest().getTiles().get(getBest().getTiles().size() - 1));
                        }
                    }
                } else {
                    lightFire();
                }
                break;
            case WALK_TO_FM_LOCATION:
                script.setStatus("Walking to Fire");

                if(script.myPosition().distance(START_TILE_ONE) > 20) {
                    WebWalkEvent event = new WebWalkEvent(START_TILE_ONE);
                    event.setBreakCondition(new Condition() { //if true, break the walker.
                        @Override
                        public boolean evaluate() {
                            return script.myPosition().distance(START_TILE_ONE) <= 20; //area.contains(myPos) || pos.distance() <= threshold
                        }
                    });
                    script.execute(event);
                } else {
                    walkExact(script, getBest().getTiles().get(getBest().getTiles().size() - 1));
                }
                break;
            case WAIT:
                script.setStatus("Waiting...");
                break;
        }
    }

    public boolean isFinished() {
        return script.skills.getDynamic(Skill.FIREMAKING) >= stopLevel || script.getBank().isOpen() && !script.getBank().contains(logType);
    }
}