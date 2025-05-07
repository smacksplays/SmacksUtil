package net.smackplays.smacksutil.menus;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.slots.BackpackSlot;
import net.smackplays.smacksutil.util.SortComparator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractBackpackMenuBase extends AbstractContainerMenu {

    public Container inventory;
    public Inventory playerInventory;
    public int rows;
    public int cols;

    protected AbstractBackpackMenuBase(@Nullable MenuType<?> menuType, int containerId, Inventory playerInv, Container inv, int rows, int cols) {
        super(menuType, containerId);
        inventory = inv;
        playerInventory = playerInv;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index < this.rows * this.cols + 4) {
                if (this.moveItemStack(itemStack2, this.rows * this.cols + 4, this.slots.size(), true, itemStack2.getMaxStackSize())) {
                    return ItemStack.EMPTY;
                }
            } else if (this.moveItemStack(itemStack2, 4, this.rows * this.cols + 4, false, inventory.getMaxStackSize())) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        if (getCarried().isEmpty()) return ItemStack.EMPTY;
        return itemStack;
    }


    protected boolean moveItemStack(ItemStack stack, int min, int max, boolean backwards, int maxStackSize) {
        boolean bl1 = false;
        int counter = min;
        if (backwards) {
            counter = max - 1;
        }

        if (stack.isStackable()) {
            while(!stack.isEmpty() && (backwards ? counter >= min : counter < max)) {
                Slot slot = this.slots.get(counter);
                ItemStack stack1 = slot.getItem();
                if (!stack1.isEmpty() && ItemStack.isSameItemSameComponents(stack, stack1)) {
                    int combinedCount = stack1.getCount() + stack.getCount();
                    if (combinedCount <= maxStackSize) {
                        stack.setCount(0);
                        stack1.setCount(combinedCount);
                        slot.setChanged();
                        bl1 = true;
                    } else if (stack1.getCount() < maxStackSize) {
                        stack.shrink(maxStackSize - stack1.getCount());
                        stack1.setCount(maxStackSize);
                        slot.setChanged();
                        bl1 = true;
                    }
                }

                if (backwards) {
                    --counter;
                } else {
                    ++counter;
                }
            }
        }

        if (!stack.isEmpty()) {
            if (backwards) {
                counter = max - 1;
            } else {
                counter = min;
            }

            while(backwards ? counter >= min : counter < max) {
                Slot slot = this.slots.get(counter);
                ItemStack stack1 = slot.getItem();
                int combinedCount = stack1.getCount() + stack.getCount();
                if (stack1.isEmpty() && slot.mayPlace(stack)) {
                    if (stack.getCount() > maxStackSize) {
                        slot.setByPlayer(stack.split(maxStackSize));
                    } else if (stack.getCount() > 1 && !stack.isStackable()) {
                        slot.setByPlayer(stack.split(1));
                    }else {
                        slot.setByPlayer(stack.split(stack.getCount()));
                    }

                    slot.setChanged();
                    bl1 = true;
                    break;
                } else if (ItemStack.isSameItemSameComponents(stack, stack1) && slot instanceof BackpackSlot && combinedCount < maxStackSize){
                    stack1.grow(stack.getCount());
                    stack.setCount(0);
                }

                if (backwards) {
                    --counter;
                } else {
                    ++counter;
                }
            }
        }
        return !bl1;
    }

    public boolean stillValid(@NotNull Player player) {
        return this.inventory.stillValid(player);
    }

    public void sort() {
        BackpackInventory impInv = (BackpackInventory) inventory;
        NonNullList<ItemStack> items = impInv.getItems();
        List<ItemStack> temp = items.subList(4, rows * cols + 4);

        int maxStackSize = impInv.getMaxStackSize();

        for (int i = 0; i < temp.size(); i++){
            for(int j = 0; j < temp.size(); j++){
                if (i != j){
                    ItemStack stack1 = temp.get(i);
                    ItemStack stack2 = temp.get(j);
                    if (ItemStack.isSameItemSameComponents(stack1, stack2)
                            && ((stack1.isStackable() && stack2.isStackable() && stack1.getCount() + stack2.getCount() <= maxStackSize)
                                || (!stack1.isStackable() && !stack2.isStackable() && stack1.getCount() + stack2.getCount() <= maxStackSize/64))) {
                        stack1.setCount(stack1.getCount() + stack2.getCount());
                        temp.set(i, stack1);
                        temp.set(j, Items.AIR.getDefaultInstance());
                    }
                }
            }
        }
        temp.sort(new SortComparator());
        for (int i = 0; i < temp.size(); i++) {
            items.set(i + 4, temp.get(i));
            this.slots.get(i).set(impInv.getItem(i));
        }
        impInv.setChanged();
    }

    @Override
    public void clicked(int slot_num1, int slot_num2, @NotNull ClickType clickType, @NotNull Player player) {
        if (slot_num1 >= 0 && slot_num2 >= 0){
            Slot slot1 = player.containerMenu.slots.get(slot_num1);
            ItemStack itemStack1 = slot1.getItem();
            ItemStack offHandStack = playerInventory.getItem(slot_num2);
            if (clickType == ClickType.SWAP){
                if (offHandStack.isEmpty() && itemStack1.getCount() <= itemStack1.getMaxStackSize()) {
                    super.clicked(slot_num1, slot_num2, clickType, player);
                } else if (offHandStack.isEmpty() && itemStack1.getCount() > itemStack1.getMaxStackSize()) {
                    int count = itemStack1.getCount();
                    ItemStack offHandStack1 = itemStack1.copy();
                    slot1.setByPlayer(itemStack1.split(count - itemStack1.getMaxStackSize()));
                    offHandStack1.setCount(itemStack1.getMaxStackSize());
                    playerInventory.setItem(slot_num2, offHandStack1);
                } else if (itemStack1.isEmpty()){
                    super.clicked(slot_num1, slot_num2, clickType, player);
                } else if (ItemStack.isSameItemSameComponents(itemStack1, offHandStack)){
                    itemStack1.setCount(itemStack1.getCount() + offHandStack.getCount());
                    playerInventory.setItem(slot_num2, ItemStack.EMPTY);
                } else if (!ItemStack.isSameItemSameComponents(itemStack1, offHandStack)
                        && itemStack1.getCount() <= itemStack1.getMaxStackSize()
                        && offHandStack.getCount() <= offHandStack.getMaxStackSize()) {
                    ItemStack temp = offHandStack.copy();
                    playerInventory.setItem(slot_num2, itemStack1);
                    slot1.set(temp);
                }
                slot1.setChanged();
                return;
            } else if (clickType == ClickType.PICKUP && slot_num1 < rows * cols + 4 && slot_num2 == 0){
                if (itemStack1.isEmpty() && getCarried().isEmpty()) return;
            }
        }
        super.clicked(slot_num1, slot_num2, clickType, player);
    }
}
