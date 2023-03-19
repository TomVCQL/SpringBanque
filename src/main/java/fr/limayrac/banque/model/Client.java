package fr.limayrac.banque.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;

import java.util.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nom",length = 50)
    private String nom;

    @Column(name="prenom",length = 50)
    private String prenom;

    @Column(name="email",length = 255)
    private String email;

    @Column(name="identifiant",length = 9)
    private int identifiant;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "client")
    private List<Compte> comptes = new ArrayList<Compte>();
   
public String getNom() {
    return nom;
}
public void setNom(String nom) {
    this.nom = nom;
}
public String getPrenom() {
    return prenom;
}
public void setPrenom(String prenom) {
    this.prenom = prenom;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public int getIdentifiant() {
    return identifiant;
}
public void setIdentifiant(int identifiant) {
    this.identifiant = identifiant;
}
public Integer getId() {
    return id;
}
public void setId(Integer id) {
    this.id = id;
}
public List<Compte> getComptes() {
    return comptes;
}
public void setComptes(List<Compte> comptes) {
    this.comptes = comptes;
}
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}

}
