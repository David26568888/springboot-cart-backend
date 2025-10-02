package com.example.demo.cart.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.cart.model.entity.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	List<User> findByUsername(String username);//屬於多筆查詢
	
	Optional<User> findFirstByUsername(String username);//屬於單筆查詢
	
	@Query(value = "select * from user where username = :username", nativeQuery=true)
	Optional<User> findByUsernameNative(@Param("username") String username);
}
