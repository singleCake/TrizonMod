package card.rare;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonExhaustThisCardActionFactory;
import action.factory.TrizonGainEnergyActionFactory;
import card.TrizonCard;

public class Stillbirth extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Stillbirth.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Stillbirth.png";
    private static final int COST = -2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    
    public Stillbirth() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);

        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            reInitBehavior();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void setBehavior() {
    	this.behavior.addToStartOfCombatBehavior(new TrizonExhaustThisCardActionFactory());
        if (this.upgraded) {
            this.behavior.addToStartOfCombatBehavior(new TrizonGainEnergyActionFactory(1));
        }
    }

}
