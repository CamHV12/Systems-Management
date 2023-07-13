package vn.spring.data.business.service;

import java.util.List;

import vn.spring.data.business.entity.User;

public interface UserService {

	User getUserById(Long id);

	User saveUser(User user);

	Long getById(Long id);

	List<User> getAllUser();

}
