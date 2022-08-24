
package net.lepidodendron.block;

import net.lepidodendron.ElementsLepidodendronMod;
import net.lepidodendron.LepidodendronConfig;
import net.lepidodendron.LepidodendronSorter;
import net.lepidodendron.util.FossilDrops;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@ElementsLepidodendronMod.ModElement.Tag
public class BlockFossilPermian extends ElementsLepidodendronMod.ModElement {
	@GameRegistry.ObjectHolder("lepidodendron:fossil_permian")
	public static final Block block = null;

	public BlockFossilPermian(ElementsLepidodendronMod instance) {
		super(instance, LepidodendronSorter.fossil_permian);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockCustom().setRegistryName("fossil_permian"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation("lepidodendron:fossil_permian", "inventory"));
	}

	@Override
	public void generateWorld(Random random, int chunkX, int chunkZ, World world, int dimID, IChunkGenerator cg, IChunkProvider cp) {
		boolean dimensionCriteria = false;
		if ((dimID == 0
				|| dimID == LepidodendronConfig.dimPleistocene
				|| dimID == LepidodendronConfig.dimNeogene
				|| dimID == LepidodendronConfig.dimPaleogene
				|| dimID == LepidodendronConfig.dimCretaceous
				|| dimID == LepidodendronConfig.dimJurassic
				|| dimID == LepidodendronConfig.dimTriassic
		) && LepidodendronConfig.genFossil)
			dimensionCriteria = true;
		if (!dimensionCriteria)
			return;

		int dimDepth = 32;
		if (dimID == LepidodendronConfig.dimTriassic) {
			dimDepth = dimDepth - 24;
		}
		if (dimID == LepidodendronConfig.dimJurassic) {
			dimDepth = dimDepth - 20;
		}
		if (dimID == LepidodendronConfig.dimCretaceous) {
			dimDepth = dimDepth - 16;
		}
		if (dimID == LepidodendronConfig.dimPaleogene) {
			dimDepth = dimDepth - 12;
		}
		if (dimID == LepidodendronConfig.dimNeogene) {
			dimDepth = dimDepth - 8;
		}
		if (dimID == LepidodendronConfig.dimPleistocene) {
			dimDepth = dimDepth - 4;
		}
		int yy = Math.max(world.getSeaLevel() - dimDepth, 1);

		for (int i = 0; i < 24; i++) {
			int x = chunkX + random.nextInt(16);// ffs they built in the offset to the vanilla WorldGenMineable! + 8;
			int y = yy + random.nextInt(4); //Anywhere in the layer which is 4 thick (0-3)
			int z = chunkZ + random.nextInt(16);// ffs they built in the offset to the vanilla WorldGenMineable! + 8;
			if (random.nextInt(dimDepth) == 0) {
				(new WorldGenMinable(block.getDefaultState(), 5, new com.google.common.base.Predicate<IBlockState>() {
					public boolean apply(IBlockState blockAt) {
						boolean blockCriteria = false;
						IBlockState require;
						if (blockAt.getBlock() == Blocks.STONE.getDefaultState().getBlock())
							blockCriteria = true;
						return blockCriteria;
					}
				})).generate(world, random, new BlockPos(x, y, z));
			}
		}

		//Small chance of mixed up fossils in hills etc:
		for (int i = 0; i < 4; i++) {
			int x = chunkX + random.nextInt(16) + 8;
			int y = (world.getSeaLevel() + 6) + random.nextInt(254 - (world.getSeaLevel() + 6));
			int z = chunkZ + random.nextInt(16) + 8;
			(new WorldGenMinable(block.getDefaultState(), 3, new com.google.common.base.Predicate<IBlockState>() {
				public boolean apply(IBlockState blockAt) {
					boolean blockCriteria = false;
					IBlockState require;
					if (blockAt.getBlock() == Blocks.STONE.getDefaultState().getBlock())
						blockCriteria = true;
					return blockCriteria;
				}
			})).generate(world, random, new BlockPos(x, y, z));
		}

	}

	public static class BlockCustom extends BlockFossil {
		public BlockCustom() {
			setTranslationKey("pf_fossil_permian");
		}

		@Override
		ItemStack[] getDropStack() {
			return FossilDrops.getPermianPetrifiedDrops();
		}
	}
}