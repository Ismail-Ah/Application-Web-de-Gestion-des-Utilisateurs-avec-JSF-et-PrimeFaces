package org.example.demo2.bean;

import java.time.LocalDate;

import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.demo2.model.User;


import java.io.Serializable;

@Named
@SessionScoped

public class AddUserBean implements Serializable {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Double accountBalance;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private EntityManager em;

    @ManagedProperty(value = "#{userManagementBean}")
    private UserManagementBean userManagementBean;

    public AddUserBean() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
            em = emf.createEntityManager();
            System.out.println("AddUserBean EntityManager initialized: " + em);
        } catch (Exception e) {
            System.err.println("Failed to initialize EntityManager in AddUserBean: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String saveUser() {
        if (em == null) {
            System.err.println("EntityManager is null in saveUser!");
            return "error?faces-redirect=true";
        }

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setBirthDate(birthDate);
        newUser.setAccountBalance(accountBalance);
        newUser.setAddress(address);
        newUser.setCity(city);
        newUser.setPostalCode(postalCode);
        newUser.setCountry(country);
        newUser.setUsername(firstName.toLowerCase() + lastName.toLowerCase() + System.currentTimeMillis());
        newUser.setPassword("default123");
        newUser.setRole("user");

        try {
            em.getTransaction().begin();
            em.persist(newUser);
            em.getTransaction().commit();
            System.out.println("User saved: " + newUser.getUsername());
            if (userManagementBean != null) {
                return userManagementBean.returnToAdminUsers();
            } else {
                System.err.println("UserManagementBean is null!");
                return "adminUsers?faces-redirect=true";
            }
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            em.getTransaction().rollback();
            return "error?faces-redirect=true";
        }
    }
    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public Double getAccountBalance() { return accountBalance; }
    public void setAccountBalance(Double accountBalance) { this.accountBalance = accountBalance; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    // Getter and Setter for UserManagementBean
    public UserManagementBean getUserManagementBean() { return userManagementBean; }
    public void setUserManagementBean(UserManagementBean userManagementBean) { this.userManagementBean = userManagementBean; }
}