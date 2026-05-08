package algorithm;

import utils.GameMap;

public class Heuristic{
    private GameMap game;
    public Heuristic(GameMap game) {
        this.game = game;
    }

    public int estimationCost(int row, int col, int targetAngka, int optionHeuristic){
        // 1: Heuristik SLD
        if (optionHeuristic==1){
            if (targetAngka < game.totalAngka){
                return SLD(row,col,game.waypointLocation[targetAngka][0], game.waypointLocation[targetAngka][1]);
            }
            return SLD(row,col,game.goalR, game.goalC);
        }
        // 2: Heuristik Manhattan distance Ver.1
        else if (optionHeuristic==2){
            if (targetAngka < game.totalAngka){
                return MD(row,col,game.waypointLocation[targetAngka][0], game.waypointLocation[targetAngka][1]);
            }
            return MD(row, col, game.goalR, game.goalC);
        }
        // 3: Heuristik Manhattan distance Ver.2
        else if (optionHeuristic==3){
            return MDmulti(row, col, targetAngka);
        }

        throw new IllegalArgumentException("Pilihan heuristic tidak valid: " + optionHeuristic);
    }

    // [1] Straight Line Distance (ke Target Goal/waypoint)
    private int SLD(int row, int col, int trow, int tcol){
        double x = Math.pow(col-tcol,2);
        double y = Math.pow(row-trow,2);
        int total = (int) Math.sqrt(x+y);
        return total;   
    }

    // [2] Manhattan distance (ke Target Goal/waypoint)
    private int MD(int row, int col, int trow, int tcol){
        int x = Math.abs(col-tcol);
        int y = Math.abs(row-trow);
        int total = x+y;
        return total;
    }

    // [3] Manhattan Distance (Terhadap semua waypoint dan target)
    private int MDmulti(int row, int col, int targetAngka){
        int total = 0, r=row, c=col;
        for (int i=targetAngka;i<game.totalAngka;i++){
            int rt = game.waypointLocation[i][0];
            int ct = game.waypointLocation[i][1];
            total += MD(r,c,rt,ct);
            r = rt;
            c = ct;
        }
        total += MD(r,c,game.goalR,game.goalC);
        return total;
    }

    // [4] Fun Way

}
