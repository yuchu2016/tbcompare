package com.octopus.tbcompare.repository;

import com.octopus.tbcompare.pojo.TransType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransTypeRepository extends JpaRepository<TransType,Integer>{

    TransType findFirstByFromIdAndToIdAndFromType(Integer fromId,Integer toId,String fromType);
}
