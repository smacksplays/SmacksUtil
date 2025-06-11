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

/** abstract class AbstractEnchantingToolItem */
public abstract class AbstractEnchantingToolItem extends Item {
    /** Constructor
     * @param properties properties */
    public AbstractEnchantingToolItem(Properties properties) {
        super(properties);
    }

    /** Use on air
     * @param world World
     * @param player Player
     * @param hand Hand
     * @return Result*/
    @Override
    public @NotNull InteractionResult use(Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (world.isClientSide) return InteractionResult.PASS;
        if (player.isCrouching()) return InteractionResult.PASS;
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionResult.PASS;
        player.openMenu(createScreenHandlerFactory(player.getMainHandItem()));

        return InteractionResult.PASS;
    }

    /** Use on block
     * @param context Context
     * @return Result*/
    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null) return InteractionResult.FAIL;
        if (context.getPlayer().isCrouching()) return InteractionResult.PASS;
        use(context.getLevel(), context.getPlayer(), context.getHand());
        return InteractionResult.SUCCESS;
    }

    /** abstract createScreenHandlerFactory
     * @param stack ItemStack
     * @return MenuProvider*/
    abstract MenuProvider createScreenHandlerFactory(ItemStack stack);

}
