package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.MagnetItem;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonToggleLightWandItemPacketHandler;

public class C2SToggleMagnetItemPacket {
    private final int slot;

    public C2SToggleMagnetItemPacket(int s) {
        slot = s;
    }

    public C2SToggleMagnetItemPacket(FriendlyByteBuf buffer) {
        slot = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(slot);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (context.getSender() != null) {
            C2SCommonToggleLightWandItemPacketHandler.handle(context.getSender(), slot);
        }
    }
}