package co.za.eskom.repository;

import co.za.eskom.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
    @Query("select u from Login u where u.username=?1")
    Login findByName(String username);

    @Query("select u from Login u where u.username=?1 and u.password=?2")
    Login authenticate(String username,String password);


}
