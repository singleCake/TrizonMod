package localization;
import modcore.TrizonMod;

public class TrizonFactoryStrings {
    public String DESCRIPTION;
    public String[] EXTENDED_DESCRIPTIONS;

    public TrizonFactoryStrings() {
    }

    public static TrizonFactoryStrings getMockFactoryStrings() {
        TrizonFactoryStrings retVal = new TrizonFactoryStrings();
        retVal.DESCRIPTION = "[MISSING_DESCRIPTION]";
        retVal.EXTENDED_DESCRIPTIONS = new String[] {"[MISSING_EXTENDED_DESCRIPTION]"};
        return retVal;
    }

    public static String getDescription(Class<?> factory) {
        TrizonFactoryStrings strings = TrizonMod.factoryStringsMap.get(factory.getSimpleName());
        if (strings == null) {
            TrizonMod.logger.warn("No factory strings found for " + factory.getSimpleName());
            return "[MISSING_DESCRIPTION]";
        }
        return strings.DESCRIPTION;
    }

    public static String[] getExtendedDescription(Class<?> factory) {
        TrizonFactoryStrings strings = TrizonMod.factoryStringsMap.get(factory.getSimpleName());
        if (strings == null) {
            TrizonMod.logger.warn("No factory strings found for " + factory.getSimpleName());
            return new String[] {"[MISSING_EXTENDED_DESCRIPTION]"};
        }
        return strings.EXTENDED_DESCRIPTIONS;
    }
}
