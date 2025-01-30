🏨 Hotel Booking System
📌 Project Overview
This Hotel Booking System is a simple Java-based application that allows users to register, log in, view available rooms, book rooms, and manage their profiles. 
The system interacts with a PostgreSQL database to store and retrieve user and room information.

🛠️ Features
User Management:
● User registration with name, surname, email, password, and phone number.
● Secure login authentication.
● Password change functionality.
● Profile update options (name, surname, phone number).
● User deletion feature.
Room Management:
● View all rooms and available rooms in the hotel.
● Book a room with payment processing.
● Automatic update of room availability after booking.
Security:
● Password hashing for security.
● Email uniqueness check to prevent duplicate registrations.
● Validation for phone numbers.

🏗️ Project Structure:
📂 Booking: 
 ├── 📄 Main.java               # Entry point of the application
 ├── 📄 User.java               # User model class
 ├── 📄 Room.java               # Room model class
 ├── 📄 IUserService.java       # User service interface
 ├── 📄 UserServiceImpl.java    # Implementation of user service logic
 ├── 📄 UserRepository.java     # In-memory user database (for now)
 ├── 📄 RoomRepository.java     # Handles room-related database operations
 ├── 📄 UserController.java     # Controller for handling user interactions
 ├── 📄 README.md               # Project documentation

 ⚙️ Technologies Used
● Java (Core logic & OOP)
● JDBC (Java Database Connectivity) for database interaction
● PostgreSQL as the relational database
