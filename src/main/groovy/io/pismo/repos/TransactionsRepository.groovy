package io.pismo.repos

import io.pismo.domain.Transactions
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionsRepository extends PagingAndSortingRepository<Transactions,Long>{

}