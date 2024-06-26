package net.lepidodendron.entity.model.llibraryextensions;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;

public class AdvancedModelRendererExtended extends AdvancedModelRenderer {

    private final AdvancedModelBaseExtended model;

    public AdvancedModelRendererExtended(AdvancedModelBaseExtended model, String name) {
        super(model, name);
        this.model = model;
    }

    public AdvancedModelRendererExtended(AdvancedModelBaseExtended model) {
        this(model, null);
    }

    public AdvancedModelRendererExtended(AdvancedModelBaseExtended model, int textureOffsetX, int textureOffsetY) {
        this(model);
        this.setTextureOffset(textureOffsetX, textureOffsetY);
    }


    public void bobExtended(float speed, float degree, boolean bounce, float offset, float f, float f1) {
        float movementScale = this.model.getMovementScale();
        degree *= movementScale;
        speed *= movementScale;
        float bob = (float) (Math.sin(f * speed + offset) * f1 * degree - f1 * degree);
        if (bounce) {
            bob = (float) -Math.abs((Math.sin(f * speed + offset) * f1 * degree));
        }
        this.rotationPointY += bob;
    }


}
