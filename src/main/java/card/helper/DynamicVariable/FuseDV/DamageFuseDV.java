package card.helper.DynamicVariable.FuseDV;

import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import action.helper.ApplyPowerHelper;
import card.TrizonFusedCard;

public class DamageFuseDV extends FuseDV {
    DamageType damageType;
    boolean isRight;

    public DamageFuseDV(int baseValue, DamageType damageType, boolean isRight) {
        super(baseValue);
        this.damageType = damageType;
        this.isRight = isRight;
    }

    public DamageFuseDV(int baseValue, DamageType damageType) {
        this(baseValue, damageType, false);
    }

    @Override
    public void applyPower(TrizonFusedCard this_card) {
        this.applyPower(this_card, null);
    }

    public void applyPower(TrizonFusedCard this_card, AbstractCreature target) {
        AbstractCreature t = null;
        if (this.isRight) {
            for (int i = AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1; i >= 0; i--) {
                if (!((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isDying &&
                        ((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).currentHealth > 0 &&
                        !((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isEscaping) {
                    t = (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i);
                    break;
                }
            }
        } else {
            t = target;
        }
        this.value = ApplyPowerHelper.applyPowerToDamage(baseValue, damageType, t, this_card);
        this.isModified = this.value != this.baseValue;
    }

}
