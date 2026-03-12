package card.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import action.factory.AbstractTrizonFactory;
import card.TrizonCard;
import card.helper.FactoryList.AbstractFactoryList;
import card.helper.FactoryList.AttackActionFactoryList;
import card.helper.FactoryList.AttackedActionFactoryList;
import card.helper.FactoryList.DrawnActionFactoryList;
import card.helper.FactoryList.EndOfTurnActionFactoryList;
import card.helper.FactoryList.EndOfTurnAfterExhaustedActionFactoryList;
import card.helper.FactoryList.EnemyFrozenAfterExhaustedActionFactoryList;
import card.helper.FactoryList.ExhaustActionFactoryList;
import card.helper.FactoryList.FrozenActionFactoryList;
import card.helper.FactoryList.OtherCardExhaustedActionFactoryList;
import card.helper.FactoryList.OtherCardFrozenAfterExhaustedActionFactoryList;
import card.helper.FactoryList.OtherCardPlayedActionFactoryList;
import card.helper.FactoryList.StartOfCombatFactoryList;
import card.helper.FactoryList.StartOfTurnAfterExhaustedActionFactoryList;
import card.helper.FactoryList.UseActionFactoryList;
import card.helper.Tip.TimingTip;
import fusable.Fusable;
import power.factory.AbstractTrizonPowerFactory;

public class CardBehavior implements Fusable<CardBehavior> {
    transient TrizonCard this_card = null;
    private ArrayList<AbstractTrizonPowerFactory> powerFactorys = new ArrayList<>();
    private ArrayList<AbstractFactoryList> allFactoryLists = new ArrayList<>();

    public void addToPowerFactorys(AbstractTrizonPowerFactory factory) {
        powerFactorys.add(factory);
    }

    public CardBehavior() {
        rebuildAllFactoryLists();
    }

    private void rebuildAllFactoryLists() {
        allFactoryLists = new ArrayList<>();
        allFactoryLists.add(useActionFactorys);
        allFactoryLists.add(exhaustActionFactorys);
        allFactoryLists.add(drawnActionFactorys);
        allFactoryLists.add(otherCardPlayedActionFactorys);
        allFactoryLists.add(otherCardExhaustedActionFactorys);
        allFactoryLists.add(attackActionFactorys);
        allFactoryLists.add(attackedActionFactorys);
        allFactoryLists.add(frozenActionFactorys);
        allFactoryLists.add(endOfTurnActionFactorys);
        allFactoryLists.add(endOfTurnAfterExhaustedActionFactorys);
        allFactoryLists.add(startOfTurnAfterExhaustedActionFactorys);
        allFactoryLists.add(onOtherCardFrozenAfterExhaustedActionFactorys);
        allFactoryLists.add(onEnemyFrozenAfterExhaustedActionFactorys);
        allFactoryLists.add(startOfCombatFactorys);
        
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

    // 回合结束时，若这张牌在手牌中
    private EndOfTurnActionFactoryList endOfTurnActionFactorys = new EndOfTurnActionFactoryList();

    public void atEndOfTurnBehavior() {
        endOfTurnActionFactorys.behave();
    }

    public void addToEndOfTurnBehavior(AbstractTrizonFactory factory) {
        endOfTurnActionFactorys.addFactory(factory);
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

    // 每场战斗开始时
    private StartOfCombatFactoryList startOfCombatFactorys = new StartOfCombatFactoryList();

    public void atStartOfCombatBehavior() {
        startOfCombatFactorys.behave();
    }

    public void addToStartOfCombatBehavior(AbstractTrizonFactory factory) {
        startOfCombatFactorys.addFactory(factory);
    }

    // 生成卡牌描述
    public String generateRawDescription() {
        String rawDescription = "";

        rawDescription += DefaultCardBooleans.getRawDescription(this_card);
        rawDescription += this_card.trizonBooleans.getRawDescription();

        for (AbstractTrizonPowerFactory factory : powerFactorys) {
            rawDescription += factory.rawDescription() + " NL ";
        }

        for (AbstractFactoryList factoryList : allFactoryLists) {
            rawDescription += factoryList.generateRawDescription();
        }

        return rawDescription;
    }

    private static final String POWER_FACTORY_TITLE = CardCrawlGame.languagePack.getUIString("Trizon:PowerFactoryTitle").TEXT[0];

    // 生成timing tip
    public ArrayList<TimingTip> generateTimingTips() {
        ArrayList<TimingTip> tips = new ArrayList<>();

        StringBuilder powerFactoryDescription = new StringBuilder();
        for (AbstractTrizonPowerFactory factory : powerFactorys) {
            String raw = factory.rawDescription();
            if (raw == null || raw.equals(""))                
                continue;
            if (powerFactoryDescription.length() > 0)
                powerFactoryDescription.append(" NL ");
            powerFactoryDescription.append(raw);
        }

        if (powerFactoryDescription.length() > 0) {
            tips.add(new TimingTip(POWER_FACTORY_TITLE, powerFactoryDescription.toString()));
        }

        for (AbstractFactoryList factoryList : allFactoryLists) {
            TimingTip tip = factoryList.generateTimingTip();
            if (tip != null) {
                tips.add(tip);
            }
        }

        return tips;
    }

    public CardBehavior clone() {
        CardBehavior copy = new CardBehavior();

        for (AbstractTrizonPowerFactory factory : this.powerFactorys) {
            copy.addToPowerFactorys(factory.clone());
        }

        copy.useActionFactorys = (UseActionFactoryList) this.useActionFactorys.clone();
        copy.exhaustActionFactorys = (ExhaustActionFactoryList) this.exhaustActionFactorys.clone();
        copy.drawnActionFactorys = (DrawnActionFactoryList) this.drawnActionFactorys.clone();
        copy.otherCardPlayedActionFactorys = (OtherCardPlayedActionFactoryList) this.otherCardPlayedActionFactorys.clone();
        copy.otherCardExhaustedActionFactorys = (OtherCardExhaustedActionFactoryList) this.otherCardExhaustedActionFactorys.clone();
        copy.attackActionFactorys = (AttackActionFactoryList) this.attackActionFactorys.clone();
        copy.attackedActionFactorys = (AttackedActionFactoryList) this.attackedActionFactorys.clone();
        copy.frozenActionFactorys = (FrozenActionFactoryList) this.frozenActionFactorys.clone();
        copy.endOfTurnActionFactorys = (EndOfTurnActionFactoryList) this.endOfTurnActionFactorys.clone();
        copy.endOfTurnAfterExhaustedActionFactorys = (EndOfTurnAfterExhaustedActionFactoryList) this.endOfTurnAfterExhaustedActionFactorys.clone();
        copy.startOfTurnAfterExhaustedActionFactorys = (StartOfTurnAfterExhaustedActionFactoryList) this.startOfTurnAfterExhaustedActionFactorys.clone();
        copy.onOtherCardFrozenAfterExhaustedActionFactorys = (OtherCardFrozenAfterExhaustedActionFactoryList) this.onOtherCardFrozenAfterExhaustedActionFactorys.clone();
        copy.onEnemyFrozenAfterExhaustedActionFactorys = (EnemyFrozenAfterExhaustedActionFactoryList) this.onEnemyFrozenAfterExhaustedActionFactorys.clone();
        copy.startOfCombatFactorys = (StartOfCombatFactoryList) this.startOfCombatFactorys.clone();
        copy.rebuildAllFactoryLists();

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

    public BehaviorData exportData() {
        Gson gson = new Gson();
        BehaviorData data = new BehaviorData();

        data.powerFactories = new ArrayList<>();
        for (AbstractTrizonPowerFactory powerFactory : this.powerFactorys) {
            FactoryData factoryData = new FactoryData();
            factoryData.className = powerFactory.getClass().getName();
            factoryData.payload = gson.toJson(powerFactory, powerFactory.getClass());
            data.powerFactories.add(factoryData);
        }

        data.factoryLists = new ArrayList<>();
        for (AbstractFactoryList list : this.allFactoryLists) {
            FactoryListData listData = new FactoryListData();
            listData.listClassName = list.getClass().getName();
            listData.factories = new ArrayList<>();
            for (AbstractTrizonFactory factory : list.getFactoriesSnapshot()) {
                FactoryData factoryData = new FactoryData();
                factoryData.className = factory.getClass().getName();
                factoryData.payload = gson.toJson(factory, factory.getClass());
                listData.factories.add(factoryData);
            }
            data.factoryLists.add(listData);
        }

        return data;
    }

    public static CardBehavior fromData(BehaviorData data) {
        CardBehavior behavior = new CardBehavior();
        behavior.clearBehavior();
        if (data == null) {
            return behavior;
        }

        Gson gson = new Gson();

        if (data.powerFactories != null) {
            for (FactoryData factoryData : data.powerFactories) {
                try {
                    Class<?> clazz = Class.forName(factoryData.className);
                    Object obj = gson.fromJson(factoryData.payload, clazz);
                    if (obj instanceof AbstractTrizonPowerFactory) {
                        behavior.addToPowerFactorys((AbstractTrizonPowerFactory) obj);
                    }
                } catch (Exception ignored) {
                }
            }
        }

        Map<String, AbstractFactoryList> listMap = new HashMap<>();
        for (AbstractFactoryList list : behavior.allFactoryLists) {
            listMap.put(list.getClass().getName(), list);
        }

        if (data.factoryLists != null) {
            for (FactoryListData listData : data.factoryLists) {
                AbstractFactoryList targetList = listMap.get(listData.listClassName);
                if (targetList == null) {
                    continue;
                }

                ArrayList<AbstractTrizonFactory> restoredFactories = new ArrayList<>();
                if (listData.factories != null) {
                    for (FactoryData factoryData : listData.factories) {
                        try {
                            Class<?> clazz = Class.forName(factoryData.className);
                            Object obj = gson.fromJson(factoryData.payload, clazz);
                            if (obj instanceof AbstractTrizonFactory) {
                                restoredFactories.add((AbstractTrizonFactory) obj);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
                targetList.setFactories(restoredFactories);
            }
        }

        return behavior;
    }

    public static class BehaviorData {
        public ArrayList<FactoryData> powerFactories;
        public ArrayList<FactoryListData> factoryLists;
    }

    public static class FactoryListData {
        public String listClassName;
        public ArrayList<FactoryData> factories;
    }

    public static class FactoryData {
        public String className;
        public String payload;
    }
}
