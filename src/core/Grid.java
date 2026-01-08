package core;

import config.Setup;
import entities.Player;
import utilities.CellState;
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
	public final int gridSize = Setup.GRID_SIZE;
	public final Node[][] nodes;
	final Random random;

	final Node START;
	private Node end;

	public Node pathStart;

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

		START = nodes[0][0];
		START.setBaseState(CellState.START);

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
		Node curr = START;
		curr.mazeVisited = true;
		stack.push(curr);

		// recurse while there are still unvisited nodes.
		while (!stack.isEmpty()) {
			Node node = stack.peek();
			Node nextNode = getRandomNeighbour(node);
			if (nextNode != null) {
				nextNode.mazeVisited = true;

				node.setOverlayState(CellState.VISITED);
				nextNode.setOverlayState(CellState.TARGET);

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
						Thread.sleep(Setup.mazeSleepTime);
					} catch (InterruptedException e) {
						Setup.handleError(e);
						return Setup.INTERRUPTED_ERROR; // error code
					}

				}

			} else {
				stack.pop();
			}

			node.setOverlayState(CellState.VISITED);

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

		Node topChoice = START;
		int maxDistance = 0;
		ArrayList<Node> deadEnds = getDeadEnds();
		for (Node n : deadEnds) {
			int currDist = START.getManhattanDistance(n);
			if (currDist > maxDistance) {
				maxDistance = currDist;
				topChoice = n;
			}
		}
		setEnd(topChoice);
		notifyListeners(); // request repaint
	}

	private void clearAllOverlays() {
		for (int y = 0; y < gridSize; y++) {
			for (int x = 0; x < gridSize; x++) {
				nodes[y][x].clearOverlay();
			}
		}
	}

	/**
	 * @param _end
	 *            the node to be set as end
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
	 *            the x index of needed node
	 * @param j
	 *            the y index of needed node
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
	 *            the node to find the neighbours of
	 * @return the (randomly) chosen neighbour. <br>
	 * @pre populated nodes[][]
	 * @post returns a random unvisited neighbour of n, or null if there is no
	 *       such neighbour.
	 */
	Node getRandomNeighbour(Node n) {
		ArrayList<Node> neighbours = getNeighbours(n);
		ArrayList<Node> validated = new ArrayList<>();

		for (Node neighbour : neighbours) {
			if (!(neighbour.getState() == CellState.VISITED))
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
	 *            the node to get the neighbours of
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

	/**
	 * Pathfind using GreedyBFS; notify when finished.
	 *
	 * @pre established maze; must be path from start to end.
	 * @post every node on the shortest path gets a parent assigned to them,
	 *       pointing to the previous node in the path
	 */
	public void GreedyBFS() {
		clearPathStates();
		pathStart.pathVisited = true;
		PriorityQueue<Node> queue = new PriorityQueue<>(
				(a, b) -> Integer.compare(a.getManhattanDistance(end), b.getManhattanDistance(end)));
		queue.add(pathStart);
		int i = 0;

		while (!queue.isEmpty()) {
			Node curr = queue.poll();
			ArrayList<Node> neighbours = getNeighbours(curr);
			if (neighbours == null)
				continue;
			for (Node neighbour : neighbours) {
				if (!neighbour.pathVisited && Node.canWalk(curr, neighbour)) {
					// set the parent to the current node, so that no matter
					// whether it's on the path the parent can be found
					neighbour.parent = curr;

					neighbour.pathVisited = true;
					queue.add(neighbour);
					neighbour.setOverlayState(CellState.TARGET);
					if (neighbour.getBaseState() == CellState.END) {
						// the first time we find the end it's guaranteed to be
						// the shortest path since we always move towards it

						pathFound();
						return;
					}
				}

			}
			// noinspection ConstantValue
			if (i++ % Setup.STEPS_PER_REDRAW != 0)
				continue;
			try {
				Thread.sleep(Setup.pathSleepTime);
			} catch (InterruptedException e) {
				Setup.handleError(e);
			}
			notifyListeners();
		}

	}

	public void clearPathStates() {
		for (Node[] row : nodes) {
			for (Node n : row) {
				n.pathVisited = false;
				CellState base = n.getBaseState();
				if (base != CellState.START && base != CellState.END) {
					n.setBaseState(CellState.BACKGROUND);

				}
				n.clearOverlay();
			}
		}
	}

	/**
	 * To be called when the path is found.
	 *
	 * @pre nodes have parents assigned to them based on the path
	 * @post nodes gain property {@link Node#onPath} if on the path.
	 */
	void pathFound() {
		try {
			Thread.sleep(Setup.sleepTimeBetweenPathRetrace); // sleep before
																// retrace so
																// that there's
																// a pause.
		} catch (InterruptedException e) {
			Setup.handleError(e);
		}

		clearAllOverlays();
		Node pathNode = end;
		while (pathNode != null) {
			pathNode.onPath = true;
			CellState base = pathNode.getBaseState();
			if (base != CellState.START && base != CellState.END)
				pathNode.setBaseState(CellState.PATH);
			pathNode = pathNode.parent;

			try {
				Thread.sleep(Setup.pathSleepTime);
			} catch (InterruptedException e) {
				Setup.handleError(e);
			}
			notifyListeners();
		}
	}

	/**
	 * initialize the player.
	 *
	 * @param player
	 *            player to be used for movement.
	 * @pre maze must be finished <br>
	 *      path from start to end is recommended unless you want to be mean.
	 * @post player initialized, node at player position set to player.
	 */
	void initPlayer(Player player) {
		Node p = player.position;
		nodes[p.indexY][p.indexX].setOverlayState(CellState.PLAYER);

	}

	/**
	 * move the player in a certain direction, checks validity.
	 *
	 * @param player
	 *            the player to be moved, can be changed to entity later on in
	 *            development.
	 * @param dir
	 *            the direction to move the player in. doesn't have to be valid.
	 * @return true if move was successful, false if not.
	 * @pre player exists, and is on grid.
	 * @post player moves in desired direction if possible.
	 */
	public boolean movePlayer(Player player, Player.Direction dir) {
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
		next.setOverlayState(CellState.PLAYER);
		player.position = next;
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

}