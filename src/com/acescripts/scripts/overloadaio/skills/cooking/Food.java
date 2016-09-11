package com.acescripts.scripts.overloadaio.skills.cooking;

/**
 * Created by Transporter on 11/08/2016 at 21:11.
 */

public enum Food {
    MEAT("Meat"),
    CHICKEN("Chicken"),
    SHRIMPS("Shrimps"),
    KARAMBWANJI("Karambwanji"),
    SARDINE("Sardine"),
    ANCHOVIES("Anchovies"),
    HERRING("Herring"),
    MACKEREL("Mackerel"),
    TROUT("Trout"),
    COD("Cod"),
    PIKE("Pike"),
    SALMON("Salmon"),
    TUNA("Tuna"),
    KARAMBWAN("Karambwan"),
    LOBSTER("Lobster"),
    BASS("Bass"),
    SWORDFISH("Swordfish"),
    MONKFISH("Monkfish"),
    SHARK("Shark"),
    SEA_TURTLE("Sea Turtle"),
    ANGLERFISH("Anglerfish"),
    DARK_CRAB("Dark Crab"),
    MANTA_RAY("Manta Ray");

    private String foodName;

    Food(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }
}

