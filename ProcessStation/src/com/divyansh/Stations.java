package com.divyansh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

public class Stations {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// Open the file
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream("./data/ghcnd-stations.txt");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		FileWriter writer = new FileWriter(new File("./data/stations.csv"));
		CSVWriter csvwriter = new CSVWriter(writer);
		
		String strLine;
		List<StationBean> allst = new ArrayList<StationBean>();
		//Read File Line By Line
		
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				strLine = strLine.replaceAll("( )+", " ");
				String []values= strLine.split(" ");
				StationBean station= new StationBean();
				station.setStation(values[0]);
				station.setLat(values[1]);
				station.setLng(values[2]);
				allst.add(station);
			}
			
			br.close();
			
			csvwriter.writeAll(toStringArray(allst));
			
			csvwriter.flush();
			csvwriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Close the input stream
		
	}
	private static List<String[]> toStringArray(List<StationBean> locs) {
		List<String[]> records = new ArrayList<String[]>();

		// adding header record
		records.add(new String[] { "Station", "Lat", "Lng"});

		Iterator<StationBean> it = locs.iterator();
		while (it.hasNext()) {
			StationBean loc = it.next();
			records.add(new String[] { loc.getStation(), loc.getLat(),loc.getLng()});
		}
		return records;
	}

}
