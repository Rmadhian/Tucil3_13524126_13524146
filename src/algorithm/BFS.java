package algorithm;

import java.util.LinkedList;
import java.util.Queue;
import utils.GameMap;
import utils.IceSlidingLogic;
import utils.StateNode;

public class BFS {
  private int iterationCount;

  public StateNode search(GameMap map) {
    iterationCount = 0;
    Queue<StateNode> q = new LinkedList<>();
    boolean[][][] visited = new boolean[map.rows][map.cols][map.totalAngka + 1];

    StateNode startNode = new StateNode(map.startR, map.startC, 0, 0, false, "");
    q.add(startNode);

    while (!q.isEmpty()) {
      StateNode current = q.poll(); 

      if (visited[current.r][current.c][current.targetAngka]) {
        continue;
      }

      iterationCount++;
      visited[current.r][current.c][current.targetAngka] = true;

      if (current.r == map.goalR && current.c == map.goalC && current.targetAngka == map.totalAngka) {
        return current;
      }

      for (int i = 0; i < 4; i++) {
        StateNode nodeBaru = IceSlidingLogic.slideMove(map.grid, map.costMatrix, current, i);
        if (nodeBaru != null && !(nodeBaru.isGameOver)) {
          q.add(nodeBaru);
        }
      }
    }
    return null;
  }

  public int getIterationCount() {
    return iterationCount;
  }
}
