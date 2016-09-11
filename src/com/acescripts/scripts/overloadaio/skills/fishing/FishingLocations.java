package com.acescripts.scripts.overloadaio.skills.fishing;

/**
 * Created by Transporter on 11/08/2016 at 21:37.
 */

public enum FishingLocations {
    BARBARIAN_VILLAGE("lummyareae", "TROUT / SALMON"),
    KARAMAJA("lummyareaw", "SHRIMPS / ANCHOVIES", "LOBSTERS", "TUNA / SWORDFISH"),
    FISHING_GUILD("draynoreara", "TUNA / SWORDFISH", "LOBSTERS", "SHARKS"),
    CATHERBY("varrockarea", "SHRIMPS / ANCHOVIES", "TUNA / SWORDFISH", "LOBSTERS", "SHARKS"),
    SEERS_VILALGE("edgevillearea", "TROUT / SALMON"),
    DRAYNOR_VILLAGE("edgevillearea", "SHRIMPS / ANCHOVIES"),
    LUMBRIDGE_SWAMP("edgevillearea", "SHRIMPS / ANCHOVIES"),
    BAXTORIAN_FALLS("edgevillearea", "LEAPING_FISH"),
    SHILO_VILLAGE("edgevillearea", "TROUT / SALMON", "PIKE");

    private String area;
    private String[] fishNames;

    FishingLocations(String area, String... fishNames) {
        this.area = area;
        this.fishNames = fishNames;
    }

    public String getArea() {
        return area;
    }

    public String[] getFishNames() {
        return fishNames;
    }
}
