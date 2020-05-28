package paper;

import paper.mapper.SentenceMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import paper.reducer.SentenceReducer;
import paper.util.SentenceOutputFormat;

import java.io.IOException;
import java.net.URI;

public class PaperChecking {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        if (args.length != 2) {
            System.err.println("Wrong Parameter");
            System.exit(2);
        }
        FileSystem fs = FileSystem.get(URI.create(args[1]), conf);
        if (fs.exists(new Path(args[1]))) {
            fs.delete(new Path(args[1]), true);
        }
        Job job = Job.getInstance(conf, "paper.PaperChecking");
        job.setJarByClass(PaperChecking.class);
        job.setMapperClass(SentenceMapper.class);
        job.setReducerClass(SentenceReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(SentenceOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
