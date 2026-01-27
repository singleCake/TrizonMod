package card.helper;

import com.megacrit.cardcrawl.cards.AbstractCard;

import fusable.Fusable;

public class DefaultCardBooleans implements Fusable<DefaultCardBooleans> {
    public boolean exhaust = false;
    public boolean isEthereal = false;
    public boolean isInnate = false;
    public boolean selfRetain = false;

    public DefaultCardBooleans() {
    }

    public DefaultCardBooleans(AbstractCard card) {
        this.exhaust = card.exhaust;
        this.isEthereal = card.isEthereal;
        this.isInnate = card.isInnate;
        this.selfRetain = card.selfRetain;
    }

    public static void applyBooleansToCard(DefaultCardBooleans booleans, AbstractCard card) {
        card.exhaust = booleans.exhaust;
        card.isEthereal = booleans.isEthereal;
        card.isInnate = booleans.isInnate;
        card.selfRetain = booleans.selfRetain;
    }

    @Override
    public boolean fuse(DefaultCardBooleans other) {
        this.exhaust = this.exhaust || other.exhaust;
        this.isEthereal = this.isEthereal || other.isEthereal;
        this.isInnate = this.isInnate || other.isInnate;
        this.selfRetain = this.selfRetain || other.selfRetain;
        return true;
    }
}
