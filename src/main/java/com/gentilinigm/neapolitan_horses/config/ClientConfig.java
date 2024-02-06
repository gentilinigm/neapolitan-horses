package com.gentilinigm.neapolitan_horses.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ClientConfig {

    public static final BooleanValue RENDER_ARMOR;

    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        COMMON_BUILDER.push("client");

        RENDER_ARMOR = COMMON_BUILDER.comment("Enable rendering of horse armor.")
                .worldRestart()
                .define("render_armor", true);

        COMMON_BUILDER.pop();

        SPEC = COMMON_BUILDER.build();
    }
}