package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.MagnetItem;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonToggleMagnetItemPacketHandler;

public class C2SToggleMagnetItemPacketHandler {
    public static void handle(C2SToggleMagnetItemPacket data, ServerPlayNetworking.Context context) {
        C2SCommonToggleMagnetItemPacketHandler.handle(context.player(), data.slot());
    }
}
