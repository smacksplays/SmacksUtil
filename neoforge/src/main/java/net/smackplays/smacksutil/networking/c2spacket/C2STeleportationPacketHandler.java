package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonTeleportationNBTPacketHandler;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonTeleportationPacketHandler;

import java.util.Set;

public class C2STeleportationPacketHandler {
    public static void handle(C2STeleportationPacket data, IPayloadContext context) {
        Vec3 pos = new Vec3(data.pos().x, data.pos().y, data.pos().z);
        C2SCommonTeleportationPacketHandler.handle((ServerPlayer) context.player(), context.player().level(), data.levelKey(), pos, data.xRot(), data.yRot());
    }
}
