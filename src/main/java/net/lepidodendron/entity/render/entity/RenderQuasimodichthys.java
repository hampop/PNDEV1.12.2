package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraQuasimodichthys;
import net.lepidodendron.entity.model.entity.ModelQuasimodichthys;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderQuasimodichthys extends RenderLivingBaseWithBook<EntityPrehistoricFloraQuasimodichthys> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/quasimodichthys.png");

    public static float getScaler() {
        return 0.55F * 0.595F;
    }
    public RenderQuasimodichthys(RenderManager mgr) {
        super(mgr, new ModelQuasimodichthys(), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraQuasimodichthys entity) {
        return RenderQuasimodichthys.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraQuasimodichthys entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }
    @Override
    protected void preRenderCallback(EntityPrehistoricFloraQuasimodichthys entity, float f) {
        float scale = this.getScaler();
        if (scale < 0.1f) {
            scale = 0.1f;
        }
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = 0;
    }

}