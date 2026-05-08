package utils;

public class StateNode implements Comparable<StateNode> {
    public int r, c; // [0-based]
    public int totalCost; // g(n)
    public int targetAngka;
    public boolean isGameOver;
    public String path;
    public int estimatedTotalCost; // Tambahan untuk informed search (A* & GBFS) dan perbandingan Priority Queue

    public StateNode(int r, int c, int totalCost, int targetAngka, boolean isGameOver, String path) {
        this.r = r; 
        this.c = c; 
        this.totalCost = totalCost;
        this.targetAngka = targetAngka; 
        this.isGameOver = isGameOver; 
        this.path = path;
        this.estimatedTotalCost = totalCost; // Secara default sama dengan g(n) untuk UCS
    }

    // Comparation For Priority Queue
    @Override
    public int compareTo(StateNode other) {
        return Integer.compare(this.estimatedTotalCost, other.estimatedTotalCost);
    }
}
