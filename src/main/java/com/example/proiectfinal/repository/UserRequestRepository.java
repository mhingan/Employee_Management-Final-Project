/**@author Mihaita Hingan*/
package com.example.proiectfinal.repository;

import com.example.proiectfinal.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest, Integer> {
}
