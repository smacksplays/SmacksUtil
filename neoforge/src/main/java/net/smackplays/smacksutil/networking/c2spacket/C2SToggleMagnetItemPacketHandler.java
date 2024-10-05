package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.MagnetItem;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonToggleLightWandItemPacketHandler;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonToggleMagnetItemPacketHandler;
import top.theillusivec4.curios.api.CuriosApi;

public class C2SToggleMagnetItemPacketHandler {
    public static void handle(final C2SToggleMagnetItemPacket data, final IPayloadContext context) {
        C2SCommonToggleMagnetItemPacketHandler.handle((ServerPlayer) context.player(), data.slot());
    }
}
