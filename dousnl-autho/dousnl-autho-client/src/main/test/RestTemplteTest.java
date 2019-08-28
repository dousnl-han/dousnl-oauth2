import com.alibaba.fastjson.JSON;
import com.dousnl.SpringAuthoClientApplication;
import com.dousnl.domain.User;
import com.dousnl.mapper.UserMapper;
import com.dousnl.util.HttpClentUtils;
import com.dousnl.util.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/28 14:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringAuthoClientApplication.class)
public class RestTemplteTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void test(){
        HttpEntity<Void> request = new HttpEntity<Void>(new HttpHeaders());
        ResponseEntity<Map> exchange = restTemplate.exchange("http://localhost:8080/oauth/token_key", HttpMethod.GET, request, Map.class);
        Object value = exchange.getBody();
        System.out.println(">>>>>value:"+JSON.toJSONString(value));

    }
    @Test
    public void test1(){
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(">>>>>value:"+ JSON.toJSONString(user));
    }


    public static void main(String[] args) throws IOException {
        Map<String, String> headers=new HashMap<String, String>();
        Map<String, String> params=new HashMap<String, String>();
        Result result = HttpClentUtils.get("http://localhost:8080/oauth/token_key", headers, params);
        System.out.println(">>>>>>>autho2-uaa返回:"+ result.getBody());
    }
}
