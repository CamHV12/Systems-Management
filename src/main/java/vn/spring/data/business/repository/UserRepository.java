package vn.spring.data.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.spring.data.business.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
