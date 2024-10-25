package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraArchaeocidaris;
import net.lepidodendron.entity.model.entity.ModelArchaeocidaris;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderArchaeocidaris extends RenderLivingBaseWithBook<EntityPrehistoricFloraArchaeocidaris> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/archaeocidaris.png");

    public RenderArchaeocidaris(RenderManager mgr) {
        super(mgr, new ModelArchaeocidaris(), 0.0f);
    }

    public static float getScaler() {
        return 0.22F;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraArchaeocidaris entity) {
        return RenderArchaeocidaris.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraArchaeocidaris entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraArchaeocidaris entity, float f) {
        float scale = this.getScaler();
        GlStateManager.scale(scale, scale, scale);
    }
}