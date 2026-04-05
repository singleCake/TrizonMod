package card.rare;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import card.TrizonCard;
import power.TrizonRecyclePower;
import power.factory.TrizonSimpleTemplatePowerFactory;

public class Recycle extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Recycle.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Recycle.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Recycle() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 6;

        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);

            reInitBehavior();
        }
    }

    @Override
    public void setBehavior() {
        this.behavior.addToPowerFactorys(new TrizonSimpleTemplatePowerFactory(TrizonRecyclePower.class, baseMagicNumber));
    }
}
