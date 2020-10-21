package br.net.comexport.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.net.comexport.api.model.User;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UsersRepositoryTest {
	
	@Autowired
    private UsersRepository usersRepository;
    
    @Test
    void injectedComponentsAreNotNull(){
      assertThat(usersRepository).isNotNull();
    }
    
    @Test
    public void whenCreate_thenPersistData () {
    	LocalDate date = LocalDate.parse("1980-01-01");
        User user = new User("Diego", "diego@mail.com", date);
        user = this.usersRepository.save(user);
        
        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo("Diego");
        assertThat(user.getEmail()).isEqualTo("diego@mail.com");
        assertThat(user.getBirthDate()).isEqualTo("1980-01-01");

    }
    
    @Test
    public void whenDelete_thenRemoveData () {
    	LocalDate date = LocalDate.parse("1980-01-01");
        User user = new User("Diego", "diego@mail.com", date);
        user = this.usersRepository.save(user);
        usersRepository.delete(user);
        assertThat(usersRepository.findById(user.getId())).isEmpty();

    }
    
    @Test
    public void whenUpdate_thenChangeAndPersistData () {
    	LocalDate date = LocalDate.parse("1980-01-01");
        User user = new User("Diego", "diego.teste@email.com",date);
        user = this.usersRepository.save(user);
        
        user.setName("Diego 2");
        user.setEmail("diego.teste2@email.com");
        user = this.usersRepository.save(user);
        
        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo("Diego 2");
        assertThat(user.getEmail()).isEqualTo("diego.teste2@email.com");
        
    }
  
}

