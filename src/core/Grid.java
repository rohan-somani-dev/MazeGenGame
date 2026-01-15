package core;

import config.Setup;
import entities.Player;
import utilities.CellState;
import utilities.SoundPlayer;
import utilities.SoundType;
import utilities.Updater;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

/**
 * A grid, holding {@link Node Nodes}, implementing depth first search for maze
 * generation and greedy best first search for pathfinding
 *
 * @author RohanSomani
 * @name core.Grid
 * @date 2025-12-10
 */
public class Grid implements Updater {
  // TODO: serialize, allow to save mazes to file to be opened later.
  // https://www.baeldung.com/java-serialization
  // TODO: outsource pathfinding and maybe maze generation?
  public int gridSize = Setup.GRID_SIZE;
  public Node[][] nodes;
  Random random;

  public Node pathStart;
  public Node start;
  public Node end;

  private ArrayList<Node> path = null;

  public Player player = null;

  /**
   * Initialize grid.
   *
   * @pre gridSize defined, Node accessible.
   * @post a 2d arr of {@link Node} objects "nodes" with the top left corner
   *       marked as the start
   */
  public Grid() {

    nodes = new Node[gridSize][gridSize];

    for (int y = 0; y < gridSize; y++) {
      for (int x = 0; x < gridSize; x++) {
        nodes[y][x] = new Node(x, y);
      }
    }

    random = new Random();

    start = nodes[0][0];
    start.setBaseState(CellState.START);

  }

  public void resetMaze() {
    for (Node n : getNodes()) {
      n.resetWalls();
      n.clearOverlay(false);
      n.mazeVisited = false;
      n.setBaseState(CellState.BACKGROUND);
    }
    Node playerPos = player.position;
    clearPathStates();
    start = playerPos;
    playerPos.setBaseState(CellState.START);
    path = null;
  }

  public void startMazeGen() {
    Thread t = new Thread(this::genMaze);
    t.start(); // no memory leak issues, thread is destroyed after genMaze()
               // is finished.
  }

  /**
   * generate a maze based on depth first search <br>
   * basically ripped pseudocode from <a href=
   * "https://en.wikipedia.org/wiki/Maze_generation_algorithm#Recursive_implementation">wikipedia</a>
   *
   * @return the error type from {@link Setup setup}
   * @pre nodes[][] array populated
   * @post nodes[][] array formatted with each node having walls where the
   *       maze said so
   */
  public int genMaze() {
    Stack<Node> stack = new Stack<>();
    Node curr = start;
    curr.mazeVisited = true;
    stack.push(curr);

    // recurse while there are still unvisited nodes.
    while (!stack.isEmpty()) {
      Node node = stack.peek();
      Node nextNode = getRandomNeighbour(node);
      if (nextNode != null) {
        nextNode.mazeVisited = true;
        notifyListeners();

        // remove walls
        int deltaX = nextNode.indexX - node.indexX;
        int deltaY = nextNode.indexY - node.indexY;

        if (deltaX == 1) {
          node.removeWall(Setup.RIGHT);
          nextNode.removeWall(Setup.LEFT);
        } else if (deltaX == -1) {
          node.removeWall(Setup.LEFT);
          nextNode.removeWall(Setup.RIGHT);
        } else if (deltaY == 1) {
          node.removeWall(Setup.DOWN);
          nextNode.removeWall(Setup.UP);
        } else if (deltaY == -1) {
          node.removeWall(Setup.UP);
          nextNode.removeWall(Setup.DOWN);
        }

        stack.push(nextNode);

        if (Setup.mazeSleepTime > 0) {
          try {
            // noinspection BusyWait
            Thread.sleep(Setup.mazeSleepTime);
          } catch (InterruptedException e) {
            Setup.handleError(e);
            return Setup.INTERRUPTED_ERROR; // error code
          }

        }

      } else {
        stack.pop();
      }

      // node.setOverlayState(CellState.VISITED);

    }
    mazeGenFinished();
    return Setup.FUNCTION_SUCCESS;
  }

  /**
   * The call for when mazeGen is finished: <br>
   * chooses an end point based on furthest distance from start with at least
   * three walls surrounding (ie it's a dead end)
   *
   * @pre nodes[][] walls set; maze is generated.
   * @post nodes are reset to their base state; end choice is set, repaint
   *       requested
   */
  void mazeGenFinished() {
    clearAllOverlays();

    Node topChoice = start;
    int maxDistance = 0;
    ArrayList<Node> deadEnds = getDeadEnds();
    for (Node n : deadEnds) {
      int currDist = start.getManhattanDistance(n);
      if (currDist > maxDistance) {
        maxDistance = currDist;
        topChoice = n;
      }
    }
    setEnd(topChoice);
    notifyListeners(); // request repaint
  }

  private void clearAllOverlays() {
    for (Node n : this.getNodes()) {
      if (n.getState() != CellState.PLAYER)
        n.clearOverlay(false);
    }
  }

  /**
   * @param _end
   *             the node to be set as end
   * @pre nodes[][] initialized. this can really be called anytime <br>
   *      further down development, if wanted to set end as always top right,
   *      deprecate {@link Grid#getDeadEnds()} and hardcode the call here.
   * @post given node set as end, all nodes point to end as their goal.
   */
  void setEnd(Node _end) {
    this.end = _end;
    end.setBaseState(CellState.END);

    for (Node[] row : nodes) {
      for (Node n : row) {
        n.target = end;
      }
    }
  }

  /**
   * Loop through the maze and get all the nodes with three walls
   *
   * @return a list of all the dead ends in the current maze.
   * @pre maze is finished, walls are set.
   * @post the return is not null, populated with all nodes in the grid with
   *       three walls, which is always guaranteed due to the nature of dfs
   */
  ArrayList<Node> getDeadEnds() {
    ArrayList<Node> out = new ArrayList<>();
    for (Node[] row : nodes) {
      for (Node n : row) {
        if (Integer.bitCount(n.walls) == 3) {
          out.add(n);
        }
      }
    }
    return out;
  }

  /**
   * return the node at the indices given, else null
   *
   * @param i
   *          the x index of needed node
   * @param j
   *          the y index of needed node
   * @return the node at x, y if exists, else null.
   * @pre nodes[][] is populated
   * @post a Node at position nodes[j][i], or null if outside bounds.
   */
  public Node getNodeOrNull(int i, int j) {
    if (i < 0 || j < 0 || i >= gridSize || j >= gridSize)
      return null;
    return nodes[j][i];
  }

  /**
   * get a random unvisited neighbour out of the four cardinal neighbours of
   * Node n, taking into account boundaries and such.
   *
   * @param n
   *          the node to find the neighbours of
   * @return the (randomly) chosen neighbour. <br>
   * @pre populated nodes[][]
   * @post returns a random unvisited neighbour of n, or null if there is no
   *       such neighbour.
   */
  Node getRandomNeighbour(Node n) {
    ArrayList<Node> neighbours = getNeighbours(n);
    ArrayList<Node> validated = new ArrayList<>();

    for (Node neighbour : neighbours) {
      if (!(neighbour.mazeVisited))
        validated.add(neighbour);
    }

    if (!validated.isEmpty()) {
      return validated.get(random.nextInt(validated.size()));
    }
    return null;
  }

  /**
   * Obtain a list of neighbours of node n.
   *
   * @param n
   *          the node to get the neighbours of
   * @return a list containing neighbours of node n.
   * @pre populated nodes[][]
   * @post A nonnull list of neighbours, only empty if n is null.
   */
  ArrayList<Node> getNeighbours(Node n) {
    int x = n.indexX;
    int y = n.indexY;
    ArrayList<Node> neighbours = new ArrayList<>();
    int[][] offsets = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    for (int[] offset : offsets) {
      int currX = x + offset[0];
      int currY = y + offset[1];
      Node neighbour = getNodeOrNull(currX, currY);
      if (neighbour != null) {
        neighbours.add(neighbour);
      }
    }

    return neighbours;
  }

  public void startPathGen() {
    pathStart = player.position;
    clearPathStates();
    path = Pathfinding.findPathBest(this, pathStart, end);
    System.out.println(path == null);
    notifyListeners();
  }

  public void clearPathStates() {
    for (Node n : getNodes()) {
      n.pathVisited = false;
      n.onPath = false;
      n.parent = null;
    }

    clearAllOverlays();
  }

  

  /**
   * initialize the player.
   *
   * @param player
   *               player to be used for movement.
   * @pre maze must be finished <br>
   *      path from start to end is recommended unless you want to be mean.
   * @post player initialized, node at player position set to player.
   */
  void initPlayer(Player player) {
    this.player = player;
  }

  /**
   * move the player in a certain direction, checks validity.
   *
   * @param player
   *               the player to be moved, can be changed to entity later on in
   *               development.
   * @param dir
   *               the direction to move the player in. doesn't have to be valid.
   * @return true if move was successful, false if not.
   * @pre player exists, and is on grid.
   * @post player moves in desired direction if possible.
   */
  public boolean movePlayer(Player.Direction dir) {
    Node curr = player.position;

    int dx = 0;
    int dy = 0;
    switch (dir) {
      case UP:
        dy = -1;
        break;
      case DOWN:
        dy = 1;
        break;
      case LEFT:
        dx = -1;
        break;
      case RIGHT:
        dx = 1;
        break;

    }
    Node next = getNodeOrNull(curr.indexX + dx, curr.indexY + dy);
    if (next == null)
      return false;
    if (!Node.canWalk(curr, next))
      return false;

    curr.clearOverlay();
    SoundPlayer.playSound(SoundType.PLAYER_WALK);
    next.setOverlayState(CellState.PLAYER);
    player.position = next;
    if (next.getBaseState() == CellState.END) {
      playerFinished();
    }
    return true;
  }

  /**
   * return the array of nodes flattened to be a 1d array.
   *
   * @return 1d array of all nodes in nodes.
   * @pre populated nodes[][]
   * @post a nonnull node array is returned.
   */
  public Node[] getNodes() {
    Node[] out = new Node[gridSize * gridSize];
    int index = 0;
    for (Node[] row : nodes) {
      for (Node n : row) {
        out[index] = n;
        index++;
      }
    }
    return out;

  }

  private void playerFinished() {
    SoundPlayer.playSound(SoundType.MAZE_FINISHED);
    resetMaze();
    startMazeGen();

  }

  public ArrayList<Node> getPath() {
    return this.path;
  }

}
