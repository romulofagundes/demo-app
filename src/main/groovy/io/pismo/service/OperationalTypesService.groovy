package io.pismo.service

import io.pismo.domain.OperationalTypes
import io.pismo.exception.InvalidOperationalTypesException
import io.pismo.repos.OperationalTypesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OperationalTypesService {

    @Autowired
    OperationalTypesRepository operationalTypesRepository

    OperationalTypes get(Long id){
        return operationalTypesRepository.findById(id).orElseThrow { new InvalidOperationalTypesException() }
    }
}
