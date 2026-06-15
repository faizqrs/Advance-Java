package com.crud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crud.entity.CrudEntity;

@Repository
public interface ICrudRepository extends CrudRepository<CrudEntity, Integer> {

}
