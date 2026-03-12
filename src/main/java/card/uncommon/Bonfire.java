package card.uncommon;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import card.TrizonCard;
import power.TrizonBonfirePower;

public class Bonfire extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Bonfire.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Bonfire.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Bonfire() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.trizonBooleans.fire = true;
        
        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);

            this.reInitBehavior();
        }
    }

    @Override
    protected void setBehavior() {
        this.behavior.addToPowerFactorys(new power.factory.TrizonSimpleTemplatePowerFactory(TrizonBonfirePower.class, baseMagicNumber));
    }
}
