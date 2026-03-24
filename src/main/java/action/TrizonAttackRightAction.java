package action;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.AbstractTrizonCard;

public class TrizonAttackRightAction extends AbstractTrizonAction {
    public TrizonAttackRightAction(AbstractTrizonCard<?> cardPlayed, int damage, int times) {
        this.this_card = cardPlayed;
        for (int i = AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1; i >= 0; i--) {
            if (!((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isDying &&
                    ((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).currentHealth > 0 &&
                    !((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isEscaping) {
                this.target = (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i);
                break;
            }
        }
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
        this.addToBot(new TrizonDamageAction(this.this_card, this.target,
                new DamageInfo(AbstractDungeon.player, damage), this.attackEffect));
    }
}
