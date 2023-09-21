/**@author Mihaita Hingan*/
package com.example.proiectfinal.repository;

import com.example.proiectfinal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByCnp(String CNP);

    Optional<Object> findByEmail(String email);
}
