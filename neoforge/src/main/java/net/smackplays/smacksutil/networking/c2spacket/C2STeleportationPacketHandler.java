package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Set;

public class C2STeleportationPacketHandler {
    public static void handle(C2STeleportationPacket data, IPayloadContext context) {
        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            ServerLevel level = (ServerLevel) context.player().level();
            Player player = context.player();

            ResourceKey<?> tempKey = null;

            Set<ResourceKey<Level>> levelSet = player.registryAccess().registryOrThrow(Registries.DIMENSION).registryKeySet();
            for (ResourceKey<Level> key : levelSet){
                if (key.toString().equals(data.levelKey())){
                    tempKey = key;
                }
            }
            if (tempKey != null){
                ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, tempKey.location());
                Vec3 pos = new Vec3(data.pos().x, data.pos().y, data.pos().z);
                float xRot = data.xRot();
                float yRot = data.yRot();

                MinecraftServer server = level.getServer();
                ServerLevel serverLevel = server.getLevel(levelKey);
                //player.teleportTo(pos.x, pos.y, pos.z);
                if (serverLevel != null) {
                    player.teleportTo(serverLevel, pos.x, pos.y, pos.z, Set.of(), yRot, xRot);
                }
            }
        });
    }
}
