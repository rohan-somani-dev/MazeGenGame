package core;

import config.Setup;
import utilities.CellState;

/**
 * Node class, implementing comparable for sorting of the priority queue in
 * pathfinding.
 *
 * @author RohanSomani
 * @name core.Node
 * @date 2025-12-10
 */
public class Node implements Comparable<Node> {

  public final int indexX;
  public final int indexY;

  int walls = 0b1111; // one bit per wall
  /*
   * to remove a wall do &= ~value
   * to add a wall do + value;
   * example:
   * walls &= ~UP removes the top wall
   * walls += UP adds the top wall
   * make sure not to add a wall twice will lead to overflow.
   */

  // maze gen states
  boolean mazeVisited;
  Node target;

  // path states
  boolean pathVisited;
  boolean onPath;
  public Node parent;

  private CellState overlayState;
  private CellState baseState;

  /**
   * initialize node, setting base state to background.
   *
   * @param indexX also known as index i.
   * @param indexY also known as index j.
   */
  public Node(int indexX, int indexY) {
    this.indexX = indexX;
    this.indexY = indexY;
    this.setBaseState(CellState.BACKGROUND);
  }

  /**
   * Determine if a node can walk between two nodes.
   *
   * @param a the first node
   * @param b the second node
   * @return a boolean stating true if the nodes can be walked between.
   * @pre nonnull nodes, not necessarily neighbours.
   * @post determine if the nodes are walkable, false if there's a wall between
   *       them.
   */
  static boolean canWalk(Node a, Node b) {
    int deltaX = a.indexX - b.indexX;
    int deltaY = a.indexY - b.indexY;
    if (deltaX == -1 && (a.checkWall(Setup.RIGHT) || b.checkWall(Setup.LEFT)))
      return false;
    if (deltaX == 1 && (a.checkWall(Setup.LEFT) || b.checkWall(Setup.RIGHT)))
      return false;
    if (deltaY == -1 && (a.checkWall(Setup.DOWN) || b.checkWall(Setup.UP)))
      return false;
    return deltaY != 1 || (!a.checkWall(Setup.UP) && !b.checkWall(Setup.DOWN));
  }

  /**
   * get the base state of a node.
   *
   * @return the current baseState of the node.
   */
  public CellState getBaseState() {
    return baseState;
  }

  /**
   * set the base state of a node.
   *
   * @param newBaseState the new state to be set.
   */
  public void setBaseState(CellState newBaseState) {
    this.baseState = newBaseState;
  }

  /**
   * Set the overlay state of the node.
   *
   * @param newState the new overlay state to be set.
   */
  public void setOverlayState(CellState newState) {
    this.overlayState = newState;
  }

  public void setOverlayState(CellState newState, boolean overridePlayer) {
    if (overridePlayer)
      setOverlayState(newState);
    else if (getState() != CellState.PLAYER)
      setOverlayState(newState);
  }

  /**
   * get the current state of the node.
   *
   * @return the current highest priority state of the node. COULD BE NULL.
   * @pre either baseState or overlayState should be nonnull
   * @post the overlay state is returned if it exists, else the baseState;
   */
  public CellState getState() {
    if (overlayState != null)
      return overlayState;
    return baseState;
  }

  /**
   * get the <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">manhattan
   * distance</a> between two nodes.
   *
   * @param other the node to be compared against.
   * @return the number of orthogonal steps it takes to get from {@code this} to
   *         {@code other}.
   * @pre nodes indices are defined, other nodes indices are defined.
   * @post the manhattan distance between the two nodes are returned, always
   *       positive, possibly 0.
   */
  public int getManhattanDistance(Node other) {
    return Math.abs(other.indexX - indexX) + Math.abs(other.indexY - indexY);
  }

  @Override
  public int compareTo(Node other) {
    int ourDist = this.getManhattanDistance(target);
    int theirDist = other.getManhattanDistance(target);
    return Integer.compare(-ourDist, -theirDist);
  }

  /**
   * check if there is a wall in the specified direction.
   *
   * @param wall the wall to be checked against, from {@link config.Setup}
   * @return true if there is a wall, false if not.
   */
  public boolean checkWall(int wall) {
    return (walls & wall) != 0;
  }

  /**
   * Set the overlay state to null, allowing the base state to shine through. <br>
   * Equivalent to {@code this.setOverlayState(null)}
   */
  public void clearOverlay() {
    this.setOverlayState(null);
  }

  public void clearOverlay(boolean clearIfPlayer) {
    if (clearIfPlayer || getState() != CellState.PLAYER)
      clearOverlay();
  }

  /**
   * remove direction in direction
   *
   * @param direction the direction from {@link Setup} ie {@code Setup.UP}
   * @pre walls initialized, doesn't have to be a wall in specified direction.
   * @post wall in the direction given is removed.
   */
  public void removeWall(int direction) {
    this.walls &= ~direction;
  }

  public void resetWalls() {
    this.walls = 0b1111;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + indexX;
    result = prime * result + indexY;
    return result;
  } // my ide did this for me.
  // gotta love modern coding tools.
  // #neverGoingBack

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass()) // checks if they're both nodes.
      return false;
    Node other = (Node) obj;
    if (indexX != other.indexX || indexY != other.indexY)
      return false;
    return true;
  }

}
