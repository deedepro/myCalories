package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);

    User findUsersByUsername(String username);
}
