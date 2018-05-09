package com.dsm.performmonitor.service;

import org.hyperic.sigar.SigarException;

public interface SystemService {
    String getInfo() throws SigarException;
}
