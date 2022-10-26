
package net.lepidodendron.item.entities;

import net.lepidodendron.ElementsLepidodendronMod;
import net.lepidodendron.LepidodendronSorter;
import net.lepidodendron.creativetab.TabLepidodendronMisc;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

@ElementsLepidodendronMod.ModElement.Tag
public class ItemWingTitanopteraClatrotitan extends ElementsLepidodendronMod.ModElement {
	@GameRegistry.ObjectHolder("lepidodendron:wing_clatrotitan")
	public static final Item block = null;
	public ItemWingTitanopteraClatrotitan(ElementsLepidodendronMod instance) {
		super(instance, LepidodendronSorter.wing_clatrotitan);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		OreDictionary.registerOre("dnaPNTitanopteraClatrotitan", ItemWingTitanopteraClatrotitan.block);
		OreDictionary.registerOre("wingInsect", ItemWingTitanopteraClatrotitan.block);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(block, 0, new ModelResourceLocation("lepidodendron:entities/wing_clatrotitan", "inventory"));
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			setMaxDamage(0);
			maxStackSize = 64;
			setTranslationKey("pf_wing_clatrotitan");
			setRegistryName("wing_clatrotitan");
			setCreativeTab(TabLepidodendronMisc.tab);
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getMaxItemUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, IBlockState par2Block) {
			return 1F;
		}

		@SideOnly(Side.CLIENT)
		@Override
	    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
	        tooltip.add("Can be used to craft arrows");
	        super.addInformation(stack, player, tooltip, advanced);
	    }
	}
}
