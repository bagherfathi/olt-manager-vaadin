package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.Frame;
import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.repository.FrameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrameService {

    private final FrameRepository frameRepository;
    public List<Frame> findByFrameNumberAndOltEquals(String frameNumber, Olt olt) {
//        System.out.println("framenumber: " + frameNumber + ":" + frameNumber.length());
        int f=-1;
        try {
            if (!frameNumber.isEmpty() && !frameNumber.trim().isEmpty())
                f = Integer.parseInt(frameNumber);
        }
        catch (Exception e) {System.out.println(e);}
//        System.out.println(f + ":" + olt);
        if(f==-1 && olt==null)
            return frameRepository.findAll();
        else if(f!=-1 && olt==null)
            return frameRepository.findByFrameNumberEquals(f);
        else if (f==-1 && olt!=null)
            return frameRepository.findByOltEquals(olt);
        else
            return frameRepository.findByFrameNumberAndOltEquals(f,olt);

    }
    public List<Frame> findByOltEquals(Olt olt) {
        if(olt==null)
            return frameRepository.findAll();
        else
            return frameRepository.findByOltEquals(olt);

    }
    public List<Frame> findByFrameNumberEquals(String frameNumber) {
//        System.out.println("framenumber: " + frameNumber + ":" + frameNumber.length());
        int f=-1;
        try {
            if (!frameNumber.isEmpty() && !frameNumber.trim().isEmpty())
                f = Integer.parseInt(frameNumber);
        }
        catch (Exception e) {System.out.println(e);}
        if(f==-1)
            return frameRepository.findAll();
        else
            return frameRepository.findByFrameNumberEquals(f);

    }
    public FrameService(FrameRepository frameRepository) {
        this.frameRepository = frameRepository;
    }

    public List<Frame> findAll() {
        return frameRepository.findAll();
    }

    public int count() {
        return (int) frameRepository.count();
    }

    public Frame save(Frame frame) {
        return frameRepository.save(frame);
    }
    public void delete(Frame frame) {
        frameRepository.delete(frame);
    }


}
