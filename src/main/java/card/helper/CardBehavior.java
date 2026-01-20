package card.helper;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import action.factory.AbstractTrizonFactory;
import card.TrizonCard;
import fusable.Fusable;

public class CardBehavior implements Fusable<CardBehavior> {
    public CardBehavior() {

    }

    // 打出时
    private ArrayList<AbstractTrizonFactory> useActionFactorys;
    public void useBehavior(TrizonCard cardPlayed, AbstractPlayer p, AbstractMonster m) {
        for (AbstractTrizonFactory factory : useActionFactorys) {
            factory.receiveTarget(m);
            factory.receiveCardPlayed(cardPlayed);
            this.addToBot(factory.create());
        }
    }
    public void addToUseBehavior(AbstractTrizonFactory factory) {
        useActionFactorys.add(factory);
    }

    // 被消耗时
    private ArrayList<AbstractTrizonFactory> exhaustActionFactorys;
    public void onExhaustBehavior() {
        for (AbstractTrizonFactory factory : exhaustActionFactorys) {
            this.addToBot(factory.create());
        }
    }
    public void addToExhaustBehavior(AbstractTrizonFactory factory) {
        exhaustActionFactorys.add(factory);
    }

    // 抽到这张牌时
    private ArrayList<AbstractTrizonFactory> drawnActionFactorys;
    public void onDrawnBehavior() {
        for (AbstractTrizonFactory factory : drawnActionFactorys) {
            this.addToBot(factory.create());
        }
    }
    public void addToDrawnBehavior(AbstractTrizonFactory factory) {
        drawnActionFactorys.add(factory);
    }

    // 打出其他手牌时
    private ArrayList<AbstractTrizonFactory> otherCardPlayedActionFactorys;
    public void onOtherCardPlayedBehavior(AbstractCard c) {
        for (AbstractTrizonFactory factory : otherCardPlayedActionFactorys) {
            factory.receiveCard(c);
            this.addToBot(factory.create());
        }
    }
    public void addToOtherCardPlayedBehavior(AbstractTrizonFactory factory) {
        otherCardPlayedActionFactorys.add(factory);
    }

    // 消耗其他手牌时
    private ArrayList<AbstractTrizonFactory> otherCardExhaustedActionFactorys;
    public void onOtherCardExhaustedBehavior(AbstractCard c) {
        for (AbstractTrizonFactory factory : otherCardExhaustedActionFactorys) {
            factory.receiveCard(c);
            this.addToBot(factory.create());
        }
    }
    public void addToOtherCardExhaustedBehavior(AbstractTrizonFactory factory) {
        otherCardExhaustedActionFactorys.add(factory);
    }

    // 此卡造成伤害时
    private ArrayList<AbstractTrizonFactory> attackActionFactorys;
    public void onAttackBehavior(AbstractCreature monster, DamageInfo info) {
        for (int i = attackActionFactorys.size() - 1; i >= 0; i--) {
            AbstractTrizonFactory factory = attackActionFactorys.get(i);
            factory.receiveTarget(monster);
            factory.receiveDamageInfo(info);
            this.addToTop(factory.create());
        }
    }
    public void addToAttackBehavior(AbstractTrizonFactory factory) {
        attackActionFactorys.add(factory);
    }

    // 受到伤害时
    private ArrayList<AbstractTrizonFactory> attackedActionFactorys;
    public void onAttackedBehavior(TrizonCard cardPlayed, DamageInfo info, int damageAmount) {
        for (AbstractTrizonFactory factory : attackedActionFactorys) {
            factory.receiveCardPlayed(cardPlayed);
            factory.receiveDamageInfo(info);
            this.addToBot(factory.create());
        }
    }
    public void addToAttackedBehavior(AbstractTrizonFactory factory) {
        attackedActionFactorys.add(factory);
    }

    // 被冻结时
    private ArrayList<AbstractTrizonFactory> frozenActionFactorys;
    public void onFrozenBehavior() {
        for (AbstractTrizonFactory factory : frozenActionFactorys) {
            this.addToBot(factory.create());
        }
    }
    public void addToFrozenBehavior(AbstractTrizonFactory factory) {
        frozenActionFactorys.add(factory);
    }

    // 在消耗堆中，每回合结束时
    private ArrayList<AbstractTrizonFactory> endOfTurnAfterExhaustedActionFactorys;
    public void atEndOfTurnAfterExhaustedBehavior() {
        for (AbstractTrizonFactory factory : endOfTurnAfterExhaustedActionFactorys) {
            this.addToBot(factory.create());
        }
    }
    public void addToEndOfTurnAfterExhaustedBehavior(AbstractTrizonFactory factory) {
        endOfTurnAfterExhaustedActionFactorys.add(factory);
    }

    // 在消耗堆中，每回合开始时
    private ArrayList<AbstractTrizonFactory> startOfTurnAfterExhaustedActionFactorys;
    public void atStartOfTurnAfterExhaustedBehavior() {
        for (AbstractTrizonFactory factory : startOfTurnAfterExhaustedActionFactorys) {
            this.addToBot(factory.create());
        }
    }
    public void addToStartOfTurnAfterExhaustedBehavior(AbstractTrizonFactory factory) {
        startOfTurnAfterExhaustedActionFactorys.add(factory);
    }

    // 在消耗堆中，其他牌被冻结时
    private ArrayList<AbstractTrizonFactory> onOtherCardFrozenAfterExhaustedActionFactorys;
    public void onOtherCardFrozenAfterExhaustedBehavior() {
        for (AbstractTrizonFactory factory : onOtherCardFrozenAfterExhaustedActionFactorys) {
            this.addToBot(factory.create());
        }
    }
    public void addToOnOtherCardFrozenAfterExhaustedBehavior(AbstractTrizonFactory factory) {
        onOtherCardFrozenAfterExhaustedActionFactorys.add(factory);
    }

    // 在消耗堆中，敌人被冻结时
    private ArrayList<AbstractTrizonFactory> onEnemyFrozenAfterExhaustedActionFactorys;
    public void onEnemyFrozenAfterExhaustedBehavior() {
        for (AbstractTrizonFactory factory : onEnemyFrozenAfterExhaustedActionFactorys) {
            this.addToBot(factory.create());
        }
    }
    public void addToOnEnemyFrozenAfterExhaustedBehavior(AbstractTrizonFactory factory) {
        onEnemyFrozenAfterExhaustedActionFactorys.add(factory);
    }

    private void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    private void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    @Override
    public void fuse(CardBehavior other) {
        this.useActionFactorys = AbstractTrizonFactory.fuseFactories(this.useActionFactorys, other.useActionFactorys);
        this.exhaustActionFactorys = AbstractTrizonFactory.fuseFactories(this.exhaustActionFactorys, other.exhaustActionFactorys);
        this.drawnActionFactorys = AbstractTrizonFactory.fuseFactories(this.drawnActionFactorys, other.drawnActionFactorys);
        this.otherCardPlayedActionFactorys = AbstractTrizonFactory.fuseFactories(this.otherCardPlayedActionFactorys, other.otherCardPlayedActionFactorys);
        this.otherCardExhaustedActionFactorys = AbstractTrizonFactory.fuseFactories(this.otherCardExhaustedActionFactorys, other.otherCardExhaustedActionFactorys);
        this.attackActionFactorys = AbstractTrizonFactory.fuseFactories(this.attackActionFactorys, other.attackActionFactorys);
        this.attackedActionFactorys = AbstractTrizonFactory.fuseFactories(this.attackedActionFactorys, other.attackedActionFactorys);
        this.frozenActionFactorys = AbstractTrizonFactory.fuseFactories(this.frozenActionFactorys, other.frozenActionFactorys);
        this.endOfTurnAfterExhaustedActionFactorys = AbstractTrizonFactory.fuseFactories(this.endOfTurnAfterExhaustedActionFactorys, other.endOfTurnAfterExhaustedActionFactorys);
        this.startOfTurnAfterExhaustedActionFactorys = AbstractTrizonFactory.fuseFactories(this.startOfTurnAfterExhaustedActionFactorys, other.startOfTurnAfterExhaustedActionFactorys);
        this.onOtherCardFrozenAfterExhaustedActionFactorys = AbstractTrizonFactory.fuseFactories(this.onOtherCardFrozenAfterExhaustedActionFactorys, other.onOtherCardFrozenAfterExhaustedActionFactorys);
        this.onEnemyFrozenAfterExhaustedActionFactorys = AbstractTrizonFactory.fuseFactories(this.onEnemyFrozenAfterExhaustedActionFactorys, other.onEnemyFrozenAfterExhaustedActionFactorys);
    }
}
