package card.rare;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonFreezeAllEnemyActionFactory;
import action.factory.TrizonFusedBlizzardActionFactory;
import card.TrizonCard;
import card.helper.CardBehavior;
import card.helper.Tip.FuseInfoTip;

public class Blizzard extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Blizzard.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Blizzard.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Blizzard() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 3;
        
        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);

            this.reInitBehavior();
        }
    }

    @Override
    protected void setBehavior() {
        this.behavior.addToUseBehavior(new TrizonFreezeAllEnemyActionFactory());
    }

    @Override
    public CardBehavior getShiftBehavior() {
        CardBehavior shiftBehavior = new CardBehavior();
        shiftBehavior.addToUseBehavior(new TrizonFusedBlizzardActionFactory(baseMagicNumber));
        return shiftBehavior;
    }

    @Override
    public FuseInfoTip getFuseInfoTip() {
        return new FuseInfoTip(String.format(CARD_STRINGS.EXTENDED_DESCRIPTION[0], baseMagicNumber));
    }
}
