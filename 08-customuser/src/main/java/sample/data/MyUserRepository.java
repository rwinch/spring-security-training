package sample.data;

import org.springframework.data.repository.CrudRepository;

public interface MyUserRepository extends CrudRepository<MyUser, Long> {
	MyUser findByUsername(String username);
}
