package net.smackplays.smacksutil.networking.c2spacket;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class C2STeleportationNBTPacketHandler {
    public static void handle(final C2STeleportationNBTPacket data, ServerPlayNetworking.Context context) {
        // Do something with the data, on the network thread
        Vector3f pos3f = data.pos();
        Vec3 pos = new Vec3(pos3f.x, pos3f.y, pos3f.z);
        String name = data.name();
        float xRot = data.xRot();
        float yRot = data.yRot();
        boolean remove = data.remove();
        String dim = data.dim();
        Player player = context.player();
        ItemStack stack = player.getMainHandItem();
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null){CompoundTag tag = customData.copyTag();
            ListTag posTag = (ListTag)tag.get("Positions");
            if (posTag == null) {
                posTag = new ListTag();
            }
            CompoundTag teleportTag = new CompoundTag();
            teleportTag.putString("name", name);
            teleportTag.putDouble("x_pos", pos.x());
            teleportTag.putDouble("y_pos", pos.y());
            teleportTag.putDouble("z_pos", pos.z());
            teleportTag.putDouble("x_rot", xRot);
            teleportTag.putDouble("y_rot", yRot);
            teleportTag.putString("dim", dim);
            if (remove){
                for (int i = 0; i < posTag.size(); i++){
                    CompoundTag t = (CompoundTag) posTag.get(i);
                    if (t.contains("name")){
                        String n = t.getString("name");
                        if (n.equals(name)){
                            posTag.remove(i);
                            tag.put("Positions", posTag);
                            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                            player.getInventory().setItem(player.getInventory().selected, stack);
                            player.inventoryMenu.broadcastChanges();
                            return;
                        }
                    }
                }
            } else {
                posTag.add(teleportTag);
            }
            tag.put("Positions", posTag);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            player.getInventory().setItem(player.getInventory().selected, stack);
            player.inventoryMenu.broadcastChanges();
        }
    }
}
