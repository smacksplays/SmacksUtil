package net.smackplays.smacksutil.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractEnchantingToolItem extends Item {
    public AbstractEnchantingToolItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResult.PASS;
        if (player.isCrouching()) return InteractionResult.PASS;
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionResult.PASS;
        player.openMenu(createScreenHandlerFactory(player.getMainHandItem()));

        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null) return InteractionResult.FAIL;
        if (context.getPlayer().isCrouching()) return InteractionResult.PASS;
        use(context.getLevel(), context.getPlayer(), context.getHand());
        return InteractionResult.SUCCESS;
    }

    abstract MenuProvider createScreenHandlerFactory(ItemStack stack);

}
