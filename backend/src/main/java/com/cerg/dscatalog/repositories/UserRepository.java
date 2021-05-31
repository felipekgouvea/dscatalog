package com.cerg.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cerg.dscatalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
