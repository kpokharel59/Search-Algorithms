import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

/* @author:Krishna Pokharel
 */

public class SearchUSA {

	/**
	 * @param args
	 *            targetCity- Destination City startCity- Source City
	 */

	public static String targetCity, sourceCity, searchType;
	public static Node<String> startCity;
	static ArrayList<String> expansion;
	static Node[] Cities = new Node[112];
	// Priority Queue to store paths
	static PriorityQueue<Path> BFSQueue = new PriorityQueue(10,
			new Comparator<Path>() {
				@Override
				public int compare(Path arg0, Path arg1) {
					// prioritized based on cost
					return Double.compare(arg0.pathCost + arg0.hCost,
							arg1.pathCost + arg1.hCost);
				}
			});

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// three command line argument
		searchType = args[0];
		sourceCity = args[1];
		targetCity = args[2];

		// File Input
		String nextLine;
		String[] info;
		File file = new File("usroads.txt");
		BufferedReader inFile = null;
		try {
			inFile = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = 0, k, l;

		try {
			while ((nextLine = inFile.readLine()) != null) {
				info = nextLine.split(",");
				if (count < 112) {
					Cities[count] = new Node<String>(info[0].trim());
					Cities[count].cityName = info[0].trim();
					Cities[count].latitude = Double.parseDouble(info[1].trim());
					Cities[count].longitude = Double
							.parseDouble(info[2].trim());
					if (Cities[count].cityName.equals(sourceCity))
						startCity = Cities[count];
					count = count + 1;
				} else {
					for (l = 0; l < 112; l++) {
						if (Cities[l].cityName.equals(info[0].trim())) {
							break;
						}
					}
					for (k = 0; k < 112; k++) {

						double cost = Double.parseDouble(info[2].trim());
						if (Cities[k].cityName.equals(info[1].trim())) {
							Cities[k].adjacentCities.put(Cities[l], cost);
							Cities[l].adjacentCities.put(Cities[k], cost);
							break;
						}
					}

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Path solutionPath;
		expansion = new ArrayList<String>();
		if (searchType.equals("greedy"))
			solutionPath = Greedy(startCity);
		else if (searchType.equals("astar"))
			solutionPath = Astar(startCity);
		else
			solutionPath = Uniform(startCity);
		int i;

		System.out.println("Expanded Nodes:");
		for (i = 0; i < expansion.size(); i++) {
			System.out.print(expansion.get(i) + ",");
		}
		System.out.println();
		System.out.println("Number of nodes expanded=" + expansion.size());
		System.out.println("Solution path:");
		for (i = 0; i < solutionPath.path.size(); i++) {
			System.out.print(solutionPath.path.get(i).cityName + ",");
		}
		System.out.println();
		System.out.println("Number of nodes in solution path="
				+ solutionPath.path.size());
		System.out
				.println("Total Distance from Source to Destination in solution path="
						+ (solutionPath.pathCost + solutionPath.gCost));
	}

	private static double heuristicCost(Node city1, Node city2) {
		// TODO Auto-generated method stub
		Double Lat1, Lat2, Long1, Long2;
		Lat1 = city1.latitude;
		Lat2 = city2.latitude;
		Long1 = city1.longitude;
		Long2 = city2.longitude;
		return (Math.sqrt(Math.pow(69.5 * (Lat1 - Lat2), 2)
				+ Math.pow(69.5 * Math.cos((Lat1 + Lat2) / 360 * Math.PI)
						* (Long1 - Long2), 2)));
	}

	private static Path Uniform(Node<String> startCity) {
		// TODO Auto-generated method stub
		if (null == startCity)
			return null;
		System.out.println("The Uniform search from " + sourceCity + " to "
				+ targetCity + ":");

		Path startNode = new Path();
		startNode.path.add(startCity);
		BFSQueue.add(startNode);
		Path CP;
		ArrayList<Node<String>> currentPath;
		while (!BFSQueue.isEmpty()) {
			CP = BFSQueue.poll();
			currentPath = CP.path;
			Node<String> currentCity = currentPath.get(currentPath.size() - 1);
			currentCity.visited = true;

			expansion.add(currentCity.cityName);
			if (currentCity.cityName.equals(targetCity))
				return CP;

			Iterator<Node<String>> iterator2 = currentCity.adjacentCities
					.keySet().iterator();
			Node<String> adjacentCity;

			while (iterator2.hasNext()) {
				Node<String> key = iterator2.next();
				adjacentCity = key;
				if (!adjacentCity.visited == true) {
					Path newPath = new Path();
					for (int i = 0; i < currentPath.size(); i++) {
						newPath.path.add(currentPath.get(i));
					}
					newPath.path.add(adjacentCity);
					newPath.pathCost = CP.pathCost
							+ (currentCity.adjacentCities.get(key));

					BFSQueue.add(newPath);
				}
			}
			removeDuplicates();
		}
		return null;
	}

	// Greedy
	private static Path Greedy(Node<String> startCity) {
		// TODO Auto-generated method stub
		if (null == startCity)
			return null;
		System.out.println("The Greedy search from " + sourceCity + " to "
				+ targetCity + ":");
		int l;
		Path startNode = new Path();
		for (l = 0; l < 112; l++) {
			if (Cities[l].cityName.equals(targetCity))
				break;
		}
		startNode.path.add(startCity);
		startNode.hCost = heuristicCost(
				startNode.path.get(startNode.path.size() - 1), Cities[l]);

		BFSQueue.add(startNode);
		Path CP;
		ArrayList<Node<String>> currentPath;
		while (!BFSQueue.isEmpty()) {
			CP = BFSQueue.poll();
			currentPath = CP.path;
			Node<String> currentCity = currentPath.get(currentPath.size() - 1);
			currentCity.visited = true;
			expansion.add(currentCity.cityName);
			if (currentCity.cityName.equals(targetCity))
				return CP;

			Iterator<Node<String>> iterator2 = currentCity.adjacentCities
					.keySet().iterator();
			Node<String> adjacentCity;
			while (iterator2.hasNext()) {
				Node<String> key = iterator2.next();
				adjacentCity = key;
				if (!adjacentCity.visited == true) {
					Path newPath = new Path();
					for (int i = 0; i < currentPath.size(); i++) {
						newPath.path.add(currentPath.get(i));
					}
					newPath.path.add(adjacentCity);
					newPath.hCost = heuristicCost(
							newPath.path.get(newPath.path.size() - 1),
							Cities[l]);
					newPath.gCost = CP.gCost
							+ (currentCity.adjacentCities.get(key));
					BFSQueue.add(newPath);
				}
			}
		}
		return null;
	}

	// A*
	private static Path Astar(Node<String> startCity) {
		// TODO Auto-generated method stub
		if (null == startCity)
			return null;
		int l;
		System.out.println("The Astar search from " + sourceCity + " to "
				+ targetCity + ":");
		for (l = 0; l < 112; l++) {
			if (Cities[l].cityName.equals(targetCity))
				break;
		}

		Path startNode = new Path();
		startNode.path.add(startCity);
		startNode.hCost = heuristicCost(
				startNode.path.get(startNode.path.size() - 1), Cities[l]);
		BFSQueue.add(startNode);
		Path CP;
		ArrayList<Node<String>> currentPath;
		while (!BFSQueue.isEmpty()) {
			CP = BFSQueue.poll();
			currentPath = CP.path;
			Node<String> currentCity = currentPath.get(currentPath.size() - 1);
			currentCity.visited = true;
			expansion.add(currentCity.cityName);
			if (currentCity.cityName.equals(targetCity))
				return CP;

			Iterator<Node<String>> iterator2 = currentCity.adjacentCities
					.keySet().iterator();
			Node<String> adjacentCity;
			while (iterator2.hasNext()) {
				Node<String> key = iterator2.next();
				adjacentCity = key;
				if (!adjacentCity.visited == true) {
					Path newPath = new Path();
					for (int i = 0; i < currentPath.size(); i++) {
						newPath.path.add(currentPath.get(i));
					}
					newPath.path.add(adjacentCity);
					newPath.hCost = heuristicCost(adjacentCity, Cities[l]);
					newPath.pathCost = CP.pathCost
							+ (currentCity.adjacentCities.get(key));

					BFSQueue.add(newPath);
				}
			}
			removeDuplicates();
		}
		return null;
	}

	private static void removeDuplicates() {
		Path cPath, cPathTemp;
		Boolean duplicate;
		Node<String> firstNode, lastNode, firstTemp, lastTemp;
		Iterator<Path> iter1 = BFSQueue.iterator();
		ArrayList<Node<String>> currentPath, currentPathTemp;
		ArrayList<Path> duplicateRemoved = new ArrayList<Path>();

		while (iter1.hasNext()) {
			duplicate = false;
			cPath = iter1.next();
			currentPath = cPath.path;
			firstNode = currentPath.get(0);
			lastNode = currentPath.get(currentPath.size() - 1);
			Iterator<Path> iter2 = BFSQueue.iterator();
			while (iter2.hasNext()) {
				cPathTemp = iter2.next();
				currentPathTemp = cPathTemp.path;
				firstTemp = currentPathTemp.get(0);
				lastTemp = currentPathTemp.get(currentPathTemp.size() - 1);
				if (firstTemp.equals(firstNode) && lastTemp.equals(lastNode)) {
					if ((cPath.pathCost + cPath.hCost) > (cPathTemp.pathCost + cPathTemp.hCost)) {
						duplicate = true;
					}
				}
			}
			if (!duplicate)
				duplicateRemoved.add(cPath);
		}
		while (!BFSQueue.isEmpty()) {
			BFSQueue.poll();
		}
		for (int i = 0; i < duplicateRemoved.size(); i++) {
			BFSQueue.add(duplicateRemoved.get(i));
		}

	}

}

class Path {
	ArrayList<Node<String>> path = null;
	double pathCost = 0;
	double hCost = 0;
	double gCost = 0;

	Path() {
		path = new ArrayList<Node<String>>();
	}
}

class Node<T> {
	T cityName;
	Double latitude;
	Double longitude;
	HashMap<Node<T>, Double> adjacentCities = null; 
	Boolean visited = false;

	Node(T name) {
		cityName = name;
		adjacentCities = new HashMap<Node<T>, Double>();

	}
}
