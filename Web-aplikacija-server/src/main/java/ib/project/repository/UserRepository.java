package ib.project.repository;

import ib.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("Select u from User u where LOWER(u.email) like LOWER(Concat('%', :email, '%'))")
    public List<User> getUsers(String email);
}
