package com.dark.hat.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.dark.hat.app.models.entity.Cliente;

public interface ClienteDao extends PagingAndSortingRepository<Cliente, Long>{

}
