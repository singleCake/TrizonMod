package action;

import java.util.Iterator;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonGainEnergyAction extends TrizonAction {

    public TrizonGainEnergyAction(int energyAmount) {
        this.amount = energyAmount;
        this.isDone = true;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.25F) {
            AbstractDungeon.player.gainEnergy(this.amount);
            AbstractDungeon.actionManager.updateEnergyGain(this.amount);
            Iterator<AbstractCard> var1 = AbstractDungeon.player.hand.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = var1.next();
                c.triggerOnGainEnergy(this.amount, true);
            }
        }

        this.tickDuration();
    }
}
