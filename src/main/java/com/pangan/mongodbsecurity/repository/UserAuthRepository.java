package com.pangan.mongodbsecurity.repository;

import com.pangan.mongodbsecurity.model.UserAuth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends MongoRepository<UserAuth, String> {
    Optional<UserAuth> findByUsername(String username);
    Boolean existsByUsername(String username);
}
