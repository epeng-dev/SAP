package com.dsm.performmonitor.config;

import org.hyperic.sigar.Sigar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SigarConfig {
    @Bean
    public Sigar getSigar() {
        Sigar sigar = new Sigar();
        return sigar;
    }
}
