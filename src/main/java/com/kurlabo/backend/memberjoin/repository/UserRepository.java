package com.kurlabo.backend.memberjoin.repository;

import com.kurlabo.backend.memberjoin.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
