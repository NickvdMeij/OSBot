package com.acescripts.scripts.overloadaio.paint;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import org.osbot.rs07.api.ui.Skill;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Transporter on 03/08/2016 at 13:11.
 */

public class PaintHandler extends Thread {
    private OverloadAIO script;
    private Boolean running;

    public PaintHandler(OverloadAIO script) {
        this.script = script;
    }

    private String formatTime(long duration) {
        String res;
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

    public boolean setRunning(boolean running) {
        return this.running = running;
    }

    @Override
    public void run() {
        if(running != null) {
            while(running) {
                int i = 0;

                for(Skill skill : Skill.values()) {
                    script.getGuiPaint().getModel().setValueAt(skill.name(), i, 0);
                    script.getGuiPaint().getModel().setValueAt(script.getXpTrack().getSkills().getStatic(skill), i, 1);
                    script.getGuiPaint().getModel().setValueAt(script.getXpTrack().getGainedLevels(skill), i, 2);
                    script.getGuiPaint().getModel().setValueAt(formatInteger(script.getXpTrack().getGainedXP(skill)), i, 3);
                    script.getGuiPaint().getModel().setValueAt(formatInteger(script.getXpTrack().getGainedXPPerHour(skill)), i, 4);
                    script.getGuiPaint().getModel().setValueAt(formatTime(script.getXpTrack().getTimeToLevel(skill)), i, 5);

                    if(i < Skill.values().length) {
                        i++;
                    } else {
                        i = 0;
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}