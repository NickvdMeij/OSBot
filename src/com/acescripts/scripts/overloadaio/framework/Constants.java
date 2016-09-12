package com.acescripts.scripts.overloadaio.framework;


import org.osbot.rs07.api.map.Position;

/**
 * Created by Transporter on 07/09/2016 at 22:58.
 */

public class Constants {
    public static class Objects {
        /**
         * DOOR/GATE_POSITIONS
         */
        public static final Position MASTER_CHEF_DOOR_POSITION = new Position(3072, 3090, 0);
        public static final Position QUEST_GUIDE_DOOR_POSITION = new Position(3086, 3126, 0);
        public static final Position COMBAT_INSTRUCTOR_GATE_POSITION = new Position(3111, 9518, 0);
        public static final Position BANK_INSTRUCTOR_DOOR_POSITION = new Position(3125, 3124, 0);
        public static final Position FINANCIAL_ADVISOR_DOOR_POSITION = new Position(3130, 3124, 0);
        public static final Position BROTHER_BRACE_DOOR_POSITION = new Position(3122, 3102, 0);

        /**
         * ROCK_COLOUR_IDS
         */
        public static final int TIN_ROCK_COLOUR = 53;
        public static final int COPPER_ROCK_COLOUR = 4510;
    }

    public static class Widgets {
        /**
         * CHAT_BOX_IDS
         */
        public static final int CHAT_BOX_ROOT = 372;
        public static final int CHAT_BOX_ROOT_2 = 421;

        /**
         * CLICK_CONTINUE_IDS
         */
        public static final int CLICK_CONTINUE = 162;
    }

    public static class WidgetText {
        /**
         * RUNESCAPE_GUIDE_STRINGS
         */
        public static final String START_TUTORIAL = "To start the tutorial use your left mouse button to click on the";
        public static final String PLAYER_CONTROLS = "Player controls";
        public static final String OPEN_FIST_DOOR = "Interacting with scenery";

        /**
         * SURVIVAL_EXPERT_STRINGS
         */
        public static final String MOVING_AROUND = "Moving around";
        public static final String OPENING_INVENTORY = "Viewing the items that you were given.";
        public static final String CHOPPING_TREE = "Cut down a tree";
        public static final String MAKING_FIRE = "Making a fire";
        public static final String CATCHING_SHRIMP = "Catch some Shrimp.";
        public static final String COOKING_SHRIMP = "Cooking your shrimp.";
        public static final String BURNING_SHRIMP = "Burning your shrimp.";
        public static final String OPENING_SURVIVAL_GATE = "Well done, you've just cooked your first RuneScape meal.";
        public static final String OPENING_SKILLS = "You gained some experience.";
        public static final String CHECKING_SKILL_STATS = "Your skill stats.";

        /**
         * MASTER_CHEF_STRINGS
         */
        public static final String MAKING_DOUGH = "Making dough.";
        public static final String COOKING_DOUGH = "Cooking dough.";
        public static final String OPEN_MASTER_CHEF_ENTRANCE = "Follow the path until you get to the door with the yellow arrow";
        public static final String TALK_TO_MASTER_CHEF = "Talk to the chef indicated. He will teach you the more advanced";
        public static final String OPENING_MUSIC = "Cooking dough";
        public static final String OPEN_MASTER_CHEF_EXIT = "The music player.";

        /**
         * QUEST_GUIDE_STRINGS
         */
        public static final String PERFORM_EMOTE = "Emotes.";
        public static final String TALK_TO_QUEST_GUIDE_JOURNAL = "Your Quest Journal.";
        public static final String OPENING_SETTINGS = "It's only a short distance to the next guide.";
        public static final String TURN_ON_RUN = "In this menu you will see many options. At the bottom in the";
        public static final String TALK_TO_QUEST_GUIDE_NORMAL = "Talk with the Quest Guide.";
        public static final String OPENING_QUESTS = "Open the Quest Journal.";
        public static final String CLIMB_DOWN_LADDER = "Moving on.";
        public static final String RUN_TO_GUIDE = "Run to the next guide.";

        /**
         * MINING_INSTRUCTOR_STRINGS
         */
        public static final String TALK_TO_MINING_INSTRUCTOR_START = "Mining and Smithing.";
        public static final String PROSPECT_TIN_ORE = "Prospecting.";
        public static final String PROSPECT_COPPER_ORE = "It's tin.";
        public static final String TALK_TO_MINING_INSTRUCTOR_SMELTING = "It's copper.";
        public static final String SMELTING = "Smelting.";
        public static final String TALK_TO_MINING_INSTRUCTOR_SMITHING = "You've made a bronze bar!";
        public static final String SMITHING_A_DAGGER = "Smithing a dagger.";
        public static final String OPEN_MINING_INSTRUCTOR_EXIT = "You've finished in this area.";
        public static final String MINE_TIN_ORE = "It's quite simple really. All you need to do is right click on the";
        public static final String MINE_COPPER_ORE = "Now you have some tin ore you just need some copper ore,";

        /**
         * COMBAT_INSTRUCTOR_STRINGS
         */
        public static final String TALK_TO_COMBAT_INSTRUCTOR_START = "Combat.";
        public static final String OPENING_EQUIPMENT = "Wielding weapons.";
        public static final String OPENING_WORN_INTERFACE = "This is your worn inventory.";
        public static final String EQUIPPING_DAGGER = "Worn interface";
        public static final String OPENING_COMBAT = "Combat interface.";
        public static final String ATTACK_RAT_MELEE = "Attacking.";
        public static final String TALK_TO_COMBAT_INSTRUCTOR_RANGE = "Well done, you've made your first kill!";
        public static final String ATTACK_RAT_RANGE = "Rat ranging.";
        public static final String OPEN_COMBAT_INSTRUCTOR_EXIT = "Moving on.";
        public static final String TALK_TO_COMBAT_INSTRUCTOR_DAGGER = "You're now holding your dagger.";
        public static final String EQUIP_BETTER_WEAPON = "Unequipping items.";
        public static final String OPEN_RAT_GATE = "This is your combat interface.";

        /**
         * BANK_INSTRUCTOR_STRINGS
         */
        public static final String OPEN_BANK = "Banking.";
        public static final String OPEN_POLL_BOOTH = "This is your bank box.";
        public static final String OPEN_BANK_INSTRUCTOR_EXIT = "This is a poll booth.";

        /**
         * FINANCIAL_ADVISOR_STRINGS
         */
        public static final String TALK_TO_FINANCIAL_ADVISOR_START = "Financial advice.";
        public static final String OPEN_FINANCIAL_ADVISOR_EXIT = "Continue through the next door.";

        /**
         * BROTHER_BRACE_STRINGS
         */
        public static final String TALK_TO_BROTHER_BRACE_START = "Prayer.";
        public static final String OPENING_PRAYER = "Click on the flashing icon to open the Prayer menu.";
        public static final String TALK_TO_BROTHER_BRACE_PRAYER = "Talk with Brother Brace and he'll tell you about prayers.";
        public static final String OPENING_FRIENDS = "Friends list.";
        public static final String OPENING_IGNORES = "This is your friends list.";
        public static final String TALK_TO_BROTHER_BRACE_IGNORE = "This is your ignore list.";
        public static final String OPEN_BROTHER_BRACE_EXIT = "Your final instructor!";

        /**
         * MAGIC_INSTRUCTOR_STRINGS
         */
        public static final String TALK_TO_MAGIC_INSTRUCTOR_START = "Your final instructor!";
        public static final String OPENING_MAGIC = "Open up your final menu.";
        public static final String TALK_TO_MAGIC_INSTRUCTOR_MAGIC = "You have almost completed the tutorial!";
        public static final String CAST_WIND_STRIKE = "Cast Wind Strike at a chicken.";
    }
}
