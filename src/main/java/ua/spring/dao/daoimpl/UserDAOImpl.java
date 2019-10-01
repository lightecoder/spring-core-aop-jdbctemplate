package ua.spring.dao.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.spring.DataBase;
import ua.spring.dao.UserDAO;
import ua.spring.domain.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component

public class UserDAOImpl implements UserDAO {
    private static final String INSERT = "INSERT INTO user (first_name,last_name,email,date_of_birth)\n" +
            "VALUES (?,?,?,?)";
    private static final String SELECT = "select * from user";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update(INSERT,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getDateOfBirth());
    }

    @Override
    public void remove(User user) {
        DataBase.users.remove(user.getId());
    }

    @Override
    public User getById(Long id) {
        return DataBase.users.get(id);
    }

    @Override
    public User getByEmail(String email) {
        return findUserByEmail(email);
    }

    @Override
    public Set<User> getAll() {
        List<User> users = jdbcTemplate.query(SELECT,
                (rs, rowNum) -> {
                    Integer userId = rs.getInt("user_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email = rs.getString("email");
                    String dateOfBirth = rs.getString("date_of_birth");
                    return new User(userId, firstName, lastName, email, dateOfBirth);
                  });
        return new HashSet<>(users);
    }

    private User findUserByEmail(String email) {
        List<User> list = DataBase.users.values().stream().
                filter(x -> x.getEmail().equals(email))
                .collect(Collectors.toList());
        if (list.size() != 0) {
            return list.get(0);
        }

        return null;
    }
}
