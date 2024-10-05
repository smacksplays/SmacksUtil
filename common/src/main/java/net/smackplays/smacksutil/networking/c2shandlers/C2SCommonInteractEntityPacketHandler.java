package net.smackplays.smacksutil.networking.c2shandlers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.smackplays.smacksutil.items.AdvancedMobCatcherItem;
import net.smackplays.smacksutil.items.MobCatcherItem;

import java.util.List;
import java.util.UUID;

public class C2SCommonInteractEntityPacketHandler {
    public static void handle(ServerPlayer player, String entityUUID, boolean isMainHand) {
        ItemStack stack = player.getMainHandItem();
        Level world = player.level();
        AABB aabb = new AABB(player.position().add(-5,-5,-5), player.position().add(5,5,5));
        List<LivingEntity> entityList = world.getEntitiesOfClass(LivingEntity.class, aabb, entity -> true);
        LivingEntity livingEntity = null;
        for  (LivingEntity e : entityList){
            if (e.getUUID().equals(UUID.fromString(entityUUID))){
                livingEntity = e;
            }
        }
        if (livingEntity == null) return;
        InteractionHand hand = isMainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        if (stack.getItem() instanceof MobCatcherItem mobItem){
            if (mobItem.pickupLivingEntity(stack, player, livingEntity, hand)){
                livingEntity.remove(Entity.RemovalReason.KILLED);
            }
        }
        if (stack.getItem() instanceof AdvancedMobCatcherItem mobItem){
            if (mobItem.pickupLivingEntity(stack, player, livingEntity, hand)){
                livingEntity.remove(Entity.RemovalReason.KILLED);
            }
        }
    }
}
