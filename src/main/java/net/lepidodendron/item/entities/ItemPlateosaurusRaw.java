
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
public class ItemPlateosaurusRaw extends ElementsLepidodendronMod.ModElement {
	@GameRegistry.ObjectHolder("lepidodendron:plateosaurus_raw")
	public static final Item block = null;
	public ItemPlateosaurusRaw(ElementsLepidodendronMod instance) {
		super(instance, LepidodendronSorter.plateosaurus_raw);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemFoodCustom());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(block, 0, new ModelResourceLocation("lepidodendron:entities/plateosaurus_raw", "inventory"));
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		OreDictionary.registerOre("dnaPNPlateosaurus", ItemPlateosaurusRaw.block);
		OreDictionary.registerOre("listAllmeatraw", ItemPlateosaurusRaw.block);
		OreDictionary.registerOre("foodMeat", ItemPlateosaurusRaw.block);
	}

	public static class ItemFoodCustom extends ItemFood {
		public ItemFoodCustom() {
			super(3, 0.3f, false);
			setTranslationKey("pf_plateosaurus_raw");
			setRegistryName("plateosaurus_raw");
			setCreativeTab(TabLepidodendronMobile.tab);
			setMaxStackSize(64);
		}
	}
}
