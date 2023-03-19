package fr.limayrac.banque.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name="compte")
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero", length = 10)
    private int numero;

    public Float getSolde() {
        return solde;
    }
    public void setSolde(Float solde) {
        this.solde = solde;
    }
    @Column(name = "solde")
    private Float solde;

    private Float newSolde;

    public Float getNewSolde() {
        return newSolde;
    }
    public void setNewSolde(Float newSolde) {
        this.newSolde = newSolde;
    }
    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    private Integer decouvert;
    public Integer getDecouvert() {
        return decouvert;
    }
    public void setDecouvert(Integer decouvert) {
        this.decouvert = decouvert;
    }
    private Integer beneficiaire;
    public Integer getBeneficiaire() {
        return beneficiaire;
    }
    public void setBeneficiaire(Integer beneficiaire) {
        this.beneficiaire = beneficiaire;
    }
    @Override
    public String toString()
    {
        return "Client [id=" + id + ", numero=" + numero;
    }
    public int getNumero() {
        return numero;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }

    
}
