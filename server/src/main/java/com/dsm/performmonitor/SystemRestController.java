package com.dsm.performmonitor;

import com.dsm.performmonitor.service.SystemService;
import org.hyperic.sigar.SigarException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemRestController {
    private final SystemService cpuService;
    private final SystemService comService;
    private final SystemService memService;
    private final HttpHeaders JsonHeader;

    @Autowired
    public SystemRestController(SystemService cpuService, SystemService comService, SystemService memService) {
        this.cpuService = cpuService;
        this.comService = comService;
        this.memService = memService;

        this.JsonHeader = new HttpHeaders();
        JsonHeader.add("Content-Type", "application/json;charset=UTF-8");
    }

    @RequestMapping(value = "/api/cpuInfo", method = RequestMethod.GET)
    public ResponseEntity<String> getCpuInfo() throws SigarException {
        return new ResponseEntity<>(cpuService.getInfo(), JsonHeader, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/comInfo", method = RequestMethod.GET)
    public ResponseEntity<String> getComInfo() throws SigarException {
        return new ResponseEntity<>(comService.getInfo(), JsonHeader, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/memInfo", method = RequestMethod.GET)
    public ResponseEntity<String> getMemInfo() throws SigarException {
        return new ResponseEntity<>(memService.getInfo(), JsonHeader, HttpStatus.OK);
    }

    @ExceptionHandler(SigarException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String sigarResponse(SigarException e){
        return "error";
    }
}
