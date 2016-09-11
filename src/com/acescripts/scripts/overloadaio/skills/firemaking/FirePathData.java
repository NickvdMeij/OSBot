package com.acescripts.scripts.overloadaio.skills.firemaking;

import org.osbot.rs07.api.map.Position;

/**
 * Created by Transporter on 28/07/2016 at 15:03.
 */

public enum FirePathData {
    DRAYNOR_VILLAGE(
            new Position(3105, 3250, 0), new Position(3077, 3250, 0),
            new Position(3097, 3249, 0), new Position(3077, 3249, 0),
            new Position(3098, 3248, 0), new Position(3072, 3248, 0),
            new Position(3098, 3247, 0), new Position(3081, 3247, 0)
    );

    private Position startTileOne;
    private Position endTileOne;
    private Position startTileTwo;
    private Position endTileTwo;
    private Position startTileThree;
    private Position endTileThree;
    private Position startTileFour;
    private Position endTileFour;

    FirePathData(Position startTileOne, Position endTileOne, Position startTileTwo, Position endTileTwo, Position startTileThree, Position endTileThree, Position startTileFour, Position endTileFour) {
        this.startTileOne = startTileOne;
        this.endTileOne = endTileOne;
        this.startTileTwo = startTileTwo;
        this.endTileTwo = endTileTwo;
        this.startTileThree = startTileThree;
        this.endTileThree = endTileThree;
        this.startTileFour = startTileFour;
        this.endTileFour = endTileFour;
    }

    public Position getStartTileOne() {
        return startTileOne;
    }

    public Position getEndTileOne() {
        return endTileOne;
    }

    public Position getStartTileTwo() {
        return startTileTwo;
    }

    public Position getEndTileTwo() {
        return endTileTwo;
    }

    public Position getStartTileThree() {
        return startTileThree;
    }

    public Position getEndTileThree() {
        return endTileThree;
    }

    public Position getStartTileFour() {
        return startTileFour;
    }

    public Position getEndTileFour() {
        return endTileFour;
    }
}