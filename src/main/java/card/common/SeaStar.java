package card.common;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonGainBlockActionFactory;
import card.TrizonCard;
import power.factory.TrizonSeaStarPowerFactory;

public class SeaStar extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(SeaStar.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/SeaStar.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SeaStar() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.block = this.baseBlock = 6;
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        
        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);

            this.reInitBehavior();
        }
    }

    @Override
    protected void setBehavior() {
        this.behavior.addToUseBehavior(new TrizonGainBlockActionFactory(baseBlock));
        this.powerFactorys.add(new TrizonSeaStarPowerFactory(baseMagicNumber));
    }
}
