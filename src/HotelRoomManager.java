import java.util.*;
import java.text.*;

public class HotelRoomManager {
    private TreeMap<Date, TreeMap<Integer, String>> roomAvailability;
    private Scanner scanner;

    public HotelRoomManager() {
        roomAvailability = new TreeMap<>();
        scanner = new Scanner(System.in);
    }

    public void updateRoomAvailability() {
        System.out.println("\nUpdate Room Availability:");
        System.out.print("Enter date in format yyyy-MM-dd: ");
        String dateString = scanner.nextLine();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);

            // Prevent booking for past dates
            Date today = dateFormat.parse(dateFormat.format(new Date()));
            if (date.before(today)) {
                System.out.println("Cannot book rooms for past dates.");
                return;
            }

            if (!roomAvailability.containsKey(date)) {
                roomAvailability.put(date, new TreeMap<>());
            }

            System.out.print("How many updates do you want to make? ");
            int updates = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < updates; i++) {
                System.out.print("Enter room number: ");
                int roomNumber = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter status (Available/Booked): ");
                String status = scanner.nextLine();

                TreeMap<Integer, String> rooms = roomAvailability.get(date);

                if (rooms.containsKey(roomNumber) &&
                    rooms.get(roomNumber).equalsIgnoreCase("Booked") &&
                    status.equalsIgnoreCase("Booked")) {
                    System.out.println("Room " + roomNumber + " is already booked on " + dateString);
                } else {
                    rooms.put(roomNumber, status);
                    System.out.println("Room availability updated successfully!");
                }
            }

        } catch (ParseException e) {
            System.out.println("Invalid date format!");
        }
    }

    public void cancelBooking() {
        System.out.println("\nCancel Booking:");
        System.out.print("Enter date in format yyyy-MM-dd: ");
        String dateString = scanner.nextLine();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);

            TreeMap<Integer, String> rooms = roomAvailability.get(date);
            if (rooms != null) {
                System.out.print("Enter room number to cancel booking: ");
                int roomNumber = scanner.nextInt();
                scanner.nextLine();

                if (rooms.containsKey(roomNumber) &&
                    rooms.get(roomNumber).equalsIgnoreCase("Booked")) {
                    rooms.put(roomNumber, "Available");
                    System.out.println("Booking cancelled successfully!");
                } else {
                    System.out.println("Room was not booked on that date.");
                }
            } else {
                System.out.println("No records found for that date.");
            }

        } catch (ParseException e) {
            System.out.println("Invalid date format!");
        }
    }

    public void filterRoomsByStatus() {
        System.out.println("\nView Rooms by Status:");
        System.out.print("Enter date in format yyyy-MM-dd: ");
        String dateString = scanner.nextLine();
        System.out.print("Enter status to filter (Available/Booked): ");
        String statusFilter = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);

            TreeMap<Integer, String> rooms = roomAvailability.get(date);

            if (rooms != null) {
                System.out.println("Rooms with status '" + statusFilter + "' on " + dateString + ":");
                boolean found = false;
                for (Map.Entry<Integer, String> entry : rooms.entrySet()) {
                    if (entry.getValue().equalsIgnoreCase(statusFilter)) {
                        System.out.println("Room Number: " + entry.getKey());
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No rooms with status '" + statusFilter + "'.");
                }
            } else {
                System.out.println("No rooms available for " + dateString);
            }

        } catch (ParseException e) {
            System.out.println("Invalid date format!");
        }
    }

    public void checkRoomAvailability() {
        System.out.println("\nCheck Room Availability:");
        System.out.print("Enter date in format yyyy-MM-dd: ");
        String dateString = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);

            TreeMap<Integer, String> rooms = roomAvailability.get(date);

            if (rooms != null) {
                System.out.println("Room availability for " + dateString + ":");
                for (Map.Entry<Integer, String> entry : rooms.entrySet()) {
                    System.out.println("Room Number: " + entry.getKey() +
                                       ", Status: " + entry.getValue());
                }
            } else {
                System.out.println("No rooms available for " + dateString);
            }

        } catch (ParseException e) {
            System.out.println("Invalid date format!");
        }
    }

    public void displayRoomAvailability() {
        System.out.println("\nAll Room Availability:");
        System.out.println("------------------------------------------");
        System.out.printf("| %-30s | %-12s | %-10s |\n", "Date", "Room Number", "Status");
        System.out.println("------------------------------------------");

        for (Map.Entry<Date, TreeMap<Integer, String>> entry : roomAvailability.entrySet()) {
            Date date = entry.getKey();
            TreeMap<Integer, String> rooms = entry.getValue();

            for (Map.Entry<Integer, String> roomEntry : rooms.entrySet()) {
                int roomNumber = roomEntry.getKey();
                String status = roomEntry.getValue();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy");
                String formattedDate = dateFormat.format(date);

                System.out.printf("| %-30s | %-12d | %-10s |\n", formattedDate, roomNumber, status);
            }
        }

        System.out.println("------------------------------------------");
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n1. Update Room Availability");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Check Room Availability");
            System.out.println("4. Display All Room Availability");
            System.out.println("5. Filter Rooms by Status");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    updateRoomAvailability();
                    break;
                case 2:
                    cancelBooking();
                    break;
                case 3:
                    checkRoomAvailability();
                    break;
                case 4:
                    displayRoomAvailability();
                    break;
                case 5:
                    filterRoomsByStatus();
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number from 1 to 6.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        HotelRoomManager manager = new HotelRoomManager();
        manager.start();
    }
}
