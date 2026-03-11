package card.rare;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonSummonCardActionFactory;
import card.TrizonCard;

public class Abyss extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Abyss.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Abyss.png";
    private static final int COST = -2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Abyss() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;

        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);

            reInitBehavior();
        }
    }

    @Override
    public void setBehavior() {
        this.behavior.addToStartOfTurnAfterExhaustedBehavior(
                new TrizonSummonCardActionFactory(baseMagicNumber, false, true));
    }
}
