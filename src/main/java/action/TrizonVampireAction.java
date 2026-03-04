package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;

public class TrizonVampireAction extends AbstractGameAction {
    AbstractCreature target;
    DamageInfo info;

    public TrizonVampireAction(AbstractCreature target, DamageInfo info) {
        this.target = target;
        this.info = info;
    }

    @Override
    public void update() {
        if (info.type == DamageType.THORNS && this.target.lastDamageTaken > 0) {
            addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, target.lastDamageTaken));
            addToTop(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY))); 
        }
        this.isDone = true;
    }
}
