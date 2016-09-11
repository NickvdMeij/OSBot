package com.acescripts.scripts.overloadaio.framework;

import org.osbot.rs07.script.Script;

/**
 * Created by Transporter on 06/08/2016 at 23:49.
 */

public abstract class Node {
    public Script script;

    public Node(Script script) {
        this.script = script;
    }

    public abstract void execute() throws InterruptedException;
    public abstract boolean validate() throws InterruptedException;
}
