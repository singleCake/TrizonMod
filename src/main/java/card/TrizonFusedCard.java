package card;

import java.util.ArrayList;
import java.util.HashMap;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.CustomSavable;
import card.helper.CardBehavior;
import card.helper.DefaultCardBooleans;
import card.helper.TrizonCardBooleans;
import card.helper.CardHelper;
import fusable.Fusable;
import power.factory.AbstractTrizonPowerFactory;

public class TrizonFusedCard extends TrizonCard implements Fusable<TrizonCard>, CustomSavable<card.TrizonFusedCard.CardData> {

    public HashMap<String, Integer> fusionData = new HashMap<>();

    public static final String BASE_ID = "TrizonMod:FusedCard";
    public static int ID_COUNTER = 0;

    public TrizonFusedCard() {
        super(BASE_ID + ID_COUNTER++, "融合卡牌", "",
                -2,
                "这是一张空的融合卡牌...",
                AbstractCard.CardType.STATUS,
                AbstractCard.CardRarity.SPECIAL,
                AbstractCard.CardTarget.NONE);
    }

    public TrizonFusedCard(TrizonCard card1, TrizonCard card2) {
        super(BASE_ID + ID_COUNTER++, "融合卡牌", card1.img,
                card1.cost + card2.cost,
                "融合卡牌",
                CardHelper.getFusedCardType(card1, card2),
                CardHelper.getFusedCardRarity(card1, card2),
                CardHelper.getFusedCardTarget(card1, card2));
        
        fuseBehavior(card1, card2);
        fuseBoolean(card1, card2);
        fuseDamageAndBlock(card1, card2);
        addToFusionData(card1);
        addToFusionData(card2);
        this.rawDescription = CardHelper.getFusedCardRawDescription(this);
        this.initializeDescription();
    }

    @Override
    public void fuse(TrizonCard other) {
        fuseBehavior(this, other);
        fuseBoolean(this, other);
        fuseDamageAndBlock(this, other);
        addToFusionData(other);
        this.rawDescription = CardHelper.getFusedCardRawDescription(this);
        this.initializeDescription();
    }

    // 融合行为
    private void fuseBehavior(TrizonCard card1, TrizonCard card2) {
        this.behavior = card1.behavior;
        this.behavior.fuse(card2.behavior);
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

    @Override
    public AbstractCard makeCopy() {
        // 基本不使用这种复制
        return new TrizonFusedCard();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        TrizonFusedCard copy = (TrizonFusedCard) super.makeStatEquivalentCopy();
        copy.behavior = this.behavior.clone();
        copy.behavior.setThisCard(copy);
        copy.powerFactorys = new ArrayList<>(this.powerFactorys);
        copy.trizonBooleans = this.trizonBooleans.clone();
        copy.img = this.img;
        copy.fusionData = new HashMap<>(this.fusionData);
        copy.cardID = this.cardID;
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
            this.img = data.img;
            this.type = data.type;
            this.rarity = data.rarity;
            this.target = data.target;
            this.cost = data.cost;
            this.baseDamage = data.baseDamage;
            this.baseDamageTimes = data.baseDamageTimes;
            this.baseBlock = data.baseBlock;

            this.fusionData = data.fusionData;
            this.powerFactorys = data.powerFactorys;
            DefaultCardBooleans.applyBooleansToCard(data.booleans, this);
            this.trizonBooleans = data.trizonBooleans;
            this.behavior = data.behavior;
            this.rawDescription = CardHelper.getFusedCardRawDescription(this);
            this.initializeDescription();
        }
    }

    public class CardData {
        // 基础信息
        public String img;
        public AbstractCard.CardType type;
        public AbstractCard.CardRarity rarity;
        public AbstractCard.CardTarget target;
        public int cost;       
        public int baseDamage;
        public int baseDamageTimes;
        public int baseBlock; 

        // 融合信息
        public HashMap<String, Integer> fusionData = null;
        public ArrayList<AbstractTrizonPowerFactory> powerFactorys = null;
        public DefaultCardBooleans booleans;
        public TrizonCardBooleans trizonBooleans;
        public CardBehavior behavior;

        public CardData(TrizonFusedCard card) {
            this.img = card.img;
            this.type = card.type;
            this.rarity = card.rarity;
            this.target = card.target;
            this.cost = card.cost;
            this.baseDamage = card.baseDamage;
            this.baseDamageTimes = card.baseDamageTimes;
            this.baseBlock = card.baseBlock;

            this.fusionData = card.fusionData;
            this.powerFactorys = card.powerFactorys;
            this.booleans = new DefaultCardBooleans(card);
            this.trizonBooleans = card.trizonBooleans;
            this.behavior = card.behavior;
        }
    }
}
