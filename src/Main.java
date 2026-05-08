import algorithm.UCS;
import utils.GameMap;
import utils.MapParser;
import utils.StateNode;

public class Main {
    public static void main(String[] args) {
        // 1. Tentukan lokasi file test lu
        // Sesuaikan path ini dengan lokasi file test.txt di laptop lu
        String testFilePath = "test/test.txt"; 

        System.out.println("Memulai program... Membaca file: " + testFilePath);

        // 2. Parse map-nya pakai MapParser yang udah lu bikin
        GameMap map = MapParser.parseMap(testFilePath);

        // Validasi kalau file gagal dibaca
        if (map == null) {
            System.out.println("Gagal memuat map. Cek lagi path atau format file txt lu boi.");
            return;
        }

        System.out.println("Map berhasil dimuat!");
        System.out.println("Ukuran: " + map.rows + "x" + map.cols);
        System.out.println("Total Angka Target: " + map.totalAngka);
        System.out.println("-------------------------------------------------");

        // 3. Inisiasi algoritma pencarian (Misal kita tes UCS dulu)
        UCS solverUCS = new UCS();
        
        System.out.println("Mencari jalan pakai UCS...");
        
        // Mulai menghitung waktu eksekusi (sesuai spek tugas lu)
        long startTime = System.currentTimeMillis();
        
        // 4. Lempar map ke algoritma
        StateNode solusi = solverUCS.search(map);
        
        long endTime = System.currentTimeMillis();
        long waktuEksekusi = endTime - startTime;

        // 5. Cek dan cetak hasilnya sesuai output yang diminta
        if (solusi != null) {
            System.out.println(">> SOLUSI DITEMUKAN! <<");
            System.out.println("Solusi Yang Ditemukan: " + solusi.path);
            System.out.println("Cost dari Solusi: " + solusi.totalCost);
            System.out.println("Waktu eksekusi: " + waktuEksekusi + " ms");
        } else {
            System.out.println(">> TIDAK ADA SOLUSI <<");
            System.out.println("Aktor tidak bisa mencapai ke titik tujuan 'O' di map ini.");
        }
    }
}
