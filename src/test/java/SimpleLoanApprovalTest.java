import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Suite untuk SimpleLoanApproval.
 * Mengimplementasikan pengujian White Box Testing untuk mencapai 100% Code Coverage
 * (Statement/Instruction Coverage, Branch Coverage, Line Coverage, dan Method Coverage).
 *
 * Sesuai dengan instruksi praktikum PPMPL Minggu 11 / Sesi 5.2.
 */
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

    // =========================================================================
    // Test Cases Bagian 1 (TC1 - TC4: approveLoan)
    // =========================================================================

    @Test
    @DisplayName("TC1: approveLoan - Very bad credit (< 300) -> Loan should be rejected")
    public void testTC1_approveLoan_veryBadCredit() {
        // Inputs: creditScore = 250, balance = 10000, loanAmount = 5000
        boolean result = service.approveLoan(250, 10000.0, 5000);
        assertFalse(result, "Pinjaman harus ditolak jika creditScore < 300");
    }

    @Test
    @DisplayName("TC2: approveLoan - Bad credit (300-599) but sufficient balance (> 2 * loanAmount) -> Approved")
    public void testTC2_approveLoan_badCreditSufficientBalance() {
        // Inputs: creditScore = 550, balance = 20000, loanAmount = 5000 (20000 > 10000)
        boolean result = service.approveLoan(550, 20000.0, 5000);
        assertTrue(result, "Pinjaman harus disetujui jika saldo > 2 * pinjaman untuk bad credit");
    }

    @Test
    @DisplayName("TC3: approveLoan - Fair credit (600-749) and sufficient balance (> loanAmount) -> Approved")
    public void testTC3_approveLoan_fairCreditSufficientBalance() {
        // Inputs: creditScore = 700, balance = 6000, loanAmount = 5000 (6000 > 5000)
        boolean result = service.approveLoan(700, 6000.0, 5000);
        assertTrue(result, "Pinjaman harus disetujui jika saldo > pinjaman untuk fair credit");
    }

    @Test
    @DisplayName("TC4: approveLoan - Good credit (>= 750), always approved -> Approved")
    public void testTC4_approveLoan_goodCreditAlwaysApproved() {
        // Inputs: creditScore = 800, balance = 1000, loanAmount = 10000
        boolean result = service.approveLoan(800, 1000.0, 10000);
        assertTrue(result, "Pinjaman harus selalu disetujui jika creditScore >= 750");
    }

    // =========================================================================
    // Test Cases Bagian 2 (TC5: calculateInterestRate)
    // =========================================================================

    @Test
    @DisplayName("TC5: calculateInterestRate - High interest rate for very bad credit (< 300) -> 20.0%")
    public void testTC5_calculateInterestRate_veryBadCredit() {
        // Inputs: creditScore = 250
        double rate = service.calculateInterestRate(250);
        assertEquals(20.0, rate, 0.001, "Suku bunga untuk skor < 300 harus 20.0%");
    }

    // =========================================================================
    // Test Cases Tambahan (TC6 - TC15) untuk Mencapai 100% Code Coverage
    // =========================================================================

    @Test
    @DisplayName("TC6: approveLoan - Bad credit (300-599) with insufficient balance (<= 2 * loanAmount) -> Rejected")
    public void testTC6_approveLoan_badCreditInsufficientBalance() {
        // Case A: Saldo pas sama dengan batas kelipatan 2 (boundary testing: 10000 == 5000 * 2) -> false
        assertFalse(service.approveLoan(550, 10000.0, 5000), 
            "Harus ditolak jika balance tepat sama dengan 2 * loanAmount");
        
        // Case B: Saldo di bawah batas kelipatan 2 (8000 < 10000) -> false
        assertFalse(service.approveLoan(550, 8000.0, 5000), 
            "Harus ditolak jika balance < 2 * loanAmount");
    }

    @Test
    @DisplayName("TC7: approveLoan - Fair credit (600-749) with insufficient balance (<= loanAmount) -> Rejected")
    public void testTC7_approveLoan_fairCreditInsufficientBalance() {
        // Case A: Saldo tepat sama dengan loanAmount (boundary testing: 5000 == 5000) -> false
        assertFalse(service.approveLoan(700, 5000.0, 5000), 
            "Harus ditolak jika balance tepat sama dengan loanAmount");
        
        // Case B: Saldo di bawah loanAmount (4000 < 5000) -> false
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
        // Cabang 1: Array kosong (loop tidak dieksekusi)
        assertTrue(service.checkLatePayments(new int[]{}, 2), 
            "Array kosong harus mengembalikan true");

        // Cabang 2: Elemen bernilai nol atau negatif (latePayments[i] <= 0, count tidak bertambah)
        assertTrue(service.checkLatePayments(new int[]{0, -1, 0}, 2), 
            "Elemen tidak lebih besar dari 0 tidak boleh menambah hitungan");

        // Cabang 3: Keterlambatan ada tetapi tidak melebihi batas (count <= maxAllowed)
        assertTrue(service.checkLatePayments(new int[]{1, 2}, 3), 
            "Hitungan keterlambatan 2 <= 3 harus mengembalikan true");
    }

    @Test
    @DisplayName("TC12: checkLatePayments - Loop terminasi dini: count > maxAllowed -> False")
    public void testTC12_checkLatePayments_exceededLimit() {
        // Keterlambatan melebihi batas maksimal yang diizinkan (count = 3 > maxAllowed = 2)
        assertFalse(service.checkLatePayments(new int[]{1, 1, 1, 1}, 2), 
            "Harus mengembalikan false saat keterlambatan melebihi batas");
    }

    @Test
    @DisplayName("TC13: evaluateCustomer - Rejected due to low credit score or insufficient balance")
    public void testTC13_evaluateCustomer_lowCreditOrInsufficientBalance() {
        // approveLoan mengembalikan false karena creditScore < 300
        String result = service.evaluateCustomer(200, 10000.0, 5000, new int[]{0});
        assertEquals("Loan Rejected: Low credit score or insufficient balance", result);
    }

    @Test
    @DisplayName("TC14: evaluateCustomer - Approved (Loan approved & late payments <= 3)")
    public void testTC14_evaluateCustomer_approved() {
        // approveLoan true dan late payments (2 kali) <= 3
        String result = service.evaluateCustomer(800, 10000.0, 5000, new int[]{1, 0, 1});
        assertEquals("Loan Approved", result);
    }

    @Test
    @DisplayName("TC15: evaluateCustomer - Rejected due to too many late payments (> 3)")
    public void testTC15_evaluateCustomer_tooManyLatePayments() {
        // approveLoan true tetapi late payments (4 kali) > 3
        String result = service.evaluateCustomer(800, 10000.0, 5000, new int[]{1, 1, 1, 1});
        assertEquals("Loan Rejected: Too many late payments", result);
    }
}
