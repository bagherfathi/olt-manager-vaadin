package com.gohardani.oltmanager.Utility.ONTID;

import com.gohardani.oltmanager.entity.Ont;
import com.gohardani.oltmanager.entity.Port;
import com.gohardani.oltmanager.service.OntService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FreeOntID {
    public static void main(String[] args) {

    }
    public static int getBiggestFreeOntID(OntService ontService, Port port) {
        int freeMax = 0;
        ArrayList<Integer> sortedIDs=new ArrayList<>();
        List<Ont> onts=ontService.findByPortEquals(port);
        for (Ont ont : onts) {
          sortedIDs.add(ont.getOntID());
        }
        Collections.sort(sortedIDs);
        for (freeMax=1;freeMax<=sortedIDs.size();freeMax++) {
            if(sortedIDs.get(freeMax-1)!=freeMax) {
//                System.out.println("freeMax in loop:"+freeMax);
                return freeMax;
            }
        }
//        System.out.println("freeMax before return:"+freeMax);
        return freeMax;
    }
}
