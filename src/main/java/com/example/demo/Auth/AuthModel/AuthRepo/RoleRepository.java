package com.example.demo.Auth.AuthModel.AuthRepo;

import com.example.demo.Auth.AuthModel.Role;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Integer> {

    Role findByName(String role);
    
}
