package com.octopus.tbcompare.service;

import com.octopus.tbcompare.pojo.TransType;
import com.octopus.tbcompare.repository.TransTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-21
 * Time: 15:22
 */
@Service
public class TransTypeService {
    @Autowired
    private TransTypeRepository transTypeRepository;


    public TransType findType(Integer fromId,Integer toId,String fromType){
        return transTypeRepository.findFirstByFromIdAndToIdAndFromType(fromId, toId,fromType);
    }
}
