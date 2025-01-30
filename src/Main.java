import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5433/postgres";
        String user = "postgres";
        String dbPassword = "26120331de";

        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword)) {
            System.out.println("Connected to the database!");
            System.out.println("Welcome to the Hotel Booking System!");

            boolean loggedIn = false;
            int currentUserId = -1;

            while (true) {
                if (!loggedIn) {
                    System.out.println("\nSelect an option:");
                    System.out.println("1. Sign up");
                    System.out.println("2. Log in");
                    System.out.println("3. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice == 1) {

                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter your surname: ");
                        String surname = scanner.nextLine();

                        System.out.print("Enter your email: ");
                        String email = scanner.nextLine();

                        System.out.print("Enter your password: ");
                        String userPassword = scanner.nextLine();

                        System.out.print("Enter your phone number: ");
                        String number = scanner.nextLine();

                        String insertUserSQL =
                                "INSERT INTO users (name, surname, email, password, number) " +
                                        "VALUES (?, ?, ?, ?, ?)";

                        try (PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {
                            pstmt.setString(1, name);
                            pstmt.setString(2, surname);
                            pstmt.setString(3, email);
                            pstmt.setString(4, userPassword);
                            pstmt.setString(5, number);
                            pstmt.executeUpdate();

                            System.out.println("Registration successful!");
                        } catch (SQLException e) {
                            System.out.println("Error during registration: " + e.getMessage());
                        }

                    } else if (choice == 2) {

                        System.out.print("Enter your email: ");
                        String email = scanner.nextLine();

                        System.out.print("Enter your password: ");
                        String userPassword = scanner.nextLine();


                        String loginUserSQL = "SELECT user_id, name, password FROM users WHERE email = ?";

                        try (PreparedStatement pstmt = conn.prepareStatement(loginUserSQL)) {
                            pstmt.setString(1, email);
                            ResultSet rs = pstmt.executeQuery();

                            if (rs.next()) {
                                String storedPassword = rs.getString("password");
                                if (storedPassword.equals(userPassword)) {

                                    loggedIn = true;
                                    currentUserId = rs.getInt("user_id");
                                    String name = rs.getString("name");
                                    System.out.println("Login successful! Welcome, " + name + "!");
                                } else {
                                    System.out.println("Invalid email or password. Please try again.");
                                }
                            } else {
                                System.out.println("Invalid email or password. Please try again.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error during login: " + e.getMessage());
                        }

                    } else if (choice == 3) {

                        System.out.println("Exiting. Goodbye!");
                        break;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }

                }

                else {
                    System.out.println("\nWhat would you like to do?");
                    System.out.println("1. Book a room");
                    System.out.println("2. Settings");
                    System.out.println("3. Logout");
                    System.out.print("Enter your choice: ");
                    int loggedChoice = scanner.nextInt();
                    scanner.nextLine(); // consume leftover newline

                    if (loggedChoice == 1) {

                        System.out.println("\nSelect an option:");
                        System.out.println("1. View all rooms");
                        System.out.println("2. View available rooms");
                        System.out.print("Enter your choice: ");
                        int bookingChoice = scanner.nextInt();
                        scanner.nextLine();


                        if (bookingChoice == 1) {

                            String getAllRoomsSQL = "SELECT room_id, room_number, is_available FROM rooms";
                            try (Statement stmt = conn.createStatement()) {
                                ResultSet rs = stmt.executeQuery(getAllRoomsSQL);
                                System.out.println("\n-- All Rooms --");
                                while (rs.next()) {
                                    int roomId = rs.getInt("room_id");
                                    int roomNumber = rs.getInt("room_number");
                                    boolean isAvailable = rs.getBoolean("is_available");
                                    System.out.printf("RoomID: %d | Number: %d | Available: %b%n",
                                            roomId, roomNumber, isAvailable);
                                }
                            } catch (SQLException e) {
                                System.out.println("Error fetching all rooms: " + e.getMessage());
                            }

                        } else if (bookingChoice == 2) {

                            String getAvailableRoomsSQL =
                                    "SELECT room_id, room_number FROM rooms WHERE is_available = TRUE";
                            try (Statement stmt = conn.createStatement()) {
                                ResultSet rs = stmt.executeQuery(getAvailableRoomsSQL);
                                System.out.println("\n-- Available Rooms --");
                                while (rs.next()) {
                                    int roomId = rs.getInt("room_id");
                                    int roomNumber = rs.getInt("room_number");
                                    System.out.printf("RoomID: %d | Number: %d%n", roomId, roomNumber);
                                }
                            } catch (SQLException e) {
                                System.out.println("Error fetching available rooms: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Invalid choice for rooms. Returning to menu.");
                            continue;
                        }


                        System.out.print("\nEnter the models.Room ID you want to book: ");
                        int selectedRoomId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter the booking date (YYYY-MM-DD): ");
                        String bookingDateStr = scanner.nextLine();
                        LocalDate bookingDate = LocalDate.parse(bookingDateStr);

                        System.out.print("Enter payment amount: ");
                        double paymentAmount = scanner.nextDouble();
                        scanner.nextLine();


                        String checkRoomSQL = "SELECT is_available FROM rooms WHERE room_id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(checkRoomSQL)) {
                            pstmt.setInt(1, selectedRoomId);
                            ResultSet rs = pstmt.executeQuery();
                            if (rs.next()) {
                                boolean isAvailable = rs.getBoolean("is_available");
                                if (!isAvailable) {
                                    System.out.println("models.Room is not available. Please choose another room.");
                                    continue;
                                }
                            } else {
                                System.out.println("models.Room not found. Please try again.");
                                continue;
                            }
                        }


                        String insertBookingSQL =
                                "INSERT INTO bookings (user_id, room_id, booking_date, payment) " +
                                        "VALUES (?, ?, ?, ?)";
                        try (PreparedStatement pstmt = conn.prepareStatement(insertBookingSQL)) {
                            pstmt.setInt(1, currentUserId);
                            pstmt.setInt(2, selectedRoomId);
                            pstmt.setDate(3, Date.valueOf(bookingDate));
                            pstmt.setDouble(4, paymentAmount);
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println("Error inserting booking: " + e.getMessage());
                            continue;
                        }


                        String updateRoomSQL = "UPDATE rooms SET is_available = FALSE WHERE room_id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(updateRoomSQL)) {
                            pstmt.setInt(1, selectedRoomId);
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println("Error updating room availability: " + e.getMessage());
                            continue;
                        }

                        System.out.println("models.Room booked successfully!");

                    } else if (loggedChoice == 2) {

                        System.out.println("\nSettings:");
                        System.out.println("1. Change password");
                        System.out.println("2. Update profile (name, surname, phone)");
                        System.out.print("Enter your choice: ");
                        int settingsChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (settingsChoice == 1) {

                            System.out.print("Enter old password: ");
                            String oldPassword = scanner.nextLine();


                            String getPasswordSQL = "SELECT password FROM users WHERE user_id = ?";
                            String storedPass = null;
                            try (PreparedStatement pstmt = conn.prepareStatement(getPasswordSQL)) {
                                pstmt.setInt(1, currentUserId);
                                ResultSet rs = pstmt.executeQuery();
                                if (rs.next()) {
                                    storedPass = rs.getString("password");
                                }
                            }

                            if (storedPass == null) {
                                System.out.println("Could not find user. Please try again.");
                                continue;
                            }

                            if (!storedPass.equals(oldPassword)) {
                                System.out.println("Incorrect old password. Please try again.");
                                continue;
                            }

                            System.out.print("Enter new password: ");
                            String newPassword = scanner.nextLine();


                            String updatePasswordSQL = "UPDATE users SET password = ? WHERE user_id = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(updatePasswordSQL)) {
                                pstmt.setString(1, newPassword);
                                pstmt.setInt(2, currentUserId);
                                pstmt.executeUpdate();
                                System.out.println("Password updated successfully!");
                            } catch (SQLException e) {
                                System.out.println("Error updating password: " + e.getMessage());
                            }

                        } else if (settingsChoice == 2) {


                            String currentName = "";
                            String currentSurname = "";
                            String currentPhone = "";

                            String getUserSQL = "SELECT name, surname, number FROM users WHERE user_id = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(getUserSQL)) {
                                pstmt.setInt(1, currentUserId);
                                ResultSet rs = pstmt.executeQuery();
                                if (rs.next()) {
                                    currentName = rs.getString("name");
                                    currentSurname = rs.getString("surname");
                                    currentPhone = rs.getString("number");
                                }
                            }

                            System.out.println("Current name: " + currentName);
                            System.out.println("Current surname: " + currentSurname);
                            System.out.println("Current phone: " + currentPhone);


                            System.out.print("Enter new name (leave blank to keep current): ");
                            String newName = scanner.nextLine();
                            if (newName.isEmpty()) newName = currentName;

                            System.out.print("Enter new surname (leave blank to keep current): ");
                            String newSurname = scanner.nextLine();
                            if (newSurname.isEmpty()) newSurname = currentSurname;

                            System.out.print("Enter new phone number (leave blank to keep current): ");
                            String newPhone = scanner.nextLine();
                            if (newPhone.isEmpty()) newPhone = currentPhone;


                            String updateUserSQL =
                                    "UPDATE users SET name = ?, surname = ?, number = ? WHERE user_id = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(updateUserSQL)) {
                                pstmt.setString(1, newName);
                                pstmt.setString(2, newSurname);
                                pstmt.setString(3, newPhone);
                                pstmt.setInt(4, currentUserId);
                                pstmt.executeUpdate();
                                System.out.println("Profile updated successfully!");
                            } catch (SQLException e) {
                                System.out.println("Error updating profile: " + e.getMessage());
                            }
                        }

                    } else if (loggedChoice == 3) {

                        loggedIn = false;
                        currentUserId = -1;
                        System.out.println("You have been logged out.");
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
