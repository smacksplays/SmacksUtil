package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonEnchantPacketHandler;

public class C2SEnchantPacketHandler {
    public static void handle(C2SEnchantPacket data, IPayloadContext context) {
        C2SCommonEnchantPacketHandler.handle((ServerPlayer) context.player(), data.enchantment(), data.level(), data.remove());
    }
}
