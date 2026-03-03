package relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import basemod.abstracts.CustomRelic;
import ui.campfire.FuseOption;

public class TrizonFuseRelic extends CustomRelic {
    public static final String ID = TrizonFuseRelic.class.getSimpleName();
    private static final String IMG_PATH = "TrizonResources/img/relics/TrizonFuseRelic.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public TrizonFuseRelic() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TrizonFuseRelic();
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(new FuseOption());
    }
}