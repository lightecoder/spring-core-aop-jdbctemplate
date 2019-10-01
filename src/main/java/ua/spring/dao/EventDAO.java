package ua.spring.dao;

import ua.spring.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public interface EventDAO {
    @Nullable
    Event getByName(@Nonnull String name);

    void save(@Nonnull Event object);


    void remove(@Nonnull Event object);


    Event getById(@Nonnull Long id);

    @Nonnull
    Collection<Event> getAll();
}
