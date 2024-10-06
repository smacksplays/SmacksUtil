package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonTeleportationNBTPacketHandler;

public class C2STeleportationNBTPacketHandler {
    public static void handle(final C2STeleportationNBTPacket data, ServerPlayNetworking.Context context) {
        Vec3 pos = new Vec3(data.pos().x, data.pos().y, data.pos().z);
        C2SCommonTeleportationNBTPacketHandler.handle(context.player(), pos, data.xRot(), data.yRot(), data.name(), data.dim(), data.remove());
    }
}
