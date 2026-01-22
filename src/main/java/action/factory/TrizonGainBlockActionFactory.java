package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonGainBlockAction;
import card.TrizonCard;

public class TrizonGainBlockActionFactory extends AbstractTrizonFactory {
    public TrizonGainBlockActionFactory(TrizonCard cardPlayed, int blockAmount) {
        this.amount = blockAmount;
        this.cardPlayed = cardPlayed;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonGainBlockAction(this.cardPlayed, this.amount);
    }
    
    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonGainBlockActionFactory) {
            this.amount += ((TrizonGainBlockActionFactory) other).amount;
        }
    }
}
