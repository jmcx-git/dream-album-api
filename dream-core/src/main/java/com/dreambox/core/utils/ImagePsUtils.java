package com.dreambox.core.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import com.dreambox.web.exception.ServiceException;


/**
 * 
 * @author liuxinglong
 * @date 2016年12月7日
 */
public class ImagePsUtils {
    private static final Logger log = Logger.getLogger(ImagePsUtils.class);

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

    /**
     * 优雅的合并图片(按指定初始x、y坐标将附加图片贴到底图之上) 并指定宽高
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
    public static void gracefulMergeImagesToJpg(String bgImagePath, int bgWidth, int bgHeight, String coverPath, int x,
            int y, int width, int height, String toPath) throws IOException {
        try {

            BufferedImage newBufferedImage = new BufferedImage(bgWidth, bgHeight, BufferedImage.TYPE_INT_RGB);
            ImageIO.read(new File(coverPath));
            BufferedImage convertBufferedImage = converScale(ImageIO.read(new File(coverPath)), width, height);
            int covertWidht = convertBufferedImage.getWidth();
            int covertHeight = convertBufferedImage.getHeight();
            int startX = covertWidht > width ? (covertWidht - width) / 2 : 0;
            int startY = covertHeight > height ? (covertHeight - height) / 2 : 0;
            int endX = covertWidht > width ? startX + width : width;
            int endY = covertHeight > height ? startY + height : height;
            newBufferedImage.createGraphics().drawImage(convertBufferedImage, x, y, width + x, height + y, startX,
                    startY, endX, endY, null);
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

    /**
     * 将图片原比例缩放到要求比例的合适比例
     */
    private static BufferedImage converScale(BufferedImage origin, int width, int height) throws IOException {
        int originWidth = origin.getWidth();
        int originHeight = origin.getHeight();
        float widthRatio = ((float) originWidth) / width;
        float heightRatio = ((float) originHeight) / height;
        float chooseRatio = heightRatio > widthRatio ? widthRatio : heightRatio;
        int resizeWidth = (int) (originWidth / chooseRatio);
        int resizeHeight = (int) (originHeight / chooseRatio);
        return Scalr.resize(origin, resizeWidth, resizeHeight);
    }

    public static void main(String[] args) throws IOException {
        String toPath = "/Users/luofei/Desktop/v.jpg";
        String bgPath = "/Users/luofei/Desktop/testbg.png";
        String coverPath = "/Users/luofei/Desktop/cover.png";
        // List<String> images = new ArrayList<String>();
        // images.add("/Users/luofei/Desktop/5.png");
        // images.add("/Users/luofei/Desktop/6.png");
        // images.add("/Users/luofei/Desktop/7.png");
        // images.add("/Users/luofei/Desktop/ft.png");
        // joinImageToJpg(images, toPath, false);
        // toPath = "/Users/luofei/Desktop/H.jpg";
        // joinImageToJpg(images, toPath, true);
        gracefulMergeImagesToJpg(bgPath, 750, 1180, coverPath, 28, 15, 695, 647, toPath);
        BufferedImage newBufferedImage = null;

        BufferedImage origin = ImageIO.read(new File(coverPath));
        newBufferedImage = new BufferedImage(origin.getWidth(), origin.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(origin, 0, 0, Color.WHITE, null);
        ImageIO.write(converScale(newBufferedImage, 695, 647), "jpg", new File("/Users/luofei/Desktop/cover1.jpg"));
    }
}
