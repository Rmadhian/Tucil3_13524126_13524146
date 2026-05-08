package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapParser {

    public static GameMap parseMap(String filePath) {
        try {
            Scanner scanner = new Scanner(new File(filePath));

            if (!scanner.hasNextInt()) {
                throw new IllegalArgumentException("Format input salah: Tidak menemukan nilai N dan M.");
            }
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();

            GameMap map = new GameMap(rows, cols);
            int maxAngka = -1; 
            boolean foundStart = false;
            boolean foundGoal = false;

            for (int r = 0; r < rows; r++) {
                String line = scanner.next();
                
                if (line.length() != cols) {
                    throw new IllegalArgumentException("Lebar baris ke-" + r + " tidak sesuai dengan nilai M (" + cols + ").");
                }

                for (int c = 0; c < cols; c++) {
                    char tile = line.charAt(c);
                    map.grid[r][c] = tile;

                    if (tile == 'Z') {
                        map.startR = r;
                        map.startC = c;
                        foundStart = true;
                    } else if (tile == 'O') {
                        map.goalR = r;
                        map.goalC = c;
                        foundGoal = true;
                    } 
                    else if (Character.isDigit(tile)) {
                        int angka = tile - '0';
                        map.waypointLocation[angka] = new int[2];
                        map.waypointLocation[angka][0] = r;
                        map.waypointLocation[angka][1] = c;
                        if (angka > maxAngka) {
                            maxAngka = angka; 
                        }
                    }
                }
            }

            map.totalAngka = maxAngka + 1; 

            for (int angka = 0; angka < map.totalAngka; angka++) {
                if (map.waypointLocation[angka] == null) {
                    throw new IllegalArgumentException("Urutan angka tidak lengkap: angka " + angka + " tidak ditemukan.");
                }
            }

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (!scanner.hasNextInt()) {
                        throw new IllegalArgumentException("Data cost matrix tidak lengkap.");
                    }
                    map.costMatrix[r][c] = scanner.nextInt();
                }
            }

            scanner.close();

            if (!foundStart || !foundGoal) {
                throw new IllegalArgumentException("Peta tidak valid! Harus terdapat tepat satu titik awal ('Z') dan satu titik tujuan ('O').");
            }

            return map; 

        } catch (FileNotFoundException e) {
            System.err.println("Gagal membaca input: File " + filePath + " tidak ditemukan!");
            return null;
        } catch (Exception e) {
            System.err.println("Error saat memparsing peta: " + e.getMessage());
            return null;
        }
    }
}
