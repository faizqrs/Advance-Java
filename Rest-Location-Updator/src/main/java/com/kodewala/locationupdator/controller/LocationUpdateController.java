package com.kodewala.locationupdator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodewala.locationupdator.request.LocationUpdateRequest;
import com.kodewala.locationupdator.response.LocationResponse;
import com.kodewala.locationupdator.service.LocationService;

@RestController
@RequestMapping("api/user/location")
public class LocationUpdateController {

	@Autowired
	LocationService locationService;
	
	@PostMapping("update")
	public ResponseEntity<LocationResponse> updateLocation(@RequestBody LocationUpdateRequest updateRequest) {
		
		boolean status = locationService.updateLocation(updateRequest.getUser(), updateRequest.getDeviceId(), updateRequest.getLn(), updateRequest.getLt());
		
		LocationResponse response = new LocationResponse();
		response.setStatus(""+status);
		response.setMessage("Location Updated");
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<LocationResponse> putLocation(@PathVariable int id,
			@RequestBody LocationUpdateRequest request) {
		
		boolean status = locationService.putLocation(id, request.getUser(), request.getDeviceId(), request.getLn(), request.getLt());
		
		LocationResponse response = new LocationResponse();
		response.setStatus(""+status);
		response.setMessage("User Updated Successfully");
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("partial/{id}")
	public ResponseEntity<LocationResponse> patchLocation(@PathVariable int id,
			@RequestBody LocationUpdateRequest request) {
		
		boolean status = locationService.patchLocation(id, request);
		
		LocationResponse response = new LocationResponse();
		response.setStatus(""+status);
		response.setMessage("Field Updated");
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("find")
	public String getAllRecords(@RequestBody LocationUpdateRequest request) {
		int page = request.getPage();
		int record = request.getRecord();
		locationService.findAllRecords(page, record);
		
		return "Found All Pages";
	}
}
