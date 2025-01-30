import java.util.Optional;

public class UserController {
    private final IUserService userService = new UserServiceImpl();

    public void registerUser(User user) {
        try {
            User createdUser = userService.registerUser(user);
            System.out.println("User registered: " + createdUser);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void login(String email, String password) {
        boolean success = userService.login(email, password);
        System.out.println(success ? "Login successful!" : "Invalid email or password.");
    }

    public void changePassword(int id, String oldPassword, String newPassword) {
        boolean success = userService.changePassword(id, oldPassword, newPassword);
        System.out.println(success ? "Password changed successfully!" : "Incorrect old password.");
    }

    public void getAllUsers() {
        userService.getAllUsers().forEach(System.out::println);
    }

    public void deleteUser(int id) {
        userService.deleteUser(id);
        System.out.println("User with ID " + id + " deleted.");
    }
}
