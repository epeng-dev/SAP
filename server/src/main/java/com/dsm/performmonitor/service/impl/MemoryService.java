package com.dsm.performmonitor.service.impl;

import com.dsm.performmonitor.service.SystemService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memService")
public class MemoryService implements SystemService {
    private final Sigar sigar;

    @Autowired
    public MemoryService(Sigar sigar) {
        this.sigar = sigar;
    }

    private JsonArray getHddMemory() throws SigarException{
        FileSystem[] fileSystems = sigar.getFileSystemList();
        JsonArray fsUsageList = new JsonArray();

        for (FileSystem fileSystem : fileSystems) {
            String fsDir = fileSystem.getDirName();
            FileSystemUsage usage = sigar.getFileSystemUsage(fsDir);

            JsonObject fsUsage = new JsonObject();
            fsUsage.addProperty("diskInfo", fsDir);
            fsUsage.addProperty("usagePercent", usage.getUsePercent());
            fsUsage.addProperty("usedGigaBytes", (double)usage.getUsed()/1024/1024);
            fsUsage.addProperty("freeGigaBytes", (double)usage.getFree()/1024/1024);
            fsUsageList.add(fsUsage);
        }

        return fsUsageList;
    }
    @Override
    public String getInfo() throws SigarException {
        JsonArray fsUsageList = getHddMemory();
        return fsUsageList.toString();
    }
}
