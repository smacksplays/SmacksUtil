package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class C2SCommonTeleportationPacketHandler {
    public static void handle(ServerPlayer player, Level level, String levelKey, Vec3 pos, float xRot, float yRot) {
        ResourceKey<?> tempKey = null;

        Set<ResourceKey<Level>> levelSet = player.registryAccess().registryOrThrow(Registries.DIMENSION).registryKeySet();
        for (ResourceKey<Level> key : levelSet){
            if (key.toString().equals(levelKey)){
                tempKey = key;
            }
        }
        if (tempKey != null){
            ResourceKey<Level> resourceKey = ResourceKey.create(Registries.DIMENSION, tempKey.location());
            MinecraftServer server = level.getServer();
            ServerLevel serverLevel = server.getLevel(resourceKey);
            //player.teleportTo(pos.x, pos.y, pos.z);
            if (serverLevel != null) {
                player.teleportTo(serverLevel, pos.x, pos.y, pos.z, Set.of(), yRot, xRot);
            }
        }
    }
}
