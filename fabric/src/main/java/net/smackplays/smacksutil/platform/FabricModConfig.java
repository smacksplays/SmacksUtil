package net.smackplays.smacksutil.platform;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.smackplays.smacksutil.platform.services.IModConfig;

import static net.smackplays.smacksutil.Constants.MOD_ID;
/**
 * Class FabricModConfig */
@Config(name = MOD_ID)
public class FabricModConfig implements IModConfig, ConfigData {
    /** Instance*/
    @ConfigEntry.Gui.Excluded
    public static FabricModConfig INSTANCE;
    /** maxRenderBlocks*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum blocks that are allowed to be rendered.")
    public int maxRenderBlocks = IModConfig.maxRenderBlocks;
    /** maxMiningBlocks*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum blocks that are allowed to be mined.")
    public int maxMiningBlocks = IModConfig.maxMiningBlocks;
    /** maxShapelessRadius*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in Shapeless mode.")
    public int maxShapelessRadius = IModConfig.maxShapelessRadius;
    /** maxRenderShapelessRadius*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in Shapeless mode.")
    public int maxRenderShapelessRadius = IModConfig.maxRenderShapelessRadius;
    /** enabledShapelessVerticalMode*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Shapeless Mode.")
    public boolean enabledShapelessVerticalMode = IModConfig.enabledShapelessVerticalMode;
    /** maxShapelessVerticalRadius*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in ShapelessVertical mode.")
    public int maxShapelessVerticalRadius = IModConfig.maxShapelessVerticalRadius;
    /** maxRenderShapelessVerticalRadius*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in ShapelessVertical mode.")
    public int maxRenderShapelessVerticalRadius = IModConfig.maxRenderShapelessVerticalRadius;
    /** enabledTunnelMode*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Tunnel Mode.")
    public boolean enabledTunnelMode = IModConfig.enabledTunnelMode;
    /** enabledMineshaftMode*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Mineshaft Mode.")
    public boolean enabledMineshaftMode = IModConfig.enabledMineshaftMode;
    /** enabledVegetationMode*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Vegetation Mode.")
    public boolean enabledVegetationMode = IModConfig.enabledVegetationMode;
    /** enabledOresMode*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Ores Mode.")
    public boolean enabledOresMode = IModConfig.enabledOresMode;
    /** enabledCropsMode*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Crops Mode.")
    public boolean enabledCropsMode = IModConfig.enabledCropsMode;
    /** enabledTreesMode*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Trees Mode.")
    public boolean enabledTreesMode = IModConfig.enabledTreesMode;
    /** enabledFastEat*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Fast eating.")
    public boolean enabledFastEat = IModConfig.enabledFastEat;
    /** enabledFastPlace*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Fast Placing.")
    public boolean enabledFastPlace = IModConfig.enabledFastPlace;
    /** enabledKeyPressSound*/
    @ConfigEntry.Gui.Tooltip()
    @Comment("Play a sound when a key was pressed.")
    public boolean enabledKeyPressSound = IModConfig.enabledKeyPressSound;
    /** Constructor*/
    public FabricModConfig() {

    }
    /** Getter
     * @return maxRenderBlocks*/
    @Override
    public int getMaxRenderBlocks() {
        return INSTANCE.maxRenderBlocks;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setMaxRenderBlocks(int toSet) {
        INSTANCE.maxRenderBlocks = toSet;
    }

    /** Getter
     * @return maxMiningBlocks*/
    @Override
    public int getMaxMiningBlocks() {
        return INSTANCE.maxMiningBlocks;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setMaxMiningBlocks(int toSet) {
        INSTANCE.maxMiningBlocks = toSet;
    }

    /** Getter
     * @return maxShapelessRadius*/
    @Override
    public int getMaxShapelessRadius() {
        return INSTANCE.maxShapelessRadius;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setMaxShapelessRadius(int toSet) {
        INSTANCE.maxShapelessRadius = toSet;
    }

    /** Getter
     * @return maxRenderShapelessRadius*/
    @Override
    public int getMaxRenderShapelessRadius() {
        return INSTANCE.maxRenderShapelessRadius;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setMaxRenderShapelessRadius(int toSet) {
        INSTANCE.maxRenderShapelessRadius = toSet;
    }

    /** Getter
     * @return enabledShapelessVerticalMode*/
    @Override
    public boolean isEnabledShapelessVerticalMode() {
        return INSTANCE.enabledShapelessVerticalMode;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setEnabledShapelessVerticalMode(boolean toSet) {
        INSTANCE.enabledShapelessVerticalMode = toSet;
    }

    /** Getter
     * @return maxShapelessVerticalRadius*/
    @Override
    public int getMaxShapelessVerticalRadius() {
        return INSTANCE.maxShapelessVerticalRadius;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setMaxShapelessVerticalRadius(int toSet) {
        INSTANCE.maxShapelessVerticalRadius = toSet;
    }

    /** Getter
     * @return maxRenderShapelessVerticalRadius*/
    @Override
    public int getMaxRenderShapelessVerticalRadius() {
        return INSTANCE.maxRenderShapelessVerticalRadius;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setMaxRenderShapelessVerticalRadius(int toSet) {
        INSTANCE.maxRenderShapelessVerticalRadius = toSet;
    }

    /** Getter
     * @return enabledTunnelMode*/
    @Override
    public boolean isEnabledTunnelMode() {
        return INSTANCE.enabledTunnelMode;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setEnabledTunnelMode(boolean toSet) {
        INSTANCE.enabledTunnelMode = toSet;
    }

    /** Getter
     * @return enabledMineshaftMode*/
    @Override
    public boolean isEnabledMineshaftMode() {
        return INSTANCE.enabledMineshaftMode;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setEnabledMineshaftMode(boolean toSet) {
        INSTANCE.enabledMineshaftMode = toSet;
    }

    /** Getter
     * @return enabledVegetationMode*/
    @Override
    public boolean isEnabledVegetationMode() {
        return INSTANCE.enabledVegetationMode;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setEnabledVegetationMode(boolean toSet) {
        INSTANCE.enabledVegetationMode = toSet;
    }

    /** Getter
     * @return enabledOresMode*/
    @Override
    public boolean isEnabledOresMode() {
        return INSTANCE.enabledOresMode;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setEnabledOresMode(boolean toSet) {
        INSTANCE.enabledOresMode = toSet;
    }

    /** Getter
     * @return enabledCropsMode*/
    @Override
    public boolean isEnabledCropsMode() {
        return INSTANCE.enabledCropsMode;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setEnabledCropsMode(boolean toSet) {
        INSTANCE.enabledCropsMode = toSet;
    }

    /** Getter
     * @return enabledTreesMode*/
    @Override
    public boolean isEnabledTreesMode() {
        return INSTANCE.enabledTreesMode;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setEnabledTreesMode(boolean toSet) {
        INSTANCE.enabledTreesMode = toSet;
    }

    /** Getter
     * @return enabledFastEat*/
    @Override
    public boolean isEnabledFastEat() {
        return INSTANCE.enabledFastEat;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setEnabledFastEat(boolean toSet) {
        INSTANCE.enabledFastEat = toSet;
    }

    /** Getter
     * @return enabledFastPlace*/
    @Override
    public boolean isEnabledFastPlace() {
        return INSTANCE.enabledFastPlace;
    }

    /** Setter
     * @param toSet toSet*/
    @Override
    public void setEnabledFastPlace(boolean toSet) {
        INSTANCE.enabledFastPlace = toSet;
    }

    /** Getter
     * @return enabledKeyPressSound*/
    @Override
    public boolean isEnabledKeyPressSound() {
        return INSTANCE.enabledKeyPressSound;
    }

    /** Setter
     * @param toSet setEnabledKeyPressSound*/
    @Override
    public void setEnabledKeyPressSound(boolean toSet) {
        INSTANCE.enabledKeyPressSound = toSet;
    }

    /** init*/
    public void init() {
        AutoConfig.register(FabricModConfig.class, GsonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(FabricModConfig.class).getConfig();
    }
}
