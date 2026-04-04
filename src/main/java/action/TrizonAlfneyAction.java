package action;

import java.util.ArrayList;
import java.util.Iterator;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class TrizonAlfneyAction extends AbstractTrizonAction {
    public TrizonAlfneyAction(int block) {
        this.baseBlock = this.block = block;
    }

    @Override
    public void update() {
        ArrayList<AbstractCreature> targets = new ArrayList<>();
        targets.add(AbstractDungeon.player);
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDying && !mo.isDead) {
                targets.add(mo);
            }
        }

        this.target = targets.get(AbstractDungeon.cardRandomRng.random(targets.size() - 1));
        AbstractDungeon.effectList
                .add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SHIELD));
        this.applyPowersToBlock();
        this.target.addBlock(this.block);
        Iterator<AbstractCard> var1 = AbstractDungeon.player.hand.group.iterator();

        while (var1.hasNext()) {
            AbstractCard c = (AbstractCard) var1.next();
            c.applyPowers();
        }

        this.isDone = true;
    }
}
