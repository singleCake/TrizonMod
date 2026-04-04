package card.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonAttackActionFactory;
import action.factory.TrizonExhaustRightCardActionFactory;
import card.TrizonCard;

public class Demon extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Demon.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Demon.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Demon() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.damage = this.baseDamage = 7;

        this.reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);

            this.reInitBehavior();
        }
    }

    @Override
    public void setBehavior() {
        this.behavior.addToUseBehavior(new TrizonAttackActionFactory(baseDamage, AttackEffect.SLASH_HORIZONTAL));
        this.behavior.addToUseBehavior(new TrizonExhaustRightCardActionFactory(1));
    }
}
