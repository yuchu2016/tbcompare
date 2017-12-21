package com.octopus.tbcompare.repository;

import com.octopus.tbcompare.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Integer>{
    List<Type> findByDb(Integer id);
}
