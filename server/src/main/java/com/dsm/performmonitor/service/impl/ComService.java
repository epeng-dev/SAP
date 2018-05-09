package com.dsm.performmonitor.service.impl;

import com.dsm.performmonitor.service.SystemService;
import com.google.gson.JsonObject;
import org.hyperic.sigar.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service("comService")
public class ComService implements SystemService {

    private final Sigar sigar;
    private Map<String, Long> rxCurrentMap;
    private Map<String, List<Long>> rxChangeMap;
    private Map<String, Long> txCurrentMap;
    private Map<String, List<Long>> txChangeMap;

    @Autowired
    public ComService(Sigar sigar) {
        rxCurrentMap = new HashMap<>();
        rxChangeMap = new HashMap<>();
        txCurrentMap = new HashMap<>();
        txChangeMap = new HashMap<>();
        this.sigar = sigar;
    }

    private JsonObject getCpuUsages() throws SigarException {
        JsonObject jsonObject = new JsonObject();
        CpuPerc cpuPerc = sigar.getCpuPerc();

        jsonObject.addProperty("cpuUsage", cpuPerc.getCombined());

        return jsonObject;
    }

    private JsonObject getRamUsages() throws SigarException {
        JsonObject jsonObject = new JsonObject();
        Mem mem = sigar.getMem();

        jsonObject.addProperty("ramUsage", mem.getUsedPercent());

        return jsonObject;
    }

    private JsonObject getNetworkUsages() throws SigarException {
        JsonObject jsonObject = new JsonObject();
        Long[] networkMetric = getNetworkMetric();
        jsonObject.addProperty("rx", networkMetric[0]);
        jsonObject.addProperty("tx", networkMetric[1]);

        return jsonObject;
    }

    private Long[] getNetworkMetric() throws SigarException {


        String[] networkInterfaceArray = sigar.getNetInterfaceList();
        String networkInterface;

        for (int i = 0; i < networkInterfaceArray.length; i++) {
            networkInterface = networkInterfaceArray[i];
            NetInterfaceStat netStat = sigar.getNetInterfaceStat(networkInterface);
            NetInterfaceConfig ifConfig = sigar.getNetInterfaceConfig(networkInterface);

            String hwAddress = null;

            if (!NetFlags.NULL_HWADDR.equals(ifConfig.getHwaddr())) {
                hwAddress = ifConfig.getHwaddr();
            }

            if (hwAddress != null) {
                long rxCurrentTmp = netStat.getRxBytes();
                saveNetworkChange(rxCurrentMap, rxChangeMap, hwAddress, rxCurrentTmp, networkInterface);
                long txCurrentTmp = netStat.getTxBytes();
                saveNetworkChange(txCurrentMap, txChangeMap, hwAddress, txCurrentTmp, networkInterface);
            }
        }

        long totalRx = getNetworkData(rxChangeMap);
        long totalTx = getNetworkData(txChangeMap);

        for (List<Long> rxByteList : rxChangeMap.values())
            rxByteList.clear();
        for (List<Long> txByteList : txChangeMap.values())
            txByteList.clear();

        return new Long[] {totalRx, totalTx};
    }

    private void saveNetworkChange(Map<String, Long> currentMap,
                                   Map<String, List<Long>> changeMap,
                                   String hwaddr, long current,
                                   String networkInterface) {
        Long oldCurrent = currentMap.get(networkInterface);
        if (oldCurrent != null) {
            List<Long> list = changeMap.get(hwaddr);
            if (list == null) {
                list = new LinkedList<>();
                changeMap.put(hwaddr, list);
            }
            list.add(current - oldCurrent);
        }
        currentMap.put(networkInterface, current);
    }

    private long getNetworkData(Map<String, List<Long>> changeMap) {
        long total = 0;
        for (Map.Entry<String, List<Long>> entry : changeMap.entrySet()) {
            int average = 0;
            for (Long changeByte : entry.getValue()){
                average += changeByte;
            }
            total += average / entry.getValue().size();
        }
        return total;
    }

    @Override
    public String getInfo() throws SigarException {
        JsonObject result = new JsonObject();
        JsonObject cpuObject = getCpuUsages();
        JsonObject ramObject = getRamUsages();
        JsonObject networkObject = getNetworkUsages();

        result.add("cpu", cpuObject);
        result.add("ram", ramObject);
        result.add("network", networkObject);

        return result.toString();
    }
}
