package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import power.TrizonSpellBuffPower;

public class TrizonSpellBuffFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonSpellBuffFactory.class);
    private int amount;

    public TrizonSpellBuffFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrizonSpellBuffPower(AbstractDungeon.player, amount));
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonSpellBuffFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonSpellBuffFactory) {
            TrizonSpellBuffFactory o = (TrizonSpellBuffFactory) other;
            this.amount += o.amount;
            return true;
        }
        return false;
    }
}
