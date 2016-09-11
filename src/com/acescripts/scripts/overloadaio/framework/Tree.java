package com.acescripts.scripts.overloadaio.framework;

/**
 * Created by Transporter on 11/08/2016 at 21:29.
 */

public enum Tree {
    NORMAL("Tree"),
    OAK("Oak"),
    WILLOW("Willow"),
    TEAK("Teak"),
    MAPLE("Maple"),
    MAHOGANY("Mahogany"),
    ARTIC_PINE("Artic pine"),
    YEW("Yew"),
    MAGIC("Magic");

    private String name;

    Tree(String name) {
        this.name = name;
    }

    String format(String str) {
        char[] stringArray = str.trim().toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);
        return new String(stringArray);
    }

    String getName() {
        return name;
    }

    public String getTreeName() {
        return this == MAPLE ? this.getName() + " tree" : this.getName();
    }

    public String getLogName() {
        return this == NORMAL ? "Logs" : this.getName() + " logs";
    }

    public String getShortbowName(boolean strung) {
        return format(getName() + " shortbow" + (strung ? " (u)" : ""));
    }

    public String getLongbowName(boolean strung) {
        return format(getName() + " longbow" + (strung ? " (u)" : ""));
    }
}
