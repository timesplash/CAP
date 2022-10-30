package org.dev.backend.repository;


import org.dev.backend.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Basic login system
 */

public interface UserRepository {
    void save(User user);

void delete(int id);

    Optional<User> findByLogin(String userName);

    List<User> findAll();
}
