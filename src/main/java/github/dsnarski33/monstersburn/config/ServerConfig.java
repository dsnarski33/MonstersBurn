package github.dsnarski33.monstersburn.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.List;
import java.util.function.Function;

import static github.dsnarski33.monstersburn.MonstersBurn.MOD_ID;

public class ServerConfig
{
    public final BooleanValue isOnSurfaceMobAlwaysBurned;
    public final BooleanValue isMonsterLootDropEnabled;
    public final BooleanValue isDimensionsWhitelist;
    public final ConfigValue<List<? extends String>> dimensions;

    ServerConfig(Builder innerBuilder)
    {
        Function<String, Builder> builder = name -> innerBuilder.translation(MOD_ID + ".config.server." + name);

        innerBuilder.push("general");

        isOnSurfaceMobAlwaysBurned = builder.apply("isOnSurfaceMobAlwaysBurned")
                .comment("Specify if monsters should always burn if on the surface, allows death during rain, etc.")
                .define("isOnSurfaceMobAlwaysBurned", true);
        isMonsterLootDropEnabled = builder.apply("isMonsterLootDropEnabled")
                .comment("Specify if monsters should drop loot on death.")
                .define("isMonsterLootDropEnabled", false);
        isDimensionsWhitelist = builder.apply("isDimensionsWhitelist")
                .comment("Specify if dimensions list is a whitelist or blacklist.")
                .define("isDimensionsWhitelist", true);
        dimensions = builder.apply("dimensions")
                .comment("Specify dimensions that monster burning applies to. Can be used as a whitelist or blacklist.")
                .defineListAllowEmpty(List.of("dimensions"), List.of("minecraft:overworld"), o -> (o instanceof String string && ResourceLocation.isValidResourceLocation(string)));

        innerBuilder.pop();
    }
}
