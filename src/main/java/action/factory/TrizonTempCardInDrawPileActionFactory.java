package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TrizonTempCardInDrawPileActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonTempCardInDrawPileActionFactory.class);

    AbstractCard card;

    public TrizonTempCardInDrawPileActionFactory(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new MakeTempCardInDrawPileAction(card, amount, true, true);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount, card.name);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonTempCardInDrawPileActionFactory(card, amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonTempCardInDrawPileActionFactory) {
            if (this.card.cardID.equals(((TrizonTempCardInDrawPileActionFactory) other).card.cardID)) {
                this.amount += other.amount;
                return true;
            }
        }
        return false;
    }
}
