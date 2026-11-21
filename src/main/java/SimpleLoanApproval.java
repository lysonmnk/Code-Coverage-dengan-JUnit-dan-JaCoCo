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
