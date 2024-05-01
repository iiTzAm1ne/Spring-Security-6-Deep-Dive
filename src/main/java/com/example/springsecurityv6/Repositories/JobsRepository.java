package com.example.springsecurityv6.Repositories;

import com.example.springsecurityv6.Models.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepository extends JpaRepository<Jobs, Long> {
}
