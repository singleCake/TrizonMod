package action;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.function.Function;

public class TrizonDebuffAllEnemyAction extends AbstractTrizonAction {
    Function<AbstractCreature, AbstractPower> powerFactory;

    public TrizonDebuffAllEnemyAction(Function<AbstractCreature, AbstractPower> powerFactory) {
        this.powerFactory = powerFactory;
    }

    @Override
    public void update() {
        for (int i = AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1; i >= 0; i--) {
            AbstractMonster monster = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            this.addToTop(new ApplyPowerAction(monster, AbstractDungeon.player, this.powerFactory.apply(monster)));
        }

        this.isDone = true;
    }
    
}
