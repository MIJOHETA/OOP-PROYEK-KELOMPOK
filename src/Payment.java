import java.util.Random;
import java.util.Scanner;

public class Payment {
    public Booking booking;
    public double amount;
    public PaymentStatus status;
    Bank selectedBank;
    public String virtualAccountNumber;

    public Payment(Booking booking, double amount) {
        this.booking = booking;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public PaymentStatus getStatus() {
        return status;
    }
    
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void guidePayment(Scanner scanner) {
        System.out.println("\nPilih bank untuk transfer:");
        int index = 1;
        for (Bank bank : Bank.values()) {
            System.out.println(index++ + ". " + bank.getDisplayName());
        }
        System.out.print("Pilihan Anda (nomor): ");
        
        int bankChoice;
        try {
            bankChoice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Proses pembayaran dibatalkan.");
            this.status = PaymentStatus.FAILED;
            return;
        }

        if (bankChoice >= 0 && bankChoice < Bank.values().length) {
            this.selectedBank = Bank.values()[bankChoice];
            this.virtualAccountNumber = generateVirtualAccountNumber();
            
            System.out.println("\n--- INSTRUKSI PEMBAYARAN ---");
            System.out.println("Silakan lakukan pembayaran ke:");
            System.out.println("Bank Tujuan: " + this.selectedBank.getDisplayName());
            System.out.println("Nomor Virtual Account: " + this.virtualAccountNumber);
            System.out.printf("Total Pembayaran: Rp%,.2f\n", this.amount);
            System.out.println("\nSetelah membayar, silakan lakukan verifikasi melalui menu utama.");
        } else {
            System.out.println("Pilihan bank tidak valid. Proses pembayaran dibatalkan.");
            this.status = PaymentStatus.FAILED;
        }
    }

    public String generateVirtualAccountNumber() {
        Random rand = new Random();
        return String.format("8808%04d%04d%04d", rand.nextInt(10000), rand.nextInt(10000), rand.nextInt(10000));
    }
}