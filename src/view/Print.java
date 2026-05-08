package view;

import utils.GameMap;
import utils.IceSlidingLogic;
import utils.StateNode;

public class Print {
    private GameMap map;
    private static final int[] DR = {-1, 0, 1, 0};
    private static final int[] DC = {0, 1, 0, -1};
    
    public Print(GameMap map){
        this.map = map;
    }

    public void printSolusi(StateNode node){
        if (node == null) {
            System.out.println("Tidak ada solusi yang bisa divisualisasikan.");
            return;
        }

        System.out.println("===== VISUALISASI SOLUSI =====");
        System.out.println("Legenda: Z = posisi aktor, . = lintasan slide pada langkah saat ini");
        System.out.println();

        StateNode current = new StateNode(map.startR, map.startC, 0, 0, false, "");
        printIteration(0, '-', current, null);

        for (int i = 0; i < node.path.length(); i++) {
            char move = node.path.charAt(i);
            int dir = getDirection(move);

            if (dir == -1) {
                System.out.println("Arah tidak dikenali pada path: " + move);
                return;
            }

            boolean[][] trace = getSlideTrace(current, dir);
            StateNode next = IceSlidingLogic.slideMove(map.grid, map.costMatrix, current, dir);

            if (next == null) {
                System.out.println("Path solusi tidak valid pada langkah ke-" + (i + 1) + ".");
                return;
            }

            current = next;
            printIteration(i + 1, move, current, trace);
        }
    }

    private void printIteration(int iteration, char move, StateNode node, boolean[][] trace) {
        System.out.println("Iterasi " + iteration + formatMove(move));
        System.out.println("Path sementara : " + (node.path.isEmpty() ? "-" : node.path));
        System.out.println("Total cost     : " + node.totalCost);
        System.out.println("Target berikut : " + formatNextTarget(node));
        printBoard(node.r, node.c, trace);
        System.out.println();
    }

    private void printBoard(int actorR, int actorC, boolean[][] trace) {
        for (int r = 0; r < map.rows; r++) {
            StringBuilder row = new StringBuilder();

            for (int c = 0; c < map.cols; c++) {
                if (r == actorR && c == actorC) {
                    row.append('Z');
                } else if (trace != null && trace[r][c] && map.grid[r][c] == '*') {
                    row.append('.');
                } else if (r == map.startR && c == map.startC) {
                    row.append('*');
                } else {
                    row.append(map.grid[r][c]);
                }
            }

            System.out.println(row);
        }
    }

    private boolean[][] getSlideTrace(StateNode current, int dir) {
        boolean[][] trace = new boolean[map.rows][map.cols];
        int r = current.r;
        int c = current.c;

        while (true) {
            int nextR = r + DR[dir];
            int nextC = c + DC[dir];

            if (nextR < 0 || nextR >= map.rows || nextC < 0 || nextC >= map.cols) {
                break;
            }

            if (map.grid[nextR][nextC] == 'X') {
                break;
            }

            r = nextR;
            c = nextC;
            trace[r][c] = true;

            if (map.grid[r][c] == 'L') {
                break;
            }
        }

        return trace;
    }

    private int getDirection(char move) {
        if (move == 'U') return 0;
        if (move == 'R') return 1;
        if (move == 'D') return 2;
        if (move == 'L') return 3;
        return -1;
    }

    private String formatMove(char move) {
        if (move == '-') {
            return " (posisi awal)";
        }

        return " - gerak " + move + " (" + directionName(move) + ")";
    }

    private String directionName(char move) {
        if (move == 'U') return "atas";
        if (move == 'R') return "kanan";
        if (move == 'D') return "bawah";
        if (move == 'L') return "kiri";
        return "tidak diketahui";
    }

    private String formatNextTarget(StateNode node) {
        if (node.r == map.goalR && node.c == map.goalC && node.targetAngka >= map.totalAngka) {
            return "selesai";
        }

        if (node.targetAngka >= map.totalAngka) {
            return "O";
        }

        return String.valueOf(node.targetAngka);
    }
}
