package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NamedQueries;
import models.Role;
import models.User;


public class UserDB {
    // copy-paste most of code from Demo09JPA(just changed note with user)
    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            // went through slides and get this code from slide 22 finally getAll is working
            // used findAll createNamedQuery in model.User to get all the users 
            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return (List<User>) users;
        } finally {
            em.close();
        }
    }
    
    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    }
    
    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Role role = user.getRole();
            role.getUserList().add(user);
            trans.begin();
            em.persist(user);
            em.merge(role);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void delete(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Role role = user.getRole();
            role.getUserList().remove(user);
            trans.begin();
            em.remove(em.merge(user));
            em.merge(role);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

}