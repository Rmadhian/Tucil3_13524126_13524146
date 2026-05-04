public class IceSlidingLogic {
    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};
    static final String[] dirChar = {"U", "R", "D", "L"};

    public static StateNode slideMove(char[][] board, int[][] costMatrix, StateNode current, int dir) {
        int r = current.r;
        int c = current.c;
        int stepCost = 0; // Cost untuk SATU kali sliding ini
        int currentTarget = current.targetAngka;

        // Looping meluncur sampai nabrak sesuatu
        while (true) {
            int nextR = r + dr[dir];
            int nextC = c + dc[dir];

            // 1. Cek tergelincir keluar map (Tidak ada dinding/batu di ujung)
            // Mengakibatkan Game Over
            if (nextR < 0 || nextR >= board.length || nextC < 0 || nextC >= board[0].length) {
                return new StateNode(r, c, current.totalCost + stepCost, currentTarget, true, current.path + dirChar[dir]);
            }

            char nextTile = board[nextR][nextC];

            // 2. Cek menabrak rintangan/batu ('X')
            // Aktor berhenti TEPAT SEBELUM batu
            if (nextTile == 'X') {
                break; // Keluar dari loop sliding, posisi fix di (r, c)
            }

            // Jika aman, aktor bergeser ke petak berikutnya
            r = nextR;
            c = nextC;
            
            // Tambahkan cost petak yang dilalui (posisi awal sebelum slide tidak dihitung)
            stepCost += costMatrix[r][c]; 

            // 3. Cek mengenai Lava ('L')
            // Melewati atau berhenti di lava = Game Over
            if (nextTile == 'L') {
                return new StateNode(r, c, current.totalCost + stepCost, currentTarget, true, current.path + dirChar[dir]);
            }

            // 4. Cek aturan urutan angka (0-9)
            if (Character.isDigit(nextTile)) {
                int tileAngka = nextTile - '0';
                
                if (tileAngka == currentTarget) {
                    // Angka sesuai urutan, update target selanjutnya[cite: 1]
                    currentTarget++; 
                } else if (tileAngka > currentTarget) {
                    // Menginjak angka yang lebih besar dari target saat ini = Game Over[cite: 1]
                    return new StateNode(r, c, current.totalCost + stepCost, currentTarget, true, current.path + dirChar[dir]);
                }
                // Jika tileAngka < currentTarget, berarti sudah pernah dilewati.
                // Dianggap sebagai tile normal (bisa ditembus)[cite: 1].
            }
            
            // Cek titik tujuan ('O') -> Catatan: aktor tetap meluncur melewati 'O'
            // jika tidak ada dinding/batu setelahnya. Pengecekan menang/sukses 
            // dilakukan DILUAR fungsi ini, setelah aktor dipastikan berhenti.
        }

        // 5. Cek apakah aktor tidak bergerak sama sekali (karena depannya langsung batu)
        if (r == current.r && c == current.c) {
            return null; // Mengembalikan null agar tidak dimasukkan ke dalam Priority Queue
        }

        // Return state baru yang valid
        return new StateNode(r, c, current.totalCost + stepCost, currentTarget, false, current.path + dirChar[dir]);
    }
}