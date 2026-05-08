package algorithm;

import java.util.Stack;
import utils.GameMap;
import utils.IceSlidingLogic;
import utils.StateNode;

public class DFS {
  public StateNode search(GameMap map) {
    Stack<StateNode> stack = new Stack<>();
    boolean[][][] visited = new boolean[map.rows][map.cols][map.totalAngka + 1];

    StateNode startNode = new StateNode(map.startR, map.startC, 0, 0, false, "");
    stack.push(startNode);

    while (!stack.isEmpty()) {
      StateNode current = stack.pop(); 

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
          stack.push(nodeBaru);
        }
      }
    }
    return null;
  }
}
