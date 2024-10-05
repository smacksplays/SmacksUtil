package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.items.AutoLightWandItem;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonTeleportationPacketHandler;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonToggleLightWandItemPacketHandler;
import top.theillusivec4.curios.api.CuriosApi;

public class C2SToggleLightWandItemPacketHandler {
    public static void handle(final C2SToggleLightWandItemPacket data, final IPayloadContext context) {
        C2SCommonToggleLightWandItemPacketHandler.handle((ServerPlayer) context.player(), data.slot());
    }
}
