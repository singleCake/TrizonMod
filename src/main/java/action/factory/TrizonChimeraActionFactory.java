package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonChimeraAction;

public class TrizonChimeraActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonChimeraActionFactory.class);

    public TrizonChimeraActionFactory(int times) {
        this.times = times;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonChimeraAction(this_card, times);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonChimeraActionFactory(times);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, times);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonChimeraActionFactory) {
            TrizonChimeraActionFactory o = (TrizonChimeraActionFactory) other;
            this.times += o.times;
            return true;
        }

        return false;
    }
    
}
