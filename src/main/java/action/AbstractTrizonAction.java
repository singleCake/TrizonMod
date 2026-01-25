package action;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import card.TrizonCard;

public abstract class AbstractTrizonAction extends AbstractGameAction {
    protected TrizonCard this_card;     // 触发这个action的卡牌
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

    public void actionBegin() {}
    public void actionEnd() {}
    public void actionRepeat() {}

    @SuppressWarnings("rawtypes")
    protected void applyPowersToAttackDamage() {
        AbstractPlayer player = AbstractDungeon.player;
        float tmp = (float)this.baseDamage;

        tmp += this_card.modifier.damage;

        Iterator var3 = player.relics.iterator();
        while(var3.hasNext()) {
            AbstractRelic r = (AbstractRelic)var3.next();
            tmp = r.atDamageModify(tmp, this_card);
        }

        AbstractPower p;
        for(var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageGive(tmp, damageType, this_card)) {
            p = (AbstractPower)var3.next();
        }

        tmp = player.stance.atDamageGive(tmp, damageType, this_card);

        for(var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageFinalGive(tmp, damageType, this_card)) {
            p = (AbstractPower)var3.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        this.damage = MathUtils.floor(tmp);
    }
}
