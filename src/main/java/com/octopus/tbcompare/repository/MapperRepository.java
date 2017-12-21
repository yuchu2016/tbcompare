package com.octopus.tbcompare.repository;

import com.octopus.tbcompare.pojo.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapperRepository extends JpaRepository<Mapper,Integer> {
    Mapper findByMsType(String msType);
    Mapper findBySsType(String msType);
    Mapper findByPgType(String msType);
}
