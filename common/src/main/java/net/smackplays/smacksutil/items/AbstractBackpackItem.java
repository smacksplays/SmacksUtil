package net.smackplays.smacksutil.items;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.AbstractBackpackMenu;
import org.jetbrains.annotations.NotNull;


/**
 * @author smack_plays
 * Test
 */
public abstract class AbstractBackpackItem extends ArmorItem{
    public AbstractBackpackItem(Holder<ArmorMaterial> material) {
        super(material, Type.BODY,
                new Properties()
                        .stacksTo(1)
                        .rarity(Rarity.EPIC)
                        .component(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag()))
                        .component(DataComponents.DYED_COLOR, new DyedItemColor(DyeColor.WHITE.getMapColor().col, true)));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.pass(stack);
        if (player.isCrouching()) return InteractionResultHolder.pass(stack);
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionResultHolder.pass(stack);
        player.openMenu(createScreenHandlerFactory(player.getMainHandItem()));

        return InteractionResultHolder.success(stack);
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