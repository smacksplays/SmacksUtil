package net.smackplays.smacksutil.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.AbstractBackpackMenu;
import org.jetbrains.annotations.NotNull;

import static net.smackplays.smacksutil.Constants.C_BACKPACK_COL_NUM;
import static net.smackplays.smacksutil.Constants.C_BACKPACK_ROW_NUM;

/** abstract class AbstractBackpackItem */
public abstract class AbstractBackpackItem extends Item{
    /** Constructor
     * @param properties properties */
    public AbstractBackpackItem(Properties properties) {
        super(properties);
    }

    /** Use on air
     * @param world world
     * @param player player
     * @param hand hand
     * @return Result */
    @Override
    public @NotNull InteractionResult use(Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (world.isClientSide) return InteractionResult.PASS;
        if (player.isCrouching()) return InteractionResult.PASS;
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionResult.PASS;
        player.openMenu(createScreenHandlerFactory(player.getMainHandItem()));
        return InteractionResult.SUCCESS;
    }

    /** Use on block
     * @param context context
     * @return Result */
    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null) return InteractionResult.FAIL;
        if (context.getPlayer().isCrouching()) return InteractionResult.PASS;
        use(context.getLevel(), context.getPlayer(), context.getHand());
        return InteractionResult.SUCCESS;
    }

    /** createScreenHandlerFactory
     * @param stack ItemStack
     * @return MenuProvider */
    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new SimpleMenuProvider((i, playerInventory, playerEntity) ->
                new AbstractBackpackMenu(MenuType.GENERIC_9x6, i, playerInventory, new BackpackInventory(stack, playerInventory.player.registryAccess(), C_BACKPACK_ROW_NUM * C_BACKPACK_COL_NUM + 4)), stack.getHoverName());
    }
}