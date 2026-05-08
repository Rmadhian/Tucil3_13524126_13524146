package utils;

public class StateNode implements Comparable<StateNode> {
    public int r, c;
    public int totalCost;
    public int targetAngka;
    public boolean isGameOver;
    public String path;
    public int estimatedTotalCost; // Tambahan untuk A* dan perbandingan Priority Queue

    public StateNode(int r, int c, int totalCost, int targetAngka, boolean isGameOver, String path) {
        this.r = r; 
        this.c = c; 
        this.totalCost = totalCost;
        this.targetAngka = targetAngka; 
        this.isGameOver = isGameOver; 
        this.path = path;
        this.estimatedTotalCost = totalCost; // Secara default sama dengan g(n) untuk UCS
    }

    // Wajib ada agar Priority Queue bisa mengurutkan dari cost terkecil
    @Override
    public int compareTo(StateNode other) {
        return Integer.compare(this.estimatedTotalCost, other.estimatedTotalCost);
    }
}
