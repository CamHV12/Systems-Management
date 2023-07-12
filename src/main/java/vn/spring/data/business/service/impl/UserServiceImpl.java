package vn.spring.data.business.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.spring.data.business.entity.User;
import vn.spring.data.business.repository.UserRepository;
import vn.spring.data.business.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).get();
	}
	
	@Override
	public Long getById(Long id) {
		return userRepository.findById(id).get().getId();
	}
}
