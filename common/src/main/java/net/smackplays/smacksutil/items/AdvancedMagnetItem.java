package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class AdvancedMagnetItem extends MagnetItem {

    public AdvancedMagnetItem(Properties properties) {
        super(properties);
    }

    @Override
    public void notifyPlayer(Player player, String msg, int color){
        player.displayClientMessage(Component.literal("Advanced Magnet: " + msg).withColor(color), true);
    }

    @Override
    public int getRange() {
        return 10;
    }
}
