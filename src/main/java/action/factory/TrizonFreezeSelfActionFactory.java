package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonFreezeCardAction;
import card.TrizonCard;

public class TrizonFreezeSelfActionFactory extends AbstractTrizonFactory {
    
    public TrizonFreezeSelfActionFactory(TrizonCard cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonFreezeCardAction(cardPlayed);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        // 这个Action不参与融合
    }
}
