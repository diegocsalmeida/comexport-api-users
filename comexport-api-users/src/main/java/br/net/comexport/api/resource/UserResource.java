package br.net.comexport.api.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.net.comexport.api.model.User;
import br.net.comexport.api.repository.filter.UserFilter;
import br.net.comexport.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST User")
@RestController
@RequestMapping("/user")
public class UserResource {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Traz a listagem de usuarios cadastrados, pode-se usar filtros para busca por e-mail, nome ou data de nascimento.")
	@GetMapping
	public Page<User> search(UserFilter userFilter, Pageable pageable) {
		return userService.search(userFilter ,pageable);
	}

	@ApiOperation(value = "Faz a busca de um usuario especifico pelo ID.")
	@GetMapping("/{userId}")
	public ResponseEntity<User> findById(@PathVariable Long userId) {
		return userService.findById(userId);
	}
	
	@ApiOperation(value = "Faz o cadastro de um usuário.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@Valid @RequestBody User user) {
		return userService.save(user);
	}

	@ApiOperation(value = "Faz a atualização de um usuário.")
	@PutMapping("/{userId}")
	public ResponseEntity<User> update(@PathVariable Long userId, @Valid @RequestBody User user) {
		return userService.update(userId, user);
	}

	@ApiOperation(value = "Faz a deleção de um usário.")
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long userId) {
		userService.delete(userId);
	}
}
