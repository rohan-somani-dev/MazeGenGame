package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author RohanSomani
 * @date 2026-01-14
 * @name Pathfinding
 */
public class Pathfinding {

  public static ArrayList<Node> findPathQuick(Grid grid, Node start, Node target) {
    Set<Node> visited = new HashSet<>();
    PriorityQueue<Node> queue = new PriorityQueue<>(
        (a, b) -> Integer.compare(a.getManhattanDistance(target), b.getManhattanDistance(target)));
    queue.add(start);

    HashMap<Node, Node> parentMap = new HashMap<>();

    parentMap.put(start, null);
    visited.add(start);

    while (!queue.isEmpty()) {
      Node curr = queue.poll();
      ArrayList<Node> neighbours = grid.getNeighbours(curr);
      if (neighbours == null)
        continue;

      for (Node neighbour : neighbours) {
        if (!visited.contains(neighbour) && Node.canWalk(curr, neighbour)) {
          parentMap.put(neighbour, curr);

          visited.add(neighbour);
          queue.add(neighbour);

          if (neighbour == target) {
            System.out.println("TRYING TO RETRACE");
            ArrayList<Node> out = retrace(parentMap, target);
            System.out.println("RETRACED");
            return out;
          }

        }
      }
    }
    System.out.println("RETURNING NULL");
    return null;
  }

  public static ArrayList<Node> retrace(HashMap<Node, Node> parentMap, Node target) {
    ArrayList<Node> path = new ArrayList<>();
    Node curr = target;
    while (curr != null) {
      path.add(curr);
      curr = parentMap.get(curr);
    }
    return path;
  }

  /**
   * Find the SHORTEST path, using a*.
   * https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode
   * 
   * @param grid
   * @return
   */
  public static ArrayList<Node> findPathBest(Grid grid, Node start, Node target) {

    HashMap<Node, Node> parentMap = new HashMap<>();
    parentMap.put(start, null);

    HashMap<Node, Integer> gScores = new HashMap<>();
    HashMap<Node, Integer> fScores = new HashMap<>();
    for (Node n : grid.getNodes()) {
      gScores.put(n, Integer.MAX_VALUE);
      fScores.put(n, Integer.MAX_VALUE);
    }
    gScores.put(start, 0);
    fScores.put(start, start.getManhattanDistance(target));

    PriorityQueue<Node> openSet = new PriorityQueue<>((a, b) -> Integer.compare(
        fScores.get(a), fScores.get(b)));

    openSet.add(start);

    while (!openSet.isEmpty()) {
      Node current = openSet.poll();
      if (current == target)
        return retrace(parentMap, current);

      ArrayList<Node> neighbours = grid.getNeighbours(current);
      for (Node neighbour : neighbours) {
        if (Node.canWalk(current, neighbour)){

          int testG = gScores.get(current) + 10; //can be replaced with weight in the future
          if (testG < gScores.getOrDefault(neighbour, Integer.MAX_VALUE)) {
            parentMap.put(neighbour, current); 
            gScores.put(neighbour, testG); 
            fScores.put(neighbour, testG + neighbour.getManhattanDistance(target)); 
            if (!openSet.contains(neighbour)) openSet.add(neighbour); 
          }
        }
      }


    }
    return null;
  }

}
