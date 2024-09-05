package net.smackplays.smacksutil.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.CustomData;

public class AdvancedMagnetItem extends MagnetItem {

    public AdvancedMagnetItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).component(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag())));
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
