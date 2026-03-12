package card.helper;

import com.megacrit.cardcrawl.core.CardCrawlGame;

import fusable.Fusable;

public class TrizonCardBooleans implements Fusable<TrizonCardBooleans> {
    private static final String[] KEYWORDS_STRINGS = CardCrawlGame.languagePack.getUIString("Trizon:TrizonCardBooleans").TEXT;

    public boolean canUse = true;
    public boolean yandere = false;
    public boolean fire = false;
    public boolean goldfish = false;
    public boolean scapegoat = false;
    public boolean rain = false;

    public TrizonCardBooleans() {
    }

    public String getRawDescription() {
        String rawDescription = "";
        if (!canUse) {
            rawDescription += KEYWORDS_STRINGS[0];
        }
        if (yandere) {
            rawDescription += KEYWORDS_STRINGS[1];
        }
        if (fire) {
            rawDescription += KEYWORDS_STRINGS[2];
        }
        if (goldfish) {
            rawDescription += KEYWORDS_STRINGS[3];
        }
        if (scapegoat) {
            rawDescription += KEYWORDS_STRINGS[4];
        }
        if (rain) {
            rawDescription += KEYWORDS_STRINGS[5];
        }

        if (!rawDescription.isEmpty()) {
            rawDescription += " NL ";
        }

        return rawDescription;
    }

    @Override
    public boolean fuse(TrizonCardBooleans other) {
        this.canUse = this.canUse && other.canUse;
        this.yandere = this.yandere || other.yandere;
        this.fire = this.fire || other.fire;
        this.goldfish = this.goldfish || other.goldfish;
        this.scapegoat = this.scapegoat || other.scapegoat;
        this.rain = this.rain || other.rain;

        return true;
    }

    public TrizonCardBooleans clone() {
        TrizonCardBooleans copy = new TrizonCardBooleans();
        copy.canUse = this.canUse;
        copy.yandere = this.yandere;
        copy.fire = this.fire;
        copy.goldfish = this.goldfish;
        copy.scapegoat = this.scapegoat;
        copy.rain = this.rain;
        return copy;
    }
}