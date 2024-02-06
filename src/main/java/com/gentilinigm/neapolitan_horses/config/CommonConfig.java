package com.gentilinigm.neapolitan_horses.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

import java.util.ArrayList;
import java.util.List;

public final class CommonConfig {

    public static final GeneralConfig GENERAL_CONFIG;
    public static final NeapolitanHorseConfig NEAPOLITAN_HORSE_CONFIG;

    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        GENERAL_CONFIG = new GeneralConfig(COMMON_BUILDER);
        NEAPOLITAN_HORSE_CONFIG = new NeapolitanHorseConfig(COMMON_BUILDER);

        SPEC = COMMON_BUILDER.build();
    }

    public static class GeneralConfig {

        public final IntValue MAXIMUM_PASSENGERS;
        public final BooleanValue REMOVE_HELMET;

        public GeneralConfig(final ForgeConfigSpec.Builder builder) {
            builder.push("general");

            this.MAXIMUM_PASSENGERS = builder.comment("Maximum number of passengers per horse. Valid for all horse types.")
                    .defineInRange("maximum_passengers", 4, 1, 4);
            this.REMOVE_HELMET = builder.comment("Prevent riding with a helmet on. Valid for all horse types.")
                    .define("remove_helmet", true);

            builder.pop();
        }

    }

    public static class NeapolitanHorseConfig {

        public final DoubleValue MAX_HEALTH;
        public final DoubleValue JUMP_STRENGTH;
        public final DoubleValue MOVEMENT_SPEED;

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> BIOME_WHITELIST;
        public final IntValue SPAWN_RATE;
        public final IntValue MIN_GROUP_SIZE;
        public final IntValue MAX_GROUP_SIZE;


        public NeapolitanHorseConfig(final ForgeConfigSpec.Builder builder) {
            builder.push("neapolitan_horse");

            this.MAX_HEALTH = builder.comment("Maximum health. Set to -1 for default (random).")
                    .defineInRange("max_health", -1F, -1F, 30F);
            this.JUMP_STRENGTH = builder.comment("Jump strength. Set to -1 for default (random).")
                    .defineInRange("jump_strength", -1F, -1F, 1F);
            this.MOVEMENT_SPEED = builder.comment("Movement speed. Set to -1 for default (random).")
                    .defineInRange("movement_speed", 0.3375F, -1F, 0.3375F);

            builder.push("spawn_options");

            this.BIOME_WHITELIST = builder.comment("Allow spawns in these biomes.")
                    .defineList("biome_whitelist", new ArrayList<>() {{
                        add("minecraft:plains");
                    }}, s -> s instanceof String);
            this.SPAWN_RATE = builder.comment("Smaller number causes less spawning, 0 to disable spawning.")
                    .defineInRange("spawn_rate", 1, 0, Integer.MAX_VALUE);
            this.MIN_GROUP_SIZE = builder.comment("Minimum number of mobs that appear in a spawn group.")
                    .defineInRange("min_group_size", 1, 1, Integer.MAX_VALUE);
            this.MAX_GROUP_SIZE = builder.comment("Maximum number of mobs that appear in a spawn group.")
                    .defineInRange("max_group_size", 1, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.pop();
        }
    }


}
