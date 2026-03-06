package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonHealActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonHealActionFactory.class);

    public TrizonHealActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new HealAction(AbstractDungeon.player, AbstractDungeon.player, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonHealActionFactory(this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonHealActionFactory) {
            TrizonHealActionFactory o = (TrizonHealActionFactory) other;
            this.amount += o.amount;
            return true;
        }

        return false;
    }
}
