package com.peterjurkovic.travelagency.common.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.peterjurkovic.travelagency.common.model.Booking;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, String>  {

}
