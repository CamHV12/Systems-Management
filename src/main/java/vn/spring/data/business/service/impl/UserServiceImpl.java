package vn.spring.data.business.service.impl;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.spring.data.business.entity.User;
import vn.spring.data.business.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).get();
	}

	public Long getById(Long id) {
		return userRepository.findById(id).get().getId();
	}

	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	public String getByUsername(String username) {
		return userRepository.findByUsername(username).get().getUsername();
	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Trả về UserDetails với username, password và quyền (roles)
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Đã mã hóa BCrypt
                .build();
	}
}
