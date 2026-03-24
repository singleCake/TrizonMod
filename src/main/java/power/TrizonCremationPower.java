package power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import card.AbstractTrizonCard;
import card.common.Fireball;

public class TrizonCremationPower extends AbstractPower {
    public static final String POWER_ID = TrizonCremationPower.class.getSimpleName();
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TrizonCremationPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.amount = amount;
        this.owner = owner;
        this.updateDescription();

        String path128 = "TrizonResources/img/powers/TrizonCremationPower84.png";
        String path48 = "TrizonResources/img/powers/TrizonCremationPower32.png";
        this.region128 = new AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card instanceof AbstractTrizonCard) {
            if (((AbstractTrizonCard<?>) card).isFire())
                return;
        }

        this.addToBot(new MakeTempCardInHandAction(new Fireball(), this.amount));
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}
