package com.peterjurkovic.travelagency.common.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.peterjurkovic.travelagency.common.model.Booking;
import com.peterjurkovic.travelagency.common.model.User;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, String>  {

    List<Booking> findByUser(User user);
}
