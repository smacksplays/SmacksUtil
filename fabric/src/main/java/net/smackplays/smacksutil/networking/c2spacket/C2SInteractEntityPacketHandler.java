package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.smackplays.smacksutil.items.AdvancedMobCatcherItem;
import net.smackplays.smacksutil.items.MobCatcherItem;
import net.smackplays.smacksutil.networking.c2shandlers.C2SCommonInteractEntityPacketHandler;

import java.util.List;
import java.util.UUID;

public class C2SInteractEntityPacketHandler {
    public static void handle(C2SInteractEntityPacket data, ServerPlayNetworking.Context context) {
        C2SCommonInteractEntityPacketHandler.handle(context.player(), data.entityUUID(), data.hand());
    }
}
