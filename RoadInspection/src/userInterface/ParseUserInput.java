package userInterface;

import java.util.ArrayList;
import java.util.Scanner;

import databaseHelper.DatabaseHelper;

public class ParseUserInput {
	
	/*
	 * Method for getting user input
	 */
	public void getUserInput() {
		System.out.println("Welcome to Road Inspection Database Helper");
		System.out.println("Type -help to get list of commands");
		String input = "";
		Scanner reader = new Scanner(System.in);

		while (!input.equals("q")) {
			System.out.print(">> ");
			input = reader.nextLine();
			if (input.equals("-help")) {
				System.out.println("Commands available : ");
				System.out.println("1. Entering city into database : ");
				System.out.println("   -ec -name <city_name> -latMax <lat_max> -latMin <lat_min> -lngMax <lng_min> -lngMin <lng_min>");
				System.out.println("2. Checking whether a city is present in database : ");
				System.out.println("   -cc -name <city_name>");
				System.out.println("q : quit");
			} else {
				try {
					ArrayList<String> res = parseInput(input);
					if (res == null) {
						System.out.println("Command not identified. Type -help to get list of commands");
					} else {
						// At this place, data coming back is in the proper format
						if (res.get(0).equals("-cc")) {
							System.out.println(res);
						} else {
							HandleUserInput handle = new HandleUserInput();
							handle.handleInput(res);
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Incomplete Command. Type -help to get list of commands");
				} catch (NumberFormatException e) {
					System.out.println("Please enter java.lang.Double for latitute and longitude values");
				}
			}
		}
	}
	
	private ArrayList<String> parseInput(String input) 
			throws ArrayIndexOutOfBoundsException, 
			NumberFormatException
	{
		ArrayList<String> res = new ArrayList<>();
		
		String[] splitInput = input.split(" ");
		
		switch(splitInput[0]) {	
		case "-ec" :
			res.add("-ec");
			if (splitInput[1].equals("-name")) {
				res.add(splitInput[2]);
			} else {
				return null;
			}
			
			if (splitInput[3].equals("-latMax")) {
				Double.parseDouble(splitInput[4]);
				res.add(splitInput[4]);
			} else {
				return null;
			}
			
			if (splitInput[5].equals("-latMin")) {
				Double.parseDouble(splitInput[6]);
				res.add(splitInput[6]);
			} else {
				return null;
			}
			
			if (splitInput[7].equals("-lngMax")) {
				Double.parseDouble(splitInput[8]);
				res.add(splitInput[8]);
			} else {
				return null;
			}
			
			if (splitInput[9].equals("-lngMin")) {
				Double.parseDouble(splitInput[10]);
				res.add(splitInput[10]);
			} else {
				return null;
			}
			return res;
			
		case "-cc" :
			if (splitInput[1].equals("-name") && splitInput.length == 3) {
				String cityName = splitInput[2];
				DatabaseHelper helper = new DatabaseHelper();
				System.out.println(helper.readCityTable(cityName));
				return helper.readCityTable(cityName);
			} else {
				return null;
			}
			
		default : return null;
		}
	}
}
