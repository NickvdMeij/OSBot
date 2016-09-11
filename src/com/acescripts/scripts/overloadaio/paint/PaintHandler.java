package com.acescripts.scripts.overloadaio.paint;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import org.osbot.rs07.script.Script;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Transporter on 03/08/2016 at 13:11.
 */

public class PaintHandler extends Thread {

    private Script script;
    public static Boolean running;

    public PaintHandler(Script script) {
        this.script = script;
    }

    private String formatTime(long duration) {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));

        String daysFormat = days < 10 ? "0" + days : "" + days;
        String hoursFormat = hours < 10 ? "0" + hours : "" + hours;
        String minutesFormat = minutes < 10 ? "0" + minutes : "" + minutes;
        String secondsFormat = seconds < 10 ? "0" + seconds : "" + seconds;

        if (days == 0) {
            res = (hoursFormat + ":" + minutesFormat + ":" + secondsFormat);
        } else {
            res = (daysFormat + ":" + hoursFormat + ":" + minutesFormat + ":" + secondsFormat);
        }
        return res;
    }

    private String formatInteger(int integer) {
        return NumberFormat.getNumberInstance(Locale.US).format(integer);
    }

    @Override
    public void run() {
        while(running) {
            if(!OverloadAIO.guiWait) {
                int i = 0;
                for(SkillTracking skill: SkillTracking.values()) {
                    GUIPaint.model.setValueAt(skill.getCurrentLevel(), i, 1);
                    GUIPaint.model.setValueAt(skill.getLevelsGained(), i, 2);
                    GUIPaint.model.setValueAt(formatInteger(skill.getXpGained()), i, 3);
                    GUIPaint.model.setValueAt(formatInteger(skill.getXpHour()), i, 4);
                    GUIPaint.model.setValueAt(formatTime(skill.getTimeUntilLevel()), i, 5);
                    if(i < SkillTracking.values().length) {
                        i++;
                    } else {
                        i = 0;
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}