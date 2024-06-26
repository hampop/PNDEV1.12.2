package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraParexus;
import net.lepidodendron.entity.model.entity.ModelParexus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderParexus extends RenderLivingBaseWithBook<EntityPrehistoricFloraParexus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/parexus.png");
    public static float getScaler() {
        return 0.147F;
    }
    public RenderParexus(RenderManager mgr) {
        super(mgr, new ModelParexus(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraParexus entity) {
        return RenderParexus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraParexus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

}