import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/*
 *@author:Krishna Pokharel
 */

public class SearchRomania {

	/**
	 * @param args
	 */
   //Initial Inputs
	public static String targetCity, sourceCity, searchType;
	public static Node<String> Source; 
	public static Node<String> Target = new Node<String>(targetCity);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Command line arguments
		searchType = args[0];
		sourceCity = args[1];
		targetCity = args[2];

		Node<String> startCity = initiateGraph();
		if (searchType.equals("BFS"))
			BreadthFirstSearch(startCity);
		else
			DepthFirstSearch(startCity);

	}

	private static void BreadthFirstSearch(Node<String> startCity) {
		// TODO Auto-generated method stub
		if (null == startCity)
			return;
		System.out.println("The BFS search from " + sourceCity + " to "
				+ targetCity + ":");
		Queue<Node<String>> BFSQueue = new LinkedList<Node<String>>();
		BFSQueue.add(startCity);
		while (!BFSQueue.isEmpty()) {
			Node<String> currentCity = BFSQueue.poll();
			currentCity.visited = true;
			System.out.println(currentCity.cityName);
			if (currentCity.cityName.equals(targetCity))
				return;
			for (Node<String> adjacentCity : currentCity.adjacentCities) {
				if (!adjacentCity.visited == true && !adjacentCity.waiting) {
					BFSQueue.add(adjacentCity);
					adjacentCity.waiting = true;
				}
			}
		}
	}

	public static void DepthFirstSearch(Node<String> startCity) {
		if (null == startCity)
			return;
		System.out.println("The DFS search from " + sourceCity + " to "
				+ targetCity + ":");
		Stack<Node<String>> dfsStack = new Stack<Node<String>>();
		dfsStack.push(startCity);
		while (!dfsStack.isEmpty()) {
			Node<String> currentCity = dfsStack.pop();
			currentCity.visitedDFS = true;
			System.out.println(currentCity.cityName);
			if (currentCity.cityName.equals(targetCity))
				return;
			Collections.reverse(currentCity.adjacentCities);
			for (Node<String> adjacentCity : currentCity.adjacentCities) {
				if (!adjacentCity.visitedDFS == true)
					dfsStack.push(adjacentCity);
			}
		}
	}

	public static Node<String> initiateGraph() {
		Node<String> oradea = new Node<String>("oradea");
		Node<String> zerind = new Node<String>("zerind");
		Node<String> arad = new Node<String>("arad");
		Node<String> sibiu = new Node<String>("sibiu");
		Node<String> timisoara = new Node<String>("timisoara");
		Node<String> lugoj = new Node<String>("lugoj");
		Node<String> mehadia = new Node<String>("mehadia");
		Node<String> dobreta = new Node<String>("dobreta");
		Node<String> fagaras = new Node<String>("fagaras");
		Node<String> rimnicu_vilcea = new Node<String>("rimnicu vilcea");
		Node<String> craiova = new Node<String>("craiova");
		Node<String> pitesti = new Node<String>("pitesti");
		Node<String> giurgiu = new Node<String>("giurgiu");
		Node<String> bucharest = new Node<String>("bucharest");
		Node<String> urziceni = new Node<String>("urziceni");
		Node<String> hirsova = new Node<String>("hirsova");
		Node<String> etoric = new Node<String>("etoric");
		Node<String> vaslui = new Node<String>("vaslui");
		Node<String> iasi = new Node<String>("iasi");
		Node<String> nearnt = new Node<String>("nearnt");

		if (sourceCity.equals("oradea"))
			Source = oradea;
		oradea.adjacentCities.add(sibiu);
		oradea.adjacentCities.add(zerind);

		if (sourceCity.equals("zerind"))
			Source = zerind;

		zerind.adjacentCities.add(arad);
		zerind.adjacentCities.add(oradea);

		if (sourceCity.equals("arad"))
			Source = arad;
		arad.adjacentCities.add(sibiu);
		arad.adjacentCities.add(timisoara);
		arad.adjacentCities.add(zerind);

		if (sourceCity.equals("timisoara"))
			Source = timisoara;
		timisoara.adjacentCities.add(arad);
		timisoara.adjacentCities.add(lugoj);

		if (sourceCity.equals("sibiu"))
			Source = sibiu;
		sibiu.adjacentCities.add(arad);
		sibiu.adjacentCities.add(fagaras);
		sibiu.adjacentCities.add(oradea);
		sibiu.adjacentCities.add(rimnicu_vilcea);

		if (sourceCity.equals("lugoj"))
			Source = lugoj;
		lugoj.adjacentCities.add(mehadia);
		lugoj.adjacentCities.add(timisoara);

		if (sourceCity.equals("mehadia"))
			Source = mehadia;
		mehadia.adjacentCities.add(dobreta);
		mehadia.adjacentCities.add(lugoj);

		if (sourceCity.equals("dobreta"))
			Source = dobreta;
		dobreta.adjacentCities.add(craiova);
		dobreta.adjacentCities.add(mehadia);

		if (sourceCity.equals("fagaras"))
			Source = fagaras;
		fagaras.adjacentCities.add(bucharest);
		fagaras.adjacentCities.add(sibiu);

		if (sourceCity.equals("rimnicu_vilcea"))
			Source = rimnicu_vilcea;
		rimnicu_vilcea.adjacentCities.add(craiova);
		rimnicu_vilcea.adjacentCities.add(pitesti);
		rimnicu_vilcea.adjacentCities.add(sibiu);

		if (sourceCity.equals("craiova"))
			Source = craiova;
		craiova.adjacentCities.add(dobreta);
		craiova.adjacentCities.add(pitesti);
		craiova.adjacentCities.add(rimnicu_vilcea);

		if (sourceCity.equals("pitesti"))
			Source = pitesti;
		pitesti.adjacentCities.add(craiova);
		pitesti.adjacentCities.add(bucharest);
		pitesti.adjacentCities.add(rimnicu_vilcea);

		if (sourceCity.equals("bucharest"))
			Source = bucharest;
		bucharest.adjacentCities.add(fagaras);
		bucharest.adjacentCities.add(giurgiu);
		bucharest.adjacentCities.add(pitesti);
		bucharest.adjacentCities.add(urziceni);

		if (sourceCity.equals("giurgiu"))
			Source = giurgiu;
		giurgiu.adjacentCities.add(bucharest);

		if (sourceCity.equals("urziceni"))
			Source = urziceni;
		urziceni.adjacentCities.add(bucharest);
		urziceni.adjacentCities.add(hirsova);
		urziceni.adjacentCities.add(vaslui);

		if (sourceCity.equals("hirsova"))
			Source = hirsova;
		hirsova.adjacentCities.add(etoric);
		hirsova.adjacentCities.add(urziceni);

		if (sourceCity.equals("eforie"))
			Source = etoric;
		etoric.adjacentCities.add(hirsova);

		if (sourceCity.equals("vaslui"))
			Source = vaslui;
		vaslui.adjacentCities.add(iasi);
		vaslui.adjacentCities.add(urziceni);

		if (sourceCity.equals("iasi"))
			Source = iasi;
		iasi.adjacentCities.add(nearnt);
		iasi.adjacentCities.add(vaslui);

		if (sourceCity.equals("nearnt"))
			Source = nearnt;
		nearnt.adjacentCities.add(iasi);

		return Source;
	}

}

class Node<T> {
	T cityName;
	ArrayList<Node<T>> adjacentCities = null;
	Boolean visited = false;
	Boolean visitedDFS = false;
	Boolean waiting = false;

	Node(T name) {
		cityName = name;
		adjacentCities = new ArrayList<Node<T>>();

	}
}
