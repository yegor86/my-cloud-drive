package org.odesamama.mcd.repositories.impl;

import java.util.List;

import org.odesamama.mcd.domain.Group;
import org.odesamama.mcd.repositories.CustomGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class GroupRepositoryImpl implements CustomGroupRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Group findByName(String groupName) {
        List<Group> groupList = jdbcTemplate.queryForList("select * from public.groups g where g.group_name = ?",
                new Object[] { groupName }, Group.class);
        return !groupList.isEmpty() ? groupList.get(0) : null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Long saveGroup(Group group) {

        Long groupId = jdbcTemplate.queryForObject(
                "insert into public.groups (group_name, owner_id) values (?, ?) RETURNING group_id",
                new Object[] { group.getGroupName(), group.getOwner().getUserId() }, Long.class);

        group.setGroupId(groupId);
        return groupId;
    }
}
