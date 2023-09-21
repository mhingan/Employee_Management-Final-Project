/**@author Mihaita Hingan*/
package com.example.proiectfinal.repository;

import com.example.proiectfinal.model.DayOffRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayOffRequestRepository extends JpaRepository<DayOffRequest, Integer> {
    List<DayOffRequest> findByUserId(int userId);
}
