import algorithm.Astar;
import algorithm.BFS;
import algorithm.DFS;
import algorithm.GBFS;
import algorithm.UCS;
import utils.GameMap;
import utils.MapParser;
import utils.StateNode;
import view.Print;

public class Main {
    private static final java.util.Scanner SCANNER = new java.util.Scanner(System.in);

    public static void main(String[] args) {
        // 1. Tentukan lokasi file test 
        String FilePath = getLine("Masukkan Path File anda: "); 
        System.out.println("Memulai program... \n Membaca file: " + FilePath);

        // 2. Parse Map
        GameMap map = MapParser.parseMap(FilePath);

        // Validasi kalau file gagal dibaca
        if (map == null) {
            System.out.println("Gagal memuat map. Cek lagi path atau format file txt lu boi :))).");
            return;
        }

        System.out.println("Map berhasil dimuat!");
        System.out.println("Ukuran: " + map.rows + "x" + map.cols);
        System.out.println("Total Angka Target: " + map.totalAngka);
        System.out.println("-------------------------------------------------");

        // 3. Pemilihan Algoritma
        System.out.println("Pilihan Algoritma: \n1. UCS\n2. GBFS\n3. A*\n4. BFS\n5. DFS");
        String algoritma = getLine("Masukkan Algoritma yang diinginkan (nomor): ");
        while (!algoritma.equals("1") && !algoritma.equals("2") && !algoritma.equals("3") && !algoritma.equals("4") && !algoritma.equals("5")){
            algoritma = getLine("Pilihan tidak valid. Masukkan angka 1-5:");
        }

        int optionHeuristic = -1;
        if (algoritma.equals("2") || algoritma.equals("3")){
            System.out.println("Pilihan Heuristik: \n1. SLD (Straight Line Distance)\n2. Manhattan Distance (ke satu target selanjutnya) \n3. Manhattan Distance (ke semua target selanjutnya)");
            String optionH = getLine("Masukkan Heuristik yang diinginkan (nomor): ");
            while (!optionH.equals("1") && !optionH.equals("2") && !optionH.equals("3")){
                optionH = getLine("Pilihan tidak valid. Masukkan angka 1-3:");
            }
            optionHeuristic = Integer.parseInt(optionH);
        }

        System.out.println("WAKTUNYA BERMAIN!!!");        
        // Start Timer (Count execution time)
        long startTime = System.currentTimeMillis();
        
        // 4. === ALGORITMA === 
        StateNode solusi;
        switch (algoritma) {
            case "1":
                System.out.println("Mencari jalan pakai UCS...");
                solusi = new UCS().search(map);
                break;
            case "2":
                System.out.println("Mencari jalan pakai GBFS...");
                solusi = new GBFS().search(map, optionHeuristic);
                break;
            case "3":
                System.out.println("Mencari jalan pakai A*...");
                solusi = new Astar().search(map, optionHeuristic);
                break;
            case "4":
                System.out.println("Mencari jalan pakai BFS...");
                solusi = new BFS().search(map);
                break;
            case "5":
                System.out.println("Mencari jalan pakai DFS...");
                solusi = new DFS().search(map);
                break;
            default:
                throw new IllegalArgumentException("Pilihan algoritma tidak valid: " + algoritma);
        }
        
        long endTime = System.currentTimeMillis();
        long waktuEksekusi = endTime - startTime;

        // 5. OUTPUT!!!
        if (solusi != null) {
            System.out.println(">> SOLUSI DITEMUKAN! <<");
            System.out.println("[1] Solusi Yang Ditemukan: " + solusi.path);
            System.out.println("[2] Cost dari Solusi: " + solusi.totalCost);
            System.out.println("[3] Waktu eksekusi: " + waktuEksekusi + " ms\n");
            new Print(map).printSolusi(solusi);
        } else {
            System.out.println(">> TIDAK ADA SOLUSI <<");
            System.out.println("Aktor tidak bisa mencapai ke titik tujuan 'O' di map ini.");
        }
    }

    private static String getLine(String prompt){
        System.out.print(">> " + prompt);
        return SCANNER.nextLine();
    }
}
