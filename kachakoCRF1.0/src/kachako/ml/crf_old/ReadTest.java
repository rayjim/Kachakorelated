package kachako.ml.crf_old;


import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.BlockReader;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileAsBinaryInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileAsTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ReadTest extends Configured implements Tool {

  static class SequenceFileMapper
    extends Mapper<Text, Text, Text, Text> {
    
    public void map(Text key, Text value, Context context)
      throws IOException, InterruptedException {

      Configuration conf = context.getConfiguration();
      
    //  String filename = conf.get("map.input.file");
      
      System.out.println(value);
      //for (int ii = 0; ii<sb.length;ii++)
      {
      //System.out.println(sb[ii]);
    
      context.write(new Text(""),  value);
      }
       
    }
  }

  static class IdentityReducer extends Reducer<Text, BytesWritable, Text, BytesWritable> {
    // Use default reduce() == org.apache.hadoop.mapred.lib.IdentityReducer.reduce()
  }

  public int run(String[] args) throws Exception {
    Configuration conf = getConf();
    if (conf == null) {
      return -1;
    }

    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    System.out.println("This is the test for TestReader");
    Job job = new Job(conf, "ReadTest");

    job.setJarByClass(ReadTest.class);

    job.setMapperClass(SequenceFileMapper.class);
    //job.setReducerClass(IdentityReducer.class);

    job.setInputFormatClass(SequenceFileInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);

    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new ReadTest(), args);
    System.exit(exitCode);
  }
}