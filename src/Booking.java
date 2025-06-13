import java.util.UUID;

public class Booking {
    private Event event;
    private TicketCategory category;
    private User user;
    private double bookingAmount;
    private String kodeBooking;

    public Booking(Event event, TicketCategory category, User user) {
        this.event = event;
        this.category = category;
        this.user = user;
        this.bookingAmount = event.getTicketPrice(category);
        this.kodeBooking = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getKodeBooking() {
        return kodeBooking;
    }

    public double getBookingAmount() {
        return bookingAmount;
    }

    public boolean checkAvailability() {
        if (event.isTicketAvailable(category)) {
            return true;
        } else {
            System.out.println("Maaf, tiket untuk kategori " + category + " sudah habis.");
            return false;
        }
    }

    public Ticket confirmAndGenerateTicket() {
        event.bookTicket(category);
        Ticket ticket = new Ticket(event, category, user);

        System.out.println("\nSelamat! Pemesanan tiket Anda telah terkonfirmasi.");
        ticket.printTicket();
        ticket.saveTicketToFile();

        return ticket;
    }
}