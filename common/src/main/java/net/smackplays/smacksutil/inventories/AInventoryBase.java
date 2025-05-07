package net.smackplays.smacksutil.inventories;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public abstract class AInventoryBase implements IInventoryBase{
    public ItemStack stack;
    public RegistryAccess registryAccess;
    public NonNullList<ItemStack> items;
    public AInventoryBase(ItemStack stack, RegistryAccess registryAccess, int inventorySize){
        this.stack = stack;
        this.registryAccess = registryAccess;
        this.items = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            loadAllItems(tag, this.items, this.registryAccess);
        }
    }
    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setChanged() {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null && data.copyTag().getCompound("Items").isPresent()) {
            CompoundTag tag = data.copyTag().getCompound("Items").get();
            tag = saveAllItems(tag, items, registryAccess);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        } else if (data != null) {
            CompoundTag tag = new CompoundTag();
            CompoundTag item_tag = new CompoundTag();
            item_tag = saveAllItems(item_tag, items, registryAccess);
            tag.put("Items", item_tag);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        } else {
            stack.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        }
    }

    public void loadAllItems (CompoundTag tag, NonNullList<ItemStack> items) {
        loadAllItems(tag, items, registryAccess);
    }

}
