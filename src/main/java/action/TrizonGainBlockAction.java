package action;

import java.util.Iterator;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class TrizonGainBlockAction extends AbstractTrizonAction {
    public TrizonGainBlockAction(int blockAmount) {
        this.target = AbstractDungeon.player;
        this.baseBlock = this.block = blockAmount;
    }
    
    @Override
    public void update() {
        if (!this.target.isDying && !this.target.isDead) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SHIELD));
            this.applyPowersToBlock();
            this.target.addBlock(this.block);
            Iterator<AbstractCard> var1 = AbstractDungeon.player.hand.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                c.applyPowers();
            }
        }

        this.isDone = true;
    }
}
