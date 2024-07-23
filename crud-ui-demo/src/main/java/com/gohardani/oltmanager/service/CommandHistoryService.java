package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.CommandHistory;
import com.gohardani.oltmanager.repository.CommandHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandHistoryService {

    private final CommandHistoryRepository commandHistoryRepository;
//    public List<CommandHistory>  findBySshCommandIs(SshCommand sshCommand) {
//        return CommandHistoryRepository.findBySshCommandIs(SshCommand sshCommand);
//    }
    public CommandHistoryService(CommandHistoryRepository commandHistoryRepository) {
        this.commandHistoryRepository = commandHistoryRepository;
    }

    public List<CommandHistory> findAll() {
        return commandHistoryRepository.findAll();
    }

    public int count() {
        return (int) commandHistoryRepository.count();
    }

    public CommandHistory save(CommandHistory commandHistory) {
        return commandHistoryRepository.save(commandHistory);
    }
    public void delete(CommandHistory commandHistory) {
        commandHistoryRepository.delete(commandHistory);
    }


}
