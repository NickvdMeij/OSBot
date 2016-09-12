package com.acescripts.scripts.overloadaio.framework;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.tutorialisland.methods.TutorialIslandMethods;

/**
 * Created by Transporter on 06/08/2016 at 23:49.
 */

public abstract class Node {
    protected OverloadAIO script;
    protected TutorialIslandMethods methods;

    public Node(OverloadAIO script) {
        this.script = script;
        this.methods = script.getMethods();
    }

    public abstract void execute() throws InterruptedException;
    public abstract boolean validate() throws InterruptedException;
}
