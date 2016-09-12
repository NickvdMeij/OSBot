package com.acescripts.scripts.overloadaio.skills.woodcutting;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.*;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.LinkedList;
import java.util.List;

import static org.osbot.rs07.script.MethodProvider.random;
import static org.osbot.rs07.script.MethodProvider.sleep;

/**
 * Created by Transporter on 26/07/2016 at 01:23.
 */

public class WoodcuttingTask implements Task {
    private OverloadAIO script;
    private String toChop;
    private int stopLevel;
    private Area choppingArea;
    private boolean shouldBank = false;

    public WoodcuttingTask(OverloadAIO script, String toChop, int stopLevel, Area choppingArea, boolean shouldBank) {
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

        if(script.inventory.isFull() && shouldBank || (!script.getEquipment().isWieldingWeaponThatContains("axe") && !script.getInventory().contains(getAxeNames()))) {
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

    private String[] getAxeNames() {
        Equipment[] states = Equipment.values();
        String[] names = new String[states.length];

        for (int i = 0; i < states.length; i++) {
            names[i] = states[i].getAxe();
        }

        return names;
    }

    private Axe[] getCurrentAxe() {
        if (script.getSkills().getStatic(Skill.WOODCUTTING) == 1) {
            return new Axe[] {
                    Axe.IRON, Axe.BRONZE };
        } else {
            List<Axe> axes = new LinkedList<>();
            for (int i = Axe.values().length - 1; i >= 0; i--) {
                if(script.getBank().contains(Axe.values()[i].getName())) {
                    axes.add(Axe.values()[i]);
                }
            }
            return axes.toArray(new Axe[axes.size()]);
        }
    }

    private void withdrawBestAxe() throws InterruptedException {
        Axe[] currentAxe = getCurrentAxe();

        if(currentAxe != null && currentAxe.length > 0) {
            if(script.getBank().contains(currentAxe[0].getName())) {
                script.getBank().withdraw(currentAxe[0].getName(), 1);
                new ConditionalSleep(2000) {
                    @Override
                    public boolean condition() {
                        return script.getInventory().contains(currentAxe[0].getName());
                    }
                }.sleep();
            }
        }
    }

    public void proceed() throws InterruptedException {
        switch (getState()) {
            case BANK:
                script.setStatus("Banking");

                Area currentBank = Bank.closestTo(script.myPlayer());

                if(currentBank.contains(script.myPlayer())) {
                    if(script.getBank().isOpen()) {
                        if(script.getEquipment().isWieldingWeaponThatContains(getAxeNames()) || script.getInventory().contains(getAxeNames())) {
                            script.getBank().depositAllExcept(getAxeNames());
                            script.getBank().close();
                        } else {
                            withdrawBestAxe();
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
                script.setStatus("Chop");

                RS2Object tree = script.getObjects().closest(new Filter<RS2Object>(){
                    @Override
                    public boolean match(RS2Object object) {
                        return object != null && object.getName().equals(toChop) && choppingArea.contains(object.getPosition());
                    }
                });

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
                script.setStatus("Drop");

                if(toChop.equals("Tree")) {
                    script.inventory.dropAll(Tree.NORMAL.getLogName());
                } else {
                    script.inventory.dropAll(Tree.valueOf(toChop).getLogName());
                }
                break;
            case WALK_TO_TREES:
                script.setStatus("Walking to Trees");
                script.walking.webWalk(choppingArea);
                break;
            case WAIT:
                script.setStatus("Waiting...");
                break;
        }
    }

    public boolean isFinished() {
        return script.skills.getDynamic(Skill.WOODCUTTING) >= stopLevel;
    }
}
