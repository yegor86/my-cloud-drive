package org.odesamama.mcd.repositories.impl;

import java.util.Date;
import java.util.List;

import org.odesamama.mcd.domain.User;
import org.odesamama.mcd.domain.UserBuilder;
import org.odesamama.mcd.repositories.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
                    builder.userId(rs.getLong("user_id")).userEmail(email).userName(rs.getString("user_name"))
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Long saveUser(User user) {

        Long userId = jdbcTemplate.queryForObject(
                "insert into public.users (user_name, user_email, create_date, update_date, last_name, group_id)"
                        + " values (?, ?, ?, ?, ?, ?) RETURNING user_id",
                new Object[] { user.getUserName(), user.getUserEmail(), new Date(), new Date(), user.getLastName(), 0 },
                Long.class);

        user.setUserId(userId);
        return userId;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean updateUser(User user) {

        long rowCount = jdbcTemplate.update(
                "update public.users set user_name = ?, update_date = ?, last_name = ?, group_id = ? where user_id = ?",
                new Object[] { user.getUserName(), user.getUpdated(), user.getLastName(), user.getGroup().getGroupId(),
                        user.getUserId() });
        return rowCount > 0;
    }
}
