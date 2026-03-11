package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import action.AbstractTrizonAction;

public class TrizonCroissantActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonCroissantActionFactory.class);

    public TrizonCroissantActionFactory() {
        this(1);
    }

    public TrizonCroissantActionFactory(int times) {
        this.times = times;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonCroissantAction(this.times);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, times);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonCroissantActionFactory(this.times);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonCroissantActionFactory) {
            this.times += other.times;
            return true;
        }
        return false;
    }

    private static class TrizonCroissantAction extends AbstractTrizonAction {
        public TrizonCroissantAction(int times) {
            this.times = times;
        }

        @Override
        public void actionRepeat() {
            int handSize = AbstractDungeon.player.hand.size();
            if (handSize > 0) {
                this.addToTop(new DrawCardAction(handSize));
                this.addToTop(new ExhaustAction(handSize, false));
            }
        }
    }
}
