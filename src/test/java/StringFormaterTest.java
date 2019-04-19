import org.junit.Assert;
import org.junit.Test;

public class StringFormaterTest {

    @Test
    public void format() {
        StringFormater stringFormater = new StringFormater(null);
        Assert.assertNull(stringFormater.toString());

        stringFormater = new StringFormater("");
        Assert.assertEquals(stringFormater.toString(), "");

        stringFormater = new StringFormater("123");
        Assert.assertEquals(stringFormater.toString(), "123");

        stringFormater = new StringFormater("{}");
        Assert.assertEquals(stringFormater.toString(), "{}");

        stringFormater = new StringFormater("{}");
        Assert.assertEquals(stringFormater.format("123").toString(), "123");

        stringFormater = new StringFormater("{}");
        Assert.assertEquals(stringFormater.format("123", "456").toString(), "123");

        stringFormater = new StringFormater("{}{}");
        Assert.assertEquals(stringFormater.format("123", "456").toString(), "123456");

        stringFormater = new StringFormater("{}{}{}");
        Assert.assertEquals(stringFormater.format("abc", "efg").toString(), "abcefg{}");

        stringFormater = new StringFormater("{}{}{}");
        Assert.assertEquals(
                "abcefg{}",
                stringFormater.
                        format("abc").
                        format("efg").
                        toString());

        stringFormater = new StringFormater("this is {} and {} and {}");
        Assert.assertEquals(
                "this is abc and efg and hij",
                stringFormater.
                        format("abc").
                        format("efg").
                        format("hij").
                        toString());
    }

}
