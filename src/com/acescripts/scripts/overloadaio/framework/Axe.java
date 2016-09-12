package com.acescripts.scripts.overloadaio.framework;

/**
 * Created by Transporter on 12/09/2016 at 02:42.
 */

public enum Axe {
    BRONZE("Bronze axe", 1, 6),
    IRON("Iron axe", 1, 6),
    STEEL("Steel axe", 6, 21),
    BLACK("Black axe", 6, 21),
    MITHRIL("Mithril axe", 21, 31),
    ADAMANT("Adamant axe", 31, 41),
    RUNE("Rune axe", 41, 61),
    DRAGON("Dragon axe", 61, 99);

    private final String axeName;
    private final int start, end;

    Axe(String axeName, int start, int end) {
        this.axeName = axeName;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getName() {
        return axeName;
    }
}

