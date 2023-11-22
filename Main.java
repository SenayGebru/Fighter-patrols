/**
 * This program analyzes the data and determines the size of the area patrolled by the pilots
 * (it does this by reading from a file(pilotinfo.txt) and performing calculations. 
 * followed by writing the results into a different file(pilot_areas.txt))
 * @author Senay Gebru 
 * NETID: STG230001
 * Date:10/01/2023
 */

import java.io.*;
import java.util.*;

public class Main {

  public static void main(String[] args) throws IOException {
    
    //accept filename from user via method
    String filename = name();
    String[][][] pilotsData = new String[20][16][2];//initialize 3D ragged array for storing values 
    String[] pilotNames = new String[20];//initialize pilot names 

    // call on instance of readFile method 
    readFile(filename, pilotsData, pilotNames);


    //create PrintWriter object to write to target file
    PrintWriter writer = new PrintWriter("pilot_areas.txt");

    //for loop that iterates as long as there are pilot names being read into the file 
    for(int i = 0; i < 20 && pilotNames[i] != null; i++){

      double area = calculateArea(pilotsData[i]);//calling calculateArea method
      writer.println(pilotNames[i] + "\t" + String.format("%.2f", area)); // formatting to two decimal places as instructed
    }

    //close writer object 
    writer.close();

  }


  //method to collect filename
  public static String name() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the name of the file containing pilot identities: ");
    return sc.nextLine();
  }
  
  //readFile method that reads from file and stores into 3D array
  public static void readFile(String filename, String[][][] pilotsData, String[] pilotNames) throws FileNotFoundException {
    
    //Scanner object that will be used for reading from inputted filename
    Scanner fileScanner = new Scanner(new File(filename));
    int pilotCount = 0;

    //while loop that runs as long as file has no null space
    while (fileScanner.hasNextLine()) {
      String line = fileScanner.nextLine();
      String[] parts = line.split(" ");//tokenizing line to acess individual values
      pilotNames[pilotCount] = parts[0];

      //for loop that stores read in file values from parts into array by x and y value
      //starts at 1 because index 0 contains pilot name
      for(int i = 1; i < parts.length; i++) {
        String[] coords = parts[i].split(",");
        pilotsData[pilotCount][i-1][0] = coords[0];//since we want coords to match original index we use i-1 in our indexing
        pilotsData[pilotCount][i-1][1] = coords[1];
      }
      
      pilotCount++;
    }
    //close scanner object
    fileScanner.close();
  }

  //method to calculate area via using shoelace method 
  public static double calculateArea(String[][] coordinates) {
    
    double area = 0.0;
    int n = coordinates.length;

    for(int i = 0; i < n-1; i++) {
      if(coordinates[i][0] != null && coordinates[i+1][0] != null) {
        area += Double.parseDouble(coordinates[i][0]) * Double.parseDouble(coordinates[i+1][1]) - Double.parseDouble(coordinates[i+1][0]) * Double.parseDouble(coordinates[i][1]);
      }
    }

    if(coordinates[n-1][0] != null && coordinates[0][0] != null) {
      area += Double.parseDouble(coordinates[n-1][0]) * Double.parseDouble(coordinates[0][1]) - Double.parseDouble(coordinates[0][0]) * Double.parseDouble(coordinates[n-1][1]);
    }

    return Math.abs(area) / 2.0;//dividing by two to get correct answer as well as use abs fucntion to ensure no negative results are returned
  }

}