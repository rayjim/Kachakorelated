package kachako.ml.crf_old;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;


public class RegressionReducer 
extends Reducer<NullWritable, MapWritable, NullWritable, MapWritable>{ 
	
    public void reduce(NullWritable key, Iterable<MapWritable> values,Context context) 
			 throws IOException, InterruptedException {
   	 //System.out.println("*************This is the reducer*******************");
   	 MapWritable mergedMap = new MapWritable();
   	          
   	          for (MapWritable value : values) {
   	        	
   	              for (Entry<Writable, Writable> entry : value.entrySet()) {
   	                 Text featureName = (Text) entry.getKey();
   	                 DoubleArrayWritable featureWts = (DoubleArrayWritable) entry.getValue();//[0] weight, [1] diff, [2] count
   	                 DoubleWritable[] fw = (DoubleWritable[]) featureWts.toArray();
   	                 DoubleArrayWritable mergedWeight = (DoubleArrayWritable) (mergedMap.get(featureName));
   	                 if (mergedWeight == null) {
   	                     mergedMap.put(featureName, featureWts);
   	                 } else {
   	                	DoubleWritable[] w = (DoubleWritable[]) mergedWeight.toArray();
   	                	w[1] = new DoubleWritable(w[1].get()+fw[1].get()); //adding all the weight
   	                	w[2] = new DoubleWritable(w[2].get()+fw[2].get()); //adding all the count
   	                    mergedWeight.set(w);
   	                 }
   	             }
   	            
   	         }
   	        //  System.out.println("The number of feature is: "+mergedMap.size());
   	         for (Entry<Writable, Writable> entry : mergedMap.entrySet()) {
   	        	 DoubleArrayWritable weights= (DoubleArrayWritable) entry.getValue();
   	        	 DoubleWritable[] mw = (DoubleWritable[]) weights.toArray();
   	        	 mw[0]=new DoubleWritable(mw[0].get()+mw[1].get()/mw[2].get());
   	        	 mw[1] = new DoubleWritable(0);
   	        	 mw[2] = new DoubleWritable(0);
   	        	 //System.out.println(entry.getKey().toString()+": "+mw[0].toString());
   	             weights.set(mw);
   	         }
   	         context.write(NullWritable.get(), mergedMap);
   	         
}
	
}