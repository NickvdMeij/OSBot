package com.acescripts.scripts.overloadaio.skills.cooking;

/**
 * Created by Transporter on 11/08/2016 at 21:10.
 */

public enum CookingLocations {
    AL_KHARID("area_here"),
    CATHERBY("area_here"),
    COOKS_GUILD("area_here"),
    EDGEVILLE("area_here"),
    GRAND_EXCHANGE("area_here"),
    LLEYTA("area_here"),
    LUMBRIDGE_CASTLE("area_here"),
    SEERS_VILLAGE("area_here"),
    ROUGES_DEN("area_here"),
    TZHAAR("area_here");

    private String area;

    CookingLocations(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }
}