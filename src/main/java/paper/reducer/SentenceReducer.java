package paper.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
* Input Key: Sentence and Filename
* Input Value: Frequency
* Output Key:
* Output Value: */
public class SentenceReducer extends Reducer<Text, IntWritable, Text, Text> {
    private final Text valInfo = new Text();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable freq : values) {
            sum += freq.get();
        }
        String[] wordSentenceFile = key.toString().split(":");
        key.set(wordSentenceFile[0]);
        valInfo.set(wordSentenceFile[1] + ":" + sum);
        context.write(key, valInfo);
    }
}
