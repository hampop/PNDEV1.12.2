package net.lepidodendron;

import net.lepidodendron.block.BlockNest;
import net.lepidodendron.block.base.IAdvancementGranter;
import net.lepidodendron.entity.*;
import net.lepidodendron.entity.base.*;
import net.lepidodendron.util.CustomTrigger;
import net.lepidodendron.util.ModTriggers;
import net.lepidodendron.util.TriggerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.text.DecimalFormat;
import java.util.Locale;

public class LepidodendronBookSubscribers {

	protected RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
		float f = playerIn.rotationPitch;
		float f1 = playerIn.rotationYaw;
		double d0 = playerIn.posX;
		double d1 = playerIn.posY + (double) playerIn.getEyeHeight();
		double d2 = playerIn.posZ;
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d3 = playerIn.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
		Vec3d vec3d1 = vec3d.add((double) f6 * d3, (double) f5 * d3, (double) f7 * d3);
		return worldIn.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onUseBook2(PlayerInteractEvent.RightClickItem event) {
		if (!event.getItemStack().getItem().getRegistryName().toString().equalsIgnoreCase("patchouli:guide_book")) {
			return;
		}
		if (event.getItemStack().getTagCompound() != null) {
			if (!event.getItemStack().getTagCompound().toString().contains("lepidodendron:paleopedia")) {
				return;
			}
			RayTraceResult raytraceresult = this.rayTrace(event.getWorld(), event.getEntityPlayer(), true);
			if (raytraceresult != null && !event.getEntityPlayer().isSneaking()) {
				event.setCanceled(true);
				return;
			}
			if (!event.getEntityPlayer().isSneaking()) {
				event.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onUseBook(PlayerInteractEvent.RightClickBlock event) {
		/// click on blocks:
		if (!event.getItemStack().getItem().getRegistryName().toString().equalsIgnoreCase("patchouli:guide_book")) {
			return;
		}
		if (event.getItemStack().getTagCompound() != null) {
			if (!event.getItemStack().getTagCompound().toString().contains("lepidodendron:paleopedia")) {
				return;
			}
			if (event.getEntityPlayer().isSneaking()) {
				return;
			}
			IBlockState state = event.getWorld().getBlockState(event.getPos());
			Block target = state.getBlock();

			//Nest info:
			if (target == BlockNest.block) {
				event.getEntityPlayer().swingArm(event.getHand());
				deliverStatsNest(event);
				event.setCanceled(true);
				return;
			}
			//Blocks:
			else if (target instanceof IAdvancementGranter) {
				if (event.getEntityPlayer() instanceof EntityPlayerMP) {
					((IAdvancementGranter) target).getModTrigger().trigger((EntityPlayerMP) event.getEntityPlayer());
				}
				event.getEntityPlayer().swingArm(event.getHand());
				event.setCanceled(true);
				return;
			}
			//Vanilla plants get specific treatment as they have not got IAdvancementGranter:
			else if (OreDictionary.containsMatch(false, OreDictionary.getOres("plantdnaPNminecraft:acacia_sapling"),
					target.getPickBlock(state, new RayTraceResult(event.getEntityPlayer()), event.getWorld(), event.getPos(), event.getEntityPlayer()))) {
				if ((event.getEntityPlayer() instanceof EntityPlayerMP)) {
					ModTriggers.CLICK_ACACIA.trigger((EntityPlayerMP) event.getEntityPlayer());
				}
				event.getEntityPlayer().swingArm(event.getHand());
				event.setCanceled(true);
				return;
			} else if (OreDictionary.containsMatch(false, OreDictionary.getOres("plantdnaPNminecraft:dark_oak_sapling"),
					target.getPickBlock(state, new RayTraceResult(event.getEntityPlayer()), event.getWorld(), event.getPos(), event.getEntityPlayer()))) {
				if ((event.getEntityPlayer() instanceof EntityPlayerMP)) {
					ModTriggers.CLICK_DARK_OAK.trigger((EntityPlayerMP) event.getEntityPlayer());
				}
				event.getEntityPlayer().swingArm(event.getHand());
				event.setCanceled(true);
				return;
			} else if (OreDictionary.containsMatch(false, OreDictionary.getOres("plantdnaPNminecraft:oak_sapling"),
					target.getPickBlock(state, new RayTraceResult(event.getEntityPlayer()), event.getWorld(), event.getPos(), event.getEntityPlayer()))) {
				if ((event.getEntityPlayer() instanceof EntityPlayerMP)) {
					ModTriggers.CLICK_OAK.trigger((EntityPlayerMP) event.getEntityPlayer());
				}
				event.getEntityPlayer().swingArm(event.getHand());
				event.setCanceled(true);
				return;
			} else if (OreDictionary.containsMatch(false, OreDictionary.getOres("plantdnaPNminecraft:spruce_sapling"),
					target.getPickBlock(state, new RayTraceResult(event.getEntityPlayer()), event.getWorld(), event.getPos(), event.getEntityPlayer()))) {
				if ((event.getEntityPlayer() instanceof EntityPlayerMP)) {
					ModTriggers.CLICK_SPRUCE.trigger((EntityPlayerMP) event.getEntityPlayer());
				}
				event.getEntityPlayer().swingArm(event.getHand());
				event.setCanceled(true);
				return;
			} else if (OreDictionary.containsMatch(false, OreDictionary.getOres("plantdnaPNminecraft:birch_sapling"),
					target.getPickBlock(state, new RayTraceResult(event.getEntityPlayer()), event.getWorld(), event.getPos(), event.getEntityPlayer()))) {
				if ((event.getEntityPlayer() instanceof EntityPlayerMP)) {
					ModTriggers.CLICK_BIRCH.trigger((EntityPlayerMP) event.getEntityPlayer());
				}
				event.getEntityPlayer().swingArm(event.getHand());
				event.setCanceled(true);
				return;
			} else if (OreDictionary.containsMatch(false, OreDictionary.getOres("plantdnaPNminecraft:jungle_sapling"),
					target.getPickBlock(state, new RayTraceResult(event.getEntityPlayer()), event.getWorld(), event.getPos(), event.getEntityPlayer()))) {
				if ((event.getEntityPlayer() instanceof EntityPlayerMP)) {
					ModTriggers.CLICK_JUNGLE.trigger((EntityPlayerMP) event.getEntityPlayer());
				}
				event.getEntityPlayer().swingArm(event.getHand());
				event.setCanceled(true);
				return;
			} else if (OreDictionary.containsMatch(false, OreDictionary.getOres("plantdnaPNminecraft:small_fern"),
					target.getPickBlock(state, new RayTraceResult(event.getEntityPlayer()), event.getWorld(), event.getPos(), event.getEntityPlayer()))
					|| OreDictionary.containsMatch(false, OreDictionary.getOres("plantdnaPNminecraft:large_fern"),
					target.getPickBlock(state, new RayTraceResult(event.getEntityPlayer()), event.getWorld(), event.getPos(), event.getEntityPlayer()))) {
				if ((event.getEntityPlayer() instanceof EntityPlayerMP)) {
					ModTriggers.CLICK_FERN.trigger((EntityPlayerMP) event.getEntityPlayer());
				}
				event.getEntityPlayer().swingArm(event.getHand());
				event.setCanceled(true);
				return;
			}

		}

	}

	public void deliverStatsEntity(PlayerInteractEvent.EntityInteract event) {
		String agePercent = "";
		double maxHealth = 0;
		double actualHealth = 0;
		String nestString = "";

		DecimalFormat df = new DecimalFormat("###.#");
		if (event.getTarget() instanceof EntityPrehistoricFloraAgeableBase) {
			agePercent = df.format(Math.floor(((EntityPrehistoricFloraAgeableBase) event.getTarget()).getAgeScale() * 100F)) + "%";
		} else {
			agePercent = "100%";
		}
		if (event.getTarget() instanceof EntityLivingBase) {
			maxHealth = ((EntityLivingBase) event.getTarget()).getMaxHealth();
			actualHealth = ((EntityLivingBase) event.getTarget()).getHealth();
		}
		nestString = getNestString(event.getTarget(), true);

		if (event.getWorld().isRemote) {
			event.getEntityPlayer().sendMessage(new TextComponentString(event.getTarget().getName() + " aged: " + agePercent + " health: " + df.format(actualHealth) + "/" + df.format(maxHealth) + " (" + Math.ceil((actualHealth / maxHealth) * 100) + "%)" + nestString));
		}
	}

	public static String getHomingString(Entity entity, boolean click) {

		String homingString = "";

		if (entity instanceof EntityPrehistoricFloraLandBase) {
			if (((EntityPrehistoricFloraLandBase) entity).homesToNest()) {
				homingString = "Returns to its nest at certain times.";
			}
		}
		return homingString;
	}

	public static String getNestString(Entity entity, boolean click) {

		String nestString = "";
		BlockPos nestPos = null;
		if (entity instanceof EntityPrehistoricFloraScorpion
				|| entity instanceof EntityPrehistoricFloraEramoscorpius
				|| entity instanceof EntityPrehistoricFloraPraearcturus) {
			nestString = " carries eggs";
		} else if (entity instanceof EntityPrehistoricFloraLandBase) {
			if (((EntityPrehistoricFloraLandBase) entity).createPFChild(((EntityPrehistoricFloraLandBase) entity)) != null) {
				nestString = " gives birth to live young";
			} else if (((EntityPrehistoricFloraLandBase) entity).hasNest()
					|| ((EntityPrehistoricFloraLandBase) entity).isNestMound()) {
				if (((EntityPrehistoricFloraLandBase) entity).isNestMound()) {
					nestString = " lays eggs into mounds in blocks";
				} else if (((EntityPrehistoricFloraLandBase) entity).dropsEggs()) {
					nestString = " drops egg items";
				} else {
					if (click) {
						nestPos = ((EntityPrehistoricFloraLandBase) entity).getNestLocation();
						if (nestPos != null
								&& ((EntityPrehistoricFloraLandBase) entity).hasNest()
								&& !((EntityPrehistoricFloraLandBase) entity).isNestMound()) {
							nestString = " has nest at " + nestPos.getX() + " " + nestPos.getY() + " " + nestPos.getZ();
						} else {
							nestString = " with no known nest";
						}
					} else {
						nestString = " requires a nest to lay into";
					}
				}
			} else if (((EntityPrehistoricFloraLandBase) entity).dropsEggs()) {
				nestString = " drops egg items";
			} else if (((EntityPrehistoricFloraLandBase) entity).laysEggs()) {
				if (((EntityPrehistoricFloraLandBase) entity).noMossEggs()) {
					nestString = " lays eggs into rotten wood";
				} else {
					nestString = " lays eggs into mosses, selaginella and rotten wood";
				}
			} else if (((EntityPrehistoricFloraLandBase) entity).isNestMound()) {
				nestString = " lays eggs into mounds in blocks";
			} else {
				nestString = " lays eggs in water";
			}
			if (((EntityPrehistoricFloraLandBase) entity).breedPNVariantsMatch() == -1) {
				nestString = nestString + ". Requires male-female to breed.";
			}
		} else {
			if (entity instanceof EntityPrehistoricFloraAgeableBase) {
				if (((EntityPrehistoricFloraAgeableBase) entity).createPFChild(((EntityPrehistoricFloraAgeableBase) entity)) != null) {
					nestString = " gives birth to live young";
				} else if (((EntityPrehistoricFloraAgeableBase) entity).dropsEggs()) {
					nestString = " drops egg items";
				} else if (((EntityPrehistoricFloraAgeableBase) entity).isNestMound()) {
					nestString = " lays eggs into mounds in blocks";
				} else if (((EntityPrehistoricFloraAgeableBase) entity).hasNest()) {
					nestString = " requires a nest to lay into";
				} else {
					nestString = " lays eggs in water";
				}
				if (((EntityPrehistoricFloraAgeableBase) entity).breedPNVariantsMatch() == -1) {
					nestString = nestString + ". Requires male-female to breed.";
				}
			} else if (entity instanceof EntityPrehistoricFloraTrilobiteBottomBase) {
				if (((EntityPrehistoricFloraTrilobiteBottomBase) entity).dropsEggs()) {
					nestString = " drops egg items";
				} else {
					nestString = " lays eggs in water";
				}
				if (((EntityPrehistoricFloraTrilobiteBottomBase) entity).breedPNVariantsMatch() == -1) {
					nestString = nestString + ". Requires male-female to breed.";
				}
			} else if (entity instanceof EntityPrehistoricFloraTrilobiteSwimBase) {
				if (((EntityPrehistoricFloraTrilobiteSwimBase) entity).dropsEggs()) {
					nestString = " drops egg items";
				} else {
					nestString = " lays eggs in water";
				}
				if (((EntityPrehistoricFloraTrilobiteSwimBase) entity).breedPNVariantsMatch() == -1) {
					nestString = nestString + ". Requires male-female to breed.";
				}
			} else if (entity instanceof EntityPrehistoricFloraCrawlingFlyingInsectBase) {
				if (((EntityPrehistoricFloraCrawlingFlyingInsectBase) entity).dropsEggs()) {
					nestString = " drops egg items";
				} else {
					nestString = " lays eggs into mosses, selaginella and rotten wood";
				}
				if (((EntityPrehistoricFloraCrawlingFlyingInsectBase) entity).breedPNVariantsMatch() == -1) {
					nestString = nestString + ". Requires male-female to breed.";
				}
			} else if (entity instanceof EntityPrehistoricFloraFishBase) {
				if (((EntityPrehistoricFloraFishBase) entity).dropsEggs()) {
					nestString = " drops egg items";
				} else {
					nestString = " lays eggs in water";
				}
				if (((EntityPrehistoricFloraFishBase) entity).breedPNVariantsMatch() == -1) {
					nestString = nestString + ". Requires male-female to breed.";
				}
			} else if (entity instanceof EntityPrehistoricFloraInsectFlyingBase) {
				if (((EntityPrehistoricFloraInsectFlyingBase) entity).dropsEggs()) {
					nestString = " drops egg items";
				} else if (!((EntityPrehistoricFloraInsectFlyingBase) entity).laysInBlock()) {
					nestString = " lays eggs in water";
				} else {
					nestString = " lays eggs into mosses, selaginella and rotten wood";
				}
				if (((EntityPrehistoricFloraInsectFlyingBase) entity).breedPNVariantsMatch() == -1) {
					nestString = nestString + ". Requires male-female to breed.";
				}
			} else if (entity instanceof EntityPrehistoricFloraJellyfishBase) {
				if (((EntityPrehistoricFloraJellyfishBase) entity).dropsEggs()) {
					nestString = " drops egg items";
				} else {
					nestString = " lays eggs in water";
				}
				if (((EntityPrehistoricFloraJellyfishBase) entity).breedPNVariantsMatch() == -1) {
					nestString = nestString + ". Requires male-female to breed.";
				}
			} else if (entity instanceof EntityPrehistoricFloraSlitheringWaterBase) {
				if (((EntityPrehistoricFloraSlitheringWaterBase) entity).dropsEggs()) {
					nestString = " drops egg items";
				} else {
					nestString = " lays eggs in water";
				}
				if (((EntityPrehistoricFloraSlitheringWaterBase) entity).breedPNVariantsMatch() == -1) {
					nestString = nestString + ". Requires male-female to breed.";
				}
			}
		}
		if (!click) {
			nestString = nestString.trim();
			nestString = nestString.substring(0, 1).toUpperCase(Locale.ROOT) + nestString.substring(1);
		}
		return nestString;
	}

	public void deliverStatsNest(PlayerInteractEvent.RightClickBlock event) {
		String nestOwner = "nobody";
		IBlockState state = event.getWorld().getBlockState(event.getPos());
		Block target = state.getBlock();
		if (target == BlockNest.block) {
			String nestType = "";
			TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());
			if (tileEntity != null) {
				if (tileEntity.getTileData().hasKey("creature")) {
					nestType = tileEntity.getTileData().getString("creature");
				}
			}

			if (!nestType.equals("")) {
				//Get the mob:
				nestType = nestType.replace("lepidodendron:", "");
				nestOwner = I18n.translateToLocal("entity." + nestType + ".name").trim();
			} else {
				nestOwner = "nobody";
			}
		} else {
			nestOwner = "nobody";
		}
		if (event.getWorld().isRemote) {
			event.getEntityPlayer().sendMessage(new TextComponentString("Nest belonging to " + nestOwner));
		}
	}

	@SubscribeEvent
	public void onUseBook(PlayerInteractEvent.EntityInteract event) {
		/// click on entities:
		if (!event.getItemStack().getItem().getRegistryName().toString().equalsIgnoreCase("patchouli:guide_book")) {
			return;
		}
		if (event.getItemStack().getTagCompound() != null) {
			if (!event.getItemStack().getTagCompound().toString().contains("lepidodendron:paleopedia")) {
				return;
			} else if (event.getTarget() instanceof IAdvancementGranter) {
				if (event.getEntityPlayer() instanceof EntityPlayerMP) {
					((IAdvancementGranter) (event.getTarget())).getModTrigger().trigger((EntityPlayerMP) event.getEntityPlayer());
				}
				event.getEntityPlayer().swingArm(event.getHand());
				deliverStatsEntity(event);
				event.setCanceled(true);
				return;
			}


			Entity target = event.getTarget();

			CustomTrigger trigger = TriggerRegistry.getTriggerForEntity(target.getClass());

			if (trigger != null && event.getEntityPlayer() instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
				trigger.trigger(player);
				event.getEntityPlayer().swingArm(event.getHand());
				deliverStatsEntity(event);
				event.setCanceled(true);
			}

		}
	}
}