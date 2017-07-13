package com.divyansh;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
 
public class DistinctReducer extends
Reducer<Text, Text, Text, NullWritable> {
	

	 
public void reduce(Text key,Iterable<Text> values,Context context)
        throws IOException, InterruptedException {

   try {				  
	   
		   //Text newkey= new Text(key.toString()+','+values.iterator().next().toString());
		   context.write(key,NullWritable.get());
	  
	} catch (Exception e) {
		// TODO: handle exception
	}
  

   
   
}
}