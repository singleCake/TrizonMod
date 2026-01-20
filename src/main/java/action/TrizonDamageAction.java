package action;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import card.TrizonCard;

public class TrizonDamageAction extends TrizonAction {
    private DamageInfo info;
    private AttackEffect attackEffect;
    
    public TrizonDamageAction(TrizonCard cardPlayed, AbstractCreature target, DamageInfo info) {
        this.cardPlayed = cardPlayed;
        this.target = target;
        this.info = info;
    }

    @Override
    public void update() {
        if (this.duration == 0.1F) {
            if (this.info.type != DamageType.THORNS && (this.info.owner.isDying || this.info.owner.halfDead)) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }
        this.tickDuration();
        if (this.isDone) {
            if (this.attackEffect == AttackEffect.POISON) {
               this.target.tint.color.set(Color.CHARTREUSE.cpy());
               this.target.tint.changeColor(Color.WHITE.cpy());
            } else if (this.attackEffect == AttackEffect.FIRE) {
               this.target.tint.color.set(Color.RED);
               this.target.tint.changeColor(Color.WHITE.cpy());
            }

            this.target.damage(this.info);
            this.cardPlayed.triggerOnAttack(this.target, this.info);
            
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
               AbstractDungeon.actionManager.clearPostCombatActions();
            }
         }
    }
}
