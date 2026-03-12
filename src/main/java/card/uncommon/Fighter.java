package card.uncommon;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import card.TrizonCard;
import power.TrizonFighterPower;
import power.factory.TrizonSimpleTemplatePowerFactory;

public class Fighter extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Fighter.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Fighter.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Fighter() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 7;

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
        this.behavior.addToPowerFactorys(new TrizonSimpleTemplatePowerFactory(TrizonFighterPower.class, baseMagicNumber));
    }
}
