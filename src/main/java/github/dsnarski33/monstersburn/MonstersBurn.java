package github.dsnarski33.monstersburn;

import github.dsnarski33.monstersburn.config.Config;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

@Mod(MonstersBurn.MOD_ID)
public class MonstersBurn
{
    public static final String MOD_ID = "monstersburn";
    public static final String MOD_NAME = "Monsters Burn";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MonstersBurn()
    {
        Config.init();
    }

    // caching these values to avoid calling the config get() values every time
    private static boolean isOnSurfaceMobAlwaysBurned, init_isOnSurfaceMobAlwaysBurned = true;
    private static boolean isMonsterLootDropEnabled, init_isMonsterLootDropEnabled = true;
    private static boolean isDimensionsWhitelist = true;
    @Nullable private static List<? extends String> dimensions = null;

    public static boolean isValidDimension(Level level) {
        if (dimensions == null) {
            isDimensionsWhitelist = Config.SERVER.isDimensionsWhitelist.get();
            dimensions = Config.SERVER.dimensions.get();
        }
        return dimensions.contains(level.dimension().location().toString()) == isDimensionsWhitelist;
    }

    public static boolean isOnSurfaceMobAlwaysBurned() {
        if(init_isOnSurfaceMobAlwaysBurned) {
            init_isOnSurfaceMobAlwaysBurned = false;
            isOnSurfaceMobAlwaysBurned = Config.SERVER.isOnSurfaceMobAlwaysBurned.get();
        }
        return isOnSurfaceMobAlwaysBurned;
    }

    public static boolean isMonsterLootDropEnabled() {
        if(init_isMonsterLootDropEnabled) {
            init_isMonsterLootDropEnabled = false;
            isMonsterLootDropEnabled = Config.SERVER.isMonsterLootDropEnabled.get();
        }
        return isMonsterLootDropEnabled;
    }
}
