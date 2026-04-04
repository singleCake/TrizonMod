package card.common;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import card.TrizonCard;
import power.TrizonMeatWallPower;
import power.factory.TrizonSimpleTemplatePowerFactory;

public class MeatWall extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(MeatWall.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/MeatWall.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public MeatWall() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;

        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);

            reInitBehavior();
        }
    }

    @Override
    public void setBehavior() {
        this.behavior.addToPowerFactorys(new TrizonSimpleTemplatePowerFactory(TrizonMeatWallPower.class, baseMagicNumber));
    }
}
