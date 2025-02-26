package org.example.demo2.bean;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.demo2.model.User;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named
@SessionScoped
public class UserManagementBean implements Serializable {
    private List<User> users;
    private User selectedUser;
    private EntityManager em;

    public UserManagementBean() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
            em = emf.createEntityManager();
            System.out.println("UserManagementBean EntityManager initialized: " + em);
            loadUsers();
        } catch (Exception e) {
            System.err.println("Failed to initialize EntityManager in UserManagementBean: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        if (em != null) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            users = query.getResultList();
            System.out.println("Loaded users from DB: " + users.size());
            for (User u : users) {
                System.out.println("User: " + u.getUsername());
            }
        } else {
            System.err.println("EntityManager is null, cannot load users!");
        }
    }

    public void refreshUsers() {
        System.out.println("Refreshing users list...");
        loadUsers();
    }

    public void deleteUser(User user) {
        if (em == null) {
            System.err.println("EntityManager is null in deleteUser!");
            return;
        }
        try {
            em.getTransaction().begin();
            User managedUser = em.find(User.class, user.getUsername());
            if (managedUser != null) {
                em.remove(managedUser);
                em.getTransaction().commit();
                System.out.println("User deleted: " + user.getUsername());
                loadUsers();
            }
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            em.getTransaction().rollback();
        }
    }

    public String addUser() {
        return "addUser?faces-redirect=true";
    }

    // New method to refresh and return to adminUsers.xhtml
    public String returnToAdminUsers() {
        refreshUsers();
        return "adminUsers?faces-redirect=true";
    }

    public String editUser(User user) {
        this.selectedUser = user;
        System.out.println("Editing user: " + user.getUsername());
        return "editUser?faces-redirect=true";
    }

    public String saveEditedUser() {
        if (em == null) {
            System.err.println("EntityManager is null in saveEditedUser!");
            return "error?faces-redirect=true";
        }
        try {
            em.getTransaction().begin();
            em.merge(selectedUser);
            em.getTransaction().commit();
            System.out.println("User updated: " + selectedUser.getUsername());
            loadUsers();
            selectedUser = null;
            return "adminUsers?faces-redirect=true";
        } catch (Exception e) {
            System.err.println("Error saving edited user: " + e.getMessage());
            em.getTransaction().rollback();
            return "error?faces-redirect=true";
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}