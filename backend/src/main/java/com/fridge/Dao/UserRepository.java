package com.fridge.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fridge.Dto.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
