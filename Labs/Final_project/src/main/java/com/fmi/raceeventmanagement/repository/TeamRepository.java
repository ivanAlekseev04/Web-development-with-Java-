package com.fmi.raceeventmanagement.repository;

import com.fmi.raceeventmanagement.model.Team;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TeamRepository extends JpaRepository<Team, String> {}