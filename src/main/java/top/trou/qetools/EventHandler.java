package top.trou.qetools;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import top.trou.qetools.item.QETool;

@Mod.EventBusSubscriber(modid = "qetools")
public class EventHandler {
    private static Item[] items = {new QETool("IRON"), new QETool("GOLD"), new QETool("WOOD"), new QETool("DIAMOND"), new QETool("STONE")};

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent event) {
        if (event.getSide().isClient()) return;
        if (event.getHand().equals(EnumHand.OFF_HAND)) return;
        if (event.getItemStack().getItem() instanceof QETool) {
            Material curMaterial = event.getWorld().getBlockState(event.getPos()).getMaterial();
            NBTTagCompound nbtTag = event.getItemStack().hasTagCompound() ? event.getItemStack().getTagCompound() : new NBTTagCompound();
            nbtTag.setString("toolType", ToolUtils.getRightTool(curMaterial));
            event.getItemStack().setTagCompound(nbtTag);
        }
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(items);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        final ModelResourceLocation modelStick = new ModelResourceLocation("stick");
        for (Item item : items) {
            QETool tool = (QETool) item;
            ModelLoader.registerItemVariants(tool, tool.getModelPickaxe(), tool.getModelAxe(), tool.getModelShovel(), modelStick);
            ModelLoader.setCustomMeshDefinition(tool, itemStack -> {
                if (itemStack.hasTagCompound()) {
                    NBTTagCompound nbtTag = itemStack.getTagCompound();
                    switch (nbtTag.getString("toolType")) {
                        case "pickaxe":
                            return tool.getModelPickaxe();
                        case "axe":
                            return tool.getModelAxe();
                        case "shovel":
                            return tool.getModelShovel();
                        default:
                            return modelStick;
                    }
                }
                return modelStick;
            });
        }
    }
}
