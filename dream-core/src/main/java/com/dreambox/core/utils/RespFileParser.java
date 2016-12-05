// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class RespFileParser implements RespParser<Boolean> {
    private File file;

    public RespFileParser(File file) {
        this.file = file;
    }

    @Override
    public Boolean handleResponse(HttpResponse resp) throws ClientProtocolException, IOException {
        boolean success = false;
        InputStream is = null;
        try {
            is = resp.getEntity().getContent();
            Header[] contentEncoding = resp.getHeaders("content-encoding");
            for (Header header : contentEncoding) {
                if (header.getValue().contains("gzip")) {
                    is = new GZIPInputStream(is);
                    break;
                }
            }
            FileUtils.copyInputStreamToFile(is, file);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        return success;
    }

}
