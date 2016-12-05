// Copyright 2015 ios.appchina.com Inc. All Rights Reserved.

package com.dreambox.core.utils;

import com.coremedia.iso.IsoFile;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.IOUtils;

/**
 * @author luofei@appchina.com (Your Name Here)
 *
 */
public class VideoUtils {
    public static void main(String[] args) {
        System.out.println(getMp4VideoDuration("/Users/luofei/Desktop/111.mp4"));
    }

    public static int getMp4VideoDuration(String fileLocation) {
        if (!fileLocation.endsWith(".mp4")) {
            throw ServiceException.getInternalException("视频格式错误，只解析mp4文件.");
        }

        IsoFile isoFile = null;
        try {
            isoFile = new IsoFile(fileLocation);
            double lengthInSeconds = (double) isoFile.getMovieBox().getMovieHeaderBox().getDuration()
                    / isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
            return new Double(lengthInSeconds).intValue();
        } catch (Exception e) {
            throw ServiceException.getInternalException("视频格式错误，只解析mp4文件.Mp4:" + fileLocation);
        } finally {
            IOUtils.close(isoFile);
        }
    }
}
