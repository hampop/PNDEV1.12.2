
package net.lepidodendron.item.entities;

import net.lepidodendron.ElementsLepidodendronMod;
import net.lepidodendron.LepidodendronSorter;
import net.lepidodendron.creativetab.TabLepidodendronMobile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ElementsLepidodendronMod.ModElement.Tag
public class ItemVachonisiaRaw extends ElementsLepidodendronMod.ModElement {
	@GameRegistry.ObjectHolder("lepidodendron:vachonisia_raw")
	public static final Item block = null;
	public ItemVachonisiaRaw(ElementsLepidodendronMod instance) {
		super(instance, LepidodendronSorter.vachonisia_raw);
	}

	@Override
	public void initElements() {

		//elements.items.add(() -> new ItemFoodCustom());
	}
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		//OreDictionary.registerOre("dnaPNVachonisia", ItemVachonisiaRaw.block);
		//OreDictionary.registerOre("listAllfishraw", ItemVachonisiaRaw.block);
		//OreDictionary.registerOre("listAllmeatraw", ItemVachonisiaRaw.block);
		//OreDictionary.registerOre("foodMeat", ItemVachonisiaRaw.block);
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		//ModelLoader.setCustomModelResourceLocation(block, 0, new ModelResourceLocation("lepidodendron:entities/vachonisia_raw", "inventory"));
	}

	public static class ItemFoodCustom extends ItemFood {
		public ItemFoodCustom() {
			super(2, 0.1f, false);
			setTranslationKey("pf_vachonisia_raw");
			setRegistryName("vachonisia_raw");
			setCreativeTab(TabLepidodendronMobile.tab);
			setMaxStackSize(64);
		}
	}
}