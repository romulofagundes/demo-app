package io.pismo.repos

import io.pismo.domain.OperationalTypes
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface OperationalTypesRepository extends PagingAndSortingRepository<OperationalTypes,Long>{
}
