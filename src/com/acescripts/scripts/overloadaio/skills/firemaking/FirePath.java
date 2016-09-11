package com.acescripts.scripts.overloadaio.skills.firemaking;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

import java.util.List;

/**
 * Created by Transporter on 27/07/2016 at 23:40.
 */

public class FirePath {
    private Area area;
    private Script script;

    public FirePath(Script script, Position p1, Position p2) {
        this.script = script;
        area = new Area(p1, p2);
    }

    public int getEmptyTileCount() {
        int count = 0;

        for (Position p : area.getPositions()) {
            if (canLight(p)) {
                count++;
            }
        }
        return count;
    }

    private boolean canLight(Position p) {
        for (RS2Object current : script.objects.get(p.getX(), p.getY())) {
            if (current != null && current.getPosition().equals(p) && !current.getName().equals("null")) {
                return false;
            }
        }
        return true;
    }

    public List<Position> getTiles() {
        return area.getPositions();
    }
}
