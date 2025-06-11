package net.smackplays.smacksutil.platform;

import net.minecraft.world.item.Item;
import net.neoforged.fml.loading.FMLLoader;
import net.smackplays.smacksutil.platform.services.IPlatformHelper;

import static net.smackplays.smacksutil.SmacksUtil.*;

public class NeoForgePlatformHelper implements IPlatformHelper {
    /** Constructor*/
    public NeoForgePlatformHelper() {

    }

    @Override
    public Item getBackpackItem() {
        if (BACKPACK_ITEM.isBound()) {
            return BACKPACK_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getLargeBackpackItem() {
        if (LARGE_BACKPACK_ITEM.isBound()) {
            return LARGE_BACKPACK_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getMagnetItem() {
        if (MAGNET_ITEM.isBound()) {
            return MAGNET_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getAdvancedMagnetItem() {
        if (ADVANCED_MAGNET_ITEM.isBound()) {
            return ADVANCED_MAGNET_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getLightWandItem() {
        if (LIGHT_WAND_ITEM.isBound()) {
            return LIGHT_WAND_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getAutoWandItem() {
        if (AUTO_LIGHT_WAND_ITEM.isBound()) {
            return AUTO_LIGHT_WAND_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getUpgrade1Item() {
        if (BACKPACK_UPGRADE_TIER1_ITEM.isBound()) {
            return BACKPACK_UPGRADE_TIER1_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getUpgrade2Item() {
        if (BACKPACK_UPGRADE_TIER2_ITEM.isBound()) {
            return BACKPACK_UPGRADE_TIER2_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getUpgrade3Item() {
        if (BACKPACK_UPGRADE_TIER3_ITEM.isBound()) {
            return BACKPACK_UPGRADE_TIER3_ITEM.get();
        }
        return null;
    }

    @Override
    public boolean isClient() {
        return FMLLoader.getDist().isClient();
    }
}