package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class C2SEnchantPacket {
    private final String enchantment;
    private final int level;
    private final boolean addRemove;

    public C2SEnchantPacket(String enchantment, int level, boolean addRemove) {
        this.enchantment = enchantment;
        this.level = level;
        this.addRemove = addRemove;
    }

    public C2SEnchantPacket(FriendlyByteBuf buffer) {
        enchantment = buffer.readUtf();
        level = buffer.readInt();
        addRemove = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
       buffer.writeUtf(enchantment);
       buffer.writeInt(level);
       buffer.writeBoolean(addRemove);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;
        AbstractContainerMenu screenHandler = player.containerMenu;
        //screenHandler.slots.get(0).set(stack);
    }
}