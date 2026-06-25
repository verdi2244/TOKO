module app.ui {
    requires app.logic;   // Hanya boleh akses app.logic — TIDAK BOLEH langsung ke app.data.internal
    requires app.data;    // Butuh app.data untuk pakai enum Kategori di menu
}