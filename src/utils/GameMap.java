package utils;

public class GameMap {
    public int rows;
    public int cols;
    public char[][] grid;       // Menyimpan letak dinding 'X', lava 'L', tujuan 'O', dan angka
    public int[][] costMatrix;  // Menyimpan cost untuk melewati setiap tile
    public int totalAngka;

    // Lokasi Waypoint
    public int[][] waypointLocation; 
    // Menyimpan posisi awal (Z) dan tujuan (O) agar tidak perlu dilooping berulang kali
    public int startR, startC;
    public int goalR, goalC;

    public GameMap(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        this.costMatrix = new int[rows][cols];
        this.waypointLocation = new int[10][];
    }
}
