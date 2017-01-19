package com.paymybill.dao;

import com.paymybill.dao.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.UUID;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, UUID> implements UserDAO {

    private SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findByUsername(String username) throws NoResultException {
        EntityManager em = sessionFactory.createEntityManager();
        User userByUsername = (User) em.createQuery("SELECT u from User u WHERE u.username = :username")
                .setParameter("username", username)
                .getSingleResult();

        return userByUsername;
    }

    @Override
    public User findByEmail(String email) throws NoResultException {
        EntityManager em = sessionFactory.createEntityManager();
        User userByEmail = (User) em.createQuery("SELECT u from User u WHERE u.email = :email")
                .setParameter("email", email)
                .getSingleResult();

        return userByEmail;
    }

    @Override
    public User findByCellPhone(String cellPhone) throws NoResultException {
        EntityManager em = sessionFactory.createEntityManager();
        User userByCellPhone = (User) em.createQuery("SELECT u from User u WHERE u.cellPhone = :cellPhone")
                .setParameter("cellPhone", cellPhone)
                .getSingleResult();

        return userByCellPhone;
    }
}
