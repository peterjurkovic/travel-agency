package com.peterjurkovic.travelagency.client.homepage;

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.peterjurkovic.travelagency.common.model.Trip;
import com.peterjurkovic.travelagency.common.repository.TripRepository;

@Component
class HomePageHelper {
    
    private final static int HOMEPAGE_COLUMN_LENGHT = 3;
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final TripRepository tripRepository;
    
    @Autowired
    public HomePageHelper(TripRepository tripRepository){
        this.tripRepository = requireNonNull(tripRepository);
    }
    
    public List<Trip> findThreeRecentTrips(){
        return findSortedBy("createdAt");
    }
    
    public List<Trip> findThreeMostPopularTrips(){
        return findSortedBy("purchases");
    }
    
 
    
    private List<Trip> findSortedBy(String property){
        Sort sort = Sort.by(Direction.DESC, property);
        PageRequest request = PageRequest.of(0,  HOMEPAGE_COLUMN_LENGHT, sort);
        Page<Trip> page = tripRepository.findAll(request);
        if (log.isDebugEnabled()) log.debug("Result for {} page: {} ", property, page );
        return page.getContent();
    }
}
