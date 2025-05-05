package net.smackplays.smacksutil.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.Objects;

public class SortComparator implements Comparator<ItemStack> {
    public SortComparator(){

    }
    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        String name1;
        int count1 = o1.getCount();
        Component customName1 = o1.getCustomName();
        name1 = Component.translatable(Objects.requireNonNullElseGet(customName1, o1::getItemName).getString()).getString();

        String name2;
        int count2 = o2.getCount();
        Component customName2 = o2.getCustomName();
        name2 = Component.translatable(Objects.requireNonNullElseGet(customName2, o2::getItemName).getString()).getString();

        if (name1.equals("Air") && name2.equals("Air")) return 0;
        else if (name1.equals("Air")) return 1;
        else if (name2.equals("Air")) return -1;
        else{
            if (count1 == count2){
                // different names => compare Strings
                return name1.compareTo(name2);
            } else {
                // same name compare count
                if (count1 < count2) return 1;
                else return -1;
            }
        }
    }
}
