import java.util.Date;
import java.util.Map;

public class Event {
    public String title;
    public Venue venue;
    public Date date;
    public EventStatus status;
    public Map<TicketCategory, Integer> ticketAvailability;
    public Map<TicketCategory, Double> ticketPrices;

    public Event(String title, Venue venue, Date date, Map<TicketCategory, Integer> ticketAvailability, Map<TicketCategory, Double> ticketPrices) {
        this.title = title;
        this.venue = venue;
        this.date = date;
        this.ticketAvailability = ticketAvailability;
        this.ticketPrices = ticketPrices;
        this.status = EventStatus.AVAILABLE;
    }

    public String getTitle() { return title; }
    public Venue getVenue() { return venue; }
    public Date getDate() { return date; }
    public EventStatus getStatus() { return status; }
    public Map<TicketCategory, Integer> getTicketAvailability() { return ticketAvailability; }

    public double getTicketPrice(TicketCategory category) {
        return ticketPrices.getOrDefault(category, 0.0);
    }

    public boolean isTicketAvailable(TicketCategory category) {
        return ticketAvailability.getOrDefault(category, 0) > 0;
    }

    public void bookTicket(TicketCategory category) {
        if (isTicketAvailable(category)) {
            ticketAvailability.put(category, ticketAvailability.get(category) - 1);
            if (ticketAvailability.values().stream().allMatch(quantity -> quantity == 0)) {
                this.status = EventStatus.SOLD_OUT;
            }
        }
    }
}