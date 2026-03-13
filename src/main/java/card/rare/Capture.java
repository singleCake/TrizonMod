package card.rare;

import static card.helper.Targeting.CardTargeting.CARD;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonCaptureActionFactory;
import action.factory.TrizonRemoveCardFromDeckActionFactory;
import card.TrizonCard;

public class Capture extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Capture.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Capture.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CARD;

    public Capture() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.exhaust = true;

        reInitBehavior();
    }

    @Override
    public void setBehavior() {
        this.behavior.addToUseBehavior(new TrizonRemoveCardFromDeckActionFactory());
        this.behavior.addToUseBehavior(new TrizonCaptureActionFactory());
    }

    @Override
    public void upgrade() {
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public boolean canFuse() {
        return false;
    }
}
