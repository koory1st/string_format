import org.junit.Assert;
import org.junit.Test;

public class StringFormatterTest {

    @Test
    public void format() {
        StringFormatter stringFormatter = new StringFormatter(null);
        Assert.assertNull(stringFormatter.toString());

        stringFormatter = new StringFormatter("");
        Assert.assertEquals(stringFormatter.toString(), "");

        stringFormatter = new StringFormatter("123");
        Assert.assertEquals(stringFormatter.toString(), "123");

        stringFormatter = new StringFormatter("{}");
        Assert.assertEquals(stringFormatter.toString(), "{}");

        stringFormatter = new StringFormatter("{}");
        Assert.assertEquals(stringFormatter.format("123").toString(), "123");

        stringFormatter = new StringFormatter("{}");
        Assert.assertEquals(stringFormatter.format("123", "456").toString(), "123");

        stringFormatter = new StringFormatter("{}{}");
        Assert.assertEquals(stringFormatter.format("123", "456").toString(), "123456");

        stringFormatter = new StringFormatter("{}{}{}");
        Assert.assertEquals(stringFormatter.format("abc", "efg").toString(), "abcefg{}");

        stringFormatter = new StringFormatter("{}{}{}");
        Assert.assertEquals(
                "abcefg{}",
                stringFormatter.
                        format("abc").
                        format("efg").
                        toString());

        stringFormatter = new StringFormatter("this is {} and {} and {}");
        Assert.assertEquals(
                "this is abc and efg and hij",
                stringFormatter.
                        format("abc").
                        format("efg").
                        format("hij").
                        toString());
    }

}
