package ua.spring.dao;

import ua.spring.domain.User;

import java.util.Set;

public interface UserDAO {

    /**
     * This method saves an existing record (row) in a database table.
     *
     * @param user - the current entity of users which will be created.
     */
    void save(User user);

    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param user - entity which will be deleted.
     */
    void remove(User user) ;

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id - id number of the record (row) in the database table..
     * @return - an entity from a database table according to the incoming id number.
   */
    User getById(Long id);


    /**
     * This method returns particular User by e-mail from the database table.
     *
     * @param email - email by which particular User will be find.
     * @return - created users with fields.
     */
    User getByEmail(String email);

    /**
     * This method returns all Users from the database table.
     *
     * @return - all users from database.
    */
    Set<User> getAll() ;
}
