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

public class TrizonPhoenixPower extends AbstractPower {
    public static final String POWER_ID = TrizonPhoenixPower.class.getSimpleName();
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    AbstractCard cardToCopy;

    private static int idOffset = 0;

    public TrizonPhoenixPower(AbstractCreature owner, AbstractCard card, int amount) {
        this.name = NAME;
        this.ID = POWER_ID + idOffset++;
        this.owner = owner;
        this.cardToCopy = card;
        this.amount = amount;
        this.type = PowerType.BUFF;
        
        String path128 = "TrizonResources/img/powers/TrizonPhoenixPower84.png";
        String path48 = "TrizonResources/img/powers/TrizonPhoenixPower32.png";
        this.region128 = new AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount, this.cardToCopy.name);
    }

    @Override
    public void atStartOfTurn() {
        flash();
        this.addToBot(new MakeTempCardInHandAction(cardToCopy, amount));
        this.addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
