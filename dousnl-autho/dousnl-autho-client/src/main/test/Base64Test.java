import org.apache.http.util.Args;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/28 9:30
 */
public class Base64Test {

    private static final String BASE_="Basic ";
    public static void main(String[] args) throws UnsupportedEncodingException {
        String base=Base64.getEncoder().encodeToString("user-service:123456".getBytes());
        System.out.println(BASE_+base);
        byte[] decode = Base64.getDecoder().decode(base);
        String baseD=new String(decode);
        System.out.println(baseD);

        final String text = "字串文字";
        //String.getBytes默认编码utf-8
        String base1=Base64.getEncoder().encodeToString(text.getBytes());
        System.out.println(base1);
        byte[] decode1 = Base64.getDecoder().decode(base1);
        System.out.println(new String(decode1,"UTF-8"));

        System.out.println("file.encoding:" + System.getProperty("file.encoding"));
        System.out.println("sun.jnu.encoding:" + System.getProperty("sun.jnu.encoding"));

        Args.check(1<2,"1 can not gt 0");
    }
}
