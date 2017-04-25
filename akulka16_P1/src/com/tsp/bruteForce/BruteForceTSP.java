package com.tsp.bruteForce;

/*
 * Author: 				Abhijeet S. Kulkarni
 * 						CS575_Fall 2016
 * Problem Statement: 	Solve traveling salesman problem using brute force search
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BruteForceTSP {

	/*
	 * shortestPath:		shortest path to visit all cities exactly once.
	 * shortestDistance:	variable will store distance to traverse shortestPath.
	 * longestDistance:		variable will contain longest distance to visit all cities.
	 *  
	 */
	public static int shortestDistance;
	public static int longestDistance;
	public static ArrayList<Integer> shortestPath;
	
	/*
	 * Reads input file; solves the traveling salesman problem using brute force search; writes the output file
	 * 
	 * @param	pathName	path of input file.
	 * */
	public static void startTSP(String pathName) {
		int[][] distanceMatrix = null;
		File file = new File("output.txt");
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
			String citiesNumber = "";
			int problemNo = 0;
			while((citiesNumber = bufferedReader.readLine()) != null){
				shortestDistance = Integer.MAX_VALUE;
				longestDistance = Integer.MIN_VALUE;
				shortestPath = null;
				int totalCities = Integer.parseInt(citiesNumber);
				distanceMatrix = new int[totalCities][totalCities];
				for(int i=0;i<totalCities*totalCities;i++){
					String[] dist = bufferedReader.readLine().split("\\s+");
					distanceMatrix[Integer.parseInt(dist[0])][Integer.parseInt(dist[1])] = Integer.parseInt(dist[2]);
				}
				long startTime = System.currentTimeMillis();
				solveTSP(distanceMatrix);
				long endTime = System.currentTimeMillis();
				if(!file.exists()){
					file.createNewFile();
				}
				bufferedWriter.write(++problemNo + " " + totalCities + " " + shortestDistance + " " + longestDistance + " " + (endTime-startTime));
				bufferedWriter.newLine();
				for(int city : shortestPath){
					bufferedWriter.write(Integer.toString(city));
					bufferedWriter.newLine();
				}
			}
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch(FileNotFoundException expception) {

			System.out.println("File given does not exists.");
			expception.printStackTrace();
			System.exit(0);
		}
		catch(IOException ioException){
			System.out.println("Error occured during Input Output operation.");
			ioException.printStackTrace();
			System.exit(0);
		}
	}
	
	/*
	 * Solves the traveling salesman problem for given input
	 * 
	 * @param	distanceMatrix	stores the distance between two cities.
	 * */
	private static void solveTSP(int[][] distanceMatrix) {

		int totalCities = distanceMatrix.length;
		ArrayList<Integer> cities = new ArrayList<Integer>();
		for(int i=0;i<totalCities;i++){
			cities.add(i);
		}
		int startCity = cities.get(0);
		int currentDistance = 0;
		bruteForceSearch(distanceMatrix, cities, startCity, currentDistance);
	}
	/*
	 * Applies brute force algorithm to generate all possible permutations of journey.
	 * 
	 * @param	distanceMatrix	stores the distance between two cities.
	 * @param	cities			possible path of a journey.
	 * @param	startCity		starting city of the journey.
	 * @param	currentDistance	distance covered in the journey.
	 * */
	
	private static void bruteForceSearch(int[][] distanceMatrix, ArrayList<Integer> cities, int startCity, int currentDistance) {

		if(startCity < cities.size()-1){
			for(int i=startCity; i < cities.size(); i++){
				int tempCity = cities.get(i);
				cities.set(i, cities.get(startCity));
				cities.set(startCity, tempCity);
				currentDistance = computeDistance(cities,distanceMatrix);
				bruteForceSearch(distanceMatrix, cities, startCity+1, currentDistance);
				tempCity = cities.get(i);
				cities.set(i, cities.get(startCity));
				cities.set(startCity, tempCity);
			}
		}
		else{
			if(shortestDistance > currentDistance){
				shortestDistance = currentDistance;
				shortestPath = new ArrayList<Integer>(cities);
			}
			if(longestDistance < currentDistance){
				longestDistance = currentDistance;
			}
		}
	}

	/*
	 * Computes the distance covered during the journey
	 * 
	 * @param	cities			possible path of the journey.
	 * @param	distanceMatrix	stores distance between two cities.
	 * 
	 * */
	private static int computeDistance(ArrayList<Integer> cities, int[][] distanceMatrix) {
		int distance = 0;
		for(int i=0;i<cities.size()-1;i++){
			distance = distance + distanceMatrix[cities.get(i)][cities.get(i+1)];
		}
		distance = distance + distanceMatrix[cities.get(cities.size()-1)][cities.get(0)];
		return distance;
	}
	
	public static void main(String[] args) {

		if(args.length == 1){
			String pathName = args[0];
			startTSP(pathName);
		}
		else
		{
			System.out.println("Proper Arguments Required");
			System.exit(0);
		}
	}
}