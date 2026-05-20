package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;

import action.helper.ApplyPowerHelper;
import card.AbstractTrizonCard;

public abstract class AbstractTrizonAction extends AbstractGameAction {
    protected AbstractTrizonCard<?> this_card; // 触发这个action的卡牌
    protected int times;

    protected int baseDamage;
    protected int damage;
    protected int baseBlock;
    protected int block;
    protected DamageType damageType = DamageType.NORMAL;

    @Override
    public void update() {
        actionBegin();
        for (int i = 0; i < this.times; i++) {
            actionRepeat();
        }
        actionEnd();
        this.isDone = true;
    }

    public void actionBegin() {
    }

    public void actionEnd() {
    }

    public void actionRepeat() {
    }

    protected void applyPowersToDamage() {
        this.damage = ApplyPowerHelper.applyPowerToDamage(this.baseDamage, this.damageType, this.target, this.this_card);
    }

    protected void applyPowersToBlock() {
        this.block = ApplyPowerHelper.applyPowerToBlock(this.baseBlock, this.this_card);
    }
}
