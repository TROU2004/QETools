package top.trou.qetools.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import top.trou.qetools.QETools;

public class QETool extends ItemPickaxe {
    private String toolModelName;

    public QETool(String toolName) {
        super(makeNewToolMaterial(ToolMaterial.valueOf(toolName)));
        this.setCreativeTab(QETools.creativeTab);
        this.setRegistryName("tool_" + toolName.toLowerCase());
        this.setTranslationKey("qetools.tool." + toolName.toLowerCase());
        this.attackDamage = ToolMaterial.valueOf(toolName).getAttackDamage() * 2;
        this.attackSpeed = -3.2F;
        if (toolName.equals("WOOD")) {
            toolModelName = "wooden";
        } else if (toolName.equals("GOLD")) {
            toolModelName = "golden";
        } else {
            toolModelName = toolName.toLowerCase();
        }
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return this.efficiency;
    }

    public ModelResourceLocation getModelPickaxe() {
        return new ModelResourceLocation(toolModelName + "_pickaxe");
    }

    public ModelResourceLocation getModelAxe() {
        return new ModelResourceLocation(toolModelName + "_axe");
    }

    public ModelResourceLocation getModelShovel() {
        return new ModelResourceLocation(toolModelName + "_shovel");
    }

    private static ToolMaterial makeNewToolMaterial(ToolMaterial toolMaterial) {
        return EnumHelper.addToolMaterial("QE" + toolMaterial.name(), toolMaterial.getHarvestLevel(), toolMaterial.getMaxUses() * 4, toolMaterial.getEfficiency() * 1.1f, toolMaterial.getAttackDamage(), toolMaterial.getEnchantability());
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }
}
