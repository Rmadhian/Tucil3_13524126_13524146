package algorithm;

import java.util.PriorityQueue;
import utils.GameMap;
import utils.IceSlidingLogic;
import utils.StateNode;

public class UCS {
  public StateNode search(GameMap map) {
    PriorityQueue<StateNode> pq = new PriorityQueue<>();
    boolean[][][] visited = new boolean[map.rows][map.cols][map.totalAngka + 1];

    StateNode startNode = new StateNode(map.startR, map.startC, 0, 0, false, "");

    pq.add(startNode);

    while(!pq.isEmpty()) {
      StateNode current = pq.poll();

      if (visited[current.r][current.c][current.targetAngka]) {
        continue;
      }

      visited[current.r][current.c][current.targetAngka] = true;

      if (current.r == map.goalR && current.c == map.goalC && current.targetAngka == map.totalAngka) {
        return current;
      }

      for (int i = 0; i < 4; i++) {
        StateNode nodeBaru = IceSlidingLogic.slideMove(map.grid, map.costMatrix, current, i);
        if (nodeBaru != null && !(nodeBaru.isGameOver)) {
          pq.add(nodeBaru);
        }
      }
    }
    return null;
  }
}
