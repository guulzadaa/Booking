import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private int nextId = 1; // Генерация ID

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(int id) {
        final int userId = id; // Создаём effectively final переменную
        return users.stream().filter(user -> user.getId() == userId).findFirst();
    }


    public Optional<User> findByEmail(String email) {
        final String userEmail = email; // Создаём effectively final переменную
        return users.stream().filter(user -> user.getEmail().equalsIgnoreCase(userEmail)).findFirst();
    }


    public User save(User user) {
        if (user.getId() == 0) { // Новый пользователь
            user = new User(nextId++, user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getNumber());
            users.add(user);
        } else { // Обновление пользователя
            final int userId = user.getId(); // Создаём effectively final переменную
            User finalUser = user;
            users.replaceAll(u -> u.getId() == userId ? finalUser : u);
        }
        return user;
    }


    public void deleteById(int id) {
        users.removeIf(user -> user.getId() == id);
    }
}
