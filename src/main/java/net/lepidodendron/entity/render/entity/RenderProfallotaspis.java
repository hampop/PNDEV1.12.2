package net.lepidodendron.entity.render.entity;

import net.lepidodendron.LepidodendronMod;
import net.lepidodendron.entity.EntityPrehistoricFloraProfallotaspis;
import net.lepidodendron.entity.model.entity.ModelProfallotaspis;
import net.lepidodendron.entity.render.RenderLivingBaseWithBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderProfallotaspis extends RenderLivingBaseWithBook<EntityPrehistoricFloraProfallotaspis> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(LepidodendronMod.MODID + ":textures/entities/profallotaspis.png");
    public static float getScaler() {
        return 0.15F;
    }
    public RenderProfallotaspis(RenderManager mgr) {
        super(mgr, new ModelProfallotaspis(), 0.00f);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoricFloraProfallotaspis entity) {
        return RenderProfallotaspis.TEXTURE;
    }

    @Override
    protected void applyRotations(EntityPrehistoricFloraProfallotaspis entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityPrehistoricFloraProfallotaspis entity, float f) {
        float scale = 0.85F;
        GlStateManager.scale(scale, scale, scale);
    }

}