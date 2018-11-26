package io.bakingo.demo.service;

import io.bakingo.demo.model.Group;
import io.bakingo.demo.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("groupService")
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(@Qualifier("groupRepository") GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(Integer id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
    }

    public Group findByName(String name) {
        return groupRepository.findByName(name);
    }

    public Group save(Group item) {
        return groupRepository.save(item);
    }

    public Group update(Group item, Integer id) {
        Group get = findById(id);
        get.setName(item.getName());
        return groupRepository.save(get);
    }

    public Group delete(Integer id) {
        Group get = findById(id);
        groupRepository.delete(get);
        return get;
    }
}
