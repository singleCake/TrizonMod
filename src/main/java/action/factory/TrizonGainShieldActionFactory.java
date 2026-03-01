package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import power.TrizonShieldPower;

public class TrizonGainShieldActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonGainShieldActionFactory.class);

    public TrizonGainShieldActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new ApplyPowerAction(
            AbstractDungeon.player, AbstractDungeon.player,
            new TrizonShieldPower(AbstractDungeon.player)
        );
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonGainShieldActionFactory();
    }
}
