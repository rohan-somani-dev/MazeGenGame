package core;
/*
 * Author: RohanSomani
 * Name: core.Grid
 * Date: 2025-12-10
 */

import config.Setup;
import entities.Player;
import utilities.CellState;
import utilities.Updater;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

//TODO: serialize, allow to save mazes to file to be opened later. https://www.baeldung.com/java-serialization
//TODO: CLEAN THIS SHIT UP
public class Grid implements Updater {
    public final int gridSize = Setup.GRID_SIZE;
    public Node[][] nodes;
    Random random;

    Node start;
    Node end;

    boolean testingInterruptedException;

    /**
     * Initialize grid.
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

    public int genMaze() {
        Stack<Node> stack = new Stack<>();
        Node curr = start;
        curr.mazeVisited = true;
        stack.push(curr);

        while (!stack.isEmpty()) {
            Node node = stack.peek();
            Node nextNode = getRandomNeighbour(node);
            if (nextNode != null) {
                nextNode.mazeVisited = true;

                node.setOverlayState(CellState.VISITED);
                nextNode.setOverlayState(CellState.TARGET);

                notifyListeners();

//                remove walls
                int deltaX = nextNode.indexX - node.indexX;
                int deltaY = nextNode.indexY - node.indexY;

                if (deltaX == 1) {
                    node.walls &= ~Setup.RIGHT;
                    nextNode.walls &= ~Setup.LEFT;
                } else if (deltaX == -1) {
                    node.walls &= ~Setup.LEFT;
                    nextNode.walls &= ~Setup.RIGHT;
                }

                if (deltaY == 1) {
                    node.walls &= ~Setup.DOWN;
                    nextNode.walls &= ~Setup.UP;
                } else if (deltaY == -1) {
                    node.walls &= ~Setup.UP;
                    nextNode.walls &= ~Setup.DOWN;
                }

                stack.push(nextNode);

                if (Setup.mazeSleepTime > 0) {
                    try {
                        if (testingInterruptedException) throw new InterruptedException("TESTING ERROR");
                        Thread.sleep(Setup.mazeSleepTime);
                    } catch (InterruptedException e) {
                        Setup.handleError(e);
                        return Setup.INTERRUPTED_ERROR; //error code
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

    void mazeGenFinished() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                nodes[y][x].clearOverlay();
            }
        }

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
        notifyListeners();
    }

    void setEnd(Node _end) {
        end = _end;
        end.setBaseState(CellState.END);

        for (Node[] row : nodes) {
            for (Node n : row) {
                n.target = end;
            }
        }
    }

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

    public Node getNodeOrNull(int i, int j) {
        if (i < 0 || j < 0 || i >= gridSize || j >= gridSize) return null;
        return nodes[j][i];
    }

    Node getRandomNeighbour(Node n) {
        ArrayList<Node> neighbours = getNeighbours(n);
        ArrayList<Node> validated = new ArrayList<>();

        for (Node neighbour : neighbours) {
            if (!(neighbour.getState() == CellState.VISITED)) validated.add(neighbour);
        }

        if (!validated.isEmpty()) {
            return validated.get(random.nextInt(validated.size()));
        }
        return null;
    }

    ArrayList<Node> getNeighbours(Node n) {
        int x = n.indexX;
        int y = n.indexY;
        ArrayList<Node> neighbours = new ArrayList<>();
        int[][] offsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] offset : offsets) {
            int currX = x + offset[0];
            int currY = y + offset[1];
            if (currX >= 0 && currX < gridSize && currY >= 0 && currY < gridSize) {
                neighbours.add(nodes[currY][currX]);
            }
        }

        return neighbours;
    }

    public void GreedyBFS() {
        start.pathVisited = true;
        PriorityQueue<Node> queue = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.getManhattanDistance(end), b.getManhattanDistance(end))
        );
        queue.add(start);

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            ArrayList<Node> neighbours = getNeighbours(curr);
            if (neighbours == null) continue;
            for (Node neighbour : neighbours) {
                if (!neighbour.pathVisited && Node.canWalk(curr, neighbour)) {
                    neighbour.parent = curr;
                    neighbour.pathVisited = true;
                    queue.add(neighbour);
                    if (neighbour.getState() == CellState.END) {
                        try {
                            Thread.sleep(Setup.sleepTimeBetweenPathRetrace);
                        } catch (InterruptedException e) {
                            Setup.handleError(e);
                        }
                        pathFound();
                        return;
                    }
                }
            }
            notifyListeners();
            try {
                Thread.sleep(Setup.pathSleepTime);
            } catch (InterruptedException e) {
                Setup.handleError(e);
            }

        }

    }

    void pathFound() {
        retrace();
    }

    void retrace() {
        Node pathNode = end;
        while (pathNode != null) {
            pathNode.onPath = true;
            CellState base = pathNode.getBaseState();
            if (base != CellState.START && base != CellState.END) pathNode.setBaseState(CellState.PATH);
            pathNode = pathNode.parent;

            try {
                Thread.sleep(Setup.pathSleepTime);
            } catch (InterruptedException _) {
                System.out.println("ERROR");
            }
        }
    }

    void initPlayer(Player player) {
        Node p = player.position;
        nodes[p.indexY][p.indexX].setOverlayState(CellState.PLAYER);

    }

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
        if (next == null) return false;
        if (!Node.canWalk(curr, next)) return false;

        curr.clearOverlay();
        next.setOverlayState(CellState.PLAYER);
        player.position = next;
        return true;
    }

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