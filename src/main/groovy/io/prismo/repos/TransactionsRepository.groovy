package io.prismo.repos

import io.prismo.domain.Transactions
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionsRepository extends PagingAndSortingRepository<Transactions,Long>{

}