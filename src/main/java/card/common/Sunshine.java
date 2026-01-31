package card.common;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonGainBlockActionFactory;
import action.factory.TrizonSummonFireActionFactory;
import card.TrizonCard;

public class Sunshine extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Sunshine.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Sunshine.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Sunshine() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.block = this.baseBlock = 8;
        this.trizonBooleans.fire = true;
        this.exhaust = true;
        
        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);

            this.reInitBehavior();
        }
    }

    @Override
    protected void setBehavior() {
        this.behavior.addToUseBehavior(new TrizonGainBlockActionFactory(baseBlock));
        this.behavior.addToExhaustBehavior(new TrizonSummonFireActionFactory());
    }
}
