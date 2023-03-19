package fr.limayrac.banque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.limayrac.banque.model.*;
import fr.limayrac.banque.repository.*;
import lombok.Data;

import java.util.*;
@Data
@Service
public class compteService {
    @Autowired
    private compteRepository compteRepository;

    public Optional<Compte> getCompte(final Integer id)
    {
        return compteRepository.findById(id);
    }

    public Iterable<Compte> getComptes()
    {
        return compteRepository.findAll();
    }

    public void deleteCompte(final Integer id)
    {
        compteRepository.deleteById(id);
    }

    public Compte saveCompte(Compte compte)
    {
        return compteRepository.save(compte);
    }
}
