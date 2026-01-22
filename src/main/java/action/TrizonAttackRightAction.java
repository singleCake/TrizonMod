package action;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.TrizonCard;

public class TrizonAttackRightAction extends TrizonAction {
    public TrizonAttackRightAction(TrizonCard cardPlayed, int damage, int times) {
        this.cardPlayed = cardPlayed;
        this.target = AbstractDungeon.getCurrRoom().monsters.monsters.get(AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1);
        this.damage = this.baseDamage = damage;
        this.times = times;
        this.attackEffect = AttackEffect.BLUNT_LIGHT;
        this.damageType = DamageInfo.DamageType.NORMAL;
    }

    @Override
    public void actionBegin() {
        this.applyPowersToDamage();
    }

    @Override
    public void actionRepeat() {
        this.addToBot(new TrizonDamageAction(this.cardPlayed, this.target, new DamageInfo(AbstractDungeon.player, damage), this.attackEffect));
    }
}
