package net.smackplays.smacksutil.inventories;


import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.NotNull;

public class EnchantmentToolInventory implements IEnchantmentToolInventory {
    private final ItemStack stack;
    private final HolderLookup.Provider provider;
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public EnchantmentToolInventory(ItemStack stack, HolderLookup.Provider provider) {
        this.stack = stack;
        this.provider = provider;
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        assert data != null;
        CompoundTag tag = data.copyTag();

        ContainerHelper.loadAllItems(tag, items, provider);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setChanged() {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        assert data != null;
        CompoundTag tag = data.copyTag().getCompound("enchantment_tool");
        tag = ContainerHelper.saveAllItems(tag, items, provider);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return IEnchantmentToolInventory.super.getItem(slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }
}