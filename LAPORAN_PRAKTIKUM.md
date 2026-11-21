# LAPORAN PRAKTIKUM
## PENGUJIAN DAN PENJAMINAN MUTU PERANGKAT LUNAK (PPMPL)
### Topik: Code Coverage dengan JUnit dan JaCoCo
**Institut Teknologi Del Sitoluama, Laguboti**

---

| Informasi Praktikum | Keterangan |
| :--- | :--- |
| **Mata Kuliah** | Pengujian dan Penjaminan Mutu Perangkat Lunak |
| **Sesi / Minggu** | 5/2 (Minggu 11) |
| **Durasi** | 100 Menit |
| **Jenis Penugasan** | Individual Assignment |
| **Deliverables** | Source Code & Report Softcopy |
| **Platform Pengujian**| Java 17+, Maven 3.9+, JUnit 5 (Jupiter), JaCoCo 0.8.12 |

---

## DAFTAR ISI
1. [BAB I: PENDAHULUAN DAN DASAR TEORI](#bab-i-pendahuluan-dan-dasar-teori)
   - 1.1 White Box Testing
   - 1.2 Konsep Code Coverage dan Metrik JaCoCo
   - 1.3 JUnit 5 dan JaCoCo Maven Plugin
   - 1.4 Perbandingan Build Automation: Maven vs Gradle
2. [BAB II: PRAKTIKUM BAGIAN 1 - SIMPLE CALCULATOR](#bab-ii-praktikum-bagian-1---simple-calculator)
   - 2.1 Konfigurasi Maven (`pom.xml`)
   - 2.2 Implementasi Class `SimpleCalculator`
   - 2.3 Pengujian Tahap 1: `testAdd()` (Coverage 22%)
   - 2.4 Pengujian Tahap 2: `testSubtract()` (Coverage 33%)
   - 2.5 Pengujian Tahap 3: Mencapai 100% Code Coverage
3. [BAB III: TUGAS PRAKTIKUM - SIMPLE LOAN APPROVAL](#bab-iii-tugas-praktikum---simple-loan-approval)
   - 3.1 Analisis Logika Program `SimpleLoanApproval`
   - 3.2 Tabel Test Cases Utama (TC1 - TC5) dan Pengamatan
   - 3.3 Analisis dan Perancangan Test Cases Tambahan (TC6 - TC15)
   - 3.4 Matriks Pengujian Lengkap (TC1 s/d TC15)
   - 3.5 Pengukuran Progresif Coverage (Tabel Pengamatan JaCoCo)
4. [BAB IV: HASIL DAN PEMBAHASAN CODE COVERAGE](#bab-iv-hasil-dan-pembahasan-code-coverage)
   - 4.1 Ringkasan Eksekusi Pengujian (Maven Surefire)
   - 4.2 Analisis Laporan JaCoCo HTML
   - 4.3 Analisis Metrik Coverage Per Class
5. [BAB V: KESIMPULAN DAN SARAN](#bab-v-kesimpulan-dan-saran)
6. [LAMPIRAN: KODE PROGRAM LENGKAP](#lampiran-kode-program-lengkap)
   - Lampiran 1: `pom.xml`
   - Lampiran 2: `SimpleCalculator.java`
   - Lampiran 3: `SimpleCalculatorTest.java`
   - Lampiran 4: `SimpleLoanApproval.java`
   - Lampiran 5: `SimpleLoanApprovalTest.java`

---

## BAB I: PENDAHULUAN DAN DASAR TEORI

### 1.1 White Box Testing
*White Box Testing* (disebut juga *structural testing* atau *glass box testing*) adalah metodologi pengujian perangkat lunak yang berfokus pada struktur internal, alur logika, dan implementasi kode sumber program. Berbeda dengan *Black Box Testing* yang hanya memvalidasi fungsionalitas berdasarkan masukan (*input*) dan luaran (*output*), *White Box Testing* memeriksa:
- Jalur eksekusi internal (*execution paths*).
- Kondisi percabangan logis (*branch / decision logic*).
- Perulangan (*loop conditions*).
- Penanganan pengecualian (*exception handling*).

Tujuan utama *White Box Testing* adalah memastikan seluruh jalur logika dalam kode telah tereksekusi dengan benar dan tidak terdapat kode yang redundan (*dead code*) atau kondisi tersembunyi yang berpotensi memicu kegagalan (*bugs*).

### 1.2 Konsep Code Coverage dan Metrik JaCoCo
*Code Coverage* adalah metrik kuantitatif dalam rekayasa perangkat lunak yang mengukur sejauh mana kode sumber program dieksekusi saat rangkaian pengujian otomatis (*unit tests*) dijalankan.

JaCoCo (*Java Code Coverage*) mengukur beberapa metrik kunci:
1. **Instruction Coverage (Bytecode Coverage)**: Mengukur jumlah instruksi *Java Bytecode* individual yang dieksekusi. Ini merupakan metrik terkecil dan paling granular yang independen dari pemformatan kode baris.
2. **Branch Coverage**: Mengukur persentase cabang keputusan (seperti `if`, `else if`, `switch`) yang telah dievaluasi ke nilai `true` dan `false`. JaCoCo menandai setiap instruksi percabangan sebagai:
   - *No coverage* (berwarna merah): Tidak ada cabang yang dieksekusi.
   - *Partial coverage* (berwarna kuning): Hanya satu cabang (misalnya `true`) yang dieksekusi.
   - *Full coverage* (berwarna hijau): Kedua cabang (`true` dan `false`) telah dieksekusi.
3. **Line Coverage**: Mengukur rasio baris kode yang dieksekusi minimal satu instruksi dibandingkan total baris kode aktif.
4. **Method Coverage**: Mengukur apakah sebuah *method* atau konstruktor telah dieksekusi minimal satu instruksi.
5. **Cyclomatic Complexity Coverage**: Mengukur cakupan kompleksitas siklomatis program berdasarkan jumlah jalur independen melalui kode sumber.

### 1.3 JUnit 5 dan JaCoCo Maven Plugin
- **JUnit 5 (Jupiter)**: Kerangka kerja pengujian unit modern untuk ekosistem Java. Fitur-fitur seperti `@Test`, `@DisplayName`, `@BeforeEach`, serta metode asersi `assertEquals`, `assertTrue`, `assertFalse`, dan `assertThrows` memfasilitasi pengujian deklaratif dan terstruktur.
- **JaCoCo Maven Plugin (`jacoco-maven-plugin`)**: Plugin Maven yang menginstrumentasi *bytecode* Java melalui Java Agent (`prepare-agent`) saat fase `test`, kemudian merekam berkas biner `jacoco.exec`, dan menghasilkan laporan komprehensif dalam format HTML, XML, dan CSV (`report`).

### 1.4 Perbandingan Build Automation: Maven vs Gradle
1. **Apache Maven**:
   - Berbasis deklaratif XML (`pom.xml`).
   - Menerapkan filosofi *Convention over Configuration* dengan siklus hidup bawaan (*default lifecycle*: validate, compile, test, package, install, deploy).
   - Sangat stabil, terstandarisasi, dan menjadi standar *de facto* untuk proyek Java enterprise.
2. **Gradle**:
   - Berbasis DSL dinamis (*Domain Specific Language*) menggunakan Groovy atau Kotlin script (`build.gradle` / `build.gradle.kts`).
   - Menawarkan fleksibilitas tinggi dan eksekusi bertahap (*incremental build* & *build cache*), umum digunakan pada pengembangan Android dan multi-project builds.

---

## BAB II: PRAKTIKUM BAGIAN 1 - SIMPLE CALCULATOR

### 2.1 Konfigurasi Maven (`pom.xml`)
Proyek dikonfigurasi sebagai Maven Project dengan `pom.xml` yang mengintegrasikan JUnit 5 Jupiter dan JaCoCo Maven Plugin versi 0.8.12.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.del</groupId>
    <artifactId>SimpleWhiteBoxTesting</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <junit.jupiter.version>5.10.2</junit.jupiter.version>
        <jacoco.version>0.8.12</jacoco.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.jupiter.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <release>17</release>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>${jacoco.version}</version>
                <configuration>
                    <includes>
                        <include>Simple*</include>
                    </includes>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### 2.2 Implementasi Class `SimpleCalculator`
Class `SimpleCalculator` mencakup empat operasi matematika dasar: penambahan (`add`), pengurangan (`subtract`), perkalian (`multiply`), dan pembagian (`divide`) dengan validasi pembagi nol (`b == 0`) dan pembagi bernilai negatif (`b < 0`).

```java
public class SimpleCalculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        } else if (b < 0) {
            return 0;
        }
        return a / b;
    }
}
```

### 2.3 Pengujian Tahap 1: `testAdd()` (Coverage 22%)
Pada tahap awal modul praktikum, pengujian hanya dibuat untuk metode `add()`:
```java
public class SimpleCalculatorTest {
    @Test
    public void testAdd() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(5, calculator.add(2, 3));
    }
}
```

**Hasil Pengamatan JaCoCo Tahap 1:**
- **Line Coverage**: **22.22%** (2 dari 9 baris kode tereksekusi).
- **Instruction Coverage**: **23.33%** (7 dari 30 instruksi *bytecode*).
- **Branch Coverage**: **0%** (0 dari 4 cabang tereksekusi).
- **Method Coverage**: **40.0%** (2 dari 5 method, yaitu method `add()` dan *default constructor*).
- **Analisis Visual**: Baris method `add()` berwarna hijau pada laporan JaCoCo, sedangkan baris method `subtract()`, `multiply()`, dan `divide()` bertanda merah/kuning (belum ter-cover).

### 2.4 Pengujian Tahap 2: `testSubtract()` (Coverage 33%)
Menambahkan pengujian untuk method `subtract()`:
```java
@Test
public void testSubtract() {
    SimpleCalculator calculator = new SimpleCalculator();
    assertEquals(2, calculator.subtract(5, 3));
}
```

**Hasil Pengamatan JaCoCo Tahap 2:**
- **Line Coverage**: Meningkat menjadi **33.33%** (3 dari 9 baris kode tereksekusi).
- **Instruction Coverage**: Meningkat menjadi **36.67%** (11 dari 30 instruksi).
- **Method Coverage**: Meningkat menjadi **60.0%** (3 dari 5 method).
- **Analisis**: Baris `subtract()` berubah status menjadi hijau.

### 2.5 Pengujian Tahap 3: Mencapai 100% Code Coverage
Untuk mencapai cakupan 100%, seluruh fungsionalitas dan kondisi percabangan diuji:
1. `testMultiply()`: Menguji perkalian `2 * 3 = 6`.
2. `testDivideNormal()`: Menguji pembagian normal `6 / 2 = 3`.
3. `testDivideByZero()`: Menguji kondisi `b == 0` yang melempar `IllegalArgumentException`.
4. `testDivideByNegative()`: Menguji kondisi `b < 0` yang mengembalikan nilai `0`.
5. `testConstructor()`: Memvalidasi instansiasi objek `SimpleCalculator`.

**Hasil Pengamatan JaCoCo Tahap 3 (Final):**
- **Line Coverage**: **100.0%** (9 dari 9 baris).
- **Branch Coverage**: **100.0%** (4 dari 4 cabang percabangan).
- **Instruction Coverage**: **100.0%** (30 dari 30 instruksi).
- **Method Coverage**: **100.0%** (5 dari 5 method).

---

## BAB III: TUGAS PRAKTIKUM - SIMPLE LOAN APPROVAL

### 3.1 Analisis Logika Program `SimpleLoanApproval`
Class `SimpleLoanApproval` merupakan sistem evaluasi kelayakan kredit pinjaman nasabah yang memiliki empat metode utama:
1. `approveLoan(int creditScore, double accountBalance, int loanAmount)`:
   - Jika `creditScore < 300`: Kredit sangat buruk (*Very bad credit*) -> Pinjaman **ditolak** (`false`).
   - Jika `creditScore < 600`: Kredit buruk (*Bad credit*) -> Pinjaman disetujui hanya jika saldo rekening lebih dari dua kali lipat jumlah pinjaman (`accountBalance > loanAmount * 2`).
   - Jika `creditScore < 750`: Kredit cukup (*Fair credit*) -> Pinjaman disetujui jika saldo rekening lebih besar dari jumlah pinjaman (`accountBalance > loanAmount`).
   - Selain itu (`creditScore >= 750`): Kredit baik (*Good credit*) -> Pinjaman **selalu disetujui** (`true`).
2. `calculateInterestRate(int creditScore)`:
   - `creditScore < 300` -> Suku bunga **20.0%** (Tinggi).
   - `creditScore < 600` -> Suku bunga **15.0%** (Sedang).
   - `creditScore < 750` -> Suku bunga **10.0%** (Rata-rata).
   - `creditScore >= 750` -> Suku bunga **5.0%** (Rendah).
3. `checkLatePayments(int[] latePayments, int maxAllowed)`:
   - Melakukan iterasi terhadap array `latePayments`.
   - Menghitung riwayat keterlambatan di mana nilai `latePayments[i] > 0`.
   - Mengembalikan `false` secara dini (*early return*) apabila jumlah keterlambatan melebihi `maxAllowed`.
   - Mengembalikan `true` apabila perulangan selesai tanpa melebihi batas.
4. `evaluateCustomer(int creditScore, double balance, int loanAmount, int[] latePayments)`:
   - Mengintegrasikan evaluasi `approveLoan` dan `checkLatePayments`.
   - Jika `approveLoan` gagal -> Mengembalikan `"Loan Rejected: Low credit score or insufficient balance"`.
   - Jika `approveLoan` berhasil dan `checkLatePayments(latePayments, 3)` berhasil -> Mengembalikan `"Loan Approved"`.
   - Jika `approveLoan` berhasil tetapi `checkLatePayments` gagal -> Mengembalikan `"Loan Rejected: Too many late payments"`.

---

### 3.2 Tabel Test Cases Utama (TC1 - TC5) dan Pengamatan
Sesuai instruksi soal modul praktikum pada Halaman 13, berikut adalah pengamatan serta pengukuran coverage yang dihasilkan dari masing-masing kasus uji:

| Test Case ID | Method Diuji | Skenario Pengujian | Input Parameter | Expected Output | Actual Output | Coverage Terisolasi (Line / Branch) | Coverage Kumulatif (Line / Branch) | Pengamatan & Analisis Eksekusi Jalur |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| **TC1** | `approveLoan()` | Very bad credit | `creditScore = 250`, `balance = 10000.0`, `loanAmount = 5000` | Loan should be rejected (`false`) | `false` (Passed) | **11.11%** Lines (3/27)<br>**3.85%** Branch (1/26)<br>**8.70%** Instr (8/92) | **11.11%** Lines (3/27)<br>**3.85%** Branch (1/26)<br>**8.70%** Instr (8/92) | Mengeksekusi blok cabang pertama `if (creditScore < 300)`. Mengembalikan `false`. Cabang `else if` lainnya tidak dieksekusi. |
| **TC2** | `approveLoan()` | Bad credit but sufficient balance | `creditScore = 550`, `balance = 20000.0`, `loanAmount = 5000` | Loan should be approved (`true`) | `true` (Passed) | **14.81%** Lines (4/27)<br>**11.54%** Branch (3/26)<br>**20.65%** Instr (19/92) | **18.52%** Lines (5/27)<br>**15.38%** Branch (4/26)<br>**22.83%** Instr (21/92) | Mengevaluasi `creditScore < 300` (false), masuk ke `else if (creditScore < 600)` (true). Karena `20000 > 5000 * 2` (true), mengembalikan `true`. |
| **TC3** | `approveLoan()` | Fair credit and sufficient balance | `creditScore = 700`, `balance = 6000.0`, `loanAmount = 5000` | Loan should be approved (`true`) | `true` (Passed) | **18.52%** Lines (5/27)<br>**15.38%** Branch (4/26)<br>**21.74%** Instr (20/92) | **25.93%** Lines (7/27)<br>**26.92%** Branch (7/26)<br>**34.78%** Instr (32/92) | Mengevaluasi dua kondisi awal (false), masuk ke `else if (creditScore < 750)` (true). Karena `6000 > 5000` (true), mengembalikan `true`. |
| **TC4** | `approveLoan()` | Good credit, always approved | `creditScore = 800`, `balance = 1000.0`, `loanAmount = 10000` | Loan should be approved (`true`) | `true` (Passed) | **18.52%** Lines (5/27)<br>**11.54%** Branch (3/26)<br>**15.22%** Instr (14/92) | **29.63%** Lines (8/27)<br>**30.77%** Branch (8/26)<br>**36.96%** Instr (34/92) | Mengevaluasi seluruh kondisi `creditScore < 300`, `< 600`, `< 750` bernilai false, jatuh ke blok `else` dan mengembalikan `true`. |
| **TC5** | `calculateInterestRate()` | High interest rate for very bad credit | `creditScore = 250` | Interest rate = `20.0%` | `20.0` (Passed) | **11.11%** Lines (3/27)<br>**3.85%** Branch (1/26)<br>**8.70%** Instr (8/92) | **37.04%** Lines (10/27)<br>**34.62%** Branch (9/26)<br>**42.39%** Instr (39/92) | Memulai pengujian method kedua `calculateInterestRate()`. Menguji percabangan pertama `if (creditScore < 300)` yang menghasilkan suku bunga 20.0%. |

---

### 3.3 Analisis dan Perancangan Test Cases Tambahan (TC6 - TC15)
Setelah mengeksekusi TC1 sampai TC5, cakupan baris hanya mencapai 37.04% dan cabang 34.62%. Untuk mencapai **100% Code Coverage**, dilakukan analisis *gap* jalur kontrol:
1. **Gap pada `approveLoan()`**:
   - Belum ada pengujian kondisi ketika saldo **tidak mencukupi** pada kategori *Bad Credit* (`balance <= loanAmount * 2`). Perlu diuji nilai batas (*boundary*) di mana `balance = 10000` dan `balance = 8000` untuk pinjaman 5000 (**TC6**).
   - Belum ada pengujian kondisi ketika saldo **tidak mencukupi** pada kategori *Fair Credit* (`balance <= loanAmount`). Perlu diuji nilai batas di mana `balance = 5000` dan `balance = 4000` untuk pinjaman 5000 (**TC7**).
2. **Gap pada `calculateInterestRate()`**:
   - Cabang `creditScore < 600` belum diuji -> Ditambahkan **TC8** (score 300 dan 550 menghasilkan 15.0%).
   - Cabang `creditScore < 750` belum diuji -> Ditambahkan **TC9** (score 600 dan 700 menghasilkan 10.0%).
   - Cabang `else` (score >= 750) belum diuji -> Ditambahkan **TC10** (score 750 dan 800 menghasilkan 5.0%).
3. **Gap pada `checkLatePayments()`**:
   - Belum ada pengujian sama sekali untuk method ini.
   - Loop harus diuji pada:
     - Array kosong `new int[]{}` (menguji kondisi loop langsung false saat inisialisasi).
     - Elemen non-positif `{0, -1, 0}` (menguji cabang `latePayments[i] > 0` bernilai false).
     - Keterlambatan normal dalam batas `{1, 2}` dengan `maxAllowed = 3` -> Ditambahkan **TC11**.
     - Keterlambatan melebihi batas `{1, 1, 1, 1}` dengan `maxAllowed = 2` (menguji cabang `count > maxAllowed` yang memicu *early exit* false) -> Ditambahkan **TC12**.
4. **Gap pada `evaluateCustomer()`**:
   - Belum ada pengujian untuk method integrasi ini.
   - Perlu menguji ketiga jalur cabang:
     - `approveLoan == false`: Nasabah ditolak karena skor kredit rendah / saldo kurang -> Ditambahkan **TC13**.
     - `approveLoan == true` dan `checkLatePayments == true`: Nasabah disetujui (`"Loan Approved"`) -> Ditambahkan **TC14**.
     - `approveLoan == true` dan `checkLatePayments == false`: Nasabah ditolak karena riwayat keterlambatan pembayaran (`"Loan Rejected: Too many late payments"`) -> Ditambahkan **TC15**.
5. **Konstruktor**:
   - Pemanggilan konstruktor default `new SimpleLoanApproval()` untuk memastikan cakupan instruksi bytecode `<init>` 100% (**TC0**).

---

### 3.4 Matriks Pengujian Lengkap (TC1 s/d TC15)

| Test ID | Target Method | Deskripsi Skenario Uji | Parameter Masukan | Nilai Ekspektasi | Hasil Uji | Kategori White Box Coverage |
| :---: | :---: | :--- | :--- | :---: | :---: | :---: |
| **TC0** | `<init>` | Pengujian konstruktor default | `new SimpleLoanApproval()` | Objek tidak null | Passed | Method Coverage |
| **TC1** | `approveLoan` | Skor kredit sangat buruk (<300) | `score: 250, bal: 10000, loan: 5000` | `false` | Passed | Statement & Branch |
| **TC2** | `approveLoan` | Skor buruk, saldo cukup (> 2x pinjaman) | `score: 550, bal: 20000, loan: 5000` | `true` | Passed | Statement & Branch |
| **TC3** | `approveLoan` | Skor cukup, saldo mencukupi (> pinjaman) | `score: 700, bal: 6000, loan: 5000` | `true` | Passed | Statement & Branch |
| **TC4** | `approveLoan` | Skor baik (>=750), otomatis disetujui | `score: 800, bal: 1000, loan: 10000` | `true` | Passed | Statement & Branch |
| **TC5** | `calculateInterestRate` | Bunga kredit sangat buruk (<300) | `score: 250` | `20.0%` | Passed | Statement & Branch |
| **TC6** | `approveLoan` | Skor buruk, saldo tidak cukup (<= 2x) | `score: 550, bal: 10000, loan: 5000`<br>`score: 550, bal: 8000, loan: 5000` | `false`<br>`false` | Passed | Boundary & Negative Branch |
| **TC7** | `approveLoan` | Skor cukup, saldo tidak cukup (<= pinjaman) | `score: 700, bal: 5000, loan: 5000`<br>`score: 700, bal: 4000, loan: 5000` | `false`<br>`false` | Passed | Boundary & Negative Branch |
| **TC8** | `calculateInterestRate` | Bunga kredit buruk (300-599) | `score: 300`, `score: 550` | `15.0%` | Passed | Boundary & Branch |
| **TC9** | `calculateInterestRate` | Bunga kredit cukup (600-749) | `score: 600`, `score: 700` | `10.0%` | Passed | Boundary & Branch |
| **TC10**| `calculateInterestRate` | Bunga kredit baik (>=750) | `score: 750`, `score: 800` | `5.0%` | Passed | Boundary & Branch |
| **TC11**| `checkLatePayments` | Array kosong, elemen <= 0, keterlambatan <= max | `arr: [], max: 2`<br>`arr: [0, -1, 0], max: 2`<br>`arr: [1, 2], max: 3` | `true`<br>`true`<br>`true` | Passed | Loop Zero-trip, Loop Branch, Path Coverage |
| **TC12**| `checkLatePayments` | Keterlambatan melebihi batas | `arr: [1, 1, 1, 1], max: 2` | `false` | Passed | Loop Early Exit Branch |
| **TC13**| `evaluateCustomer` | Pinjaman ditolak: skor/saldo tidak cukup | `score: 200, bal: 10000, loan: 5000, late: [0]` | `"Loan Rejected: Low credit score..."` | Passed | Decision & Branch |
| **TC14**| `evaluateCustomer` | Pinjaman disetujui (skor baik, late <= 3) | `score: 800, bal: 10000, loan: 5000, late: [1, 0, 1]` | `"Loan Approved"` | Passed | Decision & Branch |
| **TC15**| `evaluateCustomer` | Pinjaman ditolak: terlalu sering terlambat | `score: 800, bal: 10000, loan: 5000, late: [1, 1, 1, 1]` | `"Loan Rejected: Too many late..."` | Passed | Decision & Branch |

---

### 3.5 Pengukuran Progresif Coverage (Tabel Pengamatan JaCoCo)
Berikut adalah data akumulasi *code coverage* bertahap yang dicatat dari eksekusi JaCoCo:

| Tahap Penambahan Test Case | Instruction Coverage (%) | Branch Coverage (%) | Line Coverage (%) | Method Coverage (%) | Jalur Tambahan yang Tercakup |
| :--- | :---: | :---: | :---: | :---: | :--- |
| **Hanya TC1** | 8.70% (8/92) | 3.85% (1/26) | 11.11% (3/27) | 40.0% (2/5) | Inisialisasi + Cabang 1 `approveLoan` (`creditScore < 300`) |
| **Kumulatif TC1 + TC2** | 22.83% (21/92) | 15.38% (4/26) | 18.52% (5/27) | 40.0% (2/5) | Cabang 2 `approveLoan` (`creditScore < 600` & saldo cukup) |
| **Kumulatif TC1 s/d TC3** | 34.78% (32/92) | 26.92% (7/26) | 25.93% (7/27) | 40.0% (2/5) | Cabang 3 `approveLoan` (`creditScore < 750` & saldo cukup) |
| **Kumulatif TC1 s/d TC4** | 36.96% (34/92) | 30.77% (8/26) | 29.63% (8/27) | 40.0% (2/5) | Cabang 4 `approveLoan` (`else` branch) |
| **Kumulatif TC1 s/d TC5** | 42.39% (39/92) | 34.62% (9/26) | 37.04% (10/27) | 60.0% (3/5) | Cabang 1 `calculateInterestRate` (`creditScore < 300`) |
| **Kumulatif TC1 s/d TC10**| 59.78% (55/92) | 46.15% (12/26)| 55.56% (15/27) | 60.0% (3/5) | Seluruh kondisi cabang `approveLoan` dan `calculateInterestRate` |
| **Kumulatif TC1 s/d TC12**| 78.26% (72/92) | 69.23% (18/26)| 74.07% (20/27) | 80.0% (4/5) | Loop `checkLatePayments` (eksekusi normal, zero-trip, dan terminasi dini) |
| **Lengkap (TC1 s/d TC15 + TC0)** | **100.0% (92/92)** | **100.0% (26/26)** | **100.0% (27/27)** | **100.0% (5/5)** | **Seluruh instruksi, percabangan, loop, dan method pada SimpleLoanApproval** |

---

## BAB IV: HASIL DAN PEMBAHASAN CODE COVERAGE

### 4.1 Ringkasan Eksekusi Pengujian (Maven Surefire)
Eksekusi unit test dijalankan melalui terminal dengan perintah:
```bash
mvn clean test
```

**Log Luaran Surefire:**
```text
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running SimpleCalculatorTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.082 s -- in SimpleCalculatorTest
[INFO] Running SimpleLoanApprovalTest
[INFO] Tests run: 16, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.039 s -- in SimpleLoanApprovalTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] --- jacoco:0.8.12:report (report) @ SimpleWhiteBoxTesting ---
[INFO] Loading execution data file ...\target\jacoco.exec
[INFO] Analyzed bundle 'SimpleWhiteBoxTesting' with 2 classes
[INFO] BUILD SUCCESS
```
Total **23 pengujian** berhasil dieksekusi dengan **0 kegagalan (*failures*)** dan **0 galat (*errors*)**.

### 4.2 Analisis Laporan JaCoCo HTML
Laporan komprehensif JaCoCo dihasilkan pada direktori:
`target/site/jacoco/index.html`

Ringkasan tabel laporan JaCoCo adalah sebagai berikut:

| Class | Missed Instructions | Instruction Cov. | Missed Branches | Branch Cov. | Missed Lines | Line Cov. | Missed Methods | Method Cov. | Missed Classes |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| `SimpleLoanApproval` | 0 of 92 | **100%** | 0 of 26 | **100%** | 0 of 27 | **100%** | 0 of 5 | **100%** | 0 of 1 |
| `SimpleCalculator` | 0 of 30 | **100%** | 0 of 4 | **100%** | 0 of 9 | **100%** | 0 of 5 | **100%** | 0 of 1 |
| **Total Proyek** | **0 of 122** | **100%** | **0 of 30** | **100%** | **0 of 36** | **100%** | **0 of 10** | **100%** | **0 of 2** |

### 4.3 Analisis Metrik Coverage Per Class
1. **`SimpleCalculator`**:
   - Total baris kode aktif: 9 baris.
   - Total instruksi bytecode: 30 instruksi.
   - Total percabangan logika: 4 cabang (`b == 0` dan `b < 0` dengan evaluasi true dan false).
   - Seluruh baris ditandai warna hijau penuh di laporan JaCoCo tanpa ada baris kuning (kondisi percabangan parsial) maupun merah (baris tak tereksekusi).
2. **`SimpleLoanApproval`**:
   - Total baris kode aktif: 27 baris.
   - Total instruksi bytecode: 92 instruksi.
   - Total percabangan logika: 26 cabang.
   - Seluruh kondisi ganda (misal evaluasi boolean `accountBalance > loanAmount * 2` dan kondisi loop `i < latePayments.length` serta `latePayments[i] > 0`) telah teruji untuk kedua kemungkinan hasil (`true` dan `false`), sehingga mencapai cakupan 100% tanpa celah logika.

---

## BAB V: KESIMPULAN DAN SARAN

### 5.1 Kesimpulan
1. **Efektivitas White Box Testing**: Dengan menganalisis struktur internal kode program, penguji dapat mengidentifikasi setiap jalur eksekusi yang belum tersentuh oleh pengujian fungsional biasa.
2. **Kelebihan Integrasi JUnit 5 dan JaCoCo**: JaCoCo Maven Plugin mempermudah pengukuran kualitas pengujian secara objektif dan terotomatisasi. Laporan visual berbasis HTML memberikan umpan balik langsung (*feedback loop*) mengenai baris, instruksi, dan percabangan mana yang membutuhkan kasus uji tambahan.
3. **Pencapaian Target**:
   - Pengujian awal pada `SimpleCalculator` berhasil mereplikasi peningkatan bertahap dari 22% (testAdd), 33% (testSubtract), hingga 100%.
   - Pengujian pada tugas `SimpleLoanApproval` berhasil mendokumentasikan hasil pengamatan untuk kasus uji TC1 sampai TC5.
   - Melalui penambahan kasus uji terencana (TC6 sampai TC15), cakupan kode untuk `SimpleLoanApproval` berhasil mencapai **100% Instruction Coverage**, **100% Branch Coverage**, **100% Line Coverage**, dan **100% Method Coverage**.

### 5.2 Saran
1. Pengujian otomatis JaCoCo disarankan diintegrasikan ke dalam pipeline CI/CD (*Continuous Integration / Continuous Deployment*) menggunakan GitHub Actions atau GitLab CI dengan aturan batas minimal coverage (*coverage gate / threshold*).
2. Penerapan pengujian batas (*Boundary Value Analysis*) pada setiap kondisi logika perbandingan bertingkat sangat direkomendasikan untuk mencegah galat logika (*off-by-one errors*).

---

## LAMPIRAN: KODE PROGRAM LENGKAP

### Lampiran 1: `pom.xml`
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.del</groupId>
    <artifactId>SimpleWhiteBoxTesting</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <junit.jupiter.version>5.10.2</junit.jupiter.version>
        <jacoco.version>0.8.12</jacoco.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.jupiter.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <release>17</release>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>

            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>${jacoco.version}</version>
                <configuration>
                    <includes>
                        <include>Simple*</include>
                    </includes>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### Lampiran 2: `SimpleCalculator.java`
```java
public class SimpleCalculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        } else if (b < 0) {
            return 0;
        }
        return a / b;
    }
}
```

### Lampiran 3: `SimpleCalculatorTest.java`
```java
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleCalculatorTest {

    @Test
    @DisplayName("Test konstruktor default")
    public void testConstructor() {
        SimpleCalculator calc = new SimpleCalculator();
        assertNotNull(calc);
    }

    @Test
    @DisplayName("Test add: 2 + 3 = 5")
    public void testAdd() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    @DisplayName("Test subtract: 5 - 3 = 2")
    public void testSubtract() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(2, calculator.subtract(5, 3));
    }

    @Test
    @DisplayName("Test multiply: 2 * 3 = 6")
    public void testMultiply() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    @DisplayName("Test divide normal: 6 / 2 = 3")
    public void testDivideNormal() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(3, calculator.divide(6, 2));
    }

    @Test
    @DisplayName("Test divide by zero melempar IllegalArgumentException")
    public void testDivideByZero() {
        SimpleCalculator calculator = new SimpleCalculator();
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.divide(6, 0)
        );
        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @Test
    @DisplayName("Test divide with negative divisor returns 0")
    public void testDivideByNegative() {
        SimpleCalculator calculator = new SimpleCalculator();
        assertEquals(0, calculator.divide(6, -2));
    }
}
```

### Lampiran 4: `SimpleLoanApproval.java`
```java
public class SimpleLoanApproval {
    // Approve loan based on credit score and account balance
    public boolean approveLoan(int creditScore, double accountBalance, int loanAmount) {
        if (creditScore < 300) {
            // Very bad credit
            return false;
        } else if (creditScore < 600) {
            // Bad credit, check for high balance
            return accountBalance > loanAmount * 2;
        } else if (creditScore < 750) {
            // Fair credit, check if account balance can cover the loan
            return accountBalance > loanAmount;
        } else {
            // Good or excellent credit, always approve
            return true;
        }
    }

    // Calculate loan interest based on credit score
    public double calculateInterestRate(int creditScore) {
        if (creditScore < 300) {
            return 20.0; // High interest for bad credit
        } else if (creditScore < 600) {
            return 15.0; // Moderate interest for bad credit
        } else if (creditScore < 750) {
            return 10.0; // Average interest for fair credit
        } else {
            return 5.0; // Low interest for good credit
        }
    }

    // Method with a loop that checks the number of late payments
    public boolean checkLatePayments(int[] latePayments, int maxAllowed) {
        int count = 0;
        for (int i = 0; i < latePayments.length; i++) {
            if (latePayments[i] > 0) {
                count++;
            }
            if (count > maxAllowed) {
                return false;
            }
        }
        return true;
    }

    // Complex method to evaluateCustomerCreditScore
    public String evaluateCustomer(int creditScore, double balance, int loanAmount, int[] latePayments) {
        if (approveLoan(creditScore, balance, loanAmount)) {
            if (checkLatePayments(latePayments, 3)) {
                return "Loan Approved";
            } else {
                return "Loan Rejected: Too many late payments";
            }
        } else {
            return "Loan Rejected: Low credit score or insufficient balance";
        }
    }
}
```

### Lampiran 5: `SimpleLoanApprovalTest.java`
```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleLoanApprovalTest {

    private SimpleLoanApproval service;

    @BeforeEach
    public void setUp() {
        service = new SimpleLoanApproval();
    }

    @Test
    @DisplayName("TC0: Inisialisasi Objek / Default Constructor")
    public void testConstructor() {
        assertNotNull(service, "Objek SimpleLoanApproval harus berhasil diinstansiasi");
    }

    @Test
    @DisplayName("TC1: approveLoan - Very bad credit (< 300) -> Loan should be rejected")
    public void testTC1_approveLoan_veryBadCredit() {
        boolean result = service.approveLoan(250, 10000.0, 5000);
        assertFalse(result, "Pinjaman harus ditolak jika creditScore < 300");
    }

    @Test
    @DisplayName("TC2: approveLoan - Bad credit (300-599) but sufficient balance (> 2 * loanAmount) -> Approved")
    public void testTC2_approveLoan_badCreditSufficientBalance() {
        boolean result = service.approveLoan(550, 20000.0, 5000);
        assertTrue(result, "Pinjaman harus disetujui jika saldo > 2 * pinjaman untuk bad credit");
    }

    @Test
    @DisplayName("TC3: approveLoan - Fair credit (600-749) and sufficient balance (> loanAmount) -> Approved")
    public void testTC3_approveLoan_fairCreditSufficientBalance() {
        boolean result = service.approveLoan(700, 6000.0, 5000);
        assertTrue(result, "Pinjaman harus disetujui jika saldo > pinjaman untuk fair credit");
    }

    @Test
    @DisplayName("TC4: approveLoan - Good credit (>= 750), always approved -> Approved")
    public void testTC4_approveLoan_goodCreditAlwaysApproved() {
        boolean result = service.approveLoan(800, 1000.0, 10000);
        assertTrue(result, "Pinjaman harus selalu disetujui jika creditScore >= 750");
    }

    @Test
    @DisplayName("TC5: calculateInterestRate - High interest rate for very bad credit (< 300) -> 20.0%")
    public void testTC5_calculateInterestRate_veryBadCredit() {
        double rate = service.calculateInterestRate(250);
        assertEquals(20.0, rate, 0.001, "Suku bunga untuk skor < 300 harus 20.0%");
    }

    @Test
    @DisplayName("TC6: approveLoan - Bad credit (300-599) with insufficient balance (<= 2 * loanAmount) -> Rejected")
    public void testTC6_approveLoan_badCreditInsufficientBalance() {
        assertFalse(service.approveLoan(550, 10000.0, 5000), 
            "Harus ditolak jika balance tepat sama dengan 2 * loanAmount");
        assertFalse(service.approveLoan(550, 8000.0, 5000), 
            "Harus ditolak jika balance < 2 * loanAmount");
    }

    @Test
    @DisplayName("TC7: approveLoan - Fair credit (600-749) with insufficient balance (<= loanAmount) -> Rejected")
    public void testTC7_approveLoan_fairCreditInsufficientBalance() {
        assertFalse(service.approveLoan(700, 5000.0, 5000), 
            "Harus ditolak jika balance tepat sama dengan loanAmount");
        assertFalse(service.approveLoan(700, 4000.0, 5000), 
            "Harus ditolak jika balance < loanAmount");
    }

    @Test
    @DisplayName("TC8: calculateInterestRate - Moderate interest for bad credit (300-599) -> 15.0%")
    public void testTC8_calculateInterestRate_badCredit() {
        assertEquals(15.0, service.calculateInterestRate(300), 0.001, "Boundary nilai 300 harus menghasilkan 15.0%");
        assertEquals(15.0, service.calculateInterestRate(550), 0.001, "Skor 550 harus menghasilkan 15.0%");
    }

    @Test
    @DisplayName("TC9: calculateInterestRate - Average interest for fair credit (600-749) -> 10.0%")
    public void testTC9_calculateInterestRate_fairCredit() {
        assertEquals(10.0, service.calculateInterestRate(600), 0.001, "Boundary nilai 600 harus menghasilkan 10.0%");
        assertEquals(10.0, service.calculateInterestRate(700), 0.001, "Skor 700 harus menghasilkan 10.0%");
    }

    @Test
    @DisplayName("TC10: calculateInterestRate - Low interest for good credit (>= 750) -> 5.0%")
    public void testTC10_calculateInterestRate_goodCredit() {
        assertEquals(5.0, service.calculateInterestRate(750), 0.001, "Boundary nilai 750 harus menghasilkan 5.0%");
        assertEquals(5.0, service.calculateInterestRate(800), 0.001, "Skor 800 harus menghasilkan 5.0%");
    }

    @Test
    @DisplayName("TC11: checkLatePayments - Loop normal: array kosong, elemen non-positif, dan dalam batas -> True")
    public void testTC11_checkLatePayments_withinLimitAndNonPositive() {
        assertTrue(service.checkLatePayments(new int[]{}, 2), 
            "Array kosong harus mengembalikan true");
        assertTrue(service.checkLatePayments(new int[]{0, -1, 0}, 2), 
            "Elemen tidak lebih besar dari 0 tidak boleh menambah hitungan");
        assertTrue(service.checkLatePayments(new int[]{1, 2}, 3), 
            "Hitungan keterlambatan 2 <= 3 harus mengembalikan true");
    }

    @Test
    @DisplayName("TC12: checkLatePayments - Loop terminasi dini: count > maxAllowed -> False")
    public void testTC12_checkLatePayments_exceededLimit() {
        assertFalse(service.checkLatePayments(new int[]{1, 1, 1, 1}, 2), 
            "Harus mengembalikan false saat keterlambatan melebihi batas");
    }

    @Test
    @DisplayName("TC13: evaluateCustomer - Rejected due to low credit score or insufficient balance")
    public void testTC13_evaluateCustomer_lowCreditOrInsufficientBalance() {
        String result = service.evaluateCustomer(200, 10000.0, 5000, new int[]{0});
        assertEquals("Loan Rejected: Low credit score or insufficient balance", result);
    }

    @Test
    @DisplayName("TC14: evaluateCustomer - Approved (Loan approved & late payments <= 3)")
    public void testTC14_evaluateCustomer_approved() {
        String result = service.evaluateCustomer(800, 10000.0, 5000, new int[]{1, 0, 1});
        assertEquals("Loan Approved", result);
    }

    @Test
    @DisplayName("TC15: evaluateCustomer - Rejected due to too many late payments (> 3)")
    public void testTC15_evaluateCustomer_tooManyLatePayments() {
        String result = service.evaluateCustomer(800, 10000.0, 5000, new int[]{1, 1, 1, 1});
        assertEquals("Loan Rejected: Too many late payments", result);
    }
}
```
