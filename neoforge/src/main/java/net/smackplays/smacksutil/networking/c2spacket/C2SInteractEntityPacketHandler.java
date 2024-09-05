package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.smackplays.smacksutil.items.AdvancedMobCatcherItem;
import net.smackplays.smacksutil.items.MobCatcherItem;

import java.util.List;
import java.util.UUID;

public class C2SInteractEntityPacketHandler {
    public static void handle(C2SInteractEntityPacket data, IPayloadContext context) {
        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            Player player = context.player();
            ItemStack stack = player.getMainHandItem();
            Level world = player.level();
            AABB aabb = new AABB(player.position().add(-5,-5,-5), player.position().add(5,5,5));
            List<LivingEntity> entityList = world.getEntitiesOfClass(LivingEntity.class, aabb, entity -> true);
            LivingEntity livingEntity = null;
            for  (LivingEntity e : entityList){
                if (e.getUUID().equals(UUID.fromString(data.entityUUID()))){
                    livingEntity = e;
                }
            }
            if (livingEntity == null) return;
            InteractionHand hand = data.hand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
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
        });
    }
}
