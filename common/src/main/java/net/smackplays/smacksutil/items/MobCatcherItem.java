package net.smackplays.smacksutil.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

public class MobCatcherItem extends Item {

    private boolean isHolding;

    public MobCatcherItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null){
            CompoundTag tag = customData.copyTag();
            BlockPos clicked = context.getClickedPos();
            if (!world.isClientSide && isHolding(stack) && world.getBlockState(clicked.above()).is(Blocks.AIR)) {
                Entity toCreate = EntityType.loadEntityRecursive(tag, world, EntitySpawnReason.COMMAND, entity -> {
                    entity.setPos(clicked.above().getX(), clicked.above().getY(), clicked.above().getZ());
                    entity.setXRot(entity.getXRot());
                    entity.setYRot(entity.getYRot());
                    return entity;
                });
                if (toCreate == null) return InteractionResult.SUCCESS;
                toCreate.setUUID(UUID.randomUUID());
                toCreate.setPos(context.getClickedPos().above().getCenter().add(0, -0.5, 0));
                world.addFreshEntity(toCreate);
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()));
            }
        }

        return InteractionResult.SUCCESS;
    }

    public boolean pickupLivingEntity(@NotNull ItemStack stack, Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand interactionHand) {
        Level world = player.level();
        stack.copy();
        ItemStack mainHandStack = player.getItemInHand(interactionHand);
        if (!isHolding(mainHandStack) && !world.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag tag = new CompoundTag();
            livingEntity.save(tag);
            livingEntity.addAdditionalSaveData(tag);
            tag.putBoolean("is_Holding", true);
            mainHandStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            setHolding(true);
            return true;
        }
        return false;
    }

    public boolean isHolding(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null){
            CompoundTag tag = customData.copyTag();
            if (tag.contains("is_Holding")) {
                isHolding = tag.getBoolean("is_Holding").orElse(false);
            } else {
                return false;
            }
        }
        return isHolding;
    }

    @Override
    public void appendHoverText(ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay display,
                                @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null){
            CompoundTag tag = customData.copyTag();
            if (!tag.isEmpty() && tag.getBoolean("is_Holding").orElse(false)) {
                Component storedEntity = Component.literal("Entity: " +
                        Component.translatable("entity." + tag.getString("id").orElse("").replace(":", ".")).getString());
                consumer.accept(storedEntity);
            }
        }
        super.appendHoverText(stack, context, display, consumer, flag);
    }

    public void setHolding(boolean holding) {
        isHolding = holding;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return isHolding(stack);
    }
}
