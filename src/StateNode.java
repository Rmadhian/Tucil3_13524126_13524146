package src;
public class StateNode {
    public int r, c;
    public int totalCost;
    public int targetAngka;
    public boolean isGameOver;
    public String path;

    public StateNode(int r, int c, int totalCost, int targetAngka, boolean isGameOver, String path) {
        this.r = r; this.c = c; this.totalCost = totalCost;
        this.targetAngka = targetAngka; this.isGameOver = isGameOver; this.path = path;
    }
}