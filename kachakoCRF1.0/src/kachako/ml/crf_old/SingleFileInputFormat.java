package kachako.ml.crf_old;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class SingleFileInputFormat extends FileInputFormat {

	
	@Override
	protected boolean isSplitable(JobContext context, Path file){
			return false;
		
	}
	@Override
	public RecordReader createRecordReader(InputSplit arg0,
			TaskAttemptContext arg1) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null; //not implemented yet
	}

}
