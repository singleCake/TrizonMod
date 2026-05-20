package action.helper;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import card.AbstractTrizonCard;

public class ApplyPowerHelper {
    @SuppressWarnings("rawtypes")
    public static int applyPowerToDamage(int baseDamage, DamageType damageType, AbstractCreature target, AbstractTrizonCard<?> this_card) {
        AbstractPlayer player = AbstractDungeon.player;

        float tmp = (float) this_card.modifier.modifyDamage(baseDamage);

        Iterator var3 = player.relics.iterator();
        while (var3.hasNext()) {
            AbstractRelic r = (AbstractRelic) var3.next();
            tmp = r.atDamageModify(tmp, this_card);
        }

        AbstractPower power;
        for (var3 = player.powers.iterator(); var3.hasNext(); tmp = power.atDamageGive(tmp, damageType, this_card)) {
            power = (AbstractPower) var3.next();
        }

        tmp = player.stance.atDamageGive(tmp, damageType, this_card);

        if (target instanceof AbstractMonster) {
            AbstractMonster mo = (AbstractMonster) target;
            for (AbstractPower p : mo.powers)
                tmp = p.atDamageReceive(tmp, damageType, this_card);
            for (AbstractPower p : player.powers)
                tmp = p.atDamageFinalGive(tmp, damageType, this_card);
            for (AbstractPower p : mo.powers)
                tmp = p.atDamageFinalReceive(tmp, damageType, this_card);
            if (tmp < 0.0F)
                tmp = 0.0F;
            return MathUtils.floor(tmp);
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        return MathUtils.floor(tmp);
    }

    @SuppressWarnings("rawtypes")
    public static int applyPowerToBlock(int baseBlock, AbstractTrizonCard<?> this_card) {
        float tmp = (float)baseBlock;

        Iterator var2;
        AbstractPower p;
        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlock(tmp, this_card)) {
            p = (AbstractPower)var2.next();
        }

        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlockLast(tmp)) {
            p = (AbstractPower)var2.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        return MathUtils.floor(tmp);
    }
}
