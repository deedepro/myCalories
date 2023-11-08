package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {
    boolean existsByUsername(String username);
}
