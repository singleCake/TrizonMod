package card.special;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import card.TrizonCard;

public class EmptyFuseCard extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(EmptyFuseCard.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final int COST = -2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.STATUS;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    public EmptyFuseCard() {
        super(ID, NAME, null, COST, DESCRIPTION, TYPE, RARITY, TARGET);
    }

    @Override
    public void upgrade() {
    }

    @Override
    protected void setBehavior() {
    }
}
