package fr.limayrac.banque.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import fr.limayrac.banque.model.Compte;

@Repository
public interface compteRepository extends CrudRepository<Compte, Integer>
{
    
}
