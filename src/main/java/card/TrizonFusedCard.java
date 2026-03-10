package card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import basemod.abstracts.CustomSavable;
import card.helper.DefaultCardBooleans;
import card.helper.CardHelper;
import fusable.Fusable;

public class TrizonFusedCard extends TrizonCard implements Fusable<TrizonCard>, CustomSavable<card.TrizonFusedCard.CardData> {

    public HashMap<String, Integer> fusionData = new HashMap<>();

    public static final String BASE_ID = "TrizonMod:FusedCard";
    public static int ID_COUNTER = 0;

    public TrizonFusedCard() {
        super(BASE_ID, "融合卡牌", null,
                -2,
                "这是一张空的融合卡牌...",
                AbstractCard.CardType.STATUS,
                AbstractCard.CardRarity.SPECIAL,
                AbstractCard.CardTarget.NONE);
    }

    public TrizonFusedCard(TrizonCard card1, TrizonCard card2) {
        super(BASE_ID, "融合卡牌", CardHelper.getFusedCardImg(card1, card2),
                CardHelper.getFusedCardCost(card1, card2),
                "融合卡牌",
                CardHelper.getFusedCardType(card1, card2),
                CardHelper.getFusedCardRarity(card1, card2),
                CardHelper.getFusedCardTarget(card1, card2));
        
        antiModifyCost(card1, card2);
        fuseBehavior(card1, card2);
        fuseBoolean(card1, card2);
        fuseDamageAndBlock(card1, card2);
        addToFusionData(card1);
        addToFusionData(card2);
        this.name = CardHelper.getFusedCardName(this);
        this.rawDescription = behavior.generateRawDescription();
        this.initializeDescription();
    }

    @Override
    public boolean fuse(TrizonCard other) {
        antiModifyCost(this, other);
        fuseBehavior(this, other);
        fuseBoolean(this, other);
        fuseDamageAndBlock(this, other);
        addToFusionData(other);
        this.name = CardHelper.getFusedCardName(this);
        this.rawDescription = behavior.generateRawDescription();
        this.initializeDescription();

        return true;
    }

    // 反物质修改费用
    private void antiModifyCost(TrizonCard card1, TrizonCard card2) {
        this.anti_num = card1.anti_num + card2.anti_num;
        if (this.cost > 0) {
            this.cost = Math.max(0, this.cost - this.anti_num);
        }
        this.costForTurn = this.cost;
    }

    // 融合行为
    private void fuseBehavior(TrizonCard card1, TrizonCard card2) {
        if (card1.equals(this)) {
            this.behavior.fuse(card2.behavior);
        } else {
            this.behavior = card1.behavior.clone();
            this.behavior.fuse(card2.behavior);
        }
        this.behavior.setThisCard(this);
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

    // 记录融合材料（用于生成描述）
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
            rebuildFromFusionData();
        }
    }

    private void rebuildFromFusionData() {
        ArrayList<TrizonCard> materials = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : fusionData.entrySet()) {
            AbstractCard baseCard = CardLibrary.getCard(entry.getKey());
            if (!(baseCard instanceof TrizonCard)) {
                continue;
            }

            int count = Math.max(0, entry.getValue());
            for (int i = 0; i < count; i++) {
                AbstractCard copied = baseCard.makeStatEquivalentCopy();
                if (copied instanceof TrizonCard) {
                    materials.add((TrizonCard) copied);
                }
            }
        }

        TrizonFusedCard rebuilt;
        if (materials.size() >= 2) {
            rebuilt = new TrizonFusedCard(materials.get(0), materials.get(1));
            for (int i = 2; i < materials.size(); i++) {
                rebuilt.fuse(materials.get(i));
            }
        } else if (materials.size() == 1) {
            // Degenerate case: keep a valid fused-card state based on the only material.
            rebuilt = new TrizonFusedCard(materials.get(0), materials.get(0));
        } else {
            rebuilt = new TrizonFusedCard();
        }

        copyFromRebuilt(rebuilt);
    }

    private void copyFromRebuilt(TrizonFusedCard rebuilt) {
        this.textureImg = rebuilt.textureImg;
        if (this.textureImg != null) {
            this.loadCardImage(this.textureImg);
        }
        this.type = rebuilt.type;
        this.rarity = rebuilt.rarity;
        this.target = rebuilt.target;
        this.cost = rebuilt.cost;
        this.costForTurn = rebuilt.costForTurn;
        this.damage = this.baseDamage = rebuilt.baseDamage;
        this.block = this.baseBlock = rebuilt.baseBlock;
        this.damageTimes = this.baseDamageTimes = rebuilt.baseDamageTimes;
        this.spellNumber = this.baseSpellNumber = rebuilt.baseSpellNumber;
        this.anti_num = rebuilt.anti_num;
        this.behavior = rebuilt.behavior.clone();
        this.behavior.setThisCard(this);
        this.trizonBooleans = rebuilt.trizonBooleans.clone();
        this.fusionData = new HashMap<>(rebuilt.fusionData);
        this.name = rebuilt.name;
        this.rawDescription = rebuilt.rawDescription;
        this.initializeDescription();
    }

    public class CardData {
        public HashMap<String, Integer> fusionData = null;

        public CardData(TrizonFusedCard card) {
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
