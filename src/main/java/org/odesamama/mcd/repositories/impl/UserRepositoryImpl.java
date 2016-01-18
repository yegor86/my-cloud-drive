package org.odesamama.mcd.repositories.impl;

import java.util.List;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findByEmail(String email) {
        List<User> userList = jdbcTemplate.query("select * from public.users where user_email = ?",
                new Object[] { email }, (rs, rowNum) -> {
                    User user = new User();
                    user.setUserId(rs.getLong("user_id"));
                    user.setUserUid(rs.getString("user_uid"));
                    user.setUserName(rs.getString("user_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setUserEmail(email);

                    return user;
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
