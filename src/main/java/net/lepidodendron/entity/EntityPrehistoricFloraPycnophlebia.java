
package net.lepidodendron.entity;

import net.ilexiconn.llibrary.client.model.tools.ChainBuffer;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.block.BlockGlassJar;
import net.lepidodendron.block.BlockInsectEggsPycnophlebia;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityPrehistoricFloraPycnophlebia extends EntityPrehistoricFloraArchoblattinaInsect {

	public BlockPos currentTarget;
	@SideOnly(Side.CLIENT)
	public ChainBuffer chainBuffer;
	private int animationTick;
	private Animation animation = NO_ANIMATION;
	
	private static final DataParameter<Integer> PYCNOPHLEBIA_TYPE = EntityDataManager.<Integer>createKey(EntityPrehistoricFloraPycnophlebia.class, DataSerializers.VARINT);

	public EntityPrehistoricFloraPycnophlebia(World world) {
		super(world);
		setSize(0.18F, 0.15F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(PYCNOPHLEBIA_TYPE, 0);
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
			EntityPrehistoricFloraPycnophlebia.Type typeThis = this.getPNType();
			EntityPrehistoricFloraPycnophlebia.Type typeThat = ((EntityPrehistoricFloraPycnophlebia) otherAnimal).getPNType();
			if (typeThis == typeThat) {
				return false;
			}
		}
		return this.isInLove() && otherAnimal.isInLove();
	}

	public boolean hasPNVariants() {
		return true;
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

		public static EntityPrehistoricFloraPycnophlebia.Type byId(int id)
		{
			if (id < 0 || id >= values().length)
			{
				id = 0;
			}

			return values()[id];
		}

		public static EntityPrehistoricFloraPycnophlebia.Type getTypeFromString(String nameIn)
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
		this.setPNType(EntityPrehistoricFloraPycnophlebia.Type.byId(rand.nextInt(EntityPrehistoricFloraPycnophlebia.Type.values().length) + 1));
		return livingdata;
	}

	public void setPNType(EntityPrehistoricFloraPycnophlebia.Type type)
	{
		this.dataManager.set(PYCNOPHLEBIA_TYPE, Integer.valueOf(type.ordinal()));
	}

	public EntityPrehistoricFloraPycnophlebia.Type getPNType()
	{
		return EntityPrehistoricFloraPycnophlebia.Type.byId(((Integer)this.dataManager.get(PYCNOPHLEBIA_TYPE)).intValue());
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setString("PNType", this.getPNType().getName());
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("PNType", 8))
		{
			this.setPNType(EntityPrehistoricFloraPycnophlebia.Type.getTypeFromString(compound.getString("PNType")));
		}
	}

	@Override
	public boolean canJar() {
		return true;
	}

	@Override
	public int defaultFlyCooldown() {
		return 3000;
	}

	@Override
	public int defaultWanderCooldown() {
		return 500;
	}

	public static String getPeriod() {return "Jurassic";}
	@Override
	public float getAISpeedLand() {
		if (this.getTicks() < 0) {
			return 0.0F; //Is laying eggs
		}
		return 0.18F;
	}

	@Override
	public IBlockState getEggBlockState() {
		return BlockInsectEggsPycnophlebia.block.getDefaultState();
	}


	//public static String getHabitat() {return "Terrestrial";}



	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getImmediateSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) source.getImmediateSource();
			player.attackEntityFrom(DamageSource.CACTUS, (float) 2);
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	public String tagEgg () {
		return "insect_eggs_pycnophlebia";
	}

	@Nullable
	protected ResourceLocation getLootTable() { return LepidodendronMod.BUG_LOOT;}

	@Override
	protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)
	{
		if (source == BlockGlassJar.BlockCustom.FREEZE) {
			//System.err.println("Jar loot!");
			ResourceLocation resourcelocation = LepidodendronMod.PYCNOPHLEBIA_LOOT_JAR;
			if (this.getPNType() == Type.FEMALE) {
				resourcelocation = LepidodendronMod.PYCNOPHLEBIA_LOOT_JAR_F;
			}
			LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(resourcelocation);
			LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer)this.world)).withLootedEntity(this).withDamageSource(source);
			for (ItemStack itemstack : loottable.generateLootForPools(this.rand, lootcontext$builder.build()))
			{
				NBTTagCompound variantNBT = new NBTTagCompound();
				variantNBT.setString("PNType", "");
				String stringEgg = EntityRegistry.getEntry(this.getClass()).getRegistryName().toString();
				variantNBT.setString("PNDisplaycase", stringEgg);
				itemstack.setTagCompound(variantNBT);
				this.entityDropItem(itemstack, 0.0F);
			}
		}
		else {
			super.dropLoot(wasRecentlyHit, lootingModifier, source);
		}

	}

}