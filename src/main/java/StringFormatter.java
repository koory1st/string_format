import java.lang.reflect.Field;
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
     * new StringFormatter("{}{}").formatString("123", "abc").toString(); //result is 123abc
     * 2:
     * new StringFormatter("{}{}").formatString("123").formatString("abc").toString(); //result is 123abc
     * 3:
     * new StringFormatter("{}{}").formatString("123").toString(); //result is 123{}
     *
     * @param replacements The replacement sequence of char values
     * @return The resulting {@code String}
     */
    public StringFormatter formatString(CharSequence... replacements) {
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

    /**
     * replace each '{placeholder}' by replacement
     * can be used like:
     * new StringFormatter("{name}{age}").format("name", "Levy").format("age", 40).toString()
     *
     * @param placeholder The placeholder's name, without "{}"
     * @param replacement The replacement sequence of char values
     * @return The resulting {@code String}
     */
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

    /**
     * replace each '{placeholder}' by replacement
     * can be used like:
     * Map<String, Object> replacement = new HashMap<>();
     * replacement.put("name", "Levy");
     * replacement.put("age", 40);
     * new StringFormatter("{name}{age}").format(replacement).toString()
     *
     * @param replacementMap The key-value map, the key is the placeholder without "{}", the value is the replacement value
     * @return The resulting {@code String}
     */
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

    /**
     * replace each '{placeholder}' by replacement
     * can be used like:
     * Student student = new Student();
     * student.setName("Levy");
     * student.setAge(40);
     * new StringFormatter("{name}{age}").format(student).toString()
     *
     * @param replacement The instance, the property is the placeholder without "{}", the value is the replacement value
     * @return The resulting {@code String}
     */
    public <T> StringFormatter format(T replacement) {
        if (replacement == null) {
            return this;
        }

        if (replacement.getClass().equals(String.class)) {
            return formatString(String.valueOf(replacement));
        }

        Field[] fields = replacement.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.getType().equals(String.class) &&
                    !field.getType().equals(Integer.class) &&
                    !field.getType().equals(int.class) &&
                    !field.getGenericType().equals(Long.class) &&
                    !field.getGenericType().equals(long.class) &&
                    !field.getType().equals(Double.class) &&
                    !field.getType().equals(double.class) &&
                    !field.getType().equals(BigDecimal.class)) {
                continue;
            }

            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }

            Object value;
            try {
                value = field.get(replacement);
            } catch (IllegalAccessException ignored) {
                continue;
            } finally {
                field.setAccessible(accessible);
            }

            if (value == null) {
                continue;
            }

            str = format(field.getName(), String.valueOf(value)).toString();
        }

        return this;
    }
}
