package net.smackplays.smacksutil.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class MagnetItem extends Item {

    private static final int GREEN = 65280;
    private static final int RED = 16711680;

    public MagnetItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level world, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (world.isClientSide) return InteractionResult.SUCCESS;
        if (!player.isCrouching()) {
            toggle(stack, player);
            return InteractionResult.SUCCESS;
        }
        return super.use(world, player, interactionHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull ServerLevel world, @NotNull Entity entity, EquipmentSlot equipmentSlot) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if(customData != null){
            CompoundTag tag = customData.copyTag();
            if (tag.getBoolean("enabled").isEmpty()) return;
            if (!world.isClientSide && tag.getBoolean("enabled").get()){
                attract(entity, world, getRange());
            }
        }
        super.inventoryTick(stack, world, entity, equipmentSlot);
    }

    public void attract(Entity entity, Level world, int range){
        AABB area = new AABB(entity.position().add(-range, -range, -range), entity.position().add(range, range, range));
        Player player = (Player) entity;
        List<ItemEntity> entities = world.getEntitiesOfClass(ItemEntity.class, area);
        for (ItemEntity e : entities) {
            e.setPos(player.position());
            e.setPickUpDelay(0);
        }

        List<ExperienceOrb> orbs = world.getEntitiesOfClass(ExperienceOrb.class, area);
        for (ExperienceOrb e : orbs) {
            player.takeXpDelay = 0;
            e.playerTouch(player);
        }
    }

    public void toggle(ItemStack stack, Player player){
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if(customData != null){
            CompoundTag tag = customData.copyTag();
            boolean state = tag.getBoolean("enabled").orElse(false);
            tag.putBoolean("enabled", !state);
            String msg = !state ? "Active" : "Inactive";
            int color = !state ? GREEN : RED;
            notifyPlayer(player, msg, color);
            stack.set(DataComponents.CUSTOM_DATA,CustomData.of(tag));
        }
    }

    public void notifyPlayer(Player player, String msg, int color){
        player.displayClientMessage(Component.literal("Magnet: " + msg).withColor(color), true);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if(customData != null){
            CompoundTag tag = customData.copyTag();
            return tag.getBoolean("enabled").orElse(false);
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay display,
                                @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if(customData != null){
            CompoundTag tag = customData.copyTag();
            if (tag.getBoolean("enabled").orElse(false)) {
                consumer.accept(Component.literal("Active").withColor(GREEN));
            } else {
                consumer.accept(Component.literal("Inactive").withColor(RED));
            }
        }
    }

    public int getRange(){
        return 5;
    }
}
