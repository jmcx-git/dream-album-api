package com.dreambox.core.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.dreambox.web.exception.ServiceException;


/**
 * 
 * @author liuxinglong
 * @date 2016年12月7日
 */
public class ImagePsUtils {
    private static final Logger log = Logger.getLogger(ImagePsUtils.class);

    public static void main(String[] args) throws IOException {
        String toPath = "/Users/luofei/Desktop/v.jpg";
        List<String> images = new ArrayList<String>();
        images.add("/Users/luofei/Desktop/5.png");
        images.add("/Users/luofei/Desktop/6.png");
        images.add("/Users/luofei/Desktop/7.png");
        images.add("/Users/luofei/Desktop/ft.png");
        joinImageToJpg(images, toPath, false);
        toPath = "/Users/luofei/Desktop/H.jpg";
        joinImageToJpg(images, toPath, true);
    }

    public static void joinImageToJpg(List<String> images, String toPath, boolean vertical) throws ServiceException {
        int heigth = 0;
        int width = 0;
        List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
        for (String image : images) {
            BufferedImage bi;
            try {
                bi = ImageIO.read(new File(image));
                if (vertical) {
                    if (width == 0) {
                        width = bi.getWidth();
                    } else if (width < bi.getWidth()) {
                        width = bi.getWidth();
                    }
                    heigth += bi.getHeight();
                } else {
                    width += bi.getWidth();
                    if (heigth == 0) {
                        heigth = bi.getHeight();
                    } else if (heigth < bi.getHeight()) {
                        heigth = bi.getHeight();
                    }
                }
                bufferedImages.add(bi);
            } catch (IOException e) {
                log.error("Read image failed. Errmsg:" + e.getMessage() + ", File path:" + image, e);
                throw ServiceException.getInternalException("Get image data failed.");
            }
        }
        BufferedImage newBufferedImage = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
        int startY = 0;
        int startX = 0;
        for (BufferedImage bufferedImage : bufferedImages) {
            int drawWidth = bufferedImage.getWidth() > width ? width : bufferedImage.getWidth();
            int drawHeight = bufferedImage.getHeight() > heigth ? heigth : bufferedImage.getHeight();
            newBufferedImage.createGraphics().drawImage(bufferedImage, startX, startY, drawWidth, drawHeight,
                    Color.RED, null);
            if (vertical) {
                startY += bufferedImage.getHeight();
            } else {
                startX += bufferedImage.getWidth();
            }
        }
        File outputFile = new File(toPath);
        outputFile.mkdirs();
        try {
            ImageIO.write(newBufferedImage, "jpg", outputFile);
            outputFile.setReadable(true, false);
        } catch (IOException e) {
            String errInfo = "Write image failed. Errmsg:" + e.getMessage() + ", File path:" + toPath;
            log.error(errInfo, e);
            throw ServiceException.getInternalException("Get image data failed.");
        }
    }

    /**
     * 合并图片(按指定初始x、y坐标将附加图片贴到底图之上) 并指定宽高
     * 
     * 
     * @param bgImagePath 背景图片路径
     * @param bgWidth 背景图片的width
     * @param bgHeight 背景图片的Height
     * @param coverPath 附加图片路径
     * @param x 附加图片的起始点x坐标
     * @param y 附加图片的起始点y坐标
     * @param toPath 图片写入路径
     * @param width 覆盖的图片宽
     * @param height 覆盖的图片高
     * @param toPath 输出文件地址
     * @throws IOException
     */
    public static void mergeImagesToJpg(String bgImagePath, int bgWidth, int bgHeight, String coverPath, int x, int y,
            int width, int height, String toPath) throws IOException {
        try {
            BufferedImage newBufferedImage = new BufferedImage(bgWidth, bgHeight, BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(ImageIO.read(new File(coverPath)), x, y, width, height, null);
            newBufferedImage.createGraphics().drawImage(ImageIO.read(new File(bgImagePath)), 0, 0, null);
            File outputFile = new File(toPath);
            outputFile.mkdirs();
            ImageIO.write(newBufferedImage, "jpg", outputFile);
            outputFile.setReadable(true, false);
        } catch (Exception e) {
            log.error("Merge image failed.Errmsg:" + e.getMessage(), e);
            throw e;
        }
    }

}
