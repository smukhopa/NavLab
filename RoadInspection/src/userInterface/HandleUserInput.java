package userInterface;

import java.util.ArrayList;
import java.util.Scanner;

import databaseHelper.DatabaseHelper;
import mapsAPI.MapsAPIHelper;

public class HandleUserInput {
	
	public void handleInput(ArrayList<String> input) {
		// This means the user wants to enter a new city
		if (input.get(0).equals("-ec")) {
			// Check whether the city is already there
			String cityName = input.get(1);
			DatabaseHelper helper = new DatabaseHelper();
			ArrayList<String> res = helper.readCityTable(cityName);
			if (res != null) {
				// This means that the city already exists
				// Ask user whether he/she wants to overwrite that data
				System.out.println("RESULT : City already exists in Database");
				System.out.println("Would you like to overwite existing data? (y/n)");
				Scanner reader = new Scanner(System.in);
				String str = "";
				
				while (!str.equals("y") && !str.equals("n")) {
					System.out.print(">> ");
					str = reader.next();
					if (str.equals("y")) {
						// Overwrite entire database
						// Delete all old records
						deleteOldCity();
						
						// Then add new city
						addNewCity(input);
					} else if (str.equals("n")){
						// No need to do anything
					} else {
						System.out.println("Please enter either y/n");
					}
				}
			} else {
				addNewCity(input);
			}
		}
	}
	
	private void addNewCity(ArrayList<String> arr) {
		UIHelper.callMapsAPI(arr);
	}
	
	private void deleteOldCity() {
		UIHelper.deleteOldCity();
	}
	
}