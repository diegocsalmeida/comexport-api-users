package br.net.comexport.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.net.comexport.api.model.User;
import br.net.comexport.api.repository.filter.UserFilter;

public interface UsersRepositoryQuery {

	public Page<User> filter(UserFilter userFilter, Pageable pageable);
}
