package com.acescripts.scripts.overloadaio.skills.firemaking;

import org.osbot.rs07.api.map.Area;

/**
 * Created by Transporter on 26/07/2016 at 17:04.
 */

public enum FiremakingLocations {
    DRAYNOR_VILLAGE(new Area(3105, 3251, 3072, 3247)),
    DUEL_AREANA(new Area(3105, 3251, 3072, 3247)),
    EDGEVILLE(new Area(3105, 3251, 3072, 3247)),
    FALADOR_EAST(new Area(3105, 3251, 3072, 3247)),
    FALADOR_WEST(new Area(3105, 3251, 3072, 3247)),
    GRAND_EXCHANGE_NORTH(new Area(3105, 3251, 3072, 3247)),
    GRAND_EXCHANGE_SOUTH(new Area(3105, 3251, 3072, 3247)),
    SEERS_VILLAGE(new Area(3105, 3251, 3072, 3247)),
    VARROCK_EAST(new Area(3105, 3251, 3072, 3247)),
    VARROCK_WEST(new Area(3105, 3251, 3072, 3247)),
    YANILLE(new Area(3105, 3251, 3072, 3247));

    private Area area;

    FiremakingLocations(Area area){
        this.area = area;
    }

    public Area getArea(){
        return this.area;
    }
}
