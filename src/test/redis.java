import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import ppf.bean.User;
import ppf.service.IUserService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

@ContextConfiguration({"classpath:spring-redis.xml","classpath:spring-mvc.xml","classpath:spring-mybatis.xml"})
public class redis {
    @Resource
    private IUserService userService;

    @Test
    public void Test1(){
        User user=userService.selectUserByName("ppf");
        System.out.println(user);
    }


    @Test
    /**
     * 单例方式测试jedis
     */
    public void demo1(){
        // 1.设置IP地址跟端口号
        Jedis jedis = new Jedis("localhost",6379);
        // 2.保存数据
        jedis.set("name", "wangrun");
        // 3.获取数据
        String name = jedis.get("name");
        // 4.数据展示
        System.out.println(name);
        // 5.关闭连接
        jedis.close();
    }

    @Test
    /**
     * 连接池的方式测试jedis
     */
    public void demo2(){
        // 1. 获得jedis的配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 1-1设置最大连接数
        config.setMaxTotal(30);
        // 1-2设置最大空闲连接数
        config.setMaxIdle(10);
        //2. 获取连接池
        JedisPool jp = new JedisPool(config,"localhost",6379);
        // 3.获得核心对象：
        Jedis jedis = null;
        try {
            // 4.通过连接池获得连接
            jedis = jp.getResource();
            // 5.设置数据
            jedis.set("name", "Aaryn");
            // 6.获取数据
            String name = jedis.get("name");
            System.out.println(name);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            // 7.关闭连接
            if (jedis != null) {
                jedis.close();
            }
            // 8.关闭连接池
            if (jp != null) {
                jp.close();
            }
        }

    }
}
