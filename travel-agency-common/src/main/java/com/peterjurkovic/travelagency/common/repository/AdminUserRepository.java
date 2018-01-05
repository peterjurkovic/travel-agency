package com.peterjurkovic.travelagency.common.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.peterjurkovic.travelagency.common.model.AdminUser;

@Repository
public interface AdminUserRepository extends PagingAndSortingRepository<AdminUser, String> {

    Optional<AdminUser> findByEmail(String email);
    
    Optional<AdminUser> findByPhone(String phone);
}

