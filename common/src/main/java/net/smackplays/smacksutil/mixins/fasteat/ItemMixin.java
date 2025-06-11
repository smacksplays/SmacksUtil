package net.smackplays.smacksutil.mixins.fasteat;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.platform.Services;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Class ItemMixin */
@Mixin(Item.class)
public class ItemMixin {
    /** Constructor*/
    public ItemMixin() {

    }
    /** Used to facilitate FastEat
     * @param stack stack
     * @param entity entity
     * @param cir cir*/
    @Inject(at = @At("HEAD"), method = "getUseDuration", cancellable = true)
    private void getMaxUseTime(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        FoodProperties foodproperties = stack.get(DataComponents.FOOD);
        if (foodproperties != null && Services.CONFIG != null && Services.CONFIG.isEnabledFastEat()) {
            cir.setReturnValue(4);
        }
    }
}
