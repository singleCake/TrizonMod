package card;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import card.helper.CardBehavior;
import card.helper.TrizonCardBooleans;
import card.helper.CardModifier;
import power.factory.AbstractTrizonPowerFactory;

import static modcore.TrizonMod.PlayerColorEnum.Trizon_COLOR;

public abstract class TrizonCard extends CustomCard {
    protected CardBehavior behavior = new CardBehavior();
    protected ArrayList<AbstractTrizonPowerFactory> powerFactorys;

    public int baseDamage = 0;
    public int baseDamageTimes = 0;
    public int baseBlock = 0;

    public CardModifier modifier = new CardModifier();

    protected TrizonCardBooleans trizonBooleans = null;

    protected String img = "";

    public TrizonCard(String id, String name, String img, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, Trizon_COLOR, rarity, target);
        this.img = img;
        this.behavior.setThisCard(this);
    }

    protected abstract void setBehavior();
    protected void reInitBehavior() {
        this.behavior.clearBehavior();
        setBehavior();
        this.behavior.setThisCard(this);
    }

    protected void upgradeDamageTimes(int amount) {
        this.baseDamageTimes += amount;
    }

    // 原版接口

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        behavior.useBehavior(p, m);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return trizonBooleans.canUse;
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
    }
    
    public void triggerOnFrozen() {
        behavior.onFrozenBehavior();
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


    public boolean isYandere() {
        return trizonBooleans.yandere;
    }
}
