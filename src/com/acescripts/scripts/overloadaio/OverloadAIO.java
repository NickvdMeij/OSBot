package com.acescripts.scripts.overloadaio;

import com.acescripts.scripts.overloadaio.framework.Task;
import com.acescripts.scripts.overloadaio.gui.GUI;
import com.acescripts.scripts.overloadaio.paint.GUIPaint;
import com.acescripts.scripts.overloadaio.paint.PaintHandler;
import com.acescripts.scripts.overloadaio.paint.SkillTracking;
import org.osbot.rs07.api.Skills;
import org.osbot.rs07.api.util.ExperienceTracker;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Transporter on 26/07/2016 at 01:26.
 */

@ScriptManifest(author = "Transporter", info = "It does EVERYTHING!", name = "Overload AIO", version = 0.1, logo = "http://i.imgur.com/dskvsHA.png")
public class OverloadAIO extends Script {
    /**
     * TASK DATA
     */

    public static Deque<Task> tasks = new LinkedList<>();
    private Task current = null;
    private GUI gui = new GUI(this);
    private GUIPaint guiPaint = new GUIPaint(this);

    /**
     * PAINT DATA
     */

    public static boolean guiWait = true;
    private static Color DARKRED150 = new Color(135,206,250), BLACK100 = new Color(176,224,230);
    private final LinkedList<MousePathPoint> mousePath = new LinkedList<>();
    public static String status = "Loading";

    public static ExperienceTracker xpTrack = new ExperienceTracker();
    public static Skills skills;
    private PaintHandler paintThread;
    private long timeBegan;
    private long timeRan;

    public OverloadAIO() throws IOException {}

    @Override
    public void onStart() throws InterruptedException {
        guiWait = true;//TUT ISLAND = 406

        while(guiWait) {
            if(!gui.isShowing()) {
                gui.setVisible(true);
            }
            sleep(random(200, 500));
        }

        if(!guiWait) {
            xpTrack = getExperienceTracker();
            xpTrack.startAll();
            skills = getSkills();

            for(SkillTracking skill: SkillTracking.values()) {
                GUIPaint.model.addRow(new Object[] {
                        skill.getSkillName(), skill.getCurrentLevel(), skill.getLevelsGained(), skill.getXpGained(), skill.getXpHour(), skill.getTimeUntilLevel()
                });
            }

            timeBegan = System.currentTimeMillis();

            if(!guiPaint.isShowing()) {
                guiPaint.setVisible(true);
            }

            paintThread = new PaintHandler(this);
            PaintHandler.running = true;
            paintThread.start();
        }
    }

    @Override
    public int onLoop() throws InterruptedException {
        if (current != null) {
            if (!current.isFinished()) current.proceed();
            else {
                current = null;
            }
        } else {
            if (!tasks.isEmpty()) {
                current = tasks.poll();
            } else {
                stop();
            }
        }
        return random(500, 700);
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

    @Override
    public void onPaint(Graphics2D g) {
        paintMouse(g);
        paintMouseSpline(g);
        timeRan = System.currentTimeMillis() - this.timeBegan;
        GUIPaint.lblTimeRunning.setText("Time Running: " + formatTime(timeRan));
        GUIPaint.lblNewLabel.setText("Status: " + status);
    }

    private void paintMouse(Graphics2D g) {
        Point p = mouse.getPosition();

        Graphics2D middle = (Graphics2D) g.create();
        Graphics2D spinG = (Graphics2D) g.create();
        Graphics2D spinGRev2 = (Graphics2D) g.create();

        middle.setColor(BLACK100);
        spinG.setColor(BLACK100);
        spinGRev2.setColor(DARKRED150);

        spinG.rotate(System.currentTimeMillis() % 2000d / 2000d * (360d) * 2 * Math.PI / 180.0, p.x, p.y);
        spinGRev2.rotate(System.currentTimeMillis() % 2000d / 2000d * (-360d) * 2 * Math.PI / 180.0, p.x, p.y);

        spinG.setStroke(new BasicStroke(4.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        spinGRev2.setStroke(new BasicStroke(4.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        middle.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        spinG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        spinGRev2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int circleSize = 3;
        middle.fillOval(p.x - circleSize, p.y - circleSize, circleSize * 2, circleSize * 2);
        int outerSize = 36;
        spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize, outerSize, 0, 275);
        int medSize = 18;
        spinGRev2.drawArc(p.x - (medSize / 2), p.y - (medSize / 2), medSize, medSize, 0, 275);
    }

    private void paintMouseSpline(Graphics2D g) {
        while (!mousePath.isEmpty() && mousePath.peek().isUp()) mousePath.remove();
        Point clientCursor = mouse.getPosition();
        MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, 3000);

        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp)) mousePath.add(mpp);
        MousePathPoint lastPoint = null;

        for (MousePathPoint a : mousePath) {
            if (lastPoint != null) {
                g.setColor(a.getColor());
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.drawLine(a.x, a.y, lastPoint.x, lastPoint.y);
            }
            lastPoint = a;
        }
    }

    @SuppressWarnings("serial")
    private class MousePathPoint extends Point { // credits to Enfilade
        private int toColor(double d) {
            return Math.min(255, Math.max(0, (int) d));
        }

        private long finishTime;

        private double lastingTime;

        MousePathPoint(int x, int y, int lastingTime) {
            super(x, y);
            this.lastingTime = lastingTime;
            finishTime = System.currentTimeMillis() + lastingTime;
        }

        boolean isUp() {
            return System.currentTimeMillis() > finishTime;
        }

        Color getColor() {
            return new Color(176, 224, 230, toColor(256 * ((finishTime - System.currentTimeMillis()) / lastingTime)));
        }
    }

    @Override
    public void onExit() {
        guiPaint.dispose();
        PaintHandler.running = false;
    }
}