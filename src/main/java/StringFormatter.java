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
        if (str == null) {
            return this;
        }

        if (str.length() == 0) {
            return this;
        }

        for (CharSequence arg : replacements) {
            if (this.str.contains(PLACE_HOLDER_EMPTY)) {
                this.str = this.str.replaceFirst(PLACE_HOLDER_EMPTY_REX, arg.toString());
            }
        }

        return this;
    }
}
