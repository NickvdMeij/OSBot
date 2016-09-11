package com.acescripts.scripts.overloadaio.paint;

/**
 * Created by Transporter on 03/08/2016 at 01:39.
 */

import com.acescripts.scripts.overloadaio.OverloadAIO;
import org.osbot.rs07.api.ui.Skill;

public enum SkillTracking {
    AGILITY(Skill.AGILITY),
    ATTACK(Skill.ATTACK),
    COOKING(Skill.COOKING),
    CONSTRUCTION(Skill.CONSTRUCTION),
    CRAFTING(Skill.CRAFTING),
    DEFENCE(Skill.DEFENCE),
    FARMING(Skill.FARMING),
    FISHING(Skill.FISHING),
    FIREMAKING(Skill.FIREMAKING),
    FLETCHING(Skill.FLETCHING),
    HERBLORE(Skill.HERBLORE),
    HUNTER(Skill.HUNTER),
    PRAYER(Skill.PRAYER),
    MAGIC(Skill.MAGIC),
    MINING(Skill.MAGIC),
    RANGED(Skill.RANGED),
    RUNECRAFTING(Skill.RUNECRAFTING),
    SLAYER(Skill.SLAYER),
    SMITHING(Skill.SMITHING),
    STRENGTH(Skill.STRENGTH),
    THIEVING(Skill.THIEVING),
    WOODCUTTING(Skill.WOODCUTTING);

    private Skill skill;

    SkillTracking(Skill skill) {
        this.skill = skill;
    }

    public String getSkillName() {
        return skill.name();
    }

    public int getCurrentLevel() {
        return OverloadAIO.skills.getStatic(skill);
    }

    public int getLevelsGained() {
        return OverloadAIO.xpTrack.getGainedLevels(skill);
    }

    public int getXpGained() {
        return OverloadAIO.xpTrack.getGainedXP(skill);
    }

    public int getXpHour() {
        return OverloadAIO.xpTrack.getGainedXPPerHour(skill);
    }

    public long getTimeUntilLevel() {
        return OverloadAIO.xpTrack.getTimeToLevel(skill);
    }
}