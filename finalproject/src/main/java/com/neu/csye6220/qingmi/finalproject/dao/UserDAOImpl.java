package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
    
    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }
    
    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        
        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u ORDER BY u.createdAt DESC", User.class);
        return query.getResultList();
    }
    
    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
    
    @Override
    public void delete(Long id) {
        User user = findById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
    
    @Override
    public List<User> searchByName(String name) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(:name)", User.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}