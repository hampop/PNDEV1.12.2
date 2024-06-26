package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraMacrosemius;
import net.lepidodendron.entity.model.entity.ModelMacrosemius;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderMacrosemius extends RenderLivingBaseWithBook<EntityPrehistoricFloraMacrosemius> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/macrosemius.png");

    public static float getScaler() {
        return 0.3F;
    }
    public RenderMacrosemius(RenderManager mgr) {
        super(mgr, new ModelMacrosemius(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraMacrosemius entity) {
        return RenderMacrosemius.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraMacrosemius entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }
    @Override
    protected void preRenderCallback(EntityPrehistoricFloraMacrosemius entity, float f) {
        float scale = this.getScaler();
        if (scale < 0.1f) {
            scale = 0.1f;
        }
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = 0;
    }

}