import java.util.Date;
import java.util.Map;

abstract class BaseEvent {
    protected String title;
    protected Venue venue;
    protected Date date;
    protected EventStatus status;
    
    public BaseEvent(String title, Venue venue, Date date) {
        this.title = title;
        this.venue = venue;
        this.date = date;
        this.status = EventStatus.AVAILABLE;
    }
    
    public abstract boolean isTicketAvailable(TicketCategory category);
    public abstract void bookTicket(TicketCategory category);
    
    public String getTitle() { 
        return title; 
    }
    
    public Venue getVenue() { 
        return venue; 
    }
    
    public Date getDate() { 
        return date; 
    }
    
    public EventStatus getStatus() { 
        return status; 
    }
    
    protected void updateEventStatus() {
        // Method untuk update status event, bisa di-override oleh subclass
        this.status = EventStatus.AVAILABLE;
    }
}

public class Event extends BaseEvent {
    public Map<TicketCategory, Integer> ticketAvailability;
    public Map<TicketCategory, Double> ticketPrices;

    public Event(String title, Venue venue, Date date, Map<TicketCategory, Integer> ticketAvailability, Map<TicketCategory, Double> ticketPrices) {
        super(title, venue, date);
        this.ticketAvailability = ticketAvailability;
        this.ticketPrices = ticketPrices;
    }

    public Map<TicketCategory, Integer> getTicketAvailability() { 
        return ticketAvailability; 
    }

    public double getTicketPrice(TicketCategory category) {
        return ticketPrices.getOrDefault(category, 0.0);
    }

    @Override
    public boolean isTicketAvailable(TicketCategory category) {
        return ticketAvailability.getOrDefault(category, 0) > 0;
    }

    @Override
    public void bookTicket(TicketCategory category) {
        if (isTicketAvailable(category)) {
            ticketAvailability.put(category, ticketAvailability.get(category) - 1);
            if (ticketAvailability.values().stream().allMatch(quantity -> quantity == 0)) {
                this.status = EventStatus.SOLD_OUT;
            }
        }
    }
    
    @Override
    protected void updateEventStatus() {
        // Override method dari parent class
        if (ticketAvailability.values().stream().allMatch(quantity -> quantity == 0)) {
            this.status = EventStatus.SOLD_OUT;
        } else {
            this.status = EventStatus.AVAILABLE;
        }
    }
}
