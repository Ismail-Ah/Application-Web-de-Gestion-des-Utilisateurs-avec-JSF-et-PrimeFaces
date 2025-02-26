package org.example.demo2.bean;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.example.demo2.model.User;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named("loginBean") // Explicitly name it "loginBean" for EL
@SessionScoped

public class LoginBean implements Serializable {
    private String username;
    private String password;
    private User currentUser;
    private EntityManager em;

    public LoginBean() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
            em = emf.createEntityManager();
            System.out.println("EntityManager initialized: " + em);
        } catch (Exception e) {
            System.err.println("Failed to initialize EntityManager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String login() {
        System.out.println("Login called: " + username + "/" + password);
        if (em == null) {
            System.err.println("EntityManager is null!");
            return "error?faces-redirect=true";
        }
        currentUser = em.find(User.class, username);
        System.out.println("User: " + currentUser);
        if (currentUser != null) {
            String userPassword = currentUser.getPassword();
            if (userPassword != null && userPassword.equals(password)) { // Check for null first
                if ("admin".equals(currentUser.getRole())) {
                    return "adminUsers?faces-redirect=true";
                } else if ("user".equals(currentUser.getRole())) {
                    return "userDetails?faces-redirect=true";
                }
            }
        }
        return "error?faces-redirect=true";
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User currentUser) { this.currentUser = currentUser; }
}