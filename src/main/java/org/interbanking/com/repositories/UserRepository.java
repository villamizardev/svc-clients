package org.interbanking.com.repositories;

import java.util.Optional;

import org.interbanking.com.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findOneByEmail(String email);
}
