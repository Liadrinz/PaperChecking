package paper.util;

import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class SentenceOutputFormat<K, V> extends FileOutputFormat<K, V> {
    protected static class RedisRecordWriter<K, V> extends RecordWriter<K, V> {
        private final Jedis jedis;

        public RedisRecordWriter(Jedis jedis) {
            this.jedis = jedis;
        }

        @Override
        public void write(K k, V v) throws IOException, InterruptedException {
            if (k != null && v != null)
                jedis.lpush(k.toString(), v.toString());
        }

        @Override
        public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
            jedis.disconnect();
        }
    }

    @Override
    public RecordWriter<K, V> getRecordWriter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        Jedis jedis = JedisUtil.getJedis();
        jedis.flushAll();
        return new RedisRecordWriter<K, V>(jedis);
    }
}
