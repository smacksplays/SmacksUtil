package net.smackplays.smacksutil.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;
import net.smackplays.smacksutil.platform.services.IPlatformHelper;

import static net.smackplays.smacksutil.SmacksUtil.*;

/**
 * Class FabricPlatformHelper */
public class FabricPlatformHelper implements IPlatformHelper {
    /** Constructor*/
    public FabricPlatformHelper() {

    }

    /** Getter
     * @return true if client*/
    @Override
    public boolean isClient(){
        EnvType t = FabricLoader.getInstance().getEnvironmentType();
        return t.name().equals("CLIENT");
    }

    @Override
    public Item getBackpackItem() {
        return BACKPACK_ITEM;
    }

    @Override
    public Item getLargeBackpackItem() {
        return LARGE_BACKPACK_ITEM;
    }

    @Override
    public Item getMagnetItem() {
        return MAGNET_ITEM;
    }

    @Override
    public Item getAdvancedMagnetItem() {
        return ADVANCED_MAGNET_ITEM;
    }

    @Override
    public Item getLightWandItem() {
        return LIGHT_WAND_ITEM;
    }

    @Override
    public Item getAutoWandItem() {
        return AUTO_LIGHT_WAND_ITEM;
    }

    @Override
    public Item getUpgrade1Item() {
        return BACKPACK_UPGRADE_TIER1_ITEM;
    }

    @Override
    public Item getUpgrade2Item() {
        return BACKPACK_UPGRADE_TIER2_ITEM;
    }

    @Override
    public Item getUpgrade3Item() {
        return BACKPACK_UPGRADE_TIER3_ITEM;
    }
}
