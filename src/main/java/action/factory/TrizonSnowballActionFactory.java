package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import action.TrizonFreezeCardAction;
import action.TrizonFreezeEnemyAction;

public class TrizonSnowballActionFactory extends AbstractTrizonFactory {
    AbstractCard targetCard;
    AbstractCreature targetCreature;
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonSnowballActionFactory.class);

    public TrizonSnowballActionFactory() {
    }

    @Override
    public void receiveCard(AbstractCard card) {
        this.targetCard = card;
    }

    @Override
    public void receiveTarget(AbstractCreature creature) {
        this.targetCreature = creature;
    }

    @Override
    public AbstractGameAction create() {
        if (targetCreature != null) {
            return new TrizonFreezeEnemyAction(targetCreature);
        }
        if (targetCard != null) {
            return new TrizonFreezeCardAction(targetCard);
        }
        return null;
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonSnowballActionFactory();
    }
}
