package com.globomatics.bike.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.globomatics.bike.models.Bike;
import com.globomatics.bike.repositories.BikeRepository;

@RestController
@RequestMapping("/api/v1/bikes")
public class BikesController {
	@Autowired
	private BikeRepository bikeRepository;
	
	@GetMapping
	public List<Bike> getAll(){
		return bikeRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Bike get(@PathVariable("id") long id) {
		return bikeRepository.getOne(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void create(@RequestBody Bike bike) {
		bikeRepository.save(bike);
	}
	
	@PutMapping("/{id}")
	public Bike update(@PathVariable(value = "id") Long id,
            @Valid @RequestBody Bike bikeUpdate) {
		Optional<Bike> bike = bikeRepository.findById(id);
		Bike foundBike = bike.get();
		foundBike.setName(bikeUpdate.getName());
		foundBike.setModel(bikeUpdate.getModel());
		
		bikeRepository.save(foundBike);
		return foundBike;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		Optional<Bike> foundBike;
		if((foundBike = bikeRepository.findById(id)).isPresent()) {
			Bike bike = foundBike.get();
			bikeRepository.delete(bike);
		}
	    return ResponseEntity.ok().build();
	}
	
}