# Praktikum PPMPL - Code Coverage dengan JUnit dan JaCoCo
**Institut Teknologi Del Sitoluama, Laguboti**  
**Mata Kuliah:** Pengujian dan Penjaminan Mutu Perangkat Lunak (PPMPL)  
**Minggu / Sesi:** W11 / Sesi 5.2  
**Topik:** Code Coverage dengan JUnit dan JaCoCo  

---

## 📌 Deskripsi Proyek
Repositori ini berisi implementasi kode sumber dan pengujian unit (*Unit Testing*) berbasis *White Box Testing* menggunakan **JUnit 5 (Jupiter)** dan **JaCoCo (Java Code Coverage)** dengan build automation **Apache Maven**.

Proyek ini mencakup dua modul utama:
1. **`SimpleCalculator`**: Pengujian operasi aritmatika dasar beserta penanganan eksepsi dan nilai negatif dengan tahapan progresif coverage dari 22%, 33%, hingga 100%.
2. **`SimpleLoanApproval`**: Sistem evaluasi persetujuan pinjaman nasabah yang menguji multi-kondisi kredit, suku bunga, perulangan (*loop coverage*), evaluasi nasabah bertingkat, dan batas (*boundary values*) hingga mencapai **100% Instruction, Branch, Line, dan Method Coverage**.

---

## 📂 Struktur Direktori
```text
.
├── pom.xml                                   # Konfigurasi Maven, JUnit 5 & JaCoCo plugin
├── LAPORAN_PRAKTIKUM.md                      # Laporan praktikum lengkap (Markdown format)
├── LAPORAN_PRAKTIKUM.docx                    # Laporan praktikum softcopy resmi (Word format)
├── README.md                                 # Panduan eksekusi proyek
├── src
│   ├── main
│   │   └── java
│   │       ├── SimpleCalculator.java         # Class kalkulator sederhana
│   │       └── SimpleLoanApproval.java       # Class sistem persetujuan pinjaman
│   └── test
│       └── java
│           ├── SimpleCalculatorTest.java     # Suite unit test kalkulator (100% coverage)
│           └── SimpleLoanApprovalTest.java   # Suite unit test pinjaman (100% coverage, TC0-TC15)
└── target
    └── site
        └── jacoco
            ├── index.html                    # Laporan interaktif Code Coverage JaCoCo (HTML)
            ├── jacoco.csv                    # Data metrik coverage (CSV)
            └── jacoco.xml                    # Data metrik coverage (XML)
```

---

## 🚀 Cara Menjalankan Proyek

### 1. Prasyarat Sistem
- Java Development Kit (JDK) versi 17 atau lebih baru (termasuk JDK 21 / 25).
- Apache Maven versi 3.8+.
- IDE (IntelliJ IDEA / Eclipse / VS Code).

### 2. Eksekusi Unit Test dan Pembuatan Laporan JaCoCo
Buka terminal pada direktori root proyek dan jalankan perintah:
```bash
mvn clean test
```

### 3. Membuka Laporan JaCoCo di Browser
Setelah proses build dan pengujian selesai, buka berkas laporan JaCoCo di browser favorit Anda:
- Melalui file manager: buka file `target/site/jacoco/index.html`.
- Atau melalui terminal PowerShell:
```powershell
Start-Process "target\site\jacoco\index.html"
```

---

## 📊 Hasil Ringkasan Code Coverage (JaCoCo)

| Class | Missed Instructions | Instruction Coverage | Missed Branches | Branch Coverage | Missed Lines | Line Coverage | Missed Methods | Method Coverage |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **`SimpleLoanApproval`** | 0 of 92 | **100%** | 0 of 26 | **100%** | 0 of 27 | **100%** | 0 of 5 | **100%** |
| **`SimpleCalculator`** | 0 of 30 | **100%** | 0 of 4 | **100%** | 0 of 9 | **100%** | 0 of 5 | **100%** |
| **Total Proyek** | **0 of 122** | **100%** | **0 of 30** | **100%** | **0 of 36** | **100%** | **0 of 10** | **100%** |

---

## 📝 Dokumen Laporan
Laporan softcopy praktikum tersedia dalam format:
1. `LAPORAN_PRAKTIKUM.md`
2. `LAPORAN_PRAKTIKUM.docx`
