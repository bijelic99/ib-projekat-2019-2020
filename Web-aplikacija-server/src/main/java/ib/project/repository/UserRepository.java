package ib.project.repository;

import ib.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("Select u from User u where LOWER(u.email) like LOWER(Concat('%', :email, '%'))")
    List<User> getUsers(String email);

    @Query("Select u from User u where u.id = :id")
    Optional<User> getByCustomId(@Param("id") Integer id);

    @Query("Select u from User u where u.email = :email")
    Optional<User> getByUsername(@Param("email") String email);

    @Query("Select u from User u where u.active = false")
    List<User> getByActive();
}
