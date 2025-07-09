package vn.spring.data.business.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.spring.data.business.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

}
