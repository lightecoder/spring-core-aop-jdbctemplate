package ua.spring.services;

import ua.spring.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding users by email
     * 
     * @param email
     *            Email of the users
     * @return found users or <code>null</code>
     */
    public @Nullable User getUserByEmail(@Nonnull String email);

}
