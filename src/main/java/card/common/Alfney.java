package card.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonAlfneyActionFactory;
import action.factory.TrizonAttackActionFactory;
import card.TrizonCard;

public class Alfney extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Alfney.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Alfney.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Alfney() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.damage = this.baseDamage = 8;
        this.block = this.baseBlock = 8;
        this.isEthereal = true;
        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeBlock(3);
            this.reInitBehavior();
        }
    }

    @Override
    protected void setBehavior() {
        this.behavior.addToUseBehavior(new TrizonAttackActionFactory(baseDamage, AttackEffect.SLASH_HORIZONTAL));
        this.behavior.addToExhaustBehavior(new TrizonAlfneyActionFactory(baseBlock));
    }
}
