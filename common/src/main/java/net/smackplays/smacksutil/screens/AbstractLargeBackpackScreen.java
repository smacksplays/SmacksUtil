package net.smackplays.smacksutil.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.AbstractLargeBackpackMenu;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.slots.BackpackSlot;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

import static net.smackplays.smacksutil.Constants.C_LARGE_BACKPACK_SCREEN_LOCATION_RL;

/** class AbstractLargeBackpackScreen
 * @param <T> extends AbstractLargeBackpackMenu */
public class AbstractLargeBackpackScreen<T extends AbstractLargeBackpackMenu> extends AbstractContainerScreen<T> {
    /** backgroundWidth */
    protected final int backgroundWidth = 268;
    /** backgroundHeight */
    protected final int backgroundHeight = 274;

    /** Constructor
     * @param handler handler
     * @param inventory inventory
     * @param title title */
    public AbstractLargeBackpackScreen(T handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    /** Render Background
     * @param context context
     * @param delta delta
     * @param mouseX mouseX
     * @param mouseY mouseY */
    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.blit(RenderType::guiTextured, C_LARGE_BACKPACK_SCREEN_LOCATION_RL,
                x, y, 0.0F, 0.0F, 512, 512, 512, 512);
    }

    /** Render
     * @param context context
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @param delta delta */
    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);

        BackpackInventory inv = (BackpackInventory) this.menu.inventory;
        ItemStack backpack = inv.stack;
        CustomData customData = backpack.get(DataComponents.CUSTOM_DATA);
        if (customData != null){
            CompoundTag tag = customData.copyTag();
            inv.loadAllItems(tag, inv.getItems());
        }
        BackpackGuiGraphics c = new BackpackGuiGraphics(context, this.minecraft);
        super.render(c, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }

    /** Render tooltip
     * @param context context
     * @param mouseX mouseX
     * @param mouseY mouseY */
    @Override
    protected void renderTooltip(@NotNull GuiGraphics context, int mouseX, int mouseY) {
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem() && this.hoveredSlot instanceof BackpackSlot backpackSlot) {
            ItemStack hoveredStack = this.hoveredSlot.getItem();
            List<Component> list = this.getTooltipFromContainerItem(hoveredStack);
            list.add(1, Component.literal("Count: " + hoveredStack.getCount() + "/" + backpackSlot.getMaxStackSize(hoveredStack))
                    .withColor(CommonColors.GRAY));
            Optional<TooltipComponent> optional = hoveredStack.getTooltipImage();
            context.renderTooltip(this.font, list, optional, mouseX, mouseY);
        } else if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()){
            super.renderTooltip(context, mouseX, mouseY);
        }
    }

    /** Render labels
     * @param context context
     * @param mouseX mouseX
     * @param mouseY mouseY */
    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, this.titleLabelX - 130, this.titleLabelY - 54, 0x404040, false);
        context.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - 36, this.inventoryLabelY + 53, 0x404040, false);
    }

    /** init */
    @Override
    protected void init() {
        super.init();
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        // Center the title
        titleLabelX = (backgroundWidth - font.width(title)) / 2;
        Button buttonWidget1 = Button.builder(Component.literal("S"), (buttonWidget) -> onButtonWidgetPressed()).pos(x + 228, y + 4).size(12, 12).build();
        this.addRenderableWidget(buttonWidget1);
    }

    /** onButtonWidgetPressed */
    public void onButtonWidgetPressed() {
        if (Services.C2S_PACKET_SENDER != null) {
            Services.C2S_PACKET_SENDER.BackpackSortPacket(this.menu.playerInventory.getSelectedSlot());
        }
    }

    /** has clicked outside
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @param left left
     * @param top top
     * @param button button
     * @return true if clicked outside*/
    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button) {
        return mouseX < (double) (width - backgroundWidth) / 2 - 10
                || mouseX > (double) (width + backgroundWidth) / 2 + 10
                || mouseY < (double) (height - backgroundHeight) / 2 - 10
                || mouseY > (double) (height + backgroundHeight) / 2 + 10;
    }
}