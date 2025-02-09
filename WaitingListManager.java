public class WaitingListManager {
    private TicketSystem ticketSystem;

    WaitingListManager() {
        this.ticketSystem = TicketSystem.getInstance();
    }

    protected void processWaitingList() {
        for (Ticket waiting : ticketSystem.waitingList.values()) {
            boolean isValid;
            char source = waiting.getSource(), destination = waiting.getDestination();
            int seats = waiting.getSeats();

            isValid = ticketSystem.checkSeatAvailability(source, destination, seats);

            if (isValid) {
                ticketSystem.decreaseSeatAvailability(source, destination, seats);
                ticketSystem.setSeatsBooked(ticketSystem.getSeatsBooked() - seats);
                updateTicketToBookingList(waiting);
            }
        }
    }

    private void updateTicketToBookingList(Ticket waiting) {
        int pnrNumber = waiting.getPnrNumber();
        waiting.setTicketStatus(TicketStatus.Booked);
        ticketSystem.addToBookedTickets(pnrNumber, waiting);
        ticketSystem.waitingList.remove(pnrNumber);
        System.out.println("Booking Confirmed for pnr number " + pnrNumber);
    }

    protected void waitingListEntry(char source, char destination, int seats) {
        WaitingList wl = new WaitingList(source, destination, seats);
        wl.execute();
    }

    protected void waitingListRemoval(int pnr, int seatsToCancel, Ticket waitingListTicket) {
        int seatsBooked = waitingListTicket.getSeats();
        if (seatsBooked == seatsToCancel) {
            ticketSystem.waitingList.remove(pnr);
            System.out.println("Canceled ticket in waiting list with pnr " + pnr);
        } else {
            waitingListTicket.setSeats(waitingListTicket.getSeats() - seatsToCancel);
            System.out.println("Partially canceled ticket in waiting list with pnr " + pnr);
        }

        ticketSystem.setSeatsBooked(ticketSystem.getSeatsBooked() - seatsToCancel);
    }
}
