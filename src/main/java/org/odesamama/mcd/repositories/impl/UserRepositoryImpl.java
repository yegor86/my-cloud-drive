package org.odesamama.mcd.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.repositories.CustomUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by starnakin on 27.09.2015.
 */
@Repository
public class UserRepositoryImpl implements CustomUserRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User findByEmail(String email) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> pRoot = criteria.from(User.class);
        criteria.where(builder.equal(pRoot.get("userEmail"), email));
        TypedQuery<User> q = entityManager.createQuery(criteria);
        try {
            return q.getSingleResult();
        } catch (Exception ex) {
            LOGGER.debug("error getting user by email {}", email, ex);
            return null;
        }

    }

    @Override
    public Iterable<String> findUsers() {
        return jdbcTemplate.query(
                "select nspname from pg_namespace where nspname !~ '^pg_.*' and nspname != 'information_schema'",
                (rs, rowNum) -> rs.getString("nspname"));
    }
}
