package card.common;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonAttackRightActionFactory;
import card.TrizonCard;

public class Bird extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Bird.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Bird.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Bird() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = 1;
        this.baseDamageTimes = 6;
        setBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamageTimes(2);
            
            this.setBehavior();
        }
    }

    @Override
    public void setBehavior() {
        this.behavior.clearBehavior();
        this.behavior.addToUseBehavior(new TrizonAttackRightActionFactory(this, baseDamage, baseDamageTimes));
    }
}
