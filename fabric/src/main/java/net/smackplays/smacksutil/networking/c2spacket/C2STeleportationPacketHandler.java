package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonTeleportationPacketHandler;

public class C2STeleportationPacketHandler {
    public static void handle(C2STeleportationPacket data, ServerPlayNetworking.Context context) {
        Vec3 pos = new Vec3(data.pos().x, data.pos().y, data.pos().z);
        C2SCommonTeleportationPacketHandler.handle(context.player(), context.player().level(), data.levelKey(), pos, data.xRot(), data.yRot());
    }
}
