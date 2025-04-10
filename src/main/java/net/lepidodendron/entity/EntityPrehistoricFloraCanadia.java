
package net.lepidodendron.entity;

import net.ilexiconn.llibrary.client.model.tools.ChainBuffer;
import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.block.base.IAdvancementGranter;
import net.lepidodendron.entity.ai.*;
import net.lepidodendron.entity.base.EntityPrehistoricFloraEurypteridBase;
import net.lepidodendron.entity.util.EnumCreatureAttributePN;
import net.lepidodendron.entity.util.ITrappableWater;
import net.lepidodendron.util.CustomTrigger;
import net.lepidodendron.util.ModTriggers;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityPrehistoricFloraCanadia extends EntityPrehistoricFloraEurypteridBase implements IAdvancementGranter, ITrappableWater {

	public BlockPos currentTarget;
	@SideOnly(Side.CLIENT)
	public ChainBuffer chainBuffer;
	private int slitherStage;
	private boolean slitherState;

	public EntityPrehistoricFloraCanadia(World world) {
		super(world);
		setSize(0.25F, 0.15F);
		minWidth = 0.1F;
		maxWidth = 0.25F;
		maxHeight = 0.15F;
		maxHealthAgeable = 2.0D;
	}

	@Override
	public EnumCreatureAttributePN getPNCreatureAttribute() {
		return EnumCreatureAttributePN.INVERTEBRATE;
	}

	@Override
	public boolean isSmall() {
		return true;
	}

	public static String getPeriod() {return "Cambrian";}

	//public static String getHabitat() {return "Aquatic";}

	public int getSlitherStage() {
		int slitherstage = this.slitherStage;

		return slitherstage;
	}

	@Override
	public boolean dropsEggs() {
		return true;
	}
	
	@Override
	public boolean laysEggs() {
		return false;
	}

	@Override
	public int getAdultAge() {
		return 1;
	}

	protected void initEntityAI() {
		tasks.addTask(0, new EntityMateAIAgeableBase(this, 1.0D));
		tasks.addTask(1, new AttackAI(this, 1.0D, false, this.getAttackLength()));
		tasks.addTask(2, new EurypteridWander(this, NO_ANIMATION));
		tasks.addTask(3, new EntityLookIdleAI(this));
		this.targetTasks.addTask(0, new EatItemsEntityPrehistoricFloraAgeableBaseAI(this, 1));
	}

	@Override
	public String[] getFoodOreDicts() {
		return DietString.FISHFOOD;
	}

	@Override
	public boolean isAIDisabled() {
		return false;
	}

	@Override
	public String getTexture() {
		return this.getTexture();
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		//this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected double getSwimSpeed() {
		return this.getSwimSpeed();
	}

	@Override
	protected float getAISpeedEurypterid() {
		if (!this.isAtBottom()) {
			return 0.025F;
		}
		return 0.08F;
	}

	@Override
	public boolean isInWater() {
		return super.isInWater() || this.isInsideOfMaterial(Material.WATER) || this.isInsideOfMaterial(Material.CORAL);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

		return super.attackEntityFrom(source, (amount * 0.7F));

	}

	//@Override
	//public net.minecraft.util.SoundEvent getAmbientSound() {
	//    return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY
	//            .getObject(new ResourceLocation("lepidodendron:eurypterus_idle"));
	//}

	@Override
	public SoundEvent getAmbientSound() {
		return (SoundEvent) SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
	}


	//@Override
	//public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
	//    return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY
	//            .getObject(new ResourceLocation("lepidodendron:eurypterus_hurt"));
	//}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return (SoundEvent) SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.hurt"));
	}

	//@Override
	//public net.minecraft.util.SoundEvent getDeathSound() {
	//    return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY
	//            .getObject(new ResourceLocation("lepidodendron:eurypterus_death"));
	//}
	@Override
	public SoundEvent getDeathSound() {
		return (SoundEvent) SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.death"));
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.posY < (double) this.world.getSeaLevel() && this.isInWater();
	}

	public boolean isNotColliding() {
		return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
	}

	@Override
	public int getTalkInterval() {
		return 120;
	}

	@Override
	protected int getExperiencePoints(EntityPlayer player) {
		return 1 + this.world.rand.nextInt(3);
	}

	@Override
	public boolean isOnLadder() {
		return false;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if (!(slitherStage > 0)) {slitherStage = 0;}

		if (slitherState) {
			if (slitherStage > 30) {
				slitherState = false;
			} else {
				slitherStage = slitherStage + rand.nextInt(2);
			}
		}

		if (!slitherState) {
			if (slitherStage < 1) {
				slitherState = true;
			} else {
				slitherStage = slitherStage - rand.nextInt(2);
			}
		}
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LepidodendronMod.CANADIA_LOOT;
	}

	@Nullable
	@Override
	public CustomTrigger getModTrigger() {
		return ModTriggers.CLICK_CANADIA;
	}

}