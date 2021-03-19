package io.prismo.repos

import io.prismo.domain.OperationalTypes
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface OperationalTypesRepository extends PagingAndSortingRepository<OperationalTypes,Long>{
}
