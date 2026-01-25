package action;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class TrizonGainBlockAction extends AbstractTrizonAction {
    public TrizonGainBlockAction(int blockAmount) {
        this.target = AbstractDungeon.player;
        this.baseBlock = this.block = blockAmount;
    }
    
    @Override
    public void update() {
        if (!this.target.isDying && !this.target.isDead && this.duration == 0.25F) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SHIELD));
            this.applyPowersToBlock();
            this.target.addBlock(this.block);
            Iterator<AbstractCard> var1 = AbstractDungeon.player.hand.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                c.applyPowers();
            }
        }

        this.tickDuration();
    }

    @SuppressWarnings("rawtypes")
    private void applyPowersToBlock() {
        float tmp = (float)this.baseBlock;

        Iterator var2;
        AbstractPower p;
        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlock(tmp, this.this_card)) {
            p = (AbstractPower)var2.next();
        }

        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlockLast(tmp)) {
            p = (AbstractPower)var2.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        this.block = MathUtils.floor(tmp);
    }
}
