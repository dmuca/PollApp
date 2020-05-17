package pl.com.muca.server.usercontroller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
