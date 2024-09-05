package net.smackplays.smacksutil.networking.c2spacket;

import net.neoforged.neoforge.network.handling.IPayloadContext;

public class C2SInteractEntityPacketHandler {
    public static void handle(C2SInteractEntityPacket data, IPayloadContext context) {
        // Do something with the data, on the main thread
        context.enqueueWork(()  -> {
            /*ItemStack stack = data.stack();
            Player player = context.player();
            Level world = player.level();
            AABB aabb = new AABB(player.position().add(-5,-5,-5), player.position().add(5,5,5));
            List<LivingEntity> entityList = world.getEntitiesOfClass(LivingEntity.class, aabb, entity -> true);
            LivingEntity livingEntity = null;
            for  (LivingEntity e : entityList){
                if (e.getUUID().equals(data.entityUUID())){
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
            }*/
        });
    }
}
