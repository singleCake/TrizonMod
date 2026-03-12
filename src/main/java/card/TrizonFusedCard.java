package card;

import java.util.ArrayList;
import java.util.HashMap;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import basemod.abstracts.CustomSavable;
import card.helper.CardBehavior;
import card.helper.DefaultCardBooleans;
import card.helper.TrizonCardBooleans;
import card.helper.Modifier.CardModifierList;
import card.helper.Tip.TimingTip;
import card.helper.CardHelper;
import fusable.Fusable;

public class TrizonFusedCard extends TrizonCard implements Fusable<TrizonCard>, CustomSavable<card.TrizonFusedCard.CardData> {

    public HashMap<String, Integer> fusionData = new HashMap<>();

    public static final String ID = "TrizonMod:FusedCard";

    public TrizonFusedCard() {
        super(ID, "融合卡牌", "TrizonResources/img/cards/card.png",
                -2,
                "这是一张空的融合卡牌...",
                AbstractCard.CardType.SKILL,
                AbstractCard.CardRarity.SPECIAL,
                AbstractCard.CardTarget.NONE);
    }

    public TrizonFusedCard(TrizonCard card1, TrizonCard card2) {
        super(ID, "融合卡牌", CardHelper.getFusedCardImg(card1, card2),
                CardHelper.getFusedCardCost(card1, card2),
                "融合卡牌",
                CardHelper.getFusedCardType(card1, card2),
                CardHelper.getFusedCardRarity(card1, card2),
                CardHelper.getFusedCardTarget(card1, card2));
        
        antiModifyCost(card1, card2);
        fuseBehavior(card1, card2);
        fuseModifier(card1, card2);
        fuseBoolean(card1, card2);
        fuseDamageAndBlock(card1, card2);
        addToFusionData(card1);
        addToFusionData(card2);
        this.name = CardHelper.getFusedCardName(this);
        this.rawDescription = behavior.generateRawDescription() + modifier.rawDescription();
        this.initializeDescription();
    }

    @Override
    public boolean fuse(TrizonCard other) {
        antiModifyCost(this, other);
        fuseBehavior(this, other);
        fuseModifier(this, other);
        fuseBoolean(this, other);
        fuseDamageAndBlock(this, other);
        addToFusionData(other);
        this.name = CardHelper.getFusedCardName(this);
        this.rawDescription = behavior.generateRawDescription() + modifier.rawDescription();
        this.initializeDescription();

        return true;
    }

    // 反物质修改费用
    private void antiModifyCost(TrizonCard card1, TrizonCard card2) {
        this.anti_num = card1.anti_num + card2.anti_num;
        if (this.cost > 0) {
            int newCost = Math.max(0, this.cost - this.anti_num);
            this.anti_num -= (this.cost - newCost);
            this.cost = newCost;
        }
        this.costForTurn = this.cost;
    }

    // 融合行为
    private void fuseBehavior(TrizonCard card1, TrizonCard card2) {
        CardBehavior behavior1 = card1.getShiftBehavior();
        CardBehavior behavior2 = card2.getShiftBehavior();
        behavior1.fuse(behavior2);
        this.behavior = behavior1;
        this.behavior.setThisCard(this);
    }

    // 融合修改器
    private void fuseModifier(TrizonCard card1, TrizonCard card2) {
        CardModifierList modifiers1 = card1.modifier.clone();
        CardModifierList modifiers2 = card2.modifier.clone();
        modifiers1.fuse(modifiers2);
        this.modifier = modifiers1;
    }

    // 融合词条
    private void fuseBoolean(TrizonCard card1, TrizonCard card2) {
        DefaultCardBooleans booleans;
        if (card1.type == CardType.ATTACK && card2.type == CardType.SKILL) {
            booleans = new DefaultCardBooleans(card1);
        } else if (card1.type == CardType.SKILL && card2.type == CardType.ATTACK) {
            booleans = new DefaultCardBooleans(card2);
        } else {
            booleans = new DefaultCardBooleans(card1);
            booleans.fuse(new DefaultCardBooleans(card2));
        }
        DefaultCardBooleans.applyBooleansToCard(booleans, this);

        if (card1.equals(this)) {
            this.trizonBooleans.fuse(card2.trizonBooleans);
        } else {
            this.trizonBooleans = card1.trizonBooleans.clone();
            this.trizonBooleans.fuse(card2.trizonBooleans);
        }
    }

    // 融合伤害与格挡值（用于生成描述）
    public void fuseDamageAndBlock(TrizonCard card1, TrizonCard card2) {
        this.baseDamage = card1.baseDamage + card2.baseDamage;
        this.baseBlock = card1.baseBlock + card2.baseBlock;
        this.baseDamageTimes = Math.max(card1.baseDamageTimes, card2.baseDamageTimes);
    }

    // 记录融合材料
    private void addToFusionData(TrizonCard material) {
        if (material instanceof TrizonFusedCard) {
            TrizonFusedCard fusedMaterial = (TrizonFusedCard) material;
            for (String key : fusedMaterial.fusionData.keySet()) {
                fusionData.put(key, fusionData.getOrDefault(key, 0) + fusedMaterial.fusionData.get(key));
            }
        } else {
            fusionData.put(material.cardID, fusionData.getOrDefault(material.cardID, 0) + 1);
        }
    }

    // 判断两种卡牌类型是否支持融合
    public static boolean canFuse(TrizonCard card1, TrizonCard card2) {
        if ((card1.type == CardType.ATTACK && card2.type == CardType.SKILL) ||
            (card1.type == CardType.SKILL && card2.type == CardType.ATTACK)) {
            return true;
        }
        return card1.type == card2.type;
    }

    public ArrayList<TimingTip> getTimingTips() {
        ArrayList<TimingTip> tips = new ArrayList<>();
        tips.addAll(this.behavior.generateTimingTips());
        tips.addAll(this.modifier.getTips());
        return tips;
    }

    @Override
    public AbstractCard makeCopy() {
        // 基本不使用这种复制
        return new TrizonFusedCard();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        TrizonFusedCard copy = (TrizonFusedCard) super.makeStatEquivalentCopy();
        copy.anti_num = this.anti_num;
        copy.behavior = this.behavior.clone();
        copy.behavior.setThisCard(copy);
        copy.modifier = this.modifier.clone();
        copy.trizonBooleans = this.trizonBooleans.clone();
        copy.textureImg = this.textureImg;
        copy.loadCardImage(copy.textureImg);
        copy.fusionData = new HashMap<>(this.fusionData);
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
    public void setBehavior() {
    }
    
    @Override
    public void upgrade() {
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    // 存档逻辑
    @Override
    public CardData onSave() {
        return new CardData(this);
    }  
    
    @Override
    public void onLoad(CardData data) {
        if (data != null) {
            this.fusionData = data.fusionData != null ? new HashMap<>(data.fusionData) : new HashMap<>();

            this.type = data.type;
            this.rarity = data.rarity;
            this.target = data.target;
            this.cost = data.cost;
            this.costForTurn = data.cost;
            this.baseDamage = this.damage = data.baseDamage;
            this.baseBlock = this.block = data.baseBlock;
            this.baseDamageTimes = this.damageTimes = data.baseDamageTimes;
            this.baseSpellNumber = this.spellNumber = data.baseSpellNumber;
            this.anti_num = data.antiNum;

            if (data.booleans != null) {
                DefaultCardBooleans.applyBooleansToCard(data.booleans, this);
            }
            this.trizonBooleans = data.trizonBooleans != null ? data.trizonBooleans : new TrizonCardBooleans();

            if (data.behaviorData != null) {
                this.behavior = CardBehavior.fromData(data.behaviorData);
                this.behavior.setThisCard(this);
            }

            this.modifier = CardModifierList.fromData(data.modifierData);

            if (data.img != null) {
                this.textureImg = data.img;
                this.loadCardImage(this.textureImg);
            }

            this.name = CardHelper.getFusedCardName(this);
            this.rawDescription = behavior.generateRawDescription() + modifier.rawDescription();
            this.initializeDescription();
        }
    }

    public class CardData {
        public String img;
        public AbstractCard.CardType type;
        public AbstractCard.CardRarity rarity;
        public AbstractCard.CardTarget target;
        public int cost;
        public int baseDamage;
        public int baseBlock;
        public int baseDamageTimes;
        public int baseSpellNumber;
        public int antiNum;
        public DefaultCardBooleans booleans;
        public TrizonCardBooleans trizonBooleans;
        public CardBehavior.BehaviorData behaviorData;
        public CardModifierList.ModifierListData modifierData;
        public HashMap<String, Integer> fusionData = null;

        public CardData(TrizonFusedCard card) {
            this.img = card.textureImg;
            this.type = card.type;
            this.rarity = card.rarity;
            this.target = card.target;
            this.cost = card.cost;
            this.baseDamage = card.baseDamage;
            this.baseBlock = card.baseBlock;
            this.baseDamageTimes = card.baseDamageTimes;
            this.baseSpellNumber = card.baseSpellNumber;
            this.antiNum = card.anti_num;
            this.booleans = new DefaultCardBooleans(card);
            this.trizonBooleans = card.trizonBooleans.clone();
            this.behaviorData = card.behavior.exportData();
            this.modifierData = card.modifier.exportData();
            this.fusionData = new HashMap<>(card.fusionData);
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "allowUpgradePreview")
    public static class PreventUpgradePreview {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(SingleCardViewPopup __instance, AbstractCard ___card) {
            if (___card instanceof TrizonFusedCard) {
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }
}
