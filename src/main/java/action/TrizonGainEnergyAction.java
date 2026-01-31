package action;

import java.util.Iterator;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonGainEnergyAction extends AbstractTrizonAction {

    public TrizonGainEnergyAction(int energyAmount) {
        this.amount = energyAmount;
    }
    
    @Override
    public void update() {
        AbstractDungeon.player.gainEnergy(this.amount);
        AbstractDungeon.actionManager.updateEnergyGain(this.amount);
        Iterator<AbstractCard> var1 = AbstractDungeon.player.hand.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = var1.next();
            c.triggerOnGainEnergy(this.amount, true);
        }

        this.isDone = true;
    }
}
