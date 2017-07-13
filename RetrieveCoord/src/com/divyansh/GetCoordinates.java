package com.divyansh;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.ParseException;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class GetCoordinates {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			CSVReader reader = new CSVReader(new FileReader("./data/distinctaddress.csv"));
			List<LocationBean> allLoc = new ArrayList<LocationBean>();
			String[] record;
			System.out.println("Reading...");
			while ((record = reader.readNext()) != null) {
				LocationBean lc = new LocationBean();
				lc.setCity(record[0]);
				lc.setState(record[1]);
				lc.setMetro(record[2]);
				lc.setCountry(record[3]);
				lc.setLat(null);
				lc.setLng(null);
				allLoc.add(lc);
			}
			reader.close();
			System.out.println("Loading...");
			Iterator<LocationBean> iterator = allLoc.iterator();
			while (iterator.hasNext()) {
				LocationBean l = iterator.next();
				String[] coord = getCoordinates(l.getCity(), l.getState());
				l.setLat(coord[0]);
				l.setLng(coord[1]);
			}
			System.out.println("Writing...");
			// StringWriter writer= new StringWriter();
			FileWriter writer = new FileWriter(new File("./data/location.csv"));
			CSVWriter csvwriter = new CSVWriter(writer);
			csvwriter.writeAll(toStringArray(allLoc));
			csvwriter.flush();
			csvwriter.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static List<String[]> toStringArray(List<LocationBean> locs) {
		List<String[]> records = new ArrayList<String[]>();

		// adding header record
		records.add(new String[] { "City", "State", "Metro","Country", "Lat", "Lng" });

		Iterator<LocationBean> it = locs.iterator();
		while (it.hasNext()) {
			LocationBean loc = it.next();
			records.add(new String[] { loc.getCity(), loc.getState(),loc.getMetro(), loc.getCountry(), loc.getLat(), loc.getLng() });
		}
		return records;
	}

	public static String[] getCoordinates(String city, String state) throws IOException, ParseException {

		city = city.replace(",", "+");
		city = city.replace(" ", "+");
		state = state.replace(" ", "");

		String fullAddress = city + "+" + state;

		URL url = new URL(
				"https://maps.google.com/maps/api/geocode/json?key= AIzaSyCrAGNUYhL3qq2tRopccOcZxBZvZpcnano &address="
						+ fullAddress + "&sensor=false");

		Response res = given().when().get(url);
		JsonPath jp = new JsonPath(res.asString());

		String lat = jp.get("results.geometry.location.lat").toString();
		String lng = jp.get("results.geometry.location.lng").toString();
		String[] coord = { lat.substring(1, lat.length() - 1), lng.substring(1, lng.length() - 1) };
		
		return coord;
	}
}
