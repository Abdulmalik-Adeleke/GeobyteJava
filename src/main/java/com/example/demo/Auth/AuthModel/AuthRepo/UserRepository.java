package com.example.demo.Auth.AuthModel.AuthRepo;

import com.example.demo.Auth.AuthModel.ApplicationUser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<ApplicationUser, String> {

    @Query(value = "update application_user set password = ?2 where email = ?1", nativeQuery = true)
    void updatePassword(String email,String password);
}
    
