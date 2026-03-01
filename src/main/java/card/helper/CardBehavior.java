package card.helper;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import action.factory.AbstractTrizonFactory;
import card.TrizonCard;
import card.helper.FactoryList.AbstractFactoryList;
import card.helper.FactoryList.AttackActionFactoryList;
import card.helper.FactoryList.AttackedActionFactoryList;
import card.helper.FactoryList.DrawnActionFactoryList;
import card.helper.FactoryList.EndOfTurnAfterExhaustedActionFactoryList;
import card.helper.FactoryList.EnemyFrozenAfterExhaustedActionFactoryList;
import card.helper.FactoryList.ExhaustActionFactoryList;
import card.helper.FactoryList.FrozenActionFactoryList;
import card.helper.FactoryList.OtherCardExhaustedActionFactoryList;
import card.helper.FactoryList.OtherCardFrozenAfterExhaustedActionFactoryList;
import card.helper.FactoryList.OtherCardPlayedActionFactoryList;
import card.helper.FactoryList.StartOfTurnAfterExhaustedActionFactoryList;
import card.helper.FactoryList.UseActionFactoryList;
import fusable.Fusable;
import power.factory.AbstractTrizonPowerFactory;

public class CardBehavior implements Fusable<CardBehavior> {
    TrizonCard this_card = null;
    private ArrayList<AbstractTrizonPowerFactory> powerFactorys = new ArrayList<>();
    private ArrayList<AbstractFactoryList> allFactoryLists = new ArrayList<>();

    public void addToPowerFactorys(AbstractTrizonPowerFactory factory) {
        powerFactorys.add(factory);
    }

    public CardBehavior() {
        allFactoryLists.add(useActionFactorys);
        allFactoryLists.add(exhaustActionFactorys);
        allFactoryLists.add(drawnActionFactorys);
        allFactoryLists.add(otherCardPlayedActionFactorys);
        allFactoryLists.add(otherCardExhaustedActionFactorys);
        allFactoryLists.add(attackActionFactorys);
        allFactoryLists.add(attackedActionFactorys);
        allFactoryLists.add(frozenActionFactorys);
        allFactoryLists.add(endOfTurnAfterExhaustedActionFactorys);
        allFactoryLists.add(startOfTurnAfterExhaustedActionFactorys);
        allFactoryLists.add(onOtherCardFrozenAfterExhaustedActionFactorys);
        allFactoryLists.add(onEnemyFrozenAfterExhaustedActionFactorys);
    }

    public void clearBehavior() {
        this.powerFactorys = new ArrayList<>();

        for (AbstractFactoryList factoryList : allFactoryLists) {
            factoryList.clear();
        }
    }

    // 打出时
    private UseActionFactoryList useActionFactorys = new UseActionFactoryList();

    public void useBehavior(AbstractPlayer p, AbstractMonster m) {
        // 首先获得所有能力
        for (AbstractTrizonPowerFactory powerFactory : powerFactorys) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, powerFactory.create()));
        }

        useActionFactorys.behave(p, m);
    }

    public void addToUseBehavior(AbstractTrizonFactory factory) {
        useActionFactorys.addFactory(factory);
    }

    // 被消耗时
    private ExhaustActionFactoryList exhaustActionFactorys = new ExhaustActionFactoryList();

    public void onExhaustBehavior() {
        exhaustActionFactorys.behave();
    }

    public void addToExhaustBehavior(AbstractTrizonFactory factory) {
        exhaustActionFactorys.addFactory(factory);
    }

    // 抽到这张牌时
    private DrawnActionFactoryList drawnActionFactorys = new DrawnActionFactoryList();

    public void onDrawnBehavior() {
        drawnActionFactorys.behave();
    }

    public void addToDrawnBehavior(AbstractTrizonFactory factory) {
        drawnActionFactorys.addFactory(factory);
    }

    // 打出其他手牌时
    private OtherCardPlayedActionFactoryList otherCardPlayedActionFactorys = new OtherCardPlayedActionFactoryList();

    public void onOtherCardPlayedBehavior(AbstractCard c) {
        otherCardPlayedActionFactorys.behave(c);
    }

    public void addToOtherCardPlayedBehavior(AbstractTrizonFactory factory) {
        otherCardPlayedActionFactorys.addFactory(factory);
    }

    // 消耗其他手牌时
    private OtherCardExhaustedActionFactoryList otherCardExhaustedActionFactorys = new OtherCardExhaustedActionFactoryList();

    public void onOtherCardExhaustedBehavior(AbstractCard c) {
        otherCardExhaustedActionFactorys.behave(c);
    }

    public void addToOtherCardExhaustedBehavior(AbstractTrizonFactory factory) {
        otherCardExhaustedActionFactorys.addFactory(factory);
    }

    // 此卡造成伤害时
    private AttackActionFactoryList attackActionFactorys = new AttackActionFactoryList();

    public void onAttackBehavior(AbstractCreature monster, DamageInfo info) {
        attackActionFactorys.behave(monster, info);
    }

    public void addToAttackBehavior(AbstractTrizonFactory factory) {
        attackActionFactorys.addFactory(factory);
    }

    // 受到伤害时
    private AttackedActionFactoryList attackedActionFactorys = new AttackedActionFactoryList();

    public void onAttackedBehavior(DamageInfo info, int damageAmount) {
        attackedActionFactorys.behave(info);
    }

    public void addToAttackedBehavior(AbstractTrizonFactory factory) {
        attackedActionFactorys.addFactory(factory);
    }

    // 被冻结时
    private FrozenActionFactoryList frozenActionFactorys = new FrozenActionFactoryList();

    public void onFrozenBehavior() {
        frozenActionFactorys.behave();
    }

    public void addToFrozenBehavior(AbstractTrizonFactory factory) {
        frozenActionFactorys.addFactory(factory);
    }

    // 在消耗堆中，每回合结束时
    private EndOfTurnAfterExhaustedActionFactoryList endOfTurnAfterExhaustedActionFactorys = new EndOfTurnAfterExhaustedActionFactoryList();

    public void atEndOfTurnAfterExhaustedBehavior() {
        endOfTurnAfterExhaustedActionFactorys.behave();
    }

    public void addToEndOfTurnAfterExhaustedBehavior(AbstractTrizonFactory factory) {
        endOfTurnAfterExhaustedActionFactorys.addFactory(factory);
    }

    // 在消耗堆中，每回合开始时
    private StartOfTurnAfterExhaustedActionFactoryList startOfTurnAfterExhaustedActionFactorys = new StartOfTurnAfterExhaustedActionFactoryList();

    public void atStartOfTurnAfterExhaustedBehavior() {
        startOfTurnAfterExhaustedActionFactorys.behave();
    }

    public void addToStartOfTurnAfterExhaustedBehavior(AbstractTrizonFactory factory) {
        startOfTurnAfterExhaustedActionFactorys.addFactory(factory);
    }

    // 在消耗堆中，其他牌被冻结时
    private OtherCardFrozenAfterExhaustedActionFactoryList onOtherCardFrozenAfterExhaustedActionFactorys = new OtherCardFrozenAfterExhaustedActionFactoryList();

    public void onOtherCardFrozenAfterExhaustedBehavior() {
        onOtherCardFrozenAfterExhaustedActionFactorys.behave();
    }

    public void addToOnOtherCardFrozenAfterExhaustedBehavior(AbstractTrizonFactory factory) {
        onOtherCardFrozenAfterExhaustedActionFactorys.addFactory(factory);
    }

    // 在消耗堆中，敌人被冻结时
    private EnemyFrozenAfterExhaustedActionFactoryList onEnemyFrozenAfterExhaustedActionFactorys = new EnemyFrozenAfterExhaustedActionFactoryList();

    public void onEnemyFrozenAfterExhaustedBehavior() {
        onEnemyFrozenAfterExhaustedActionFactorys.behave();
    }

    public void addToOnEnemyFrozenAfterExhaustedBehavior(AbstractTrizonFactory factory) {
        onEnemyFrozenAfterExhaustedActionFactorys.addFactory(factory);
    }

    // 生成卡牌描述
    public String generateRawDescription() {
        String rawDescription = "";

        for (AbstractTrizonPowerFactory factory : powerFactorys) {
            rawDescription += factory.rawDescription() + " NL ";
        }

        for (AbstractFactoryList factoryList : allFactoryLists) {
            rawDescription += factoryList.generateRawDescription();
        }

        return rawDescription;
    }

    public CardBehavior clone() {
        CardBehavior copy = new CardBehavior();

        for (AbstractTrizonPowerFactory factory : this.powerFactorys) {
            copy.addToPowerFactorys(factory.clone());
        }

        for (int i = 0; i < allFactoryLists.size(); i++) {
            allFactoryLists.set(i, allFactoryLists.get(i).clone());
        }

        copy.this_card = null;
        return copy;
    }

    @Override
    public boolean fuse(CardBehavior other) {
        this.powerFactorys = AbstractTrizonPowerFactory.fuseFactories(this.powerFactorys, other.powerFactorys);

        for (int i = 0; i < allFactoryLists.size(); i++) {
            allFactoryLists.get(i).fuse(other.allFactoryLists.get(i));
        }

        return true;
    }

    public void setThisCard(TrizonCard card) {
        this.this_card = card;

        for (AbstractFactoryList factoryList : allFactoryLists) {
            factoryList.receiveThisCard(card);
        }
    }
}
