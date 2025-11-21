package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.Page;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PageDAOImpl implements PageDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(Page page) {
        entityManager.persist(page);
    }
    
    @Override
    public Page findById(Long id) {
        return entityManager.find(Page.class, id);
    }
    
    @Override
    public List<Page> findByUserId(Long userId) {
        TypedQuery<Page> query = entityManager.createQuery(
            "SELECT p FROM Page p WHERE p.userId = :userId ORDER BY p.createdAt DESC",
            Page.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public List<Page> findRootPagesByUserId(Long userId) {
        TypedQuery<Page> query = entityManager.createQuery(
            "SELECT p FROM Page p WHERE p.userId = :userId AND p.parentId IS NULL ORDER BY p.createdAt DESC",
            Page.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public List<Page> findChildPages(Long parentId) {
        TypedQuery<Page> query = entityManager.createQuery(
            "SELECT p FROM Page p WHERE p.parentId = :parentId ORDER BY p.createdAt ASC",
            Page.class);
        query.setParameter("parentId", parentId);
        return query.getResultList();
    }
    
    @Override
    public void update(Page page) {
        entityManager.merge(page);
    }
    
    @Override
    public void delete(Long id) {
        Page page = findById(id);
        if (page != null) {
            entityManager.remove(page);
        }
    }
    
    @Override
    public List<Page> searchByTitle(String title, Long userId) {
        TypedQuery<Page> query = entityManager.createQuery(
            "SELECT p FROM Page p WHERE p.userId = :userId AND LOWER(p.title) LIKE LOWER(:title) ORDER BY p.createdAt DESC",
            Page.class);
        query.setParameter("userId", userId);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }
}