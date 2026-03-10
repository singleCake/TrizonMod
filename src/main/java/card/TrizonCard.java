package card;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import card.helper.CardBehavior;
import card.helper.TrizonCardBooleans;
import card.helper.Modifier.CardModifierList;
import power.TrizonSpellBuffPower;

import static modcore.TrizonMod.PlayerColorEnum.Trizon_COLOR;

public abstract class TrizonCard extends CustomCard {
    protected CardBehavior behavior = new CardBehavior();

    public int damageTimes = 1;
    public int baseDamageTimes = 1;
    public boolean isDamageTimesModified = false;
    public boolean upgradedDamageTimes = false;

    public int spellNumber = 0;
    public int baseSpellNumber = 0;
    public boolean isSpellNumberModified = false;
    public boolean upgradedSpellNumber = false;

    protected int anti_num = 0;

    public CardModifierList modifier = new CardModifierList();

    protected TrizonCardBooleans trizonBooleans = new TrizonCardBooleans();

    public TrizonCard(String id, String name, String img, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, Trizon_COLOR, rarity, target);
        this.textureImg = img;
        this.behavior.setThisCard(this);
    }

    protected abstract void setBehavior();
    protected void reInitBehavior() {
        this.behavior.clearBehavior();
        this.modifier.clear();
        setBehavior();
        this.behavior.setThisCard(this);
    }

    protected void upgradeDamageTimes(int amount) {
        this.baseDamageTimes += amount;
        this.damageTimes = this.baseDamageTimes;
        this.upgradedDamageTimes = true;
    }

    protected void upgradeSpellNumber(int amount) {
        this.baseSpellNumber += amount;
        this.spellNumber = this.baseSpellNumber;
        this.upgradedSpellNumber = true;
    }

    // 部分卡牌在融合时有特殊效果，重写这个方法
    public void onFuse(TrizonFusedCard fusedCard) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return trizonBooleans.canUse && super.canUse(p, m);
    }

    public boolean canFuse() {
        return true;
    }

    @Override
    public void update() {
        modifier.updateCost(this);
        super.update();
    }

    // 原版接口

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        behavior.useBehavior(p, m);
    }

    @Override
    public void triggerOnExhaust() {
        behavior.onExhaustBehavior();
    }

    @Override
    public void triggerWhenDrawn() {
        behavior.onDrawnBehavior();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        behavior.onOtherCardPlayedBehavior(c);
        modifier.triggerOnOtherCardPlayed(c);
    }

    // 新的接口

    public void triggerOnAttack(AbstractCreature monster, DamageInfo info) {
        behavior.onAttackBehavior(monster, info);
    }

    public int triggerOnAttacked(DamageInfo info, int damageAmount) {
        behavior.onAttackedBehavior(info, damageAmount);
        return damageAmount;
    }

    public void triggerOnOtherCardExhausted(AbstractCard c) {
        behavior.onOtherCardExhaustedBehavior(c);
        modifier.triggerOnOtherCardExhausted(c);
    }
    
    public void triggerOnFrozen() {
        behavior.onFrozenBehavior();
    }

    public void triggerAtEndOfTurn() {
        behavior.atEndOfTurnBehavior();
    }

    // 消耗后接口

    public void triggerAtEndOfTurnAfterExhausted() {
        behavior.atEndOfTurnAfterExhaustedBehavior();
    }

    public void triggerAtStartOfTurnAfterExhausted() {
        behavior.atStartOfTurnAfterExhaustedBehavior();
    }

    public void triggerOnOtherCardFrozenAfterExhausted() {
        behavior.onOtherCardFrozenAfterExhaustedBehavior();
    }

    public void triggerOnEnemyFrozenAfterExhausted() {
        behavior.onEnemyFrozenAfterExhaustedBehavior();
    }


    public boolean isFire() {
        return trizonBooleans.fire;
    }
    
    public boolean isYandere() {
        return trizonBooleans.yandere;
    }

    public boolean isGoldFish() {
        return trizonBooleans.goldfish;
    }

    public boolean isScapegoat() {
        return trizonBooleans.scapegoat;
    }

    @Override
    public void applyPowers() {
        if (AbstractDungeon.player.hasPower(TrizonSpellBuffPower.POWER_ID)) {
            int tmp = this.baseSpellNumber;
            TrizonSpellBuffPower power = (TrizonSpellBuffPower) AbstractDungeon.player.getPower(TrizonSpellBuffPower.POWER_ID);
            tmp += power.amount;
            this.spellNumber = tmp;
        }
        super.applyPowers();
        this.isDamageTimesModified = this.damageTimes != this.baseDamageTimes;
        this.isSpellNumberModified = this.spellNumber != this.baseSpellNumber;
    }

     @Override
     public void calculateCardDamage(AbstractMonster mo) {
        if (AbstractDungeon.player.hasPower(TrizonSpellBuffPower.POWER_ID)) {
            int tmp = this.baseSpellNumber;
            TrizonSpellBuffPower power = (TrizonSpellBuffPower) AbstractDungeon.player.getPower(TrizonSpellBuffPower.POWER_ID);
            tmp += power.amount;
            this.spellNumber = tmp;
        }
        super.calculateCardDamage(mo);
        this.isDamageTimesModified = this.damageTimes != this.baseDamageTimes;
        this.isSpellNumberModified = this.spellNumber != this.baseSpellNumber;
     }
}
