package com.acescripts.scripts.overloadaio.framework;

/**
 * Created by Transporter on 26/07/2016 at 01:23.
 */

public interface Task {
    void proceed() throws InterruptedException;
    boolean isFinished();
}
