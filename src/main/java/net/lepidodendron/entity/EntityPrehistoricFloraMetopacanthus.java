
package net.lepidodendron.entity;

import net.ilexiconn.llibrary.client.model.tools.ChainBuffer;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.block.base.IAdvancementGranter;
import net.lepidodendron.entity.ai.AgeableFishWanderBottomDweller;
import net.lepidodendron.entity.ai.DietString;
import net.lepidodendron.entity.ai.EatItemsEntityPrehistoricFloraAgeableBaseAI;
import net.lepidodendron.entity.ai.EntityMateAIAgeableBase;
import net.lepidodendron.entity.base.EntityPrehistoricFloraAgeableFishBase;
import net.lepidodendron.entity.render.entity.RenderMetopacanthus;
import net.lepidodendron.entity.render.tile.RenderDisplays;
import net.lepidodendron.entity.util.ITrappableWater;
import net.lepidodendron.item.entities.ItemUnknownEgg;
import net.lepidodendron.util.CustomTrigger;
import net.lepidodendron.util.ModTriggers;
import net.minecraft.client.model.ModelBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;

public class EntityPrehistoricFloraMetopacanthus extends EntityPrehistoricFloraAgeableFishBase implements IAdvancementGranter, ITrappableWater {


	public BlockPos currentTarget;
	@SideOnly(Side.CLIENT)
	public ChainBuffer chainBuffer;
	public ChainBuffer tailBuffer;
	int bottomCooldown;
	boolean bottomFlag;

	private static final DataParameter<Integer> METOPACANTHUS_TYPE = EntityDataManager.<Integer>createKey(EntityPrehistoricFloraMetopacanthus.class, DataSerializers.VARINT);

	public EntityPrehistoricFloraMetopacanthus(World world) {
		super(world);
		if (world != null) {
			this.moveHelper = new EntityPrehistoricFloraMetopacanthus.SwimmingMoveHelperBase();
			this.navigator = new PathNavigateSwimmer(this, world);
		}
		setSize(0.5F, 0.5F);
		minWidth = 0.2F;
		maxWidth = 0.6F;
		maxHeight = 0.7F;
		maxHealthAgeable = 8.0D;
		if (FMLCommonHandler.instance().getSide().isClient()) {
			tailBuffer = new ChainBuffer();
		}
	}

	@Override
	public ItemStack getPropagule() {
		ItemStack stack = new ItemStack(ItemUnknownEgg.block, (int) (1));
		NBTTagCompound propaguleNBT = new NBTTagCompound();
		propaguleNBT.setString("PNType", "gendered");
		stack.setTagCompound(propaguleNBT);
		return stack;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(METOPACANTHUS_TYPE, 0);
	}

	@Override
	public byte breedPNVariantsMatch() {
		return -1;
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal)
	{
		if (otherAnimal == this)
		{
			return false;
		}
		else if (otherAnimal.getClass() != this.getClass())
		{
			return false;
		}
		else {
			switch (this.breedPNVariantsMatch()) {
				case 0: default:
					break;

				case -1:
					if (((EntityPrehistoricFloraMetopacanthus)otherAnimal).getPNType() == this.getPNType()) {
						return false;
					}
					break;

				case 1:
					if (((EntityPrehistoricFloraMetopacanthus)otherAnimal).getPNType() != this.getPNType()) {
						return false;
					}
					break;

			}
		}

		return this.isInLove() && otherAnimal.isInLove();
	}

	@Override
	public boolean hasPNVariants() {
		return true;
	}

	@Override
	public String getPNTypeName()
	{
		return this.getPNType().getName();
	}

	@Nullable
	@Override
	public CustomTrigger getModTrigger() {
		return ModTriggers.CLICK_METOPACANTHUS;
	}

	public enum Type
	{
		MALE(1, "male"),
		FEMALE(2, "female")
		;

		private final String name;
		private final int metadata;

		Type(int metadataIn, String nameIn)
		{
			this.name = nameIn;
			this.metadata = metadataIn;
		}

		public String getName()
		{
			return this.name;
		}

		public int getMetadata()
		{
			return this.metadata;
		}

		public String toString()
		{
			return this.name;
		}

		public static EntityPrehistoricFloraMetopacanthus.Type byId(int id)
		{
			if (id < 0 || id >= values().length)
			{
				id = 0;
			}

			return values()[id];
		}

		public static EntityPrehistoricFloraMetopacanthus.Type getTypeFromString(String nameIn)
		{
			for (int i = 0; i < values().length; ++i)
			{
				if (values()[i].getName().equals(nameIn))
				{
					return values()[i];
				}
			}

			return values()[0];
		}

	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		this.setPNType(EntityPrehistoricFloraMetopacanthus.Type.byId(rand.nextInt(EntityPrehistoricFloraMetopacanthus.Type.values().length) + 1));
		return livingdata;
	}

	public void setPNType(EntityPrehistoricFloraMetopacanthus.Type type)
	{
		this.dataManager.set(METOPACANTHUS_TYPE, Integer.valueOf(type.ordinal()));
	}

	public EntityPrehistoricFloraMetopacanthus.Type getPNType()
	{
		return EntityPrehistoricFloraMetopacanthus.Type.byId(((Integer)this.dataManager.get(METOPACANTHUS_TYPE)).intValue());
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setString("PNType", this.getPNType().getName());
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		if (this.world != null) {
			super.readEntityFromNBT(compound);
		}
		if (compound.hasKey("PNType", 8))
		{
			this.setPNType(EntityPrehistoricFloraMetopacanthus.Type.getTypeFromString(compound.getString("PNType")));
		}
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		switch (this.getPNType()) {
			case MALE:
			default:
				return LepidodendronMod.METOPACANTHUS_LOOT;

			case FEMALE:
				return LepidodendronMod.METOPACANTHUS_LOOT_F;
		}
	}


	@Override
	public void onUpdate() {
		super.onUpdate();
		if (world.isRemote && !this.isAIDisabled()) {
			tailBuffer.calculateChainSwingBuffer(50, 10, 5F, this);
		}
	}
	@Override
	public boolean isSmall() {
		return true;
	}

	public static String getPeriod() {return "Jurassic";}

	//public static String getHabitat() {return "Aquatic";}

	@Override
	public void playLivingSound() {
	}

	@Override
	public int getAttackLength() {
		return 8;
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
		return 92000;
	}

	@Override
	protected float getAISpeedFish() {
		if (this.isAtBottom() && this.bottomCooldown > 0 && (!this.getIsFast()) && (!this.isInLove())) {
			return 0.15F;
		}
		return 0.2f;
	}

	@Override
	protected boolean isSlowAtBottom() {
		return false;
	}



	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source != DamageSource.DROWN) {
			return super.attackEntityFrom(source, (amount * 0.5F));
		}
		return super.attackEntityFrom(source, amount);
	}

	protected void initEntityAI() {
		tasks.addTask(0, new EntityMateAIAgeableBase(this, 1));
		tasks.addTask(3, new AgeableFishWanderBottomDweller(this, NO_ANIMATION));
		this.targetTasks.addTask(0, new EatItemsEntityPrehistoricFloraAgeableBaseAI(this, 1));
	}

	@Override
	public String[] getFoodOreDicts() {
		return ArrayUtils.addAll(DietString.FISHFOOD, DietString.FISH);
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
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEFINED;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	@Override
	public SoundEvent getAmbientSound() {
		return (SoundEvent) SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return (SoundEvent) SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return (SoundEvent) SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.death"));
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		//this.renderYawOffset = this.rotationYaw;

		if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 7 && this.getAttackTarget() != null) {
			launchAttack();
		}

		if (this.isAtBottom() && (!this.bottomFlag) && !this.getIsFast()) {
			this.bottomFlag = true;
			this.bottomCooldown = 600;
		}
		if (!this.isAtBottom()) {
			this.bottomFlag = false;
			this.bottomCooldown = 0;
		}
		if (this.bottomCooldown > 0) {this.bottomCooldown -= 1;}

		AnimationHandler.INSTANCE.updateAnimations(this);

	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (this.getAnimation() == NO_ANIMATION) {
			this.setAnimation(ATTACK_ANIMATION);
			//System.err.println("set attack");
		}
		return false;
	}

	public boolean isDirectPathBetweenPoints(Vec3d vec1, Vec3d vec2) {
		RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec1, new Vec3d(vec2.x, vec2.y, vec2.z), false, true, false);
		return movingobjectposition == null || movingobjectposition.typeOfHit != RayTraceResult.Type.BLOCK;
	}

	public void onEntityUpdate() {
		super.onEntityUpdate();
	}

	@Override
	public void travel(float strafe, float vertical, float forward) {
		float f4;
		if (this.isServerWorld()) {
			if (this.isInWater()) {
				this.moveRelative(strafe, vertical, forward, 0.1F);
				f4 = 0.8F;
				float speedModifier = (float) EnchantmentHelper.getDepthStriderModifier(this);
				if (speedModifier > 3.0F) {
					speedModifier = 3.0F;
				}
				if (!this.onGround) {
					speedModifier *= 0.5F;
				}
				if (speedModifier > 0.0F) {
					f4 += (0.54600006F - f4) * speedModifier / 3.0F;
				}
				this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

				if (this.collidedHorizontally && this.isCollidingRim())
				{
					this.motionY = 0.05D;
				}

				this.motionX *= f4;
				this.motionX *= 0.9;
				this.motionY *= 0.9;
				this.motionY *= f4;
				this.motionZ *= 0.9;
				this.motionZ *= f4;
			} else {
				super.travel(strafe, vertical, forward);
			}
		}
		this.prevLimbSwingAmount = this.limbSwingAmount;
		double deltaX = this.posX - this.prevPosX;
		double deltaZ = this.posZ - this.prevPosZ;
		double deltaY = this.posY - this.prevPosY;
		float delta = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 4.0F;
		if (delta > 1.0F) {
			delta = 1.0F;
		}
		this.limbSwingAmount += (delta - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;
	}

	class SwimmingMoveHelperBase extends EntityMoveHelper {
		private final EntityPrehistoricFloraMetopacanthus EntityBase = EntityPrehistoricFloraMetopacanthus.this;

		public SwimmingMoveHelperBase() {
			super(EntityPrehistoricFloraMetopacanthus.this);
		}

		@Override
		public void onUpdateMoveHelper() {
			if (this.action == Action.MOVE_TO && !this.EntityBase.getNavigator().noPath()) {
				double distanceX = this.posX - this.EntityBase.posX;
				double distanceY = this.posY - this.EntityBase.posY;
				double distanceZ = this.posZ - this.EntityBase.posZ;
				double distance = Math.abs(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
				distance = MathHelper.sqrt(distance);
				distanceY /= distance;
				float angle = (float) (Math.atan2(distanceZ, distanceX) * 180.0D / Math.PI) - 90.0F;

				this.EntityBase.rotationYaw = this.limitAngle(this.EntityBase.rotationYaw, angle, 10.0F);
				float speed = getAISpeedFish();
				this.EntityBase.setAIMoveSpeed(speed);

				if (this.EntityBase.isAtBottom()) {
					this.EntityBase.setAIMoveSpeed(speed * 0.25F);
				}

				this.EntityBase.motionY += (double) this.EntityBase.getAIMoveSpeed() * distanceY * 0.1D;
			} else {
				this.EntityBase.setAIMoveSpeed(0.0F);
			}
		}
	}
	public static double offsetWall(@Nullable String variant) {
		return -1.36;
	}
	public static double upperfrontverticallinedepth(@Nullable String variant) {
		return 2.0;
	}
	public static double upperbackverticallinedepth(@Nullable String variant) {
		return 2.0;
	}
	public static double upperfrontlineoffset(@Nullable String variant) {
		return 0.0;
	}
	public static double upperfrontlineoffsetperpendiular(@Nullable String variant) {
		return 0.0F;
	}
	public static double upperbacklineoffset(@Nullable String variant) {
		return 0.2;
	}
	public static double upperbacklineoffsetperpendiular(@Nullable String variant) {
		return 1.4F;
	}
	public static double lowerfrontverticallinedepth(@Nullable String variant) {
		if (variant.equalsIgnoreCase("female")) {
			return 1.2;
		}
		return 1.5;
	}
	public static double lowerbackverticallinedepth(@Nullable String variant) {
		if (variant.equalsIgnoreCase("female")) {
			return 1.1;
		}
		return 1.4;
	}
	public static double lowerfrontlineoffset(@Nullable String variant) {
		return -0.1;
	}
	public static double lowerfrontlineoffsetperpendiular(@Nullable String variant) {
		return 0.5F;
	}
	public static double lowerbacklineoffset(@Nullable String variant) {
		return 0.0;
	}
	public static double lowerbacklineoffsetperpendiular(@Nullable String variant) {
		return -0.2F;
	}
	@SideOnly(Side.CLIENT)
	public static ResourceLocation textureDisplay(@Nullable String variant) {
		if (variant.equalsIgnoreCase("female")) {
			return RenderMetopacanthus.TEXTURE_F;
		}
		return RenderMetopacanthus.TEXTURE;
	}
	@SideOnly(Side.CLIENT)
	public static ModelBase modelDisplay(@Nullable String variant) {
		return RenderDisplays.modelMetopacanthus;
	}
	public static float getScaler(@Nullable String variant) {
		if (variant.equalsIgnoreCase("female")) {
			return RenderMetopacanthus.getScaler() * 0.75F;
		}
		return RenderMetopacanthus.getScaler();
	}

}