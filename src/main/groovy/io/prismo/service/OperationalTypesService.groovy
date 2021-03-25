package io.prismo.service

import io.prismo.domain.OperationalTypes
import io.prismo.exception.InvalidOperationalTypesException
import io.prismo.repos.OperationalTypesRepository
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
