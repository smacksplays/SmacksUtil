package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class C2STeleportationPacketHandler {
    public static void handle(C2STeleportationPacket data, IPayloadContext context) {
        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            ServerLevel level = (ServerLevel) context.player().level();
            Player player = context.player();
            /*
            TODO fix
            ResourceKey<?> tempKey = data.levelKey();
            ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, tempKey.location());
            Vec3 pos = data.pos();
            float xRot = data.xRot();
            float yRot = data.yRot();

            MinecraftServer server = level.getServer();
            ServerLevel serverLevel = server.getLevel(levelKey);
            //player.teleportTo(pos.x, pos.y, pos.z);
            if (serverLevel != null) {
                player.teleportTo(serverLevel, pos.x, pos.y, pos.z, Set.of(), yRot, xRot);
            }*/
        });
    }
}
