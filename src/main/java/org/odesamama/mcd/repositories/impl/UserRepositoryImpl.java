package org.odesamama.mcd.repositories.impl;

import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.UserBuilder;
import org.odesamama.mcd.repositories.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by starnakin on 27.09.2015.
 */
@Repository
public class UserRepositoryImpl implements CustomUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findByEmail(String email) {
        List<User> userList = jdbcTemplate.query("select * from public.users where user_email = ?",
                new Object[] { email }, (rs, rowNum) -> {
                    UserBuilder builder = new UserBuilder();
                    builder.userId(rs.getLong("user_id"))
                            .userEmail(email)
                            .userName(rs.getString("user_name"))
                            .lastName(rs.getString("last_name"));

                    return builder.build();
                });

        return !userList.isEmpty() ? userList.get(0) : null;
    }

    @Override
    public Iterable<String> findUsers() {
        return jdbcTemplate.query(
                "select nspname from pg_namespace where nspname !~ '^pg_.*' and nspname != 'information_schema'",
                (rs, rowNum) -> rs.getString("nspname"));
    }
}
