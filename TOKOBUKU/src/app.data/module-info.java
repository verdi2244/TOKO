module app.data {
    exports app.data.entitas;                     // agar Buku, Kategori bisa dipakai semua modul
    exports app.data.internal to app.logic;       // hanya untuk app.logic (ProsesToko)
}