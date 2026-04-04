package card.rare;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonSpellBuffFactory;
import card.TrizonCard;

public class Noodle extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Noodle.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Noodle.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Noodle() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            
            reInitBehavior();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void setBehavior() {
        this.behavior.addToUseBehavior(new TrizonSpellBuffFactory(baseMagicNumber));
        
    }
}
