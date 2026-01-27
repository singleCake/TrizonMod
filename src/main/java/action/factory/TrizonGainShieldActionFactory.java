package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import power.TrizonShieldPower;

public class TrizonGainShieldActionFactory extends AbstractTrizonFactory {
    public TrizonGainShieldActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new ApplyPowerAction(
            AbstractDungeon.player, AbstractDungeon.player,
            new TrizonShieldPower(AbstractDungeon.player)
        );
    }
}
