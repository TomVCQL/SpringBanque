package fr.limayrac.banque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import fr.limayrac.banque.model.Compte;
import fr.limayrac.banque.model.Client;
import fr.limayrac.banque.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.StackWalker.Option;
import java.util.*;

@RestController
public class comptesController {
    private static final Logger log = LoggerFactory.getLogger(comptesController.class);

    @Autowired
    private compteService compteService;
    @Autowired
    private clientService clientService;

    @GetMapping("/compte/lister")
    public ModelAndView listeComptes()
    {
        return new ModelAndView("listeComptes", "comptes", compteService.getComptes());
    }
    
    @GetMapping("/compte/lister/{id}")
    public ModelAndView detailCompte(@PathVariable("id")final Integer id)
    {
        Optional<Compte> compte = compteService.getCompte(id);
        return new ModelAndView("detailCompte", "compte", compte.orElse(null));
    }

    @GetMapping("/compte/creer")
    public ModelAndView creerCompte(@ModelAttribute("compte") Compte compte, ModelMap model){
        model.addAttribute("clients", clientService.getClients());
        return new ModelAndView("creationCompteForm","compte", compte); 
    }

    @PostMapping("/compte/creer")
    public ModelAndView submit(@ModelAttribute("compte") Compte compte, ModelMap model)
    {
        model.addAttribute("numero", compte.getNumero());
        model.addAttribute("client", compte.getClient());
        Client client = compte.getClient();
        model.addAttribute("comptes",client.getComptes());
        compte.setSolde(0f);
        compte.setDecouvert(0);

        compteService.saveCompte(compte);
        log.info("Compte créer");
        return new ModelAndView("detailClient", "client", compte.getClient());
    }
    @GetMapping("/compte/editer/{id}")
    public ModelAndView editerCompte(@PathVariable("id") final Integer id, @ModelAttribute("compte") Compte compte, ModelMap model){
        Optional<Compte> comptes = compteService.getCompte(id);
        compte=comptes.get();
        model.addAttribute("client", compte.getClient());

        return new ModelAndView("editerCompte", "compte", compte);
    }

    @PostMapping("/compte/editer")
    public ModelAndView submitEdition(@ModelAttribute("compte") Compte compte, ModelMap model)
    {
        Client client = compte.getClient();

        compteService.saveCompte(compte);
        log.info("compte modifier");
        model.addAttribute("comptes",client.getComptes());
        return new ModelAndView("detailClient", "client", client);
    }

    @GetMapping("/compte/effacer/{id}")
    public ModelAndView effacerCompte(@PathVariable("id") final Integer id, ModelMap model)
    {
        Optional<Compte> compteById = compteService.getCompte(id);
        Compte compte = compteById.get();

        Client client = compte.getClient();

        compteService.deleteCompte(id);
        log.info("compte effacer");
        model.addAttribute("comptes",client.getComptes());
        return new ModelAndView("detailClient", "client", client);
    }

    @GetMapping("/compte/virement/{id}")
    public ModelAndView virementCompte(@PathVariable("id") final Integer id, @ModelAttribute("compte") Compte compte, ModelMap model)
    {
        Optional<Compte> compteById = compteService.getCompte(id);
        compte = compteById.get();

        model.addAttribute("client", compte.getClient());
        return new ModelAndView("virementCompte", "compte", compte);
    }

    @PostMapping("/compte/virement")
    public ModelAndView submitVirementCompte(@ModelAttribute("compte") Compte compte, ModelMap model)
    {
        Client client = compte.getClient();

        log.info("Nom client "+compte.getNumero());
        
        compte.setSolde(compte.getNewSolde() + compte.getSolde());
        compte.setNewSolde(0f);
        compteService.saveCompte(compte);
        log.info("Virement effectuer");
        model.addAttribute("comptes",client.getComptes());
        return new ModelAndView("detailClient", "client", compte.getClient());
    }

    @GetMapping("/compte/retrait/{id}")
    public ModelAndView retraitCompte(@PathVariable("id") final Integer id, @ModelAttribute("compte") Compte compte, ModelMap model)
    {
        Optional<Compte> compteById = compteService.getCompte(id);
        compte = compteById.get();

        model.addAttribute("client", compte.getClient());
        return new ModelAndView("retraitCompte", "compte", compte);
    }

    @PostMapping("/compte/retrait")
    public ModelAndView submitRetraitCompte(@ModelAttribute("compte") Compte compte, ModelMap model)
    {
        Client client = compte.getClient();

        if((compte.getSolde()-compte.getNewSolde())>=compte.getDecouvert())
        {
            compte.setSolde(compte.getSolde()-compte.getNewSolde());
            compte.setNewSolde(0f);
            log.info("retrait effectuer");
        }
        else
        {
            log.info("Virement impossible car fond insuffisant");
            model.addAttribute("erreur", "Virement impossible car fond insuffisant");
        }
        
        compteService.saveCompte(compte);
        model.addAttribute("comptes",client.getComptes());
        return new ModelAndView("detailClient", "client", compte.getClient());
    }

    @GetMapping("/compte/transfert/{id}")
    public ModelAndView transfertCompte(@PathVariable("id") final Integer id, @ModelAttribute("compte") Compte compte, ModelMap model)
    {
        Optional<Compte> compteById = compteService.getCompte(id);
        compte = compteById.get();
        Client client = compte.getClient();
        model.addAttribute("client", client);
        model.addAttribute("comptes", client.getComptes());
        return new ModelAndView("transfertCompte", "compte", compte);
    }

    @PostMapping("/compte/transfert")
    public ModelAndView submitTransfertCompte(@ModelAttribute("compte") Compte compte, ModelMap model)
    {
        Client client = compte.getClient();

        Optional<Compte> compteById = compteService.getCompte(compte.getBeneficiaire());
        Compte beneficiaire = compteById.get();

        if((compte.getSolde()-compte.getNewSolde())>=compte.getDecouvert())
        {
            beneficiaire.setSolde(beneficiaire.getSolde()+compte.getNewSolde());
            compte.setSolde(compte.getSolde()-compte.getNewSolde());
            compte.setNewSolde(0f);
            compte.setBeneficiaire(null);
            log.info("Transfert effectuer");
        }
        else
        {
            compte.setBeneficiaire(null);
            compte.setNewSolde(0f);
            log.info("Virement impossible car fond insuffisant");
            model.addAttribute("erreur", "Virement impossible car fond insuffisant");
        }

        compteService.saveCompte(compte);

        model.addAttribute("comptes",client.getComptes());
        return new ModelAndView("detailClient", "client", client);
    }
}
