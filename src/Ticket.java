import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Ticket {
    public Event event;
    public TicketCategory category;
    public User user;

    public Ticket(Event event, TicketCategory category, User user) {
        this.event = event;
        this.category = category;
        this.user = user;
    }

    public void printTicket() {
        String fullName = user.getName();
        String[] splitNama = fullName.split(" ");
        String nama = splitNama[splitNama.length - 1];

        String kategori = category.toString();
        String venue = event.getVenue().getName() + ", " + event.getVenue().getLocation();
        String tanggal = new SimpleDateFormat("dd-MM-yyyy").format(event.getDate());

        System.out.println("+--------------+------------------+--------------------------+--------------+");
        System.out.println("|     Nama     | Kategori Tiket   |          Venue           |   Tanggal    |");
        System.out.println("+--------------+------------------+--------------------------+--------------+");
        System.out.printf("| %-12s | %-16s | %-24s | %-12s |\n", nama, kategori, venue, tanggal);
        System.out.println("+--------------+------------------+--------------------------+--------------+");
    }

    public void saveTicketToFile() {
        String fullName = user.getName();
        String[] splitNama = fullName.split(" ");
        String nama = splitNama[splitNama.length - 1];

        String kategori = category.toString();
        String venue = event.getVenue().getName() + ", " + event.getVenue().getLocation();
        String tanggal = new SimpleDateFormat("dd-MM-yyyy").format(event.getDate());

        String fileName = "Tiket_" + nama + "_" + event.getTitle().replaceAll("\\s+", "_") + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("+--------------+------------------+--------------------------+--------------+\n");
            writer.write("|     Nama     | Kategori Tiket   |          Venue           |   Tanggal    |\n");
            writer.write("+--------------+------------------+--------------------------+--------------+\n");
            writer.write(String.format("| %-12s | %-16s | %-24s | %-12s |\n", nama, kategori, venue, tanggal));
            writer.write("+--------------+------------------+--------------------------+--------------+\n");
            System.out.println("Tiket telah disimpan di file: " + fileName);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan tiket: " + e.getMessage());
        }
    }
}