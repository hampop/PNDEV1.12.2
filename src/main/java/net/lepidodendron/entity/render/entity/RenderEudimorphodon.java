package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraEudimorphodon;
import net.lepidodendron.entity.model.entity.ModelEudimorphodon;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEudimorphodon extends RenderLivingBaseWithBook<EntityPrehistoricFloraEudimorphodon> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/eudimorphodon.png");

    public RenderEudimorphodon(RenderManager mgr) {
        super(mgr, new ModelEudimorphodon(), 0.10f);
    }
    public static float getScaler() {
        return 0.1925F;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraEudimorphodon entity) {
        return RenderEudimorphodon.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraEudimorphodon entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraEudimorphodon entity, float f) {
        float scale = entity.getAgeScale();
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * scale * 0.35F;
    }

}





