package card.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonAttackActionFactory;
import action.factory.TrizonTempCardInHandActionFactory;
import card.TrizonCard;
import card.common.Snowball;

public class Snowman extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Snowman.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Snowman.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Snowman() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.damage = this.baseDamage = 5;
        this.magicNumber = this.baseMagicNumber = 1;
        this.cardsToPreview = new Snowball();
        
        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);

            this.reInitBehavior();
        }
    }

    @Override
    public void setBehavior() {
        this.behavior.addToUseBehavior(new TrizonAttackActionFactory(baseDamage, AttackEffect.SLASH_HORIZONTAL));
        this.behavior.addToUseBehavior(new TrizonTempCardInHandActionFactory(cardsToPreview, baseMagicNumber));
    }
}
