package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraSpathicephalus;
import net.lepidodendron.entity.model.entity.ModelSpathicephalus;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSpathicephalus extends RenderLivingBaseWithBook<EntityPrehistoricFloraSpathicephalus> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/spathicephalus.png");

    public static float getScaler() {return 0.275f;}
    public RenderSpathicephalus(RenderManager mgr) {
        super(mgr, new ModelSpathicephalus(), 0.26f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraSpathicephalus entity) {
        return RenderSpathicephalus.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraSpathicephalus entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraSpathicephalus entity, float f) {
        float scale = entity.getAgeScale()*getScaler();
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.35F;
    }

}