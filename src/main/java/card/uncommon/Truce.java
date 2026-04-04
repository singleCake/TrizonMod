package card.uncommon;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonFusedTruceActionFactory;
import action.factory.TrizonTruceActionFactory;
import card.TrizonCard;
import card.helper.CardBehavior;
import card.helper.Tip.FuseInfoTip;

public class Truce extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Truce.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Truce.png";
    private static final int COST = 3;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;

    public Truce() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 4;
        
        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);

            this.reInitBehavior();
        }
    }

    @Override
    public void setBehavior() {
        this.behavior.addToUseBehavior(new TrizonTruceActionFactory());
    }

    @Override
    public CardBehavior getShiftBehavior() {
        CardBehavior shiftBehavior = new CardBehavior();
        shiftBehavior.addToUseBehavior(new TrizonFusedTruceActionFactory(baseMagicNumber));
        return shiftBehavior;
    }

    @Override
    public FuseInfoTip getFuseInfoTip() {
        return new FuseInfoTip(String.format(CARD_STRINGS.EXTENDED_DESCRIPTION[0], baseMagicNumber));
    }
}
