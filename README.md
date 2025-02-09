# Train Ticket Booking System

This is a simple Java-based train ticket booking and cancellation system.

## Features

- **Book Tickets**: Book tickets by choosing the source, destination, and number of seats.
- **Cancel Tickets**: Cancel tickets using the PNR number and specify how many seats to cancel (partial cancellation is supported).
- **View Ticket Status**: Shows booked, canceled, and waiting list tickets.
- **Seat Availability Chart**: View seat availability at each station.

## How to Run

1. Download or clone the repository.
2. Open the project in your IDE or use the command line.
3. Compile and run the `Main.java` file.

```bash
javac *.java
java Main
```

## Example Usage

1. **Booking a Ticket**:

```text
1. Book Ticket
2. Cancel Ticket
3. Print chart
Choose any one: 1
Enter the source: A
Enter the destination: B
Enter no of seats to book: 2
Ticket Booked! Your PNR number is 1
```

2. **Cancelling a Ticket**:

```text
1. Book Ticket
2. Cancel Ticket
3. Print chart
Choose any one: 2
Enter the PNR number: 1
Enter no of seats to cancel: 1
Cancelled Ticket PNR 1
```

3. **Printing the Seat Availability Chart**:

```text
1. Book Ticket
2. Cancel Ticket
3. Print chart
Choose any one: 3
Tickets Booked:
Ticket{pnrNumber=1, source=A, destination=B, seats=1, ticketStatus=Booked}

Seat Availability: [8, 8, 8, 8, 8]
```
