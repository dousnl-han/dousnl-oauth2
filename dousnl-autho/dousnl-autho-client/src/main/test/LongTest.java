import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/30 15:11
 */
public class LongTest {
    private static long systemCuil;

    public static void main(String[] args) {
        //1567417978320
        //1567415800
        System.out.println(systemCuil);
        System.out.println(System.currentTimeMillis());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss").format(new Date(System.currentTimeMillis())));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss").format(new Date(System.currentTimeMillis()+1000L)));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss").format(new Date(1567418774000L)));
    }
}
