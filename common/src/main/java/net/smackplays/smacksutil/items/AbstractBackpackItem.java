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


/**
 * @author smack_plays
 * Test
 */
public abstract class AbstractBackpackItem extends Item{
    public AbstractBackpackItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) return InteractionResult.PASS;
        if (player.isCrouching()) return InteractionResult.PASS;
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionResult.PASS;
        player.openMenu(createScreenHandlerFactory(player.getMainHandItem()));

        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null) return InteractionResult.FAIL;
        if (context.getPlayer().isCrouching()) return InteractionResult.PASS;
        use(context.getLevel(), context.getPlayer(), context.getHand());
        return InteractionResult.SUCCESS;
    }

    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new SimpleMenuProvider((i, playerInventory, playerEntity) ->
                new AbstractBackpackMenu(MenuType.GENERIC_9x6, i, playerInventory, new BackpackInventory(stack, playerInventory.player.registryAccess())), stack.getHoverName());
    }
}