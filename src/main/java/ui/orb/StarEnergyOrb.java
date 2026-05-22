package ui.orb;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

public class StarEnergyOrb implements EnergyOrbInterface {
    private static final float ORB_IMG_SCALE;
    protected Texture energyLayers;
    protected Texture noEnergyLayers;
    protected Texture orbVfx;
    protected float layerSpeeds;
    protected float angles = 0.0f;

    public StarEnergyOrb() {
        energyLayers = ImageMaster.loadImage("TrizonResources/img/ui/orb/star.png");
        noEnergyLayers = ImageMaster.loadImage("TrizonResources/img/ui/orb/no_star.png");
        orbVfx = ImageMaster.loadImage("TrizonResources/img/ui/orb/star_vfx.png");
    }

    public Texture getEnergyImage() {
        return this.orbVfx;
    }

    public void updateOrb(int energyCount) {
        if (energyCount == 0) {

        } else {

        }

    }

    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        sb.setColor(Color.WHITE);
        if (enabled) {
            sb.draw(this.energyLayers, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F,
                    ORB_IMG_SCALE, ORB_IMG_SCALE, this.angles, 0, 0, 128, 128, false, false);
        } else {
            sb.draw(this.noEnergyLayers, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F,
                    ORB_IMG_SCALE, ORB_IMG_SCALE, this.angles, 0, 0, 128, 128, false, false);
        }
    }

    static {
        ORB_IMG_SCALE = 1.15F * Settings.scale;
    }
}
