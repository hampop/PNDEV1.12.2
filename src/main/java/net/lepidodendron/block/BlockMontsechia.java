
package net.lepidodendron.block;

import net.lepidodendron.*;
import net.lepidodendron.block.base.IAdvancementGranter;
import net.lepidodendron.item.ItemMontsechiaItem;
import net.lepidodendron.item.ItemMontsechiaSeeds;
import net.lepidodendron.util.BlockSounds;
import net.lepidodendron.util.CustomTrigger;
import net.lepidodendron.util.ModTriggers;
import net.lepidodendron.world.biome.jurassic.BiomeJurassic;
import net.lepidodendron.world.gen.MontsechiaGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@ElementsLepidodendronMod.ModElement.Tag
public class BlockMontsechia extends ElementsLepidodendronMod.ModElement {
	@GameRegistry.ObjectHolder("lepidodendron:montsechia")
	public static final Block block = null;
	public BlockMontsechia(ElementsLepidodendronMod instance) {
		super(instance, LepidodendronSorter.montsechia);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockCustom());
		//elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	//@SideOnly(Side.CLIENT)
	//@Override
	//public void registerModels(ModelRegistryEvent event) {
	//	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
	//			new ModelResourceLocation("lepidodendron:montsechia", "inventory"));
	//}


	@Override
	public void generateWorld(Random random, int chunkX, int chunkZ, World world, int dimID, IChunkGenerator cg, IChunkProvider cp) {

		boolean biomeCriteria = false;
		Biome biome = world.getBiome(new BlockPos(chunkX + 16, world.getSeaLevel(), chunkZ + 16));
		if (!matchBiome(biome, LepidodendronConfigPlants.genMontsechiaBlacklistBiomes)) {
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP))
				biomeCriteria = true;
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MUSHROOM))
				biomeCriteria = false;
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.DEAD))
				biomeCriteria = false;
		}

//		if (biome instanceof BiomeJurassic)
//		{
//			BiomeJurassic biomeJurassic = (BiomeJurassic) biome;
//			if (biomeJurassic.getBiomeType() == EnumBiomeTypeJurassic.Floodplain
//					|| biomeJurassic.getBiomeType() == EnumBiomeTypeJurassic.Forest
//					|| biomeJurassic.getBiomeType() == EnumBiomeTypeJurassic.Mire
//					|| biomeJurassic.getBiomeType() == EnumBiomeTypeJurassic.River
//					|| biomeJurassic.getBiomeType() == EnumBiomeTypeJurassic.IslandRock) {
//				biomeCriteria = true;
//			}
//			else {
//				biomeCriteria = false;
//			}
//		}
		if (!biomeCriteria)
			return;

		int GenChance = 30;
		double GenMultiplier = LepidodendronConfigPlants.multiplierMontsechia;
		if (GenMultiplier < 0) {GenMultiplier = 0;}
		GenChance = Math.min(100, (int) Math.round((double) GenChance * GenMultiplier));
		//Is this a transformed biome?
		if (LepidodendronDecorationHandler.matchBiome(biome, LepidodendronConfigPlants.genTransformBiomes)) {
			//if (biome.getRegistryName().toString().substring(0, biome.getRegistryName().toString().indexOf(":")).equalsIgnoreCase("minecraft"))
			GenChance = Math.min(GenChance * 10, 100);
		}

		if (biome instanceof BiomeJurassic) {
			GenChance = 192;
		}

		for (int i = 0; i < (int) GenChance; i++) {
			int l6 = chunkX + random.nextInt(16) + 8;
			int i11 = random.nextInt(128);
			int l14 = chunkZ + random.nextInt(16) + 8;
			(new MontsechiaGenerator((Block) block)).generate(world, random, new BlockPos(l6, i11, l14));
		}
	}

	public static boolean matchBiome(Biome biome, String[] biomesList) {

		//String regName = biome.getRegistryName().toString();

		String[] var2 = biomesList;
		int var3 = biomesList.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			String checkBiome = var2[var4];
			if (!checkBiome.contains(":")) {
				//System.err.println("modid test: " + biome.getRegistryName().toString().substring(0, biome.getRegistryName().toString().indexOf(":") - 1));
				if (checkBiome.equalsIgnoreCase(
						biome.getRegistryName().toString().substring(0, biome.getRegistryName().toString().indexOf(":"))
				)) {
					return true;
				}
			}
			if (checkBiome.equalsIgnoreCase(biome.getRegistryName().toString())) {
				return true;
			}
		}

		return false;
	}

	public static class BlockCustom extends BlockLilyPad implements IGrowable, net.minecraftforge.common.IShearable, IAdvancementGranter {
		public BlockCustom() {
			//super(Material.PLANTS);
			setSoundType(SoundType.PLANT);
			setHardness(0F);
			setResistance(0F);
			setLightLevel(0F);
			setLightOpacity(0);
			setCreativeTab(null);
			if (LepidodendronConfigPlants.spreadMontsechia && LepidodendronConfig.spreadPlantsAtAll) {
				setTickRandomly(true);
			}
			else {
				setTickRandomly(false);
			}
			setTranslationKey("pf_montsechia");
			setRegistryName("montsechia");
		}

		@Nullable
		@Override
		public CustomTrigger getModTrigger() {
			return ModTriggers.CLICK_MONTSECHIA;
		}

		@Override
		public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
			//super.onEntityCollision(worldIn, pos, state, entityIn);

			if (entityIn instanceof EntityBoat) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				if (!LepidodendronConfig.doPropagation) {
					EntityItem entityToSpawn = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemMontsechiaItem.block, (int) (1)));
					entityToSpawn.setPickupDelay(10);
					worldIn.spawnEntity(entityToSpawn);
				}
				worldIn.playSound(null, pos, SoundType.PLANT.getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			else {
				super.onEntityCollision(worldIn, pos, state, entityIn);
			}
		}

		@Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos){ return true; }
		
		@Override
		public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
			return NonNullList.withSize(1, new ItemStack(ItemMontsechiaItem.block, (int) (1)));
		}

		@SideOnly(Side.CLIENT)
		@Override
    	public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

		@Override
		@Nullable
		public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
			return NULL_AABB;
		}

		@Override
		public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
			return true;
		}

		@Override
		public boolean isFullCube(IBlockState state) {
			return false;
		}

		@Override
		public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
			return new AxisAlignedBB(0D, -0.5D, 0D, 1D, 0.5D, 1D);
		}

		@Override
		public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
	    {}

		@Override
		public boolean isOpaqueCube(IBlockState state) {
			return false;
		}

		@Override
		public boolean isReplaceable(IBlockAccess blockAccess, BlockPos pos) {
			return true;
		}

		@Override
		public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
			return new ItemStack(ItemMontsechiaItem.block, (int) (1));
		}

		@Override
		public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
			if (LepidodendronConfig.doPropagation) {
				drops.add(new ItemStack(Blocks.AIR, (int) (1)));
			}
			else {
				drops.add(new ItemStack(ItemMontsechiaItem.block, (int) (1)));
			}
		}

		@Override
		protected boolean canSilkHarvest()
	    {
	        return false;
	    }

		@Override
		public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos) {
			//super.neighborChanged(state, world, pos, neighborBlock, fromPos);
			{
		        if (!this.canBlockStay(world, pos, state))
		        {
		            world.scheduleUpdate(pos, this, 1);
		        }
		    }
		}

		@Override
		public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
			//super.updateTick(world, pos, state, random);
	    	
	         if (!world.isRemote)
	        {
	            if (!world.isAreaLoaded(pos, 3)) return;
	            
				if (!this.canSurviveAt(world, pos))
		        {
		            world.destroyBlock(pos, false);
		        }
		        else {
		        	Random rand = new Random(); 
		        	int xx = (rand.nextInt(3) - 1) * 2;
		        	int zz = (rand.nextInt(3) - 1) * 2;
					//Try to spread:
					BlockPos targetBlock = pos.add(xx, 0, zz);
					double spread = (double) LepidodendronConfig.spreadPlants;
					if (spread < 1) {
						spread = 1;
					}
					if (spread > 100) {
						spread = 100;
					}
					if (Math.random() > 0.5) {spread = 1;} //lower the chance
					if (Math.random() > (1-(spread/100)) && (targetBlock != pos) && (world.isAirBlock(targetBlock)) && (canSurviveAt(world, targetBlock))) {
						world.setBlockState(targetBlock, BlockMontsechia.block.getDefaultState(), 3);
					}
					//Perhaps the original plant also dies back now, but only if there another plant within 2 blocks (else the colony dies!):
					boolean YouAreNotAloneNooneIsAlone = false;
					int xct = -3;
					int zct;
					while ((xct <= 3) && (!YouAreNotAloneNooneIsAlone)) {
						zct = -3;
						while ((zct <= 3) && (!YouAreNotAloneNooneIsAlone)) {
							if ((Math.pow((int) Math.abs(xct),2) + Math.pow((int) Math.abs(zct),2) <= Math.pow((int) 3,2)) && ((world.getBlockState(new BlockPos(pos.getX() + xct, pos.getY(), pos.getZ() + zct))).getBlock() == this)) {
								if (!(xct == 0 && zct == 0)) {YouAreNotAloneNooneIsAlone = true;}
							}
							zct = zct + 1;
						}
						xct = xct + 1;
					}
					//System.err.println("YouAreNotAloneNooneIsAlone: " + YouAreNotAloneNooneIsAlone);
					if (YouAreNotAloneNooneIsAlone && Math.random() > 0.9) {
						if (Math.random() > 0.7) {
							//world.destroyBlock(pos, false);
							world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
							world.playSound(null, pos, BlockSounds.WET_CRUNCH_PLANTS, SoundCategory.BLOCKS, 1.0F, 1.0F);
						}

					}
				}
			}
		}

		@Override
		public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
			return BlockFaceShape.UNDEFINED;
		}
		
		@Override
		public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	    {
	    	this.updateTick(worldIn, pos, state, rand);
		}
		
		@Override
		public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	    {
	    	if (!LepidodendronConfigPlants.spreadMontsechia) {return true;}
	        return false;
	    }

		@Override
	    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	    {
	        if (!LepidodendronConfigPlants.spreadMontsechia) {return true;}
	        return false;
	    }
	    
		@Override
		public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	    {
	        if (pos.getY() >= 0 && pos.getY() < 256)
	        {
	            IBlockState iblockstate = worldIn.getBlockState(pos.down());
	            Material material = iblockstate.getMaterial();
	            return canSurviveAt(worldIn, pos) && (material == Material.WATER
						//&& (
								//(Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0
						//
						||
						material == Material.ICE);
	        }
	        else
	        {
	            return false;
	        }
	    }	

		//@Override
		public boolean canSurviveAt(World worldIn, BlockPos pos) {
			if (worldIn.getBlockState(pos.down()).getMaterial() != Material.WATER && worldIn.getBlockState(pos.down()).getMaterial() != Material.ICE)
			{
				return false;
			}

			//double getLight = worldIn.getLight(pos);
			//if (!worldIn.canSeeSky(pos) && (worldIn.isDaytime()) && (getLight < 7))
			//{
			//	return false;
			//}

			if (worldIn.getBlockState(pos.down(3)).getMaterial() == Material.WATER)
			{
				return false;
			}

			return true;

		}
		
		@Override
	    public net.minecraftforge.common.EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	    {
	        return net.minecraftforge.common.EnumPlantType.Water;
	    }

	    @Override
	    protected boolean canSustainBush(IBlockState state)
	    {
	        return state.getBlock() == Blocks.WATER || state.getMaterial() == Material.ICE;
	    }

	    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	    {
        if ((!player.capabilities.allowEdit) || (player.getHeldItemMainhand().getItem() != new ItemStack(ItemMontsechiaItem.block, (int) (1)).getItem()) || !LepidodendronConfig.doPropagation)
	        {
	            return true;
	        }
	        else {
	        	ItemStack itemstack = player.getHeldItem(hand);
	        	if (!player.isCreative()) {itemstack.shrink(1);}
	        	if (!((hand != player.getActiveHand()) && (hand == EnumHand.MAIN_HAND))) {
					if (Math.random() > 0.35) {
						ItemStack stackSeed = new ItemStack(ItemMontsechiaSeeds.block, (int) (1));
						stackSeed.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(player, stackSeed);
						if (Math.random() > 0.75) {
							world.destroyBlock(pos, false);
						}
						return true;
					}
					else {
						if (Math.random() > 0.85) {
							world.destroyBlock(pos, false);
		    				return true;
						}
					}	
				}
	        	return true;
	        }
	    }

		@Override
	    public EnumOffsetType getOffsetType()
	    {
	        return EnumOffsetType.XZ;
	    }

	}
}
