package app.data.entitas;

public class Buku {
    private String judul;
    private String penulis;
    private double harga;
    private Kategori kategori;
    private int stok;

    public Buku(String judul, String penulis, double harga, Kategori kategori, int stok) {
        this.judul    = judul;
        this.penulis  = penulis;
        this.harga    = harga;
        this.kategori = kategori;
        this.stok     = stok;
    }

    // Getters
    public String   getJudul()    { return judul; }
    public String   getPenulis()  { return penulis; }
    public double   getHarga()    { return harga; }
    public Kategori getKategori() { return kategori; }
    public int      getStok()     { return stok; }

    // Setter stok (untuk dikurangi saat transaksi)
    public void setStok(int stok) { this.stok = stok; }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s | Rp%.0f | Stok: %d",
            kategori, judul, penulis, harga, stok);
    }
}