package action;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.TrizonCard;

public class TrizonAttackRightAction extends TrizonAction {
    public TrizonAttackRightAction(TrizonCard cardPlayed, int damage, int times) {
        this.cardPlayed = cardPlayed;
        this.target = AbstractDungeon.getCurrRoom().monsters.monsters.get(AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1);
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
