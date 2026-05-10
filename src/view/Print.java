package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

        List<Frame> frames = buildFrames(node);
        for (Frame frame : frames) {
            printFrame(frame);
        }
    }

    public void playbackSolusi(StateNode node, Scanner scanner) {
        if (node == null) {
            System.out.println("Tidak ada solusi yang bisa diplayback.");
            return;
        }

        List<Frame> frames = buildFrames(node);
        if (frames.isEmpty()) {
            System.out.println("Tidak ada frame playback yang bisa ditampilkan.");
            return;
        }

        int currentFrame = 0;
        String message = "";

        while (true) {
            clearScreen();
            System.out.println("===== PLAYBACK SOLUSI =====");
            System.out.println("Frame " + currentFrame + " dari " + (frames.size() - 1));
            System.out.println();
            printFrame(frames.get(currentFrame));
            System.out.println("Kontrol: [Enter/n/d] maju | [p/a] mundur | [j <step>] lompat | [q] keluar");
            if (!message.isEmpty()) {
                System.out.println(message);
            }
            System.out.print(">> ");

            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("q")) {
                System.out.println("Playback selesai.");
                return;
            } else if (command.isEmpty() || command.equalsIgnoreCase("n") || command.equalsIgnoreCase("d")) {
                if (currentFrame < frames.size() - 1) {
                    currentFrame++;
                    message = "";
                } else {
                    message = "Sudah berada di frame terakhir.";
                }
            } else if (command.equalsIgnoreCase("p") || command.equalsIgnoreCase("a")) {
                if (currentFrame > 0) {
                    currentFrame--;
                    message = "";
                } else {
                    message = "Sudah berada di frame pertama.";
                }
            } else if (command.startsWith("j")) {
                int targetFrame = parseJumpTarget(command);
                if (targetFrame < 0 || targetFrame >= frames.size()) {
                    message = "Step tidak valid. Masukkan angka 0 sampai " + (frames.size() - 1) + ".";
                } else {
                    currentFrame = targetFrame;
                    message = "";
                }
            } else {
                message = "Perintah tidak dikenal.";
            }
        }
    }

    private List<Frame> buildFrames(StateNode node) {
        List<Frame> frames = new ArrayList<>();
        StateNode current = new StateNode(map.startR, map.startC, 0, 0, false, "");
        frames.add(new Frame(0, '-', current, null));

        for (int i = 0; i < node.path.length(); i++) {
            char move = node.path.charAt(i);
            int dir = getDirection(move);

            if (dir == -1) {
                System.out.println("Arah tidak dikenali pada path: " + move);
                return frames;
            }

            boolean[][] trace = getSlideTrace(current, dir);
            StateNode next = IceSlidingLogic.slideMove(map.grid, map.costMatrix, current, dir);

            if (next == null) {
                System.out.println("Path solusi tidak valid pada langkah ke-" + (i + 1) + ".");
                return frames;
            }

            current = next;
            frames.add(new Frame(i + 1, move, current, trace));
        }

        return frames;
    }

    private void printFrame(Frame frame) {
        System.out.println("Step " + frame.iteration + formatMove(frame.move));
        System.out.println("Path sementara : " + (frame.node.path.isEmpty() ? "-" : frame.node.path));
        System.out.println("Total cost     : " + frame.node.totalCost);
        System.out.println("Target berikut : " + formatNextTarget(frame.node));
        printBoard(frame.node.r, frame.node.c, frame.trace);
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

    private int parseJumpTarget(String command) {
        String numberText = command.substring(1).trim();

        if (numberText.isEmpty()) {
            return -1;
        }

        try {
            return Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static class Frame {
        int iteration;
        char move;
        StateNode node;
        boolean[][] trace;

        Frame(int iteration, char move, StateNode node, boolean[][] trace) {
            this.iteration = iteration;
            this.move = move;
            this.node = node;
            this.trace = trace;
        }
    }
}
