package mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import util.SentenceSplitter;

import java.io.IOException;
import java.util.StringTokenizer;

/*
* Output Key: Word, Sentence and Filename
* Output Value: Frequency
* */
public class SentenceMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final Text keyInfo = new Text();
    private final IntWritable valInfo = new IntWritable();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String filename = ((FileSplit)context.getInputSplit()).getPath().getName();
        SentenceSplitter splitter = new SentenceSplitter(value.toString());
        while (splitter.hasMoreSentences()) {
            String sentence = splitter.nextSentence();
            StringTokenizer tokenizer = new StringTokenizer(sentence);
            while (tokenizer.hasMoreTokens()) {
                String word = tokenizer.nextToken();
                keyInfo.set(word + ":" + sentence + ":" + filename);
                valInfo.set(1);
                context.write(keyInfo, valInfo);
            }
        }
    }
}
