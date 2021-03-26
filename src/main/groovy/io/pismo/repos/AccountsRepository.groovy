package io.pismo.repos

import io.pismo.domain.Accounts
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountsRepository extends PagingAndSortingRepository<Accounts,Long>{
}
