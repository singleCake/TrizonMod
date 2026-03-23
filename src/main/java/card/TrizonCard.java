package card;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
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
import card.helper.Tip.FuseInfoTip;
import card.rare.Rain;
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

    public TrizonCardBooleans trizonBooleans = new TrizonCardBooleans();

    public TrizonCard(String id, String name, String img, int cost, String rawDescription, AbstractCard.CardType type,
            AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, Trizon_COLOR, rarity, target);
        this.textureImg = img;
        this.behavior.setThisCard(this);
        setBgTexture();
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

    // 部分卡牌在融合时改变效果，重写这个方法
    public CardBehavior getShiftBehavior() {
        return this.behavior.clone();
    }

    // 如果卡牌在融合时改变效果，重写这个方法来描述效果
    public FuseInfoTip getFuseInfoTip() {
        return null;
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
        this.rainExhaust();
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

    public void triggerAtStartOfCombatPreDraw() {
        behavior.atStartOfCombatBehavior();
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

    public boolean isRain() {
        return trizonBooleans.rain;
    }

    private void rainExhaust() {
        if (isRain()) {
            this.trizonBooleans.rain = false;
            this.addToTop(new MakeTempCardInHandAction(this));
            if (this instanceof Rain) {
                ((Rain) this).afterExhaust();
            } else if (this instanceof TrizonFusedCard) {
                ((TrizonFusedCard) this).initDescription();
            }
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        TrizonCard copy = (TrizonCard) super.makeStatEquivalentCopy();
        copy.anti_num = this.anti_num;
        copy.behavior = this.behavior.clone();
        copy.behavior.setThisCard(copy);
        copy.modifier = this.modifier.clone();
        copy.trizonBooleans = this.trizonBooleans.clone();
        copy.textureImg = this.textureImg;
        copy.loadCardImage(copy.textureImg);
        copy.cardID = this.cardID;
        copy.type = this.type;
        copy.rarity = this.rarity;
        copy.target = this.target;
        copy.cost = this.cost;
        copy.costForTurn = this.costForTurn;
        copy.damage = copy.baseDamage = this.baseDamage;
        copy.block = copy.baseBlock = this.baseBlock;
        copy.damageTimes = copy.baseDamageTimes = this.baseDamageTimes;
        copy.spellNumber = copy.baseSpellNumber = this.baseSpellNumber;
        copy.name = this.name;
        copy.rawDescription = this.rawDescription;
        copy.initializeDescription();
        return copy;
    }

    @Override
    public void applyPowers() {
        if (AbstractDungeon.player.hasPower(TrizonSpellBuffPower.POWER_ID)) {
            int tmp = this.baseSpellNumber;
            TrizonSpellBuffPower power = (TrizonSpellBuffPower) AbstractDungeon.player
                    .getPower(TrizonSpellBuffPower.POWER_ID);
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
            TrizonSpellBuffPower power = (TrizonSpellBuffPower) AbstractDungeon.player
                    .getPower(TrizonSpellBuffPower.POWER_ID);
            tmp += power.amount;
            this.spellNumber = tmp;
        }
        super.calculateCardDamage(mo);
        this.isDamageTimesModified = this.damageTimes != this.baseDamageTimes;
        this.isSpellNumberModified = this.spellNumber != this.baseSpellNumber;
    }

    private static final String GOLD_ATTACK_512 = "TrizonResources/img/512/gold_attack_512.png";
    private static final String GOLD_POWER_512 = "TrizonResources/img/512/gold_power_512.png";
    private static final String GOLD_SKILL_512 = "TrizonResources/img/512/gold_skill_512.png";
    private static final String GOLD_ATTACK_1024 = "TrizonResources/img/1024/gold_attack.png";
    private static final String GOLD_POWER_1024 = "TrizonResources/img/1024/gold_power.png";
    private static final String GOLD_SKILL_1024 = "TrizonResources/img/1024/gold_skill.png";
    public void setBgTexture() {
        if (this.trizonBooleans.gold) {
            switch (this.type) {
                case ATTACK:
                    this.setBackgroundTexture(GOLD_ATTACK_512, GOLD_ATTACK_1024);
                    break;
                case POWER:
                    this.setBackgroundTexture(GOLD_POWER_512, GOLD_POWER_1024);
                    break;
                case SKILL:
                    this.setBackgroundTexture(GOLD_SKILL_512, GOLD_SKILL_1024);
                    break;
                default:
                    break;
            }
        }
    }
}
