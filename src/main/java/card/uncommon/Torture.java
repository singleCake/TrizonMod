package card.uncommon;

import static com.evacipated.cardcrawl.mod.stslib.cards.targeting.SelfOrEnemyTargeting.SELF_OR_ENEMY;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonSpellActionFactory;
import action.factory.TrizonTortureActionFactory;
import card.TrizonCard;
import card.helper.CardBehavior;
import card.helper.Tip.FuseInfoTip;

public class Torture extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Torture.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME =  CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Torture.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = SELF_OR_ENEMY;

    public Torture() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.spellNumber = this.baseSpellNumber = 1;
        this.damageTimes = this.baseDamageTimes = 4;
        
        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamageTimes(1);

            this.reInitBehavior();
        }
    }

    @Override
    public void setBehavior() {
        this.behavior.addToUseBehavior(new TrizonSpellActionFactory(baseSpellNumber, baseDamageTimes, AttackEffect.POISON));
    }

    @Override
    public CardBehavior getShiftBehavior() {
        CardBehavior shiftBehavior = new CardBehavior();
        shiftBehavior.addToUseBehavior(new TrizonTortureActionFactory(baseSpellNumber, baseDamageTimes, AttackEffect.POISON));
        return shiftBehavior;
    }

    @Override
    public FuseInfoTip getFuseInfoTip() {
        return new FuseInfoTip(String.format(CARD_STRINGS.EXTENDED_DESCRIPTION[0], baseSpellNumber, baseDamageTimes));
    }
}
