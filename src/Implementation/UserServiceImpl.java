package Implementation;

import java.util.List;
import java.util.Optional;

import Interfaces.IUserService;
import Repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User registerUser(User user) {
        if (user == null || user.getEmail() == null || user.getNumber() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("User fields cannot be null.");
        }

        if (isEmailTaken(user.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }

        if (!isPhoneNumberValid(user.getNumber())) {
            throw new RuntimeException("Invalid phone number.");
        }

        user.setPassword(hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean login(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> checkPassword(password, user.getPassword()))
                .orElse(false);
    }

    @Override
    public boolean changePassword(int id, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (checkPassword(oldPassword, user.getPassword())) {
                user.setPassword(hashPassword(newPassword));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isPhoneNumberValid(String number) {
        return number.matches("\\d{10}");
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}