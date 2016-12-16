// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dream.album.service.DeveloperServerService;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ApiRespWrapper;
import com.dreambox.web.utils.IOUtils;

/**
 * @author mokous86@gmail.com create date: Dec 15, 2016
 *
 */
@Service("developerServerService")
public class DeveloperServerServiceImpl implements DeveloperServerService {
    @Value("${dream.album.developerrestartbashpath}")
    private String bashFile = "/srv/jmcx/developer/bin/restart_dream_album.sh";
    private static final String LINE_SEP = System.getProperty("line.separator");

    @Override
    public ApiRespWrapper<Boolean> restart() throws ServiceException {
        if (StringUtils.isEmpty(bashFile)) {
            return new ApiRespWrapper<Boolean>(false);
        }
        boolean restarted = false;
        ProcessBuilder pb = new ProcessBuilder("bash", bashFile);
        pb.inheritIO();
        Process process;
        String ioResult = "";
        try {
            process = pb.start();
            process.waitFor();
            String result;
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                do {
                    result = br.readLine();
                    if (result == null) {
                        break;
                    }
                    ioResult = ioResult + result + LINE_SEP;
                } while (true);
                restarted = true;
            } catch (Exception e) {
                ioResult = ioResult + e.getMessage();
            } finally {
                IOUtils.close(br);
            }
        } catch (Exception e) {
            ioResult = ioResult + e.getMessage();
        }
        return new ApiRespWrapper<Boolean>(0, ioResult, restarted);
    }

}
