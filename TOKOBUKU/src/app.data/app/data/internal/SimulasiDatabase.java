package app.data.internal;

import app.data.entitas.Buku;
import app.data.entitas.Kategori;
import java.util.ArrayList;
import java.util.List;

// Kelas ini TIDAK bisa diakses app.ui langsung — hanya lewat app.logic
public class SimulasiDatabase {

    private static List<Buku> daftarBuku = new ArrayList<>();

    // Data awal saat program dijalankan
    static {
        daftarBuku.add(new Buku("Laskar Pelangi",   "Andrea Hirata",  85000, Kategori.FIKSI,     10));
        daftarBuku.add(new Buku("Sapiens",           "Yuval Noah",    120000, Kategori.SEJARAH,    5));
        daftarBuku.add(new Buku("Fisika Kuantum",    "Kip Thorne",    150000, Kategori.SAINS,      3));
        daftarBuku.add(new Buku("Atomic Habits",     "James Clear",    95000, Kategori.NON_FIKSI,  8));
    }

    public static List<Buku> semuaBuku() {
        return daftarBuku;
    }
}