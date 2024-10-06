package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonEnchantPacketHandler;

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
        if (context.getSender() != null) {
            C2SCommonEnchantPacketHandler.handle(context.getSender(), enchantment, level, addRemove);
        }
    }
}