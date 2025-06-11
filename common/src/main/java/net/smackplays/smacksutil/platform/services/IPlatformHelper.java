package net.smackplays.smacksutil.platform.services;

import net.minecraft.world.item.Item;

/**
 * Interface IPlatformHelper */
public interface IPlatformHelper {
    /** Interface method getBackpackItem*/
    Item getBackpackItem();

    /** Interface method getLargeBackpackItem*/
    Item getLargeBackpackItem();

    /** Interface method getMagnetItem*/
    Item getMagnetItem();

    /** Interface method getAdvancedMagnetItem*/
    Item getAdvancedMagnetItem();

    /** Interface method getLightWandItem*/
    Item getLightWandItem();

    /** Interface method getAutoWandItem*/
    Item getAutoWandItem();

    /** Interface method getUpgrade1Item*/
    Item getUpgrade1Item();

    /** Interface method getUpgrade2Item*/
    Item getUpgrade2Item();

    /** Interface method getUpgrade3Item*/
    Item getUpgrade3Item();

    /** Interface method isClient
     * @return true if client*/
    boolean isClient();
}