package com.pietkiewicz.bankapp.repository;

import com.pietkiewicz.bankapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}