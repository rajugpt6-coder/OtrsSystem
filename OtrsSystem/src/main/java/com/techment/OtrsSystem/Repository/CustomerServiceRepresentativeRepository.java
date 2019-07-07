package com.techment.OtrsSystem.Repository;

import com.techment.OtrsSystem.domain.CustomerServiceRepresentative;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface CustomerServiceRepresentativeRepository extends CrudRepository<CustomerServiceRepresentative, Long> {
}
