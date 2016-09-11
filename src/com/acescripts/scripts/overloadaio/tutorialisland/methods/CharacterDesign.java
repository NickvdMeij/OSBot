package com.acescripts.scripts.overloadaio.tutorialisland.methods;

/**
 * Created by Transporter on 03/08/2016 at 22:59.
 */

public enum CharacterDesign {
    GENDER(269, 136, 137, 10),
    HEAD_DESIGN(269, 106, 113, 16),
    JAW_DESIGN(269, 107, 114, 14),
    TORSO_DESIGN(269, 108, 115, 14),
    ARMS_DESIGN(269, 109, 116, 12),
    HANDS_DESIGN(269, 110, 117, 4),
    LEGS_DESIGN(269, 111, 118, 11),
    FEET_DESIGN(269, 112, 119, 4),
    HAIR_COLOUR(269, 105, 121, 25),
    TORSO_COLOUR(269, 123, 127, 14),
    LEG_COLOUR(269, 122, 129, 14),
    FEET_COLOUR(269, 124, 130, 6),
    SKIN_COLOUR(269, 125, 131, 8);

    private int parent, childLeft, childRight, possibilities;

    CharacterDesign(int parent, int childLeft, int childRight, int possibilities) {
        this.parent = parent;
        this.childLeft = childLeft;
        this.childRight = childRight;
        this.possibilities = possibilities;
    }

    public int getParent() {
        return parent;
    }

    public int getChildLeft() {
        return childLeft;
    }

    public int getChildRight() {
        return childRight;
    }

    public int getPossibilities() {
        return possibilities;
    }
}
