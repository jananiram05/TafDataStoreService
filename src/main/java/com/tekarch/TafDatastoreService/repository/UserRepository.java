package com.tekarch.TafDatastoreService.repository;

import com.tekarch.TafDatastoreService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
