package fr.limayrac.banque.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import fr.limayrac.banque.model.Client;

@Repository
public interface clientRepository extends CrudRepository<Client, Integer>
{
    
}
