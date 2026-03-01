package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TrizonTempCardInHandActionFactory extends AbstractTrizonFactory {
    AbstractCard card;
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(MakeTempCardInHandAction.class);

    public TrizonTempCardInHandActionFactory(AbstractCard card) {
        this(card, 1);
    }

    public TrizonTempCardInHandActionFactory(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new MakeTempCardInHandAction(card, amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount, card.name);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonTempCardInHandActionFactory(card, amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonTempCardInHandActionFactory) {
            if (this.card.cardID.equals(((TrizonTempCardInHandActionFactory) other).card.cardID)) {
                this.amount += other.amount;
                return true;
            }
        }
        return false;
    }
}
