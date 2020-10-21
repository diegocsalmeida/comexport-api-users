package br.net.comexport.api.service;

import java.time.OffsetDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.net.comexport.api.model.User;
import br.net.comexport.api.repository.UsersRepository;
import br.net.comexport.api.repository.filter.UserFilter;
import br.net.comexport.api.service.exceptions.ResourceAlredyExistsException;
import br.net.comexport.api.service.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;

	public User save(User user) {

		User existingUser = usersRepository.findByEmail(user.getEmail());

		if (existingUser != null && !existingUser.equals(user)) {
			throw new ResourceAlredyExistsException("There is already a user with the registered email.");
		}
		
		user.setCreatedAt(OffsetDateTime.now());
		
		return usersRepository.save(user);
	}

	public ResponseEntity<User> findById(Long userId) {
		Optional<User> user = usersRepository.findById(userId);

		if (!user.isPresent()) {
			throw new ResourceNotFoundException("User not found.");
		}

		return ResponseEntity.ok(user.get());
	}

	public Page<User> search(UserFilter userFilter, Pageable pageable) {
		return usersRepository.filter(userFilter, pageable);
	}

	public ResponseEntity<User> update(Long userId, @Valid User user) {
		Optional<User> u = usersRepository.findById(userId);
		if(!u.isPresent()) {
			throw new ResourceNotFoundException("User not found.");
		}
		User userToUpdate = u.get();
		BeanUtils.copyProperties(user, userToUpdate, "id","createdAt", "updatedAt", "email");
		userToUpdate.setUpdatedAt(OffsetDateTime.now());
		usersRepository.save(userToUpdate);
		return ResponseEntity.ok(userToUpdate);
	}

	public void delete(Long userId) {
		Optional<User> u = usersRepository.findById(userId);
		if(!u.isPresent()) {
			throw new ResourceNotFoundException("User not found.");
		}
		User user = u.get();
		usersRepository.delete(user);
	}

}
