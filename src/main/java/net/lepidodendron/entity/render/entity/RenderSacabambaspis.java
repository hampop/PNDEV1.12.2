package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraSacabambaspis;
import net.lepidodendron.entity.model.entity.ModelSacabambaspis;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSacabambaspis extends RenderLivingBaseWithBook<EntityPrehistoricFloraSacabambaspis> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/sacabambaspis.png");
    public static float getScaler() {
        return 0.25F;
    }

    public RenderSacabambaspis(RenderManager mgr) {
        super(mgr, new ModelSacabambaspis(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraSacabambaspis entity) {
        return RenderSacabambaspis.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraSacabambaspis entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }
    protected void preRenderCallback(EntityPrehistoricFloraSacabambaspis entity, float f) {
        float scale = this.getScaler();
        if (scale < 0.1f) {
            scale = 0.1f;
        }
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = 0;
    }
}