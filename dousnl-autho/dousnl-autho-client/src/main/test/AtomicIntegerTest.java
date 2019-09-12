import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/9/11 14:50
 */
public class AtomicIntegerTest {

    public static void main(String[] args) {
        AtomicInteger count=new AtomicInteger(1);
        int i = count.incrementAndGet();
        System.out.println("count:"+count);
        System.out.println("i:"+count);
        int andIncrement = count.getAndIncrement();
        System.out.println("andIncrement:"+andIncrement);
        System.out.println("count:"+count);
    }

}
