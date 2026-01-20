package action;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.TrizonCard;

public class TrizonAttackAction extends TrizonAction {
    public TrizonAttackAction(TrizonCard cardPlayed, AbstractCreature target, int damage, int times) {
        this.cardPlayed = cardPlayed;
        this.target = target;
        this.damage = this.baseDamage = damage;
        this.damageTimes = this.baseDamageTimes = times;
        this.damageType = DamageInfo.DamageType.NORMAL;
    }

    @Override
    public void update() {
        this.applyPowersToDamage();
        for (int i = 0; i < this.damageTimes; i++) {
            this.addToBot(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, damage), AttackEffect.SLASH_HORIZONTAL));
        }
        this.isDone = true;
    }
}
