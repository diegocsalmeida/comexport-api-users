package br.net.comexport.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.comexport.api.model.User;


@Repository
public interface UsersRepository extends JpaRepository<User, Long>, UsersRepositoryQuery{
	List<User> findByNameContaining(String name);
	User findByEmail(String email);
}
