package paper.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

public class JedisUtil {
    private static JedisPool jedisPool = null;
    static {
        try {
            Properties props = new Properties();
            props.load(JedisUtil.class.getClassLoader().getResourceAsStream("redis.properties"));
            String HOST = props.getProperty("redis.host");
            int PORT = Integer.parseInt(props.getProperty("redis.port"));
            String AUTH = props.getProperty("redis.auth");
            int DB = Integer.parseInt(props.getProperty("redis.db"));
            int MAX_ACTIVE = Integer.parseInt(props.getProperty("redis.max-active"));
            int MAX_IDLE = Integer.parseInt(props.getProperty("redis.max-idle"));
            int MAX_WAIT = Integer.parseInt(props.getProperty("redis.max-wait"));
            int TIMEOUT = Integer.parseInt(props.getProperty("redis.timeout"));
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT, AUTH, DB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized static Jedis getJedis() {
        return jedisPool.getResource();
    }
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
