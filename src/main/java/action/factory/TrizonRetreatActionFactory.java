package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class TrizonRetreatActionFactory extends AbstractTrizonFactory {
    public TrizonRetreatActionFactory() {
        this.times = 1;
    }

    @Override
    public AbstractGameAction create() {
        return new action.TrizonRetreatAction(this.times);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonRetreatActionFactory) {
            this.times += ((TrizonRetreatActionFactory) other).times;
        }
    }
}
