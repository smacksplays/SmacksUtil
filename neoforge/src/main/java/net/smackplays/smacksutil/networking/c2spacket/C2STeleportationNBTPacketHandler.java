package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonTeleportationNBTPacketHandler;

public class C2STeleportationNBTPacketHandler {
    public static void handle(final C2STeleportationNBTPacket data, final IPayloadContext context) {
        Vec3 pos = new Vec3(data.pos().x, data.pos().y, data.pos().z);
        C2SCommonTeleportationNBTPacketHandler.handle((ServerPlayer) context.player(), pos, data.xRot(), data.yRot(), data.name(), data.dim(), data.addRemove());
    }
}
