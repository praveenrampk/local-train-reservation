import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TicketSystem {

    protected Map<Integer, Ticket> ticketsBooked = new HashMap<>();
    protected Map<Integer, Ticket> ticketsCanceled = new HashMap<>();
    protected ConcurrentHashMap<Integer, Ticket> waitingList = new ConcurrentHashMap<>();

    protected int[] seatsAvailable = new int[5];

    protected Map<Integer, Integer> partiallyCanceled = new HashMap<>();

    protected int seatsBooked = 0;

    private static TicketSystem instance = null;

    private TicketSystem() {
        Arrays.fill(seatsAvailable, 8);
    }

    public static TicketSystem getInstance() {
        if (instance == null) {
            instance = new TicketSystem();
        }
        return instance;
    }

    protected void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    protected int getSeatsBooked() {
        return seatsBooked;
    }

    public void addToBookedTickets(int newPnr, Ticket ticket) {
        ticketsBooked.put(newPnr, ticket);
    }

    protected void decreaseSeatAvailability(char source, char destination, int seats) {
        for (int i = source - 'A'; i < destination - 'A'; i++) {
            seatsAvailable[i] -= seats;
        }
    }

    protected boolean checkSeatAvailability(char source, char destination, int seats) {
        for (int i = source - 'A'; i < destination - 'A'; i++) {
            if (seatsAvailable[i] < seats) {
                return false;
            }
        }
        return true;
    }

    protected Ticket getTicket(int pnr) {
        Ticket bookedTicket = ticketsBooked.get(pnr);
        return bookedTicket != null ? bookedTicket : waitingList.get(pnr);
    }

    protected void increaseSeatAvailability(char source, char destination, int seats) {
        for (int i = source - 'A'; i < destination - 'A'; i++) {
            seatsAvailable[i] += seats;
        }
    }

    protected void storePartiallyCanceledSeats(int pnr, int seats) {
        partiallyCanceled.merge(pnr, seats, Integer::sum);

    }

    protected void processCancellation(int pnr, Ticket ticket) {

        Integer getSeats = partiallyCanceled.get(pnr);
        int seatsToAdd = getSeats != null ? getSeats : 0;
        ticket.setSeats(ticket.getSeats() + seatsToAdd);
        addToCanceledTickets(pnr, ticket);
    }

    protected void addToCanceledTickets(int pnr, Ticket t1) {
        t1.setTicketStatus(TicketStatus.Canceled);
        ticketsCanceled.put(pnr, t1);
        removeFromBookedTickets(pnr);
    }

    protected void removeFromBookedTickets(int pnr) {
        ticketsBooked.remove(pnr);
    }

    public void printChart() {
        System.out.println("\nTickets Booked :");
        ticketsBooked.values().forEach(System.out::println);

        System.out.println("\nTickets Canceled :");
        ticketsCanceled.values().forEach(System.out::println);

        System.out.println("\nTickets in Waiting List :");
        waitingList.values().forEach(System.out::println);

        System.out.println("\nSeat Availability : " + Arrays.toString(seatsAvailable));

        System.out.println("\n\t\tSeats Booked : ");
        System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8");
        for (char c = 'A'; c <= 'E'; c++) {
            System.out.print(c);
            int seatsBooked = 8 - seatsAvailable[c - 'A'];
            for (int i = 0; i < seatsBooked; i++) {
                System.out.print("\t*");
            }
            System.out.println();
        }
        System.out.println();
    }
}
