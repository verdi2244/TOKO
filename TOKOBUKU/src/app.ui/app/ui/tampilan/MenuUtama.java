package app.ui.tampilan;

import app.data.entitas.Buku;
import app.data.entitas.Kategori;
import app.logic.bisnis.ProsesToko;

import java.util.List;
import java.util.Scanner;

public class MenuUtama {

    // ProsesToko adalah satu-satunya jembatan ke data — app.ui tidak sentuh database langsung!
    private static final ProsesToko proses  = new ProsesToko();
    private static final Scanner    scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     SELAMAT DATANG DI TOKO BUKU      ║");
        System.out.println("║          DIGITAL NUSANTARA           ║");
        System.out.println("╚══════════════════════════════════════╝");

        boolean jalan = true;
        while (jalan) {
            tampilkanMenu();
            int pilihan = bacaAngka("Pilih menu: ");

            switch (pilihan) {
                case 1 -> lihatSemuaBuku();
                case 2 -> cariBukuKategori();
                case 3 -> cariBukuJudul();
                case 4 -> beliBuku();
                case 5 -> {
                    System.out.println("\n👋 Terima kasih sudah berkunjung. Sampai jumpa!");
                    jalan = false;
                }
                default -> System.out.println("⚠️  Pilihan tidak valid, coba lagi.\n");
            }
        }

        scanner.close();
    }

    // =============================================
    // MENU UTAMA
    // =============================================
    private static void tampilkanMenu() {
        System.out.println("\n┌─────────────────────────────┐");
        System.out.println("│           MENU UTAMA        │");
        System.out.println("├─────────────────────────────┤");
        System.out.println("│  1. Lihat Semua Buku        │");
        System.out.println("│  2. Cari Buku by Kategori   │");
        System.out.println("│  3. Cari Buku by Judul      │");
        System.out.println("│  4. Beli Buku               │");
        System.out.println("│  5. Keluar                  │");
        System.out.println("└─────────────────────────────┘");
    }

    // =============================================
    // FITUR 1: LIHAT SEMUA BUKU
    // =============================================
    private static void lihatSemuaBuku() {
        System.out.println("\n📚 DAFTAR SEMUA BUKU:");
        System.out.println("─".repeat(60));

        List<Buku> daftar = proses.ambilSemuaBuku();
        if (daftar.isEmpty()) {
            System.out.println("   (Tidak ada buku tersedia)");
        } else {
            for (int i = 0; i < daftar.size(); i++) {
                System.out.printf("  %d. %s%n", i + 1, daftar.get(i));
            }
        }
        System.out.println("─".repeat(60));
    }

    // =============================================
    // FITUR 2: CARI BY KATEGORI
    // =============================================
    private static void cariBukuKategori() {
        System.out.println("\n🔍 CARI BUKU BERDASARKAN KATEGORI");
        System.out.println("Kategori tersedia: FIKSI | NON_FIKSI | SAINS | SEJARAH");
        System.out.print("Masukkan kategori: ");
        String input = scanner.nextLine().trim().toUpperCase();

        try {
            Kategori kategori = Kategori.valueOf(input);
            List<Buku> hasil  = proses.cariBerdasarKategori(kategori);

            System.out.println("\n📂 Hasil kategori [" + kategori + "]:");
            System.out.println("─".repeat(60));
            if (hasil.isEmpty()) {
                System.out.println("   (Tidak ada buku di kategori ini)");
            } else {
                hasil.forEach(b -> System.out.println("  • " + b));
            }
            System.out.println("─".repeat(60));

        } catch (IllegalArgumentException e) {
            System.out.println("⚠️  Kategori \"" + input + "\" tidak dikenal.");
        }
    }

    // =============================================
    // FITUR 3: CARI BY JUDUL
    // =============================================
    private static void cariBukuJudul() {
        System.out.println("\n🔍 CARI BUKU BERDASARKAN JUDUL");
        System.out.print("Masukkan kata kunci judul: ");
        String keyword    = scanner.nextLine().trim();
        List<Buku> hasil  = proses.cariBerdasarJudul(keyword);

        System.out.println("\n📖 Hasil pencarian \"" + keyword + "\":");
        System.out.println("─".repeat(60));
        if (hasil.isEmpty()) {
            System.out.println("   (Tidak ada buku yang cocok)");
        } else {
            hasil.forEach(b -> System.out.println("  • " + b));
        }
        System.out.println("─".repeat(60));
    }

    // =============================================
    // FITUR 4: BELI BUKU
    // =============================================
    private static void beliBuku() {
        System.out.println("\nBELI BUKU");
        System.out.println("─".repeat(60));
        System.out.println(" Tips diskon:");
        System.out.println("   Beli 3-4 buku dapat diskon 10%");
        System.out.println("   Beli 5+  buku dapat diskon 20%");
        System.out.println("─".repeat(60));

        System.out.print("Masukkan judul buku: ");
        String judul  = scanner.nextLine().trim();
        int jumlah    = bacaAngka("Masukkan jumlah beli: ");

        System.out.println();
        System.out.println("─".repeat(60));
        System.out.println(proses.prosesPembelian(judul, jumlah));
        System.out.println("─".repeat(60));
    }

    // =============================================
    // HELPER: Baca input angka dengan validasi
    // =============================================
    private static int bacaAngka(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Masukkan angka yang valid!");
            }
        }
    }
}