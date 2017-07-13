package com.divyansh;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class DistinctMapper extends Mapper<Object, Text, Text, NullWritable> {

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

		String values[] = value.toString().split(",");
		String region = null;
		String city = null;
		String state = null;
		String metro = null;
		String country = null;
		String address=null;
		try {
			region = values[0].trim();
			city = values[1].trim();
			state = values[2].trim();
			metro = values[3].trim();
			country = values[4].trim();
			
			address= city+','+state+','+metro+','+country;
			System.out.println(address);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (null != region || null != city || state != null) {

			//CompositeKeyWritable cw = new CompositeKeyWritable(region,city,state);
			
			try {
				context.write(new Text(address), NullWritable.get());
			} catch (Exception e) {
				//System.out.println(cw);
				// System.out.println(values[10]);				
				System.out.println("" + e.getMessage());

			}

		}

	}

}