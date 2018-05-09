package com.dsm.performmonitor.service.impl;

import com.dsm.performmonitor.service.SystemService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cpuService")
public class CpuService implements SystemService{
    private final Sigar sigar;

    @Autowired
    public CpuService(Sigar sigar) {
        this.sigar = sigar;
    }


    private JsonArray getCpuUsages() throws SigarException {
        CpuPerc[] cpuArray;
        JsonArray jsonArray = new JsonArray();

        cpuArray = sigar.getCpuPercList();

        for (int i = 0; i < cpuArray.length; i++) {
            JsonObject subCpu = new JsonObject();
            subCpu.addProperty("cpuNum", i+1);
            subCpu.addProperty("cpuTotal", cpuArray[i].getCombined());
            subCpu.addProperty("cpuSystem", cpuArray[i].getSys());
            subCpu.addProperty("cpuNice", cpuArray[i].getNice());
            subCpu.addProperty("cpuIdle", cpuArray[i].getIdle());
            jsonArray.add(subCpu);
        }

        return jsonArray;
    }

    @Override
    public String getInfo() throws SigarException {
        return getCpuUsages().toString();
    }
}
