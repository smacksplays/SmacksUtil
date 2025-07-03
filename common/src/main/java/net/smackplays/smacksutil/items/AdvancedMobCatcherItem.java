package net.smackplays.smacksutil.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ProblemReporter;
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
import net.minecraft.world.level.storage.TagValueOutput;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

public class AdvancedMobCatcherItem extends Item {
    private boolean isHolding;

    public AdvancedMobCatcherItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null){
            CompoundTag mainTag = customData.copyTag();
            ListTag listTag = (ListTag) mainTag.get("Entities");
            if (listTag != null && !listTag.isEmpty()) {
                CompoundTag tag = (CompoundTag) listTag.getFirst();
                BlockPos clicked = context.getClickedPos();
                if (isHolding(stack)
                        && world.getBlockState(clicked.above()).getCollisionShape(world, clicked.above()).isEmpty()) {
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
                    listTag.remove(tag);
                    if (listTag.isEmpty()) {
                        mainTag.putBoolean("is_Holding", false);
                    }
                    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(mainTag));
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    public boolean pickupLivingEntity(@NotNull ItemStack stack, Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand interactionHand) {
        Level world = player.level();
        ItemStack mainHandStack = player.getItemInHand(interactionHand);
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        CustomData maincustomData = mainHandStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null && maincustomData != null){
            CompoundTag tag = customData.copyTag();ListTag list;
            if (!tag.contains("Entities")) {
                list = new ListTag();
                tag.put("Entities", list);
            }
            list = (ListTag) tag.get("Entities");
            if (list != null && isBelowMax(mainHandStack) && !world.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
                TagValueOutput out = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, player.registryAccess());
                livingEntity.save(out);
                // TODO
                //livingEntity.addAdditionalSaveData(out);
                CompoundTag entityTag = out.buildResult();
                if (!list.contains(entityTag)) {
                    list.add(entityTag);
                }
                tag.put("Entities", list);
                tag.putBoolean("is_Holding", true);
                mainHandStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                setHolding(true);
                return true;
            }
        }
        return false;
    }

    public boolean isHolding(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if(customData != null) {
            CompoundTag tag = customData.copyTag();
            if (tag.contains("is_Holding")) {
                isHolding = tag.getBoolean("is_Holding").orElse(false);
            } else {
                return false;
            }
        }
        return isHolding;
    }

    public boolean isBelowMax(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if(customData != null){
            CompoundTag tag = customData.copyTag();
            if (tag.contains("is_Holding")) {
                ListTag listTag = (ListTag) tag.get("Entities");
                return listTag != null && listTag.size() < 10;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay display,
                                @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if(customData != null){
            CompoundTag tag = customData.copyTag();
            if (!tag.isEmpty()) {
                ListTag listTag = (ListTag) tag.get("Entities");
                if (listTag == null) return;
                for (Tag ltag : listTag) {
                    CompoundTag compoundTag = (CompoundTag) ltag;
                    Component storedEntity = Component.literal("Entity: " +
                            Component.translatable("entity." + compoundTag.getString("id").orElse("").replace(":", ".")).getString());
                    consumer.accept(storedEntity);
                }
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
