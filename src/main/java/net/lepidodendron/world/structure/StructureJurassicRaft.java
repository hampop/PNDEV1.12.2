
package net.lepidodendron.world.structure;

import net.lepidodendron.ElementsLepidodendronMod;
import net.lepidodendron.LepidodendronConfig;
import net.lepidodendron.block.*;
import net.lepidodendron.entity.EntityPrehistoricFloraDorygnathus;
import net.lepidodendron.entity.base.EntityPrehistoricFloraAgeableBase;
import net.lepidodendron.procedure.ProcedureTreeLog;
import net.lepidodendron.util.EnumBiomeTypeJurassic;
import net.lepidodendron.util.Functions;
import net.lepidodendron.world.biome.jurassic.BiomeJurassic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fluids.BlockFluidBase;

import java.util.Random;

@ElementsLepidodendronMod.ModElement.Tag
public class StructureJurassicRaft extends ElementsLepidodendronMod.ModElement {
	public StructureJurassicRaft(ElementsLepidodendronMod instance) {
		super(instance, 44);
	}

	@Override
	public void generateWorld(Random random, int i2, int k2, World world, int dimID, IChunkGenerator cg, IChunkProvider cp) {
		if (world.isRemote)
			return;

		if (dimID != LepidodendronConfig.dimJurassic)
			return;

		Biome biome = world.getBiome(new BlockPos(i2, world.getSeaLevel(), k2));
		if (biome instanceof BiomeJurassic) {
			BiomeJurassic biomeJurassic = (BiomeJurassic) biome;
			if (biomeJurassic.getBiomeType() != EnumBiomeTypeJurassic.Ocean) {
				return;
			}
		}
		else {
			return;
		}
		int GenChance = 1500;
		int genTries = 1;
		if (biome.getRegistryName().toString().equalsIgnoreCase("lepidodendron:jurassic_ocean_rafts")) {
			GenChance = 200000;
			genTries = random.nextInt(4) + 1;
		}
		for (int tryGo = 1; tryGo <= genTries; tryGo++) {
			if ((random.nextInt(1000000) + 1) <= GenChance) {
				boolean doSpawn = true;
				int i = i2 + random.nextInt(16) + 8;
				int k = k2 + random.nextInt(16) + 8;
				int height = world.getSeaLevel() - 1;
				if (
						(!world.isAirBlock(new BlockPos(i, height, k)))
								&& world.getBlockState(new BlockPos(i, height, k)).getMaterial() == Material.WATER
				) {
					int d = 1;
					while (d < 10 && doSpawn) {
						if (!(world.getBlockState(new BlockPos(i, (height - d), k)).getMaterial() == Material.WATER
								&& (world.getBlockState(new BlockPos(i, (height - d), k)).getBlock() instanceof BlockFluidBase
								|| world.getBlockState(new BlockPos(i, (height - d), k)).getBlock() instanceof BlockLiquid)
						)) {
							doSpawn = false;
						}
						d += 1;
					}
				} else {
					doSpawn = false;
				}

				if (!doSpawn)
					return;

				int x = i;
				int y = height;
				int z = k;

				//System.err.println("Spawned log " + x + " " + y + " " + z);
				Block log = BlockBrachyphyllumLog.block;
				boolean fat = false;
				boolean big = true;
				if (random.nextInt(8) == 0) {
					log = BlockDeadLog.block;
					big = false;
				}
				if (random.nextInt(6) == 0) {
					log = BlockGinkgoLog.block;
					big = false;
				}
				if (random.nextInt(3) == 0) {
					log = BlockTallAraucariaLog.block;
					fat = true;
				}

				int dir = random.nextInt(4);
				int l = 3 + random.nextInt(8);
				if (big) {
					l = l * 2;
				}
				int ll;
				if (!fat) {
					switch (dir) {
						case 0: { //North
							ll = 0;
							while (ll <= l) {
								ProcedureTreeLog.executeProcedure(x, y, z - ll, world, log, EnumFacing.EAST);
								spawnLife(world, x, y, z - ll, random);
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x, y + 1, z - ll + 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x, y + 1, z - ll - 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x, y + 1, z - ll, world, log, EnumFacing.NORTH);
									spawnLife(world, x, y + 1, z - ll, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x, y - 1, z - ll + 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x, y - 1, z - ll - 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x, y - 1, z - ll, world, log, EnumFacing.NORTH);
									spawnLife(world, x, y - 1, z - ll, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x + 1, y, z - ll + 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x + 1, y, z - ll - 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x + 1, y, z - ll, world, log, EnumFacing.UP);
									spawnLife(world, x + 1, y, z - ll, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x - 1, y, z - ll + 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x - 1, y, z - ll - 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x - 1, y, z - ll, world, log, EnumFacing.UP);
									spawnLife(world, x - 1, y, z - ll, random);
								}
								ll += 1;
							}
							break;
						}

						case 1: { //South
							ll = 0;
							while (ll <= l) {
								ProcedureTreeLog.executeProcedure(x, y, z + ll, world, log, EnumFacing.EAST);
								spawnLife(world, x, y, z + ll, random);
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x, y + 1, z + ll + 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x, y + 1, z + ll - 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x, y + 1, z + ll, world, log, EnumFacing.NORTH);
									spawnLife(world, x, y + 1, z + ll, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x, y - 1, z + ll + 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x, y - 1, z + ll - 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x, y - 1, z + ll, world, log, EnumFacing.NORTH);
									spawnLife(world, x, y - 1, z + ll, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x + 1, y, z + ll + 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x + 1, y, z + ll - 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x + 1, y, z + ll, world, log, EnumFacing.UP);
									spawnLife(world, x + 1, y, z + ll, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x - 1, y, z + ll + 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x - 1, y, z + ll - 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x - 1, y, z + ll, world, log, EnumFacing.UP);
									spawnLife(world, x - 1, y, z + ll, random);
								}
								ll += 1;
							}
							break;
						}


						case 2: { //West
							ll = 0;
							while (ll <= l) {
								ProcedureTreeLog.executeProcedure(x - ll, y, z, world, log, EnumFacing.UP);
								spawnLife(world, x - ll, y, z, random);
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x - ll + 1, y + 1, z)).getBlock() == log
										|| world.getBlockState(new BlockPos(x - ll - 1, y + 1, z)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x - ll, y + 1, z, world, log, EnumFacing.NORTH);
									spawnLife(world, x - ll, y + 1, z, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x - ll + 1, y - 1, z)).getBlock() == log
										|| world.getBlockState(new BlockPos(x - ll - 1, y - 1, z)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x - ll, y - 1, z, world, log, EnumFacing.NORTH);
									spawnLife(world, x - ll, y - 1, z, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x - ll + 1, y, z + 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x - ll - 1, y, z + 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x - ll, y, z + 1, world, log, EnumFacing.EAST);
									spawnLife(world, x - ll, y, z + 1, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x - ll + 1, y, z - 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x - ll - 1, y, z - 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x - ll, y, z - 1, world, log, EnumFacing.EAST);
									spawnLife(world, x - ll, y, z - 1, random);
								}
								ll += 1;
							}
							break;
						}

						case 3: { //East
							ll = 0;
							while (ll <= l) {
								ProcedureTreeLog.executeProcedure(x + ll, y, z, world, log, EnumFacing.UP);
								spawnLife(world, x + ll, y, z, random);
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x + ll + 1, y + 1, z)).getBlock() == log
										|| world.getBlockState(new BlockPos(x + ll - 1, y + 1, z)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x + ll, y + 1, z, world, log, EnumFacing.NORTH);
									spawnLife(world, x + ll, y + 1, z, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x + ll + 1, y - 1, z)).getBlock() == log
										|| world.getBlockState(new BlockPos(x + ll - 1, y - 1, z)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x + ll, y - 1, z, world, log, EnumFacing.NORTH);
									spawnLife(world, x + ll, y - 1, z, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x + ll + 1, y, z + 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x + ll - 1, y, z + 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x + ll, y, z + 1, world, log, EnumFacing.EAST);
									spawnLife(world, x + ll, y, z + 1, random);
								}
								if (ll <= 3 + random.nextInt(2) && random.nextInt(4) == 0
										&& !(world.getBlockState(new BlockPos(x + ll + 1, y, z - 1)).getBlock() == log
										|| world.getBlockState(new BlockPos(x + ll - 1, y, z - 1)).getBlock() == log)) {
									ProcedureTreeLog.executeProcedure(x + ll, y, z - 1, world, log, EnumFacing.EAST);
									spawnLife(world, x + ll, y, z - 1, random);
								}
								ll += 1;
							}
							break;
						}
					}
				}
				else {
					//fat logs which can also do diagonal like shapes:
					int diagonal = random.nextInt(3) - 1;
					//if (random.nextInt(4) != 0) {
					//	diagonal = 0;
					//}
					switch (dir) {
						case 0: { //North
							ll = 0;
							while (ll <= l) {
								if (random.nextInt(5) != 0) {
									x = x + diagonal;
									ProcedureTreeLog.executeProcedure(x, y, z - ll, world, log, EnumFacing.EAST);
									ProcedureTreeLog.executeProcedure(x + 1, y, z - ll, world, log, EnumFacing.EAST);
									ProcedureTreeLog.executeProcedure(x, y - 1, z - ll, world, log, EnumFacing.EAST);
									ProcedureTreeLog.executeProcedure(x + 1, y - 1, z - ll, world, log, EnumFacing.EAST);
									spawnLife(world, x, y, z - ll, random);
									spawnLife(world, x + 1, y, z - ll, random);
									spawnLife(world, x, y - 1, z - ll, random);
									spawnLife(world, x + 1, y - 1, z - ll, random);
									if (random.nextInt(3) == 0) {
										createBigBranch(world, x + (random.nextInt(2) - Math.abs(diagonal)), y - random.nextInt(2), z - ll, random, log, diagonal);
									}
									ll += 1;
								}
							}
							break;
						}
						case 1: { //South
							ll = 0;
							while (ll <= l) {
								if (random.nextInt(5) != 0) {
									x = x + diagonal;
									ProcedureTreeLog.executeProcedure(x, y, z + ll, world, log, EnumFacing.EAST);
									ProcedureTreeLog.executeProcedure(x + 1, y, z + ll, world, log, EnumFacing.EAST);
									ProcedureTreeLog.executeProcedure(x, y - 1, z + ll, world, log, EnumFacing.EAST);
									ProcedureTreeLog.executeProcedure(x + 1, y - 1, z + ll, world, log, EnumFacing.EAST);
									spawnLife(world, x, y, z + ll, random);
									spawnLife(world, x + 1, y, z + ll, random);
									spawnLife(world, x, y - 1, z + ll, random);
									spawnLife(world, x + 1, y - 1, z + ll, random);
									if (random.nextInt(3) == 0) {
										createBigBranch(world, x + (random.nextInt(2) - Math.abs(diagonal)), y - random.nextInt(2), z + ll, random, log, diagonal);
									}
									ll += 1;
								}
							}
							break;
						}
						case 2: { //East
							ll = 0;
							while (ll <= l) {
								if (random.nextInt(5) != 0) {
									z = z + diagonal;
									ProcedureTreeLog.executeProcedure(x + ll, y, z, world, log, EnumFacing.UP);
									ProcedureTreeLog.executeProcedure(x + 1, y, z + 1, world, log, EnumFacing.UP);
									ProcedureTreeLog.executeProcedure(x + ll, y - 1, z, world, log, EnumFacing.UP);
									ProcedureTreeLog.executeProcedure(x + ll, y - 1, z + 1, world, log, EnumFacing.UP);
									spawnLife(world, x + ll, y, z, random);
									spawnLife(world, x + 1, y, z + 1, random);
									spawnLife(world, x + ll, y - 1, z, random);
									spawnLife(world, x + ll, y - 1, z + 1, random);
									if (random.nextInt(3) == 0) {
										createBigBranch(world, x + ll, y - random.nextInt(2), z + (random.nextInt(2) - Math.abs(diagonal)), random, log, diagonal);
									}
									ll += 1;
								}
							}
							break;
						}
						case 3: { //West
							ll = 0;
							while (ll <= l) {
								if (random.nextInt(5) != 0) {
									z = z + diagonal;
									ProcedureTreeLog.executeProcedure(x - ll, y, z, world, log, EnumFacing.UP);
									ProcedureTreeLog.executeProcedure(x - ll, y, z + 1, world, log, EnumFacing.UP);
									ProcedureTreeLog.executeProcedure(x - ll, y - 1, z, world, log, EnumFacing.UP);
									ProcedureTreeLog.executeProcedure(x - ll, y - 1, z + 1, world, log, EnumFacing.UP);
									spawnLife(world, x - ll, y, z, random);
									spawnLife(world, x - ll, y, z + 1, random);
									spawnLife(world, x - ll, y - 1, z, random);
									spawnLife(world, x - ll, y - 1, z + 1, random);
									if (random.nextInt(3) == 0) {
										createBigBranch(world, x - ll, y - random.nextInt(2), z + (random.nextInt(2) - Math.abs(diagonal)), random, log, diagonal);
									}
									ll += 1;
								}
							}
							break;
						}
					}
				}
			}
		}
	}

	public void createBigBranch(World world, int x, int y, int z, Random random, Block log, int diagonal) {
		if (random.nextInt(3) == 0) {
			ProcedureTreeLog.executeProcedure(x, y + 1, z, world, log, EnumFacing.NORTH);
			spawnLife(world, x, y + 1, z, random);
			if (world.getBlockState(new BlockPos(x, y + 1, z)).getBlock() == log) {
				ProcedureTreeLog.executeProcedure(x, y + 2, z, world, log, EnumFacing.NORTH);
				spawnLife(world, x, y + 2, z, random);
				if (random.nextInt(2) == 0 && world.getBlockState(new BlockPos(x, y + 2, z)).getBlock() == log) {
					ProcedureTreeLog.executeProcedure(x, y + 3, z, world, log, EnumFacing.NORTH);
					spawnLife(world, x, y + 3, z, random);
				}
			}
		}

		if (random.nextInt(5) == 0) {
			ProcedureTreeLog.executeProcedure(x, y - 1, z, world, log, EnumFacing.NORTH);
			spawnLife(world, x, y - 1, z, random);
			if (world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == log) {
				ProcedureTreeLog.executeProcedure(x, y - 2, z, world, log, EnumFacing.NORTH);
				spawnLife(world, x, y - 2, z, random);
				if (random.nextInt(2) == 0 && world.getBlockState(new BlockPos(x, y - 2, z)).getBlock() == log) {
					ProcedureTreeLog.executeProcedure(x, y - 3, z, world, log, EnumFacing.NORTH);
					spawnLife(world, x, y - 3, z, random);
				}
			}
		}

		if (random.nextInt(5) == 0) {
			ProcedureTreeLog.executeProcedure(x, y, z + 1, world, log, EnumFacing.EAST);
			spawnLife(world, x, y, z + 1, random);
			if (world.getBlockState(new BlockPos(x, y, z + 1)).getBlock() == log) {
				ProcedureTreeLog.executeProcedure(x, y, z + 2, world, log, EnumFacing.EAST);
				spawnLife(world, x, y, z, random);
				if (random.nextInt(2) == 0 && world.getBlockState(new BlockPos(x, y, z + 2)).getBlock() == log) {
					ProcedureTreeLog.executeProcedure(x, y, z + 3, world, log, EnumFacing.EAST);
					spawnLife(world, x, y, z + 3, random);
					if (random.nextInt(2) == 0 && diagonal != 0 && world.getBlockState(new BlockPos(x, y, z + 3)).getBlock() == log) {
						ProcedureTreeLog.executeProcedure(x, y, z + 4, world, log, EnumFacing.EAST);
						spawnLife(world, x, y, z + 4, random);
					}
				}
			}
		}

		if (random.nextInt(5) == 0) {
			ProcedureTreeLog.executeProcedure(x, y, z - 1, world, log, EnumFacing.EAST);
			spawnLife(world, x, y, z - 1, random);
			if (world.getBlockState(new BlockPos(x, y, z - 1)).getBlock() == log) {
				ProcedureTreeLog.executeProcedure(x, y, z - 2, world, log, EnumFacing.EAST);
				spawnLife(world, x, y, z - 2, random);
				if (random.nextInt(2) == 0 && world.getBlockState(new BlockPos(x, y, z - 2)).getBlock() == log) {
					ProcedureTreeLog.executeProcedure(x, y, z - 3, world, log, EnumFacing.EAST);
					spawnLife(world, x, y, z - 3, random);
					if (random.nextInt(2) == 0 && diagonal != 0 && world.getBlockState(new BlockPos(x, y, z - 3)).getBlock() == log) {
						ProcedureTreeLog.executeProcedure(x, y, z - 4, world, log, EnumFacing.EAST);
						spawnLife(world, x, y, z - 4, random);
					}
				}
			}
		}

		if (random.nextInt(5) == 0) {
			ProcedureTreeLog.executeProcedure(x + 1, y, z, world, log, EnumFacing.UP);
			spawnLife(world, x + 1, y, z, random);
			if (world.getBlockState(new BlockPos(x + 1, y, z)).getBlock() == log) {
				ProcedureTreeLog.executeProcedure(x + 2, y, z, world, log, EnumFacing.UP);
				spawnLife(world, x + 2, y, z, random);
				if (random.nextInt(2) == 0 && world.getBlockState(new BlockPos(x + 2, y, z)).getBlock() == log) {
					ProcedureTreeLog.executeProcedure(x + 3, y, z, world, log, EnumFacing.UP);
					spawnLife(world, x + 3, y, z, random);
					if (random.nextInt(2) == 0 && diagonal != 0 && world.getBlockState(new BlockPos(x + 3, y, z)).getBlock() == log) {
						ProcedureTreeLog.executeProcedure(x + 4, y, z, world, log, EnumFacing.UP);
						spawnLife(world, x + 4, y, z, random);
					}
				}
			}
		}

		if (random.nextInt(5) == 0) {
			ProcedureTreeLog.executeProcedure(x - 1, y, z, world, log, EnumFacing.UP);
			spawnLife(world, x - 1, y, z, random);
			if (world.getBlockState(new BlockPos(x - 1, y, z)).getBlock() == log) {
				ProcedureTreeLog.executeProcedure(x - 2, y, z, world, log, EnumFacing.UP);
				spawnLife(world, x - 2, y, z, random);
				if (random.nextInt(2) == 0 && world.getBlockState(new BlockPos(x - 2, y, z)).getBlock() == log) {
					ProcedureTreeLog.executeProcedure(x - 3, y, z, world, log, EnumFacing.UP);
					spawnLife(world, x - 3, y, z, random);
					if (random.nextInt(2) == 0 && diagonal != 0 && world.getBlockState(new BlockPos(x - 3, y, z)).getBlock() == log) {
						ProcedureTreeLog.executeProcedure(x - 4, y, z, world, log, EnumFacing.UP);
						spawnLife(world, x - 4, y, z, random);
					}
				}
			}
		}
	}

	public void spawnLife(World world, int x, int y, int z, Random random) {
		int c;
		int cc;
		if (random.nextInt(256) == 0) {
			//Dorygnathus:
			if (world.isAirBlock(new BlockPos(x, (y + 1), z))
				&& BlockNest.block.canPlaceBlockAt(world, new BlockPos(x, (y + 1), z))) {
				world.setBlockState(new BlockPos(x, (y + 1), z), BlockNest.block.getDefaultState());
				if (world.rand.nextInt(3) != 0) {
					//Place some eggs too:
					TileEntity te = world.getTileEntity(new BlockPos(x, (y + 1), z));
					te.getTileData().setString("creature", "lepidodendron:prehistoric_flora_dorygnathus");
					te.getTileData().setBoolean("isMound", false);
					ItemStack stack = BlockNest.BlockCustom.getEggItemStack("lepidodendron:prehistoric_flora_dorygnathus");
					stack.setCount(1);
					((BlockNest.TileEntityNest) te).setInventorySlotContents((int) (0), stack);
				}
				if (!(world.isRemote)) {
					EntityPrehistoricFloraAgeableBase.summon(world, EntityList.getKey(EntityPrehistoricFloraDorygnathus.class).toString(), "{AgeTicks:" + (new EntityPrehistoricFloraDorygnathus(world)).getAdultAge() + "}", x + 0.5 , y + 1.01, z + 0.5);
				}
			}
		}
		if (random.nextInt(10) != 0) {
			c = 1;
			if (random.nextInt(3) != 0) {
				cc = random.nextInt(20);
				while (c <= cc && (world.getBlockState(new BlockPos(x, (y - c), z)).getMaterial() == Material.WATER
						&& (world.getBlockState(new BlockPos(x, (y - c), z)).getBlock() instanceof BlockFluidBase
						|| world.getBlockState(new BlockPos(x, (y - c), z)).getBlock() instanceof BlockLiquid)
				)) {
					Functions.setBlockStateAndCheckForDoublePlant(world,new BlockPos(x, (y - c), z), BlockCrinoidSeirocrinus.block.getDefaultState());
					c += 1;
				}
			}
			else {
				cc = random.nextInt(5);
				while (c <= cc && (world.getBlockState(new BlockPos(x, (y - c), z)).getMaterial() == Material.WATER
						&& (world.getBlockState(new BlockPos(x, (y - c), z)).getBlock() instanceof BlockFluidBase
						|| world.getBlockState(new BlockPos(x, (y - c), z)).getBlock() instanceof BlockLiquid)
				)) {
					Functions.setBlockStateAndCheckForDoublePlant(world,new BlockPos(x, (y - c), z), BlockCrinoidPentacrinites.block.getDefaultState());
					c += 1;
				}
			}
		}

		//Non-crinoid life:
		BlockPos pos = new BlockPos(x, y, z);
		//Moss:
		if (random.nextInt(3) == 0 && BlockSlimyAlgaeLand.block.canPlaceBlockAt(world, pos.up())) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.up(), BlockSlimyAlgaeLand.block.getDefaultState());
		}
		else if (random.nextInt(5) == 0 && BlockAncientMoss.block.canPlaceBlockAt(world, pos.up())) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.up(), BlockAncientMoss.block.getDefaultState().withProperty(BlockAncientMoss.BlockCustom.FACING, EnumFacing.UP));
		}
		else if (random.nextInt(7) == 0 && BlockSelaginella.block.canPlaceBlockAt(world, pos.up())) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.up(), BlockSelaginella.block.getDefaultState().withProperty(BlockSelaginella.BlockCustom.FACING, EnumFacing.UP));
		}
		//Algae:
		if (random.nextInt(4) == 0 && BlockGreenAlgaeMat.block.canPlaceBlockOnSide(world, pos.north(), EnumFacing.NORTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.north(), BlockGreenAlgaeMat.block.getDefaultState().withProperty(BlockGreenAlgaeMat.BlockCustom.FACING, EnumFacing.NORTH));
		}
		if (random.nextInt(4) == 0 && BlockGreenAlgaeMat.block.canPlaceBlockOnSide(world, pos.south(), EnumFacing.SOUTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.south(), BlockGreenAlgaeMat.block.getDefaultState().withProperty(BlockGreenAlgaeMat.BlockCustom.FACING, EnumFacing.SOUTH));
		}
		if (random.nextInt(4) == 0 && BlockGreenAlgaeMat.block.canPlaceBlockOnSide(world, pos.east(), EnumFacing.EAST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.east(), BlockGreenAlgaeMat.block.getDefaultState().withProperty(BlockGreenAlgaeMat.BlockCustom.FACING, EnumFacing.EAST));
		}
		if (random.nextInt(4) == 0 && BlockGreenAlgaeMat.block.canPlaceBlockOnSide(world, pos.west(), EnumFacing.WEST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.west(), BlockGreenAlgaeMat.block.getDefaultState().withProperty(BlockGreenAlgaeMat.BlockCustom.FACING, EnumFacing.WEST));
		}

		if (random.nextInt(4) == 0 && BlockRedAlgaeMat.block.canPlaceBlockOnSide(world, pos.north(), EnumFacing.NORTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.north(), BlockRedAlgaeMat.block.getDefaultState().withProperty(BlockRedAlgaeMat.BlockCustom.FACING, EnumFacing.NORTH));
		}
		if (random.nextInt(4) == 0 && BlockRedAlgaeMat.block.canPlaceBlockOnSide(world, pos.south(), EnumFacing.SOUTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.south(), BlockRedAlgaeMat.block.getDefaultState().withProperty(BlockRedAlgaeMat.BlockCustom.FACING, EnumFacing.SOUTH));
		}
		if (random.nextInt(4) == 0 && BlockRedAlgaeMat.block.canPlaceBlockOnSide(world, pos.east(), EnumFacing.EAST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.east(), BlockRedAlgaeMat.block.getDefaultState().withProperty(BlockRedAlgaeMat.BlockCustom.FACING, EnumFacing.EAST));
		}
		if (random.nextInt(4) == 0 && BlockRedAlgaeMat.block.canPlaceBlockOnSide(world, pos.west(), EnumFacing.WEST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.west(), BlockRedAlgaeMat.block.getDefaultState().withProperty(BlockRedAlgaeMat.BlockCustom.FACING, EnumFacing.WEST));
		}

		if (random.nextInt(5) == 0 && BlockGreenCodiumAlgae.block.canPlaceBlockOnSide(world, pos.north(), EnumFacing.NORTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.north(), BlockGreenCodiumAlgae.block.getDefaultState().withProperty(BlockGreenCodiumAlgae.BlockCustom.FACING, EnumFacing.NORTH));
		}
		if (random.nextInt(5) == 0 && BlockGreenCodiumAlgae.block.canPlaceBlockOnSide(world, pos.south(), EnumFacing.SOUTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.south(), BlockGreenCodiumAlgae.block.getDefaultState().withProperty(BlockGreenCodiumAlgae.BlockCustom.FACING, EnumFacing.SOUTH));
		}
		if (random.nextInt(5) == 0 && BlockGreenCodiumAlgae.block.canPlaceBlockOnSide(world, pos.east(), EnumFacing.EAST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.east(), BlockGreenCodiumAlgae.block.getDefaultState().withProperty(BlockGreenCodiumAlgae.BlockCustom.FACING, EnumFacing.EAST));
		}
		if (random.nextInt(5) == 0 && BlockGreenCodiumAlgae.block.canPlaceBlockOnSide(world, pos.west(), EnumFacing.WEST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.west(), BlockGreenCodiumAlgae.block.getDefaultState().withProperty(BlockGreenCodiumAlgae.BlockCustom.FACING, EnumFacing.WEST));
		}

		if (random.nextInt(5) == 0 && BlockGreenSproutingAlgae.block.canPlaceBlockOnSide(world, pos.north(), EnumFacing.NORTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.north(), BlockGreenSproutingAlgae.block.getDefaultState().withProperty(BlockGreenSproutingAlgae.BlockCustom.FACING, EnumFacing.NORTH));
		}
		if (random.nextInt(5) == 0 && BlockGreenSproutingAlgae.block.canPlaceBlockOnSide(world, pos.south(), EnumFacing.SOUTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.south(), BlockGreenSproutingAlgae.block.getDefaultState().withProperty(BlockGreenSproutingAlgae.BlockCustom.FACING, EnumFacing.SOUTH));
		}
		if (random.nextInt(5) == 0 && BlockGreenSproutingAlgae.block.canPlaceBlockOnSide(world, pos.east(), EnumFacing.EAST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.east(), BlockGreenSproutingAlgae.block.getDefaultState().withProperty(BlockGreenSproutingAlgae.BlockCustom.FACING, EnumFacing.EAST));
		}
		if (random.nextInt(5) == 0 && BlockGreenSproutingAlgae.block.canPlaceBlockOnSide(world, pos.west(), EnumFacing.WEST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.west(), BlockGreenSproutingAlgae.block.getDefaultState().withProperty(BlockGreenSproutingAlgae.BlockCustom.FACING, EnumFacing.WEST));
		}

		if (random.nextInt(5) == 0 && BlockGreenSproutingAlgae.block.canPlaceBlockOnSide(world, pos.north(), EnumFacing.NORTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.north(), BlockGreenSproutingAlgae.block.getDefaultState().withProperty(BlockGreenSproutingAlgae.BlockCustom.FACING, EnumFacing.NORTH));
		}
		if (random.nextInt(5) == 0 && BlockGreenSproutingAlgae.block.canPlaceBlockOnSide(world, pos.south(), EnumFacing.SOUTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.south(), BlockGreenSproutingAlgae.block.getDefaultState().withProperty(BlockGreenSproutingAlgae.BlockCustom.FACING, EnumFacing.SOUTH));
		}
		if (random.nextInt(5) == 0 && BlockGreenSproutingAlgae.block.canPlaceBlockOnSide(world, pos.east(), EnumFacing.EAST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.east(), BlockGreenSproutingAlgae.block.getDefaultState().withProperty(BlockGreenSproutingAlgae.BlockCustom.FACING, EnumFacing.EAST));
		}
		if (random.nextInt(5) == 0 && BlockGreenSproutingAlgae.block.canPlaceBlockOnSide(world, pos.west(), EnumFacing.WEST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.west(), BlockGreenSproutingAlgae.block.getDefaultState().withProperty(BlockGreenSproutingAlgae.BlockCustom.FACING, EnumFacing.WEST));
		}

		if (random.nextInt(5) == 0 && BlockStalkyBrownAlgae.block.canPlaceBlockOnSide(world, pos.north(), EnumFacing.NORTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.north(), BlockStalkyBrownAlgae.block.getDefaultState().withProperty(BlockStalkyBrownAlgae.BlockCustom.FACING, EnumFacing.NORTH));
		}
		if (random.nextInt(5) == 0 && BlockStalkyBrownAlgae.block.canPlaceBlockOnSide(world, pos.south(), EnumFacing.SOUTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.south(), BlockStalkyBrownAlgae.block.getDefaultState().withProperty(BlockStalkyBrownAlgae.BlockCustom.FACING, EnumFacing.SOUTH));
		}
		if (random.nextInt(5) == 0 && BlockStalkyBrownAlgae.block.canPlaceBlockOnSide(world, pos.east(), EnumFacing.EAST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.east(), BlockStalkyBrownAlgae.block.getDefaultState().withProperty(BlockStalkyBrownAlgae.BlockCustom.FACING, EnumFacing.EAST));
		}
		if (random.nextInt(5) == 0 && BlockStalkyBrownAlgae.block.canPlaceBlockOnSide(world, pos.west(), EnumFacing.WEST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.west(), BlockStalkyBrownAlgae.block.getDefaultState().withProperty(BlockStalkyBrownAlgae.BlockCustom.FACING, EnumFacing.WEST));
		}

		if (random.nextInt(5) == 0 && BlockBrownAlgae.block.canPlaceBlockOnSide(world, pos.north(), EnumFacing.NORTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.north(), BlockBrownAlgae.block.getDefaultState().withProperty(BlockBrownAlgae.BlockCustom.FACING, EnumFacing.NORTH));
		}
		if (random.nextInt(5) == 0 && BlockBrownAlgae.block.canPlaceBlockOnSide(world, pos.south(), EnumFacing.SOUTH)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.south(), BlockBrownAlgae.block.getDefaultState().withProperty(BlockBrownAlgae.BlockCustom.FACING, EnumFacing.SOUTH));
		}
		if (random.nextInt(5) == 0 && BlockBrownAlgae.block.canPlaceBlockOnSide(world, pos.east(), EnumFacing.EAST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.east(), BlockBrownAlgae.block.getDefaultState().withProperty(BlockBrownAlgae.BlockCustom.FACING, EnumFacing.EAST));
		}
		if (random.nextInt(5) == 0 && BlockBrownAlgae.block.canPlaceBlockOnSide(world, pos.west(), EnumFacing.WEST)) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.west(), BlockBrownAlgae.block.getDefaultState().withProperty(BlockBrownAlgae.BlockCustom.FACING, EnumFacing.WEST));
		}

		if (random.nextInt(5) == 0 && canSlimyAlgaeStay(world, pos.up(2))) {
			Functions.setBlockStateAndCheckForDoublePlant(world,pos.up(2), BlockSlimyAlgaeWater.block.getDefaultState());
		}

	}

	public boolean canSlimyAlgaeStay(World worldIn, BlockPos pos)
	{
		if (pos.getY() >= 0 && pos.getY() < 256)
		{
			IBlockState iblockstate = worldIn.getBlockState(pos.down());
			Material material = iblockstate.getMaterial();
			return BlockSlimyAlgaeWater.BlockCustom.canSurviveAt(worldIn, pos) && (material == Material.WATER
					|| material == Material.ICE);
		}
		else
		{
			return false;
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
	
}
