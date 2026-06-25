# Sistem Manajemen Toko Buku Digital — Tugas Kelompok Modular OOP

**Mata Kuliah:** Pemrograman Berbasis Objek — Sesi 12: Perancangan Program Modular

**Anggota Kelompok:**

1. Verdi Osenta Perangin Angin (202333500668)
2. Ester Sisilia Siregar (202333500690)
3. Diva

## Deskripsi Proyek

Sistem Manajemen Toko Buku Digital merupakan aplikasi berbasis Java Modular yang dirancang untuk membantu pengelolaan data buku dan proses transaksi pembelian secara sederhana melalui antarmuka Command Line Interface (CLI).

Proyek ini dikembangkan sebagai implementasi konsep **Pemrograman Berorientasi Objek (OOP)** dan **Java Platform Module System (JPMS)**. Fokus utama sistem adalah penerapan modularitas, enkapsulasi tingkat modul, pemisahan tanggung jawab antar lapisan aplikasi, serta pengelolaan akses data secara aman.

Aplikasi memungkinkan pengguna untuk melihat daftar buku, mencari buku berdasarkan kategori maupun judul, serta melakukan transaksi pembelian dengan perhitungan diskon otomatis sesuai aturan bisnis yang telah ditentukan.

---

## Struktur Proyek

```text
TOKOBUKU/
├── README.md
└── src/
    ├── app.data/
    │   ├── module-info.java
    │   └── app/data/
    │       ├── entitas/
    │       │   ├── Buku.java
    │       │   └── Kategori.java
    │       └── internal/
    │           └── SimulasiDatabase.java
    │
    ├── app.logic/
    │   ├── module-info.java
    │   └── app/logic/bisnis/
    │       └── ProsesToko.java
    │
    └── app.ui/
        ├── module-info.java
        └── app/ui/tampilan/
            └── MenuUtama.java
```

---

## Arsitektur Modular

Proyek dibagi menjadi tiga modul utama yang memiliki tanggung jawab berbeda:

### 1. Modul `app.data`

Bertanggung jawab untuk menyimpan dan menyediakan data yang digunakan sistem.

Komponen utama:

* `Buku.java` → Representasi objek buku.
* `Kategori.java` → Kategori buku yang tersedia.
* `SimulasiDatabase.java` → Penyimpanan data sementara (in-memory).

### 2. Modul `app.logic`

Berisi seluruh aturan bisnis dan proses pengolahan data.

Komponen utama:

* `ProsesToko.java`

  * Menampilkan daftar buku.
  * Melakukan pencarian buku.
  * Menghitung transaksi pembelian.
  * Menghitung diskon otomatis.

### 3. Modul `app.ui`

Menangani seluruh interaksi pengguna melalui terminal.

Komponen utama:

* `MenuUtama.java`

  * Menampilkan menu.
  * Menerima input pengguna.
  * Menampilkan hasil proses dari modul logika.

---

## Bagaimana Enkapsulasi Ditegakkan dengan JPMS

Sistem ini memanfaatkan **Java Platform Module System (JPMS)** untuk menerapkan enkapsulasi yang lebih kuat dibandingkan aplikasi Java tradisional.

### Package yang Diekspor

Package:

```java
app.data.entitas
```

diekspor oleh modul `app.data` sehingga dapat digunakan oleh modul lain.

Package ini berisi:

* `Buku`
* `Kategori`

yang merupakan representasi data utama aplikasi.

### Package Internal

Package:

```java
app.data.internal
```

tidak diekspor secara umum, melainkan menggunakan:

```java
exports app.data.internal to app.logic;
```

Konfigurasi tersebut menyebabkan:

* Modul `app.logic` dapat mengakses `SimulasiDatabase`.
* Modul `app.ui` tidak dapat mengakses `SimulasiDatabase`.
* Akses langsung ke database hanya diperbolehkan melalui lapisan logika.

Jika pengembang mencoba menambahkan:

```java
import app.data.internal.SimulasiDatabase;
```

ke dalam modul `app.ui`, proses kompilasi akan gagal karena package tersebut tidak terlihat oleh modul UI.

Dengan demikian, seluruh akses data harus melalui `ProsesToko`, sehingga prinsip **Separation of Concerns** dan **Strong Encapsulation** dapat diterapkan secara efektif.

---

## Konsep OOP yang Diimplementasikan

### Encapsulation

Data buku disimpan dalam atribut privat dan diakses melalui method yang telah disediakan.

### Modular Programming

Aplikasi dipisahkan menjadi tiga modul independen:

* Data Layer
* Business Logic Layer
* Presentation Layer

### Separation of Concerns

Setiap modul memiliki tanggung jawab yang jelas sehingga kode lebih mudah dipelihara dan dikembangkan.

### Collection Framework

Data buku disimpan dan dikelola menggunakan koleksi Java untuk mempermudah pencarian dan pengolahan data.

---

## Cara Build dan Menjalankan Program

### Kompilasi

Jalankan perintah berikut dari direktori utama proyek:

```powershell
javac -d out --module-source-path src -m app.ui
```

### Menjalankan Program

```powershell
java --module-path out -m app.ui/app.ui.tampilan.MenuUtama
```

---

## Contoh Tampilan Program

```text
╔══════════════════════════════════════╗
║     SELAMAT DATANG DI TOKO BUKU      ║
║          DIGITAL NUSANTARA           ║
╚══════════════════════════════════════╝

┌─────────────────────────────┐
│           MENU UTAMA        │
├─────────────────────────────┤
│  1. Lihat Semua Buku        │
│  2. Cari Buku by Kategori   │
│  3. Cari Buku by Judul      │
│  4. Beli Buku               │
│  5. Keluar                  │
└─────────────────────────────┘
Pilih menu:
```

---

## Contoh Transaksi Pembelian

```text
🛒 BELI BUKU

Masukkan judul buku : Java Modular
Masukkan jumlah beli : 3

────────────────────────────────────────────

🧾 STRUK PEMBELIAN

Judul Buku    : Java Modular
Harga Satuan  : Rp 85.000,00
Jumlah Beli   : 3
Total Sebelum : Rp 255.000,00
Diskon (10%)  : -Rp 25.500,00
Total Bayar   : Rp 229.500,00

────────────────────────────────────────────
```

---

## Aturan Bisnis yang Diimplementasikan

### Pencarian Buku

Pengguna dapat mencari buku berdasarkan:

* Kategori

  * FIKSI
  * NON_FIKSI
  * SAINS
  * SEJARAH

* Kata kunci judul buku.

### Validasi Pembelian

Sistem akan memastikan jumlah pembelian tidak melebihi stok yang tersedia.

Jika stok tidak mencukupi, transaksi akan ditolak.

### Perhitungan Diskon

Diskon diberikan berdasarkan jumlah buku yang dibeli:

| Jumlah Buku | Diskon |
| ----------- | ------ |
| 1–2 Buku    | 0%     |
| 3–4 Buku    | 10%    |
| ≥ 5 Buku    | 20%    |

### Perhitungan Total Pembayaran

```text
Total Bayar = (Harga × Jumlah) − Diskon
```

Seluruh perhitungan dilakukan secara otomatis oleh modul `app.logic`.

---

## Catatan Implementasi

* `SimulasiDatabase` digunakan sebagai penyimpanan data sementara (in-memory database).
* Modul UI tidak memiliki akses langsung ke database.
* Seluruh komunikasi data dilakukan melalui lapisan logika (`ProsesToko`).
* Sistem dijalankan sepenuhnya melalui Command Line Interface (CLI).
* Desain modular memudahkan pengembangan fitur baru tanpa mengubah keseluruhan sistem.

---

## Kesimpulan

Proyek Sistem Manajemen Toko Buku Digital menunjukkan penerapan konsep Pemrograman Berorientasi Objek dan Java Modular secara terstruktur. Dengan memanfaatkan JPMS, sistem mampu menerapkan enkapsulasi tingkat modul yang kuat, memisahkan tanggung jawab antar lapisan aplikasi, serta menjaga keamanan akses data.

Pendekatan ini menghasilkan aplikasi yang lebih terorganisir, mudah dipelihara, dan siap dikembangkan untuk kebutuhan yang lebih kompleks pada masa mendatang.
