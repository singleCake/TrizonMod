package action;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.TrizonCard;

public class TrizonAttackAction extends AbstractTrizonAction {
    public TrizonAttackAction(TrizonCard cardPlayed, AbstractCreature target, int damage, int times, AttackEffect attackEffect) {
        this.this_card = cardPlayed;
        this.target = target;
        this.damage = this.baseDamage = damage;
        this.times = times;
        this.damageType = DamageInfo.DamageType.NORMAL;
        this.attackEffect = attackEffect;
    }

    @Override
    public void actionBegin() {
        this.applyPowersToAttackDamage();
    }

    @Override
    public void actionRepeat() {
        this.addToBot(new TrizonDamageAction(this.this_card, this.target, new DamageInfo(AbstractDungeon.player, damage), this.attackEffect));
    }
}
