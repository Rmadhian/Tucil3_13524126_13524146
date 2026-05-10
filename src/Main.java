import algorithm.Astar;
import algorithm.BFS;
import algorithm.DFS;
import algorithm.GBFS;
import algorithm.UCS;
import utils.GameMap;
import utils.MapParser;
import utils.StateNode;
import utils.writeFile;
import view.Print;

public class Main {
    private static final java.util.Scanner SCANNER = new java.util.Scanner(System.in);

    public static void main(String[] args) {
        // 1. Tentukan lokasi file test 
        System.out.println("===== PROGRAM SOLVER ICE SLIDING PUZZLE =====");
        System.err.println("[!] Path File yang dimasukkan berupa path secara lengkap (Cth: test/test.txt) [!]\n");
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
        int iterationCount;
        switch (algoritma) {
            case "1":
                System.out.println("Mencari jalan pakai UCS...");
                UCS ucs = new UCS();
                solusi = ucs.search(map);
                iterationCount = ucs.getIterationCount();
                break;
            case "2":
                System.out.println("Mencari jalan pakai GBFS...");
                GBFS gbfs = new GBFS();
                solusi = gbfs.search(map, optionHeuristic);
                iterationCount = gbfs.getIterationCount();
                break;
            case "3":
                System.out.println("Mencari jalan pakai A*...");
                Astar astar = new Astar();
                solusi = astar.search(map, optionHeuristic);
                iterationCount = astar.getIterationCount();
                break;
            case "4":
                System.out.println("Mencari jalan pakai BFS...");
                BFS bfs = new BFS();
                solusi = bfs.search(map);
                iterationCount = bfs.getIterationCount();
                break;
            case "5":
                System.out.println("Mencari jalan pakai DFS...");
                DFS dfs = new DFS();
                solusi = dfs.search(map);
                iterationCount = dfs.getIterationCount();
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
            System.out.println("[4] Banyak iterasi yang ditinjau: " + iterationCount + " iterasi\n");
            Print printer = new Print(map);
            printer.printSolusi(solusi);

            String playback = getLine("Apakah Anda ingin melakukan playback? (Ya/Tidak): ");
            if (playback.equalsIgnoreCase("Ya") || playback.equalsIgnoreCase("Y")) {
                printer.playbackSolusi(solusi, SCANNER);
            }
            System.out.println("\n[!] File Save akan disimpan di folder yang sama dengan input file");
            String saveor = getLine("Apakah Anda ingin menyimpan solusi? (Ya/Tidak): ");
            if (saveor.equalsIgnoreCase("Ya") || saveor.equalsIgnoreCase("Y")) {
                String outputPath = writeFile.buildSolutionFilePath(FilePath);
                String content = buildSolutionContent(solusi, waktuEksekusi, iterationCount);

                if (writeFile.writeToFile(outputPath, content)) {
                    System.out.println("Solusi disimpan pada " + outputPath);
                }
            }
        } else {
            System.out.println(">> TIDAK ADA SOLUSI <<");
            System.out.println("Aktor tidak bisa mencapai ke titik tujuan 'O' di map ini.");
        }
    }

    private static String getLine(String prompt){
        System.out.print(">> " + prompt);
        return SCANNER.nextLine();
    }

    private static String buildSolutionContent(StateNode solusi, long waktuEksekusi, int iterationCount) {
        StringBuilder content = new StringBuilder();

        content.append("Sequence Gerakan: ").append(solusi.path).append(System.lineSeparator());
        content.append("Cost dari Solusi: ").append(solusi.totalCost).append(System.lineSeparator());
        content.append("Waktu eksekusi: ").append(waktuEksekusi).append(" ms").append(System.lineSeparator());
        content.append("Banyak iterasi yang ditinjau: ").append(iterationCount).append(" iterasi").append(System.lineSeparator());

        return content.toString();
    }
}
