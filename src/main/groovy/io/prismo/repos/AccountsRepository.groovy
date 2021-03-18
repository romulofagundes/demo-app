package io.prismo.repos

import io.prismo.domain.Accounts
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountsRepository extends PagingAndSortingRepository<Accounts,Long>{
}
