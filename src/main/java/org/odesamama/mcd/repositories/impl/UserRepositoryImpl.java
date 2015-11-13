package org.odesamama.mcd.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by starnakin on 27.09.2015.
 */
@Repository
public class UserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> q = entityManager.createQuery("select u from User u where u.userEmail = :userEmail",
                User.class);
        return !q.getResultList().isEmpty() ? q.getResultList().get(0) : null;
    }

    @Override
    public Iterable<String> findUsers() {
        return jdbcTemplate.query(
                "select nspname from pg_namespace where nspname !~ '^pg_.*' and nspname != 'information_schema'",
                (rs, rowNum) -> rs.getString("nspname"));
    }
}
