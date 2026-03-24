package card;

import com.megacrit.cardcrawl.cards.AbstractCard;
import java.lang.reflect.Type;

public abstract class TrizonCard extends AbstractTrizonCard<Boolean> {
    public TrizonCard(String id, String name, String img, int cost, String rawDescription, AbstractCard.CardType type,
            AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, rarity, target);
    }

    @Override
    public Type savedType() {
        return Boolean.class;
    }

    @Override
    public Boolean onSave() {
        return this.trizonBooleans.gold;
    }

    @Override
    public void onLoad(Boolean gold) {
        this.trizonBooleans.gold = gold;
        setBgTexture();
    }
}
