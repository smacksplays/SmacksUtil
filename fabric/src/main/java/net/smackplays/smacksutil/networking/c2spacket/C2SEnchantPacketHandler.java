package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonEnchantPacketHandler;

public class C2SEnchantPacketHandler {
    public static void handle(C2SEnchantPacket data, ServerPlayNetworking.Context context) {
        C2SCommonEnchantPacketHandler.handle(context.player(), data.enchantment(), data.level(), data.remove());
    }
}
