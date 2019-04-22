import java.math.BigDecimal;
import java.util.Map;

/**
 *
 */
public class StringFormatter {
    private static final String EMPTY_STRING = "";
    private static final String PLACE_HOLDER_EMPTY = "{}";
    private static final String PLACE_HOLDER_EMPTY_REX = "\\{}";
    private String str = EMPTY_STRING;

    public StringFormatter(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

    /**
     * replace each '{}' by arg
     * can be used like:
     * 1:
     * new StringFormatter("{}{}").format("123", "abc").toString(); //result is 123abc
     * 2:
     * new StringFormatter("{}{}").format("123").format("abc").toString(); //result is 123abc
     * 3:
     * new StringFormatter("{}{}").format("123").toString(); //result is 123{}
     *
     * @param replacements The replacement sequence of char values
     * @return The resulting {@code String}
     */
    public StringFormatter format(CharSequence... replacements) {
        if (str == null || str.length() == 0) {
            return this;
        }

        for (CharSequence arg : replacements) {
            if (this.str.contains(PLACE_HOLDER_EMPTY)) {
                this.str = this.str.replaceFirst(PLACE_HOLDER_EMPTY_REX, arg.toString());
            }
        }

        return this;
    }

    public StringFormatter format(String placeholder, String replacement) {
        if (str == null || str.length() == 0) {
            return this;
        }

        if (placeholder == null || placeholder.length() == 0) {
            throw new RuntimeException("placeholder is empty (replacement: " + replacement + ")");
        }

        if (replacement == null) {
            throw new RuntimeException("replacement is null (placeholder: " + placeholder + ")");
        }

        str = str.replace("{" + placeholder + "}", replacement);

        return this;
    }

    public StringFormatter format(String placeholder, int replacement) {
        return format(placeholder, Integer.toString(replacement));
    }

    public StringFormatter format(String placeholder, long replacement) {
        return format(placeholder, Long.toString(replacement));
    }

    public StringFormatter format(String placeholder, double replacement) {
        return format(placeholder, Double.toString(replacement));
    }

    public StringFormatter format(String placeholder, BigDecimal replacement) {
        return format(placeholder, replacement.toString());
    }

    public StringFormatter format(Map<String, Object> replacementMap) {
        if (replacementMap == null) {
            return this;
        }
        for (Map.Entry<String, Object> entry : replacementMap.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String ||
                    value instanceof Integer ||
                    value instanceof Long ||
                    value instanceof Double ||
                    value instanceof BigDecimal) {
                str = format(entry.getKey(), String.valueOf(value)).toString();
            }
        }

        return this;
    }
}
