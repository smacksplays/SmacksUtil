package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
/** class AdvancedMagnetItem */
public class AdvancedMagnetItem extends MagnetItem {
    /** Constructor
     * @param properties properties */
    public AdvancedMagnetItem(Properties properties) {
        super(properties);
    }

    /** Notify Player
     * @param player player
     * @param msg msg
     * @param color color */
    @Override
    public void notifyPlayer(Player player, String msg, int color){
        player.displayClientMessage(Component.literal("Advanced Magnet: " + msg).withColor(color), true);
    }

    /** Get Range
     * @return Range */
    @Override
    public int getRange() {
        return 10;
    }
}
