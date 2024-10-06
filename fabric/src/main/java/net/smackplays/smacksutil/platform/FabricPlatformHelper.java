package net.smackplays.smacksutil.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.platform.services.IPlatformHelper;
import net.smackplays.smacksutil.util.CustomRenderLayer;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Item getBackackItem() {
        return SmacksUtil.BACKPACK_ITEM;
    }

    @Override
    public Item getLargeBackackItem() {
        return SmacksUtil.LARGE_BACKPACK_ITEM;
    }

    @Override
    public Item getMagnetItem() {
        return SmacksUtil.MAGNET_ITEM;
    }

    @Override
    public Item getAdvancedMagnetItem() {
        return SmacksUtil.ADVANCED_MAGNET_ITEM;
    }

    @Override
    public Item getLightWandItem() {
        return SmacksUtil.LIGHT_WAND_ITEM;
    }

    @Override
    public Item getAutoWandItem() {
        return SmacksUtil.AUTO_LIGHT_WAND_ITEM;
    }

    @Override
    public Item getUpgrade1Item() {
        return SmacksUtil.BACKPACK_UPGRADE_TIER1_ITEM;
    }

    @Override
    public Item getUpgrade2Item() {
        return SmacksUtil.BACKPACK_UPGRADE_TIER2_ITEM;
    }

    @Override
    public Item getUpgrade3Item() {
        return SmacksUtil.BACKPACK_UPGRADE_TIER3_ITEM;
    }

    @Override
    public boolean isClient(){
        EnvType t = FabricLoader.getInstance().getEnvironmentType();
        return t.name().equals("CLIENT");
    }

    // Trinkets extend player inventory, thus this function is never used but has to be implemented.
    @Override
    public ItemStack getTrinketOrCuriosStack(Player player, String slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public RenderType getRenderType() {
        return CustomRenderLayer.LINES;
    }
}
