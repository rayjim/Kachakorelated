package kachako.ml.crf_old;


	/**
	 * @param args
	 */

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;



public class MapToText extends Configured implements Tool {

  static class SequenceFileMapper
    extends Mapper<NullWritable, MapWritable, Text, Text> {
    
    public void map(NullWritable key, MapWritable value, Context context)
      throws IOException, InterruptedException {

     // Configuration conf = context.getConfiguration();
      
     // String filename = conf.get("map.input.file")
    	
     context.write(new Text(String.valueOf(value.size())), new Text(""));
      for (Entry<Writable, Writable> entry : value.entrySet()) {
	        	
    	  Text word = (Text) entry.getKey();
          DoubleArrayWritable featureArray = (DoubleArrayWritable) entry.getValue();
          DoubleWritable[] featureWts = (DoubleWritable[])featureArray.toArray();
     
                 context.write(word,  new Text(featureWts[0].toString()));
             
             
      }
      
       
    }

	private String IntWritable(int size) {
		// TODO Auto-generated method stub
		return null;
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

    @SuppressWarnings("deprecation")
	Job job = new Job(conf, "MapToText");

    job.setJarByClass(MapToText.class);

    job.setMapperClass(SequenceFileMapper.class);
    //job.setReducerClass(IdentityReducer.class);

    job.setInputFormatClass(SequenceFileInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path("output/weight-15"));
    FileOutputFormat.setOutputPath(job, new Path("output/modelfile"));

    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new MapToText(), args);
    System.exit(exitCode);
  }
  }

