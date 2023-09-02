package com.example.transport2.repository;

import com.example.transport2.model.Stop;
import com.example.transport2.model.StopTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StopTimeRepository extends JpaRepository<StopTime, Integer> { //<тип сущности, тип айдишника>

}
