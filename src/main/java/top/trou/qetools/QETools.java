package top.trou.qetools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "qetools", name = "QETools", version = "1.0")
public class QETools {
    public static CreativeTabs creativeTab = new CreativeTabs("qetools") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.GOLDEN_PICKAXE);
        }
    };
}
