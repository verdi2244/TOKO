package app.logic.bisnis;

import app  .data.entitas.Buku;
import app.data.entitas.Kategori;
import app.data.internal.SimulasiDatabase;

import java.util.List;
import java.util.stream.Collectors;

public class ProsesToko {

    // =============================================
    // BAGIAN 1: LIHAT BUKU
    // =============================================

    // Ambil semua buku dari database
    public List<Buku> ambilSemuaBuku() {
        return SimulasiDatabase.semuaBuku();
    }

    // Cari buku berdasarkan kategori
    public List<Buku> cariBerdasarKategori(Kategori kategori) {
        return SimulasiDatabase.semuaBuku().stream()
                .filter(b -> b.getKategori() == kategori)
                .collect(Collectors.toList());
    }

    // Cari buku berdasarkan kata kunci judul
    public List<Buku> cariBerdasarJudul(String keyword) {
        return SimulasiDatabase.semuaBuku().stream()
                .filter(b -> b.getJudul().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // =============================================
    // BAGIAN 2: HITUNG DISKON
    // =============================================

    /**
     * Aturan diskon:
     *  - Beli 5 buku atau lebih  → diskon 20%
     *  - Beli 3–4 buku           → diskon 10%
     *  - Beli 1–2 buku           → tidak ada diskon
     */
    public double hitungDiskon(int jumlahBeli, double hargaSatuan) {
        double totalSebelumDiskon = hargaSatuan * jumlahBeli;

        if (jumlahBeli >= 5) {
            return totalSebelumDiskon * 0.20;   // diskon 20%
        } else if (jumlahBeli >= 3) {
            return totalSebelumDiskon * 0.10;   // diskon 10%
        } else {
            return 0;                           // tidak ada diskon
        }
    }

    // =============================================
    // BAGIAN 3: HITUNG TOTAL & PROSES BELI
    // =============================================

    /**
     * Proses pembelian buku:
     * 1. Cari buku berdasarkan judul
     * 2. Cek stok
     * 3. Hitung diskon & total
     * 4. Kurangi stok
     * 5. Kembalikan struk ringkas
     */
    public String prosesPembelian(String judulBuku, int jumlahBeli) {

        // Cari buku di database
        Buku bukuDitemukan = null;
        for (Buku b : SimulasiDatabase.semuaBuku()) {
            if (b.getJudul().equalsIgnoreCase(judulBuku)) {
                bukuDitemukan = b;
                break;
            }
        }

        // Buku tidak ada
        if (bukuDitemukan == null) {
            return "❌ Buku \"" + judulBuku + "\" tidak ditemukan di toko.";
        }

        // Stok tidak cukup
        if (bukuDitemukan.getStok() < jumlahBeli) {
            return "❌ Stok tidak cukup! Stok tersedia: " + bukuDitemukan.getStok() + " buku.";
        }

        // Hitung harga
        double hargaSatuan        = bukuDitemukan.getHarga();
        double totalSebelumDiskon = hargaSatuan * jumlahBeli;
        double totalDiskon        = hitungDiskon(jumlahBeli, hargaSatuan);
        double totalBayar         = totalSebelumDiskon - totalDiskon;

        // Kurangi stok
        bukuDitemukan.setStok(bukuDitemukan.getStok() - jumlahBeli);

        // Tentukan label diskon
        String labelDiskon;
        if (jumlahBeli >= 5)      labelDiskon = "20%";
        else if (jumlahBeli >= 3) labelDiskon = "10%";
        else                      labelDiskon = "Tidak ada";

        // Kembalikan struk
        return String.format(
            "✅ PEMBELIAN BERHASIL\n" +
            "   Buku      : %s\n" +
            "   Penulis   : %s\n" +
            "   Jumlah    : %d buku\n" +
            "   Harga     : Rp%.0f / buku\n" +
            "   Subtotal  : Rp%.0f\n" +
            "   Diskon    : %s  (-Rp%.0f)\n" +
            "   TOTAL     : Rp%.0f\n" +
            "   Sisa Stok : %d buku",
            bukuDitemukan.getJudul(),
            bukuDitemukan.getPenulis(),
            jumlahBeli,
            hargaSatuan,
            totalSebelumDiskon,
            labelDiskon,
            totalDiskon,
            totalBayar,
            bukuDitemukan.getStok()
        );
    }
}