package action;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import card.AbstractTrizonCard;
import patch.TrizonEnum;

public class TrizonDamageAction extends AbstractTrizonAction {
    private DamageInfo info;
    private AttackEffect attackEffect;

    public TrizonDamageAction(AbstractTrizonCard<?> cardPlayed, AbstractCreature target, DamageInfo info,
            AttackEffect attackEffect) {
        this.this_card = cardPlayed;
        this.target = target;
        this.info = info;
        this.attackEffect = attackEffect;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        if (this.duration == 0.1F) {
            if (target == null) {
                this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            }
            if (this.info.owner.isDying || this.info.owner.halfDead) {
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
        if (this.isDone) {
            if (this.attackEffect == AttackEffect.POISON) {
                this.target.tint.color.set(Color.CHARTREUSE.cpy());
                this.target.tint.changeColor(Color.WHITE.cpy());
                AbstractDungeon.effectList
                        .add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            } else if (this.attackEffect == AttackEffect.FIRE) {
                this.target.tint.color.set(Color.RED);
                this.target.tint.changeColor(Color.WHITE.cpy());
                AbstractDungeon.effectList
                        .add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            } else if (this.attackEffect == AttackEffect.LIGHTNING) {
                CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1F);
                AbstractDungeon.effectList.add(new LightningEffect(this.target.hb.cX, this.target.hb.cY));
            } else if (this.attackEffect == TrizonEnum.VAMPIRE) {
                this.target.tint.color.set(Color.RED.cpy());
                this.target.tint.changeColor(Color.WHITE.cpy());
                AbstractDungeon.effectList.add(new BiteEffect(this.target.hb.cX, this.target.hb.cY));
            } else {
                AbstractDungeon.effectList
                        .add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            }

            if (!this.target.isDying && !this.target.halfDead) {
                this.target.damage(this.info);
                this.this_card.triggerOnAttack(this.target, this.info);
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
    }
}
