
package net.lepidodendron.item.entities;

import net.lepidodendron.ElementsLepidodendronMod;
import net.lepidodendron.LepidodendronSorter;
import net.lepidodendron.creativetab.TabLepidodendronMobile;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@ElementsLepidodendronMod.ModElement.Tag
public class ItemFurcacaudaRaw extends ElementsLepidodendronMod.ModElement {
	@GameRegistry.ObjectHolder("lepidodendron:furcacauda_raw")
	public static final Item block = null;
	public ItemFurcacaudaRaw(ElementsLepidodendronMod instance) {
		super(instance, LepidodendronSorter.furcacauda_raw);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemFoodCustom());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(block, 0, new ModelResourceLocation("lepidodendron:entities/furcacauda_raw", "inventory"));
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
		OreDictionary.registerOre("dnaPNFurcacauda", ItemFurcacaudaRaw.block);
		OreDictionary.registerOre("listAllfishraw", ItemFurcacaudaRaw.block);
		OreDictionary.registerOre("listAllmeatraw", ItemFurcacaudaRaw.block);
		OreDictionary.registerOre("foodMeat", ItemFurcacaudaRaw.block);
	}

	public static class ItemFoodCustom extends ItemFood {
		public ItemFoodCustom() {
			super(2, 0.1f, false);
			setTranslationKey("pf_furcacauda_raw");
			setRegistryName("furcacauda_raw");
			setCreativeTab(TabLepidodendronMobile.tab);
			setMaxStackSize(64);
		}
	}
}
