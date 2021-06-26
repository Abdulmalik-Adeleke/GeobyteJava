package com.example.demo.Repository;

import com.example.demo.Models.ConfirmationToken;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken,String> {

    @Query(value = "select Count(*) from confirmation_token where email = ?1 and token = ?2", nativeQuery = true)
    Integer emailExists(String email,String token);
}
