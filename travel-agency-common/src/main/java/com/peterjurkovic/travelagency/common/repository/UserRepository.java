package com.peterjurkovic.travelagency.common.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.peterjurkovic.travelagency.common.model.User;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    Optional<User> findByEmail(String email);
    
    Optional<User> findByPhone(String phone);
}
