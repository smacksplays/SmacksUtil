package net.smackplays.smacksutil.platform.services;

/**
 * Interface IKeyHandler */
public interface IModConfig {
    /** maxRenderBlocks*/
    int maxRenderBlocks = 150;
    /** maxShapelessRadius*/
    int maxShapelessRadius = 6;
    /** maxRenderShapelessRadius*/
    int maxRenderShapelessRadius = 3;
    /** enabledShapelessVerticalMode*/
    boolean enabledShapelessVerticalMode = true;
    /** maxShapelessVerticalRadius*/
    int maxShapelessVerticalRadius = 6;
    /** maxRenderShapelessVerticalRadius*/
    int maxRenderShapelessVerticalRadius = 3;
    /** enabledTunnelMode*/
    boolean enabledTunnelMode = true;
    /** enabledMineshaftMode*/
    boolean enabledMineshaftMode = true;
    /** enabledVegetationMode*/
    boolean enabledVegetationMode = true;
    /** enabledOresMode*/
    boolean enabledOresMode = true;
    /** enabledCropsMode*/
    boolean enabledCropsMode = true;
    /** enabledTreesMode*/
    boolean enabledTreesMode = true;
    /** enabledFastEat*/
    boolean enabledFastEat = false;
    /** enabledFastPlace*/
    boolean enabledFastPlace = false;
    /** enabledKeyPressSound*/
    boolean enabledKeyPressSound = true;

    /** Interface method init*/
    void init();

    /** Interface method getMaxRenderBlocks
     * @return MaxRenderBlocks*/
    int getMaxRenderBlocks();

    /** Interface method setMaxRenderBlocks
     * @param toSet toSet*/
    void setMaxRenderBlocks(int toSet);

    /** Interface method getMaxShapelessRadius
     * @return MaxShapelessRadius*/
    int getMaxShapelessRadius();

    /** Interface method setMaxShapelessRadius
     * @param toSet toSet*/
    void setMaxShapelessRadius(int toSet);

    /** Interface method getMaxRenderShapelessRadius
     * @return MaxRenderShapelessRadius*/
    int getMaxRenderShapelessRadius();

    /** Interface method setMaxRenderShapelessRadius
     * @param toSet toSet*/
    void setMaxRenderShapelessRadius(int toSet);

    /** Interface method isEnabledShapelessVerticalMode
     * @return EnabledShapelessVerticalMode*/
    boolean isEnabledShapelessVerticalMode();

    /** Interface method setEnabledShapelessVerticalMode
     * @param toSet toSet*/
    void setEnabledShapelessVerticalMode(boolean toSet);

    /** Interface method getMaxShapelessVerticalRadius
     * @return MaxShapelessVerticalRadius*/
    int getMaxShapelessVerticalRadius();

    /** Interface method setMaxShapelessVerticalRadius
     * @param toSet toSet*/
    void setMaxShapelessVerticalRadius(int toSet);

    /** Interface method getMaxRenderShapelessVerticalRadius
     * @return MaxRenderShapelessVerticalRadius*/
    int getMaxRenderShapelessVerticalRadius();

    /** Interface method setMaxRenderShapelessVerticalRadius
     * @param toSet toSet*/
    void setMaxRenderShapelessVerticalRadius(int toSet);

    /** Interface method isEnabledTunnelMode
     * @return EnabledTunnelMode*/
    boolean isEnabledTunnelMode();

    /** Interface method setEnabledTunnelMode
     * @param toSet toSet*/
    void setEnabledTunnelMode(boolean toSet);

    /** Interface method isEnabledMineshaftMode
     * @return EnabledMineshaftMode*/
    boolean isEnabledMineshaftMode();

    /** Interface method setEnabledMineshaftMode
     * @param toSet toSet*/
    void setEnabledMineshaftMode(boolean toSet);

    /** Interface method isEnabledVegetationMode
     * @return EnabledVegetationMode*/
    boolean isEnabledVegetationMode();

    /** Interface method setEnabledVegetationMode
     * @param toSet toSet*/
    void setEnabledVegetationMode(boolean toSet);

    /** Interface method isEnabledOresMode
     * @return EnabledOresMode*/
    boolean isEnabledOresMode();

    /** Interface method setEnabledOresMode
     * @param toSet toSet*/
    void setEnabledOresMode(boolean toSet);

    /** Interface method isEnabledCropsMode
     * @return EnabledCropsMode*/
    boolean isEnabledCropsMode();

    /** Interface method setEnabledCropsMode
     * @param toSet toSet*/
    void setEnabledCropsMode(boolean toSet);

    /** Interface method isEnabledTreesMode
     * @return EnabledTreesMode*/
    boolean isEnabledTreesMode();

    /** Interface method setEnabledTreesMode
     * @param toSet toSet*/
    void setEnabledTreesMode(boolean toSet);

    /** Interface method isEnabledFastEat
     * @return EnabledFastEat*/
    boolean isEnabledFastEat();

    /** Interface method setEnabledFastEat
     * @param toSet toSet*/
    void setEnabledFastEat(boolean toSet);

    /** Interface method isEnabledFastPlace
     * @return EnabledFastPlace*/
    boolean isEnabledFastPlace();

    /** Interface method setEnabledFastPlace
     * @param toSet toSet*/
    void setEnabledFastPlace(boolean toSet);

    /** Interface method isEnabledKeyPressSound
     * @return EnabledKeyPressSound*/
    boolean isEnabledKeyPressSound();

    /** Interface method setEnabledKeyPressSound
     * @param toSet setEnabledKeyPressSound*/
    void setEnabledKeyPressSound(boolean toSet);
}
