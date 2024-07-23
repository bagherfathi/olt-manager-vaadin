package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.Group;
import com.gohardani.oltmanager.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public int count() {
        return (int) groupRepository.count();
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

}
