package power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import action.TrizonSummonCardAction;
import power.helper.PowerHelper;

public class TrizonScavengerPower extends AbstractPower {
    public static final String POWER_ID = PowerHelper.makeID(TrizonScavengerPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TrizonScavengerPower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = amount;
        this.type = PowerType.BUFF;

        String path128 = "TrizonResources/img/powers/TrizonScavengerPower84.png";
        String path48 = "TrizonResources/img/powers/TrizonScavengerPower32.png";
        this.region128 = new AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new TrizonSummonCardAction(1));

        addToBot(new ReducePowerAction(owner, owner, POWER_ID, 1));
        if (this.amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }
}
