package net.smackplays.smacksutil.items;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/** abstract class AbstractEffectTotemItem */
public abstract class AbstractEffectTotemItem extends Item {
    /** counter */
    private static int counter = 0;
    /** LIST_NAME */
    private static final String LIST_NAME = "effect_list";
    /** EFFECT_TIME */
    private static final int EFFECT_TIME = 30 * 20;

    /** Constructor
     * @param properties properties */
    public AbstractEffectTotemItem(Properties properties) {
        super(properties);
    }

    /** Inventory tick
     * @param stack stack
     * @param level level
     * @param entity entity
     * @param slot slot */
    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level, @NotNull Entity entity, EquipmentSlot slot) {
        if (counter == 50){
            Player player = (Player) entity;
            CustomData data = stack.get(DataComponents.CUSTOM_DATA);
            if (data != null){
                CompoundTag c_tag = data.copyTag();
                if (c_tag.get(LIST_NAME) != null) {
                    Tag effectTag = c_tag.get(LIST_NAME);
                    if (effectTag != null && effectTag.asList().isPresent()){
                        ListTag effectList = effectTag.asList().get();
                        for (Tag tag_1 : effectList){
                            CompoundTag c_tag_1 = (CompoundTag) tag_1;
                            Holder<MobEffect> effect = getEffect(c_tag_1.getStringOr("name", ""));
                            if (effect != null && c_tag_1.getInt("amplifier").isPresent()){
                                int amplifier = c_tag_1.getInt("amplifier").get();
                                player.addEffect(new MobEffectInstance(effect, EFFECT_TIME, amplifier, true, true));
                            }
                        }
                    }
                }
            } else {
                stack.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            }
            counter = 0;
        }
        counter ++;
    }

    /** Use on air
     * @param level level
     * @param player player
     * @param hand hand
     * @return InteractionResult*/
    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level.isClientSide) return InteractionResult.PASS;
        if (player.isCrouching()) return InteractionResult.PASS;
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionResult.PASS;
        player.openMenu(createScreenHandlerFactory(player.getMainHandItem()));
        return InteractionResult.SUCCESS;
    }

    /** Use on Block
     * @param context context
     * @return InteractionResult*/
    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null) return InteractionResult.FAIL;
        if (context.getPlayer().isCrouching()) return InteractionResult.PASS;
        use(context.getLevel(), context.getPlayer(), context.getHand());
        return InteractionResult.SUCCESS;
    }

    /** Abstract createScreenHandlerFactory
     * @param stack  stack
     * @return MenuProvider*/
    abstract MenuProvider createScreenHandlerFactory(ItemStack stack);

    /** getEffect
     * @param name name
     * @return Effect matching name*/
    private Holder<MobEffect> getEffect(String name){
        return switch (name) {
            case "speed" -> MobEffects.SPEED;
            case "slowness" -> MobEffects.SLOWNESS;
            case "haste" -> MobEffects.HASTE;
            case "mining_fatigue" -> MobEffects.MINING_FATIGUE;
            case "strength" -> MobEffects.STRENGTH;
            case "instant_health" -> MobEffects.INSTANT_HEALTH;
            case "instant_damage" -> MobEffects.INSTANT_DAMAGE;
            case "jump_boost" -> MobEffects.JUMP_BOOST;
            case "nausea" -> MobEffects.NAUSEA;
            case "regeneration" -> MobEffects.REGENERATION;
            case "resistance" -> MobEffects.RESISTANCE;
            case "fire_resistance" -> MobEffects.FIRE_RESISTANCE;
            case "water_breathing" -> MobEffects.WATER_BREATHING;
            case "invisibility" -> MobEffects.INVISIBILITY;
            case "blindness" -> MobEffects.BLINDNESS;
            case "night_vision" -> MobEffects.NIGHT_VISION;
            case "hunger" -> MobEffects.HUNGER;
            case "weakness" -> MobEffects.WEAKNESS;
            case "poison" -> MobEffects.POISON;
            case "wither" -> MobEffects.WITHER;
            case "health_boost" -> MobEffects.HEALTH_BOOST;
            case "absorption" -> MobEffects.ABSORPTION;
            case "saturation" -> MobEffects.SATURATION;
            case "glowing" -> MobEffects.GLOWING;
            case "levitation" -> MobEffects.LEVITATION;
            case "luck" -> MobEffects.LUCK;
            case "unluck" -> MobEffects.UNLUCK;
            case "slow_falling" -> MobEffects.SLOW_FALLING;
            case "conduit_power" -> MobEffects.CONDUIT_POWER;
            case "dolphins_grace" -> MobEffects.DOLPHINS_GRACE;
            case "bad_omen" -> MobEffects.BAD_OMEN;
            case "hero_of_the_village" -> MobEffects.HERO_OF_THE_VILLAGE;
            case "darkness" -> MobEffects.DARKNESS;
            case "trial_omen" -> MobEffects.TRIAL_OMEN;
            case "raid_omen" -> MobEffects.RAID_OMEN;
            case "wind_charged" -> MobEffects.WIND_CHARGED;
            case "weaving" -> MobEffects.WEAVING;
            case "oozing" -> MobEffects.OOZING;
            case "infested" -> MobEffects.INFESTED;
            default -> null;
        };
    }
}
