package com.kodewala.locationupdator.service;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodewala.locationupdator.entity.LocationEntity;
import com.kodewala.locationupdator.repository.LocationRepository;
import com.kodewala.locationupdator.request.LocationUpdateRequest;

@Service
public class LocationService {

	@Autowired
	LocationRepository locationRepository;
	
	public Boolean updateLocation( String user, String deviceID, String ln, String lt) {
	
		boolean status = false;
		
		LocationEntity entity = new LocationEntity();
		
		entity.setDeviceID(deviceID);
		entity.setUser(user);
		entity.setLn(ln);;
		entity.setLt(lt);
		
		LocationEntity response = locationRepository.save(entity);
	
		if (response.getId() > 0) {
			
			status = true;
		}
		
		return status;
	}
	
	public Boolean putLocation(int id, String user, String deviceID, String ln, String lt) {
		
		LocationEntity entity = locationRepository.findById(id).orElseThrow();
		entity.setUser(user);
		entity.setDeviceID(deviceID);
		entity.setLn(ln);;
		entity.setLt(lt);
		
		LocationEntity response = locationRepository.save(entity);
		
		return response.getId() > 0;
	}
	
	
public Boolean patchLocation(int id, LocationUpdateRequest request) {
		
		LocationEntity entity = locationRepository.findById(id).orElseThrow();
		
		if (request.getUser() != null) {
			entity.setUser(request.getUser());
		}
		
		if (request.getDeviceId() != null) {
			entity.setDeviceID(request.getDeviceId());
		}
		
		if (request.getLn() != null) {
			entity.setLn(request.getLn());
		}
		
		if (request.getLt() != null) {
			entity.setLt(request.getLt());
		}
		
		LocationEntity response = locationRepository.save(entity);
		
		return response.getId() > 0;
	}


    public void findAllRecords(int page, int record) {
	
    	Pageable pageable = PageRequest.of(page, record);
    	Page<LocationEntity> pages = locationRepository.findAll(pageable);
    	
    	for(LocationEntity location : pages.getContent()) {
    		
    		System.out.println(location.getDeviceID() + " " + location.getId() + " " + location.getUser());
    	}
}
}
