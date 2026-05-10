package algorithm;

import java.util.PriorityQueue;
import utils.GameMap;
import utils.IceSlidingLogic;
import utils.StateNode;

public class GBFS{
    private int iterationCount;

    public StateNode search(GameMap map, int optionHeuristic){
        iterationCount = 0;
        Heuristic heuristic = new Heuristic(map);
        PriorityQueue<StateNode> pq = new PriorityQueue<>();
        boolean[][][] visited = new boolean[map.rows][map.cols][map.totalAngka + 1];
        
        StateNode node = new StateNode(map.startR, map.startC, 0, 0, false, "");
        node.estimatedTotalCost = heuristic.estimationCost(node.r, node.c, node.targetAngka, optionHeuristic);
        
        pq.add(node);

        while (!pq.isEmpty()){
            StateNode current = pq.poll();

            if (visited[current.r][current.c][current.targetAngka]){
                continue;
            }

            iterationCount++;
            visited[current.r][current.c][current.targetAngka] = true;

            if (current.r == map.goalR && current.c == map.goalC && current.targetAngka == map.totalAngka) {
                return current;
            }

            for (int i=0;i<4;i++){
                StateNode newNode = IceSlidingLogic.slideMove(map.grid, map.costMatrix, current, i);

                if (newNode != null && !(newNode.isGameOver)){
                    // EstimationTotalCost pakai Heuristik 
                    newNode.estimatedTotalCost =  heuristic.estimationCost(newNode.r, newNode.c, newNode.targetAngka, optionHeuristic);
                    pq.add(newNode);
                }
                
            }

        }

        // Tidak Ketemu Solusi
        return null;
            
    }

    public int getIterationCount() {
        return iterationCount;
    }
}
