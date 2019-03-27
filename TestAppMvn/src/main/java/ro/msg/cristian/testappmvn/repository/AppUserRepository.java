package ro.msg.cristian.testappmvn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.msg.cristian.testappmvn.domain.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("select u.currency from AppUser u where u.id = :idUser")
    Double getCurrency(@Param("idUser") Long idUser);

    @Query("select u from AppUser u where u.username = :username")
    AppUser getAppUserByUsername(@Param("username") String username);
}
