package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import action.AbstractTrizonAction;
import action.TrizonDebuffAllEnemyAction;
import action.TrizonFreezeCardAction;
import power.TrizonFrozenPower;

public class TrizonTruceActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonTruceActionFactory.class);

    public TrizonTruceActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonTruceAction();
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonTruceActionFactory();
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    private static class TrizonTruceAction extends AbstractTrizonAction {
        @Override
        public void update() {
            for (int i = AbstractDungeon.player.hand.group.size() - 1; i >= 0; i--) {
                this.addToTop(new TrizonFreezeCardAction(AbstractDungeon.player.hand.group.get(i)));
            }
            this.addToTop(new TrizonDebuffAllEnemyAction(TrizonFrozenPower::new));
            this.isDone = true;
        }
    }
    
}
