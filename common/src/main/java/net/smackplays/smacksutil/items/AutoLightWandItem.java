package net.smackplays.smacksutil.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.smackplays.smacksutil.util.PlayerComparator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

public class AutoLightWandItem extends LightWandItem {
    private static final int GREEN = 65280;
    private static final int RED = 16711680;

    public AutoLightWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level world, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (world.isClientSide) return InteractionResult.SUCCESS;
        if (player.isCrouching()) {
            toggle(stack, player);
            return InteractionResult.SUCCESS;
        }
        return super.use(world, player, interactionHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull ServerLevel world, @NotNull Entity entity, EquipmentSlot slot) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null && !world.isClientSide && customData.copyTag().getBoolean("enabled").orElse(false)) {
            Player player = (Player) entity;

            BlockPos sourcePos = player.blockPosition();
            ArrayList<BlockPos> toDark = new ArrayList<>();

            for (int x = -4; x < 4; x++) {
                for (int y = -2; y < 2; y++) {
                    for (int z = -4; z < 4; z++) {
                        BlockPos curr = sourcePos.offset(x, y, z);
                        int light = world.getBrightness(LightLayer.BLOCK, curr);
                        if (light <= 9 && !world.getBlockState(curr.below()).is(Blocks.AIR)
                                && (world.getBlockState(curr).is(Blocks.AIR)
                                || world.getBlockState(curr).is(Blocks.CAVE_AIR))
                                && world.getBlockState(curr).getFluidState().isEmpty()
                                && Block.isShapeFullBlock(world.getBlockState(curr.below()).getShape(world, curr))) {
                            toDark.add(curr);
                        }
                    }
                }
            }

            toDark.sort(new PlayerComparator(player));
            if (!toDark.isEmpty()) {
                world.setBlockAndUpdate(toDark.getFirst(), Blocks.LIGHT.defaultBlockState());
            }
        }
        super.inventoryTick(stack, world, entity, slot);
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

    public void toggle(ItemStack stack, Player player) {
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
        player.displayClientMessage(Component.literal("Auto Light Wand: " + msg).withColor(color), true);
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
}
