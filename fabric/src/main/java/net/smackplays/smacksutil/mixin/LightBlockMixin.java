package net.smackplays.smacksutil.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smackplays.smacksutil.ModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightBlock.class)
public class LightBlockMixin {

    @Inject(at = @At("HEAD"), method = "getShape", cancellable = true)
    private void getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        cir.setReturnValue(context.isHoldingItem(Items.LIGHT)
                || context.isHoldingItem(ModClient.LIGHT_WAND)
                || context.isHoldingItem(ModClient.AUTO_LIGHT_WAND) ? Shapes.block() : Shapes.empty());
    }

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    private void use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (level.isClientSide) return;
        if (player.getItemInHand(interactionHand).is(ModClient.LIGHT_WAND)
                || player.getItemInHand(interactionHand).is(ModClient.AUTO_LIGHT_WAND)) {
            if (level.getBlockState(blockPos).is(Blocks.LIGHT)) {
                level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
            }
            cir.setReturnValue(InteractionResult.CONSUME);
        }
    }
}
