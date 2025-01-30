# 🏨 Hotel Booking System  

## 📌 Project Overview  
This **Hotel Booking System** is a simple Java-based application that allows users to register, log in, view available rooms, book rooms, and manage their profiles. The system interacts with a **PostgreSQL database** to store and retrieve user and room information.  

## 🛠️ Features  
### **User Management**  
- User registration with name, surname, email, password, and phone number.  
- Secure login authentication.  
- Password change functionality.  
- Profile update options (name, surname, phone number).  
- User deletion feature.  

### **Room Management**  
- View **all rooms** and **available rooms** in the hotel.  
- Book a room with payment processing.  
- Automatic update of room availability after booking.  

### **Security**  
- Password hashing for security.  
- Email uniqueness check to prevent duplicate registrations.  
- Validation for phone numbers.  

## 🏗️ Project Structure  
```
📂 Booking/src
 ├── 📄 Main.java               
 ├── 📄 User.java               
 ├── 📄 Room.java               
 ├── 📄 IUserService.java       
 ├── 📄 UserServiceImpl.java    
 ├── 📄 UserRepository.java     
 ├── 📄 RoomRepository.java     
 ├── 📄 UserController.java     
 ├── 📄 README.md               
```

## ⚙️ Technologies Used  
- **Java** (Core logic & OOP)  
- **JDBC (Java Database Connectivity)** for database interaction  
- **PostgreSQL** as the relational database   
