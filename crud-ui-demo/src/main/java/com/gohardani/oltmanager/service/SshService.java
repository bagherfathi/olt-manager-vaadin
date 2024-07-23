package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.OltType;
import com.gohardani.oltmanager.entity.SshCommand;
import org.springframework.stereotype.Service;
import com.gohardani.oltmanager.repository.SshRepository;

import java.util.List;

@Service
public class SshService {

    private final SshRepository sshRepository;
    public List<SshCommand> findByNameContainingIgnoreCase(String name) {
        return sshRepository.findByNameContainingIgnoreCase(name);
    }
    public SshService(SshRepository sshRepository) {
        this.sshRepository = sshRepository;
    }

    public List<SshCommand> findAll() {
        return sshRepository.findAll();
    }

    public int count() {
        return (int) sshRepository.count();
    }

    public SshCommand save(SshCommand sshCommand) {
        return sshRepository.save(sshCommand);
    }
    public void delete(SshCommand sshCommand) {
        sshRepository.delete(sshCommand);
    }

    public List<SshCommand> findSshCommandsByOltType(OltType oltType) {
        return sshRepository.findSshCommandsByOltType(oltType);
    }

}
