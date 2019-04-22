import entity.Person;
import entity.Student;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class StringFormatterTest {

    @Test
    public void format1() {
        StringFormatter stringFormatter = new StringFormatter(null);
        Assert.assertNull(stringFormatter.toString());

        stringFormatter = new StringFormatter("");
        Assert.assertEquals(stringFormatter.toString(), "");

        stringFormatter = new StringFormatter("123");
        Assert.assertEquals(stringFormatter.toString(), "123");

        stringFormatter = new StringFormatter("{}");
        Assert.assertEquals(stringFormatter.toString(), "{}");

        stringFormatter = new StringFormatter("{}");
        Assert.assertEquals(stringFormatter.formatString("123").toString(), "123");

        stringFormatter = new StringFormatter("{}");
        Assert.assertEquals(stringFormatter.formatString("123", "456").toString(), "123");

        stringFormatter = new StringFormatter("{}{}");
        Assert.assertEquals(stringFormatter.formatString("123", "456").toString(), "123456");

        stringFormatter = new StringFormatter("{}{}{}");
        Assert.assertEquals(stringFormatter.formatString("abc", "efg").toString(), "abcefg{}");

        stringFormatter = new StringFormatter("{}{}{}");
        Assert.assertEquals(
                "abcefg{}",
                stringFormatter.
                        formatString("abc").
                        formatString("efg").
                        toString());

        stringFormatter = new StringFormatter("this is {} and {} and {}");
        Assert.assertEquals(
                "this is abc and efg and hij",
                stringFormatter.
                        formatString("abc").
                        formatString("efg").
                        formatString("hij").
                        toString());
    }

    @Test
    public void format2() {
        StringFormatter stringFormatter = new StringFormatter("my name is {name}");

        try {
            stringFormatter.format(null, "");
            Assert.fail();
        } catch (Exception ignored) {
        }

        try {
            stringFormatter.format("", "");
            Assert.fail();
        } catch (Exception ignored) {
        }

        String replacement = null;
        try {
            stringFormatter.format("name", replacement);
            Assert.fail();
        } catch (Exception ignored) {
        }

        Assert.assertEquals(
                "my name is Levy",
                stringFormatter.format("name", "Levy").toString());

        stringFormatter = new StringFormatter("my name is {name}, and I'm {age} years old.");

        Assert.assertEquals(
                "my name is Levy, and I'm 40 years old.",
                stringFormatter.
                        format("name", "Levy").
                        format("age", "40").toString());

        Assert.assertEquals(
                "my name is Levy, and I'm 40 years old.",
                stringFormatter.
                        format("name", "Levy").
                        format("age", 40).toString());

        Assert.assertEquals(
                "my name is Levy, and I'm 40 years old.",
                stringFormatter.
                        format("name", "Levy").
                        format("age", 40L).toString());

        stringFormatter = new StringFormatter("my name is {name}, and the number is {number}.");
        Assert.assertEquals(
                "my name is Levy, and the number is 40.1.",
                stringFormatter.
                        format("name", "Levy").
                        format("number", 40.1).toString());

        stringFormatter = new StringFormatter("my name is {name}, and the number is {number}.");
        BigDecimal bigDecimal = new BigDecimal("50.111111");
        Assert.assertEquals(
                "my name is Levy, and the number is 50.111111.",
                stringFormatter.
                        format("name", "Levy").
                        format("number", bigDecimal).toString());

        stringFormatter = new StringFormatter("my name is {name}, and the number is {number}.");
        Integer integer = 15;
        Assert.assertEquals(
                "my name is Levy, and the number is 15.",
                stringFormatter.
                        format("name", "Levy").
                        format("number", integer).toString());
    }

    @Test
    public void format3() {
        StringFormatter stringFormatter = new StringFormatter("my name is {name}, and the number is {number}.");

        Map<String, Object> replacement = null;
        Assert.assertEquals(
                "my name is {name}, and the number is {number}.",
                stringFormatter.
                        format(replacement).toString());

        replacement = new HashMap<>();
        replacement.put("aaa", 123);
        Assert.assertEquals(
                "my name is {name}, and the number is {number}.",
                stringFormatter.
                        format(replacement).toString());

        replacement = new HashMap<>();
        replacement.put("name", "Levy");
        replacement.put("number", 15);
        Assert.assertEquals(
                "my name is Levy, and the number is 15.",
                stringFormatter.
                        format(replacement).toString());
    }

    @Test
    public void format4() {
        StringFormatter stringFormatter = new StringFormatter("my name is {name}, and I'm {age} years old, and the number is {number}.");

        Person person = new Person();
        Assert.assertEquals(
                "my name is {name}, and I'm {age} years old, and the number is {number}.",
                stringFormatter.
                        format(person).toString());

        person.setName("Levy");
        Assert.assertEquals(
                "my name is Levy, and I'm {age} years old, and the number is {number}.",
                stringFormatter.
                        format(person).toString());

        person.setName("Levy");
        person.setAge(40);
        Assert.assertEquals(
                "my name is Levy, and I'm 40 years old, and the number is {number}.",
                stringFormatter.
                        format(person).toString());

        Student student = new Student();
        student.setName("Levy");
        student.setAge(40);
        student.setNumber(15L);
        Assert.assertEquals(
                "my name is Levy, and I'm 40 years old, and the number is 15.",
                stringFormatter.
                        format(student).toString());
    }
}
