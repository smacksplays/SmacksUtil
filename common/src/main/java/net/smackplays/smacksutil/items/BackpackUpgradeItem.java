package net.smackplays.smacksutil.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static net.smackplays.smacksutil.Constants.MOD_ID;

public class BackpackUpgradeItem extends Item {
    private int multiplier = 1;
    @SuppressWarnings("unused")
    public BackpackUpgradeItem(Properties props, int multiplier) {
        super(props);
    }
    public BackpackUpgradeItem(String name, int multiplier){
        super(new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name)))
                .stacksTo(1));
        this.multiplier = multiplier;
    }

    @Override
    public void appendHoverText(ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay display,
                                @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        consumer.accept(Component.literal("Multiplier: " + multiplier));
        super.appendHoverText(stack, context, display, consumer, flag);
    }

    public int getMultiplier(){
        return multiplier;
    }
}
