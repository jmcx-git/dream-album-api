// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.dream.album.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dream.album.model.JoinImgFileResp;
import com.dream.album.model.MergeImgFileResp;
import com.dream.album.model.UploadFileSaveResp;
import com.dream.album.service.ImgService;
import com.dreambox.core.dto.album.AlbumItemInfo;
import com.dreambox.core.dto.album.UserAlbumItemInfo;
import com.dreambox.core.utils.EasyImage;
import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.IOUtils;

/**
 * @author mokous86@gmail.com create date: Dec 12, 2016
 *
 */
@Service("imgService")
public class ImgServiceImpl implements ImgService {
    private static final Logger log = Logger.getLogger(ImgServiceImpl.class);
    @Value("${dream.album.useralbumitemuploadimglocalpath}")
    private String userAlbumItemUploadImgLocalPath;
    @Value("${dream.album.useralbumitemuploadimgprefixurl}")
    private String userAlbumItemUploadImgPrefixUrl;
    @Value("${dream.album.useralbumitempreviewimgurlprefix}")
    private String userAlbumItemPreviewImgUrlPrefix = "";
    @Value("${dream.album.useralbumitempreviewimglocaldir}")
    private String userAlbumItemPreviewImgLocalDir = "";

    @Value("${dream.album.useralbumpriviewimgprefixurl}")
    private String userAlbumPriviewImgPrefixUrl;
    @Value("${dream.album.useralbumpriviewimglocalpath}")
    private String userAlbumPriviewImgLocalPath;

    @Value("${dream.album.albumitemeditimgurlprefix}")
    private String albumItemEditImgUrlPrefix = "";
    @Value("${dream.album.albumitemeditimglocaldir}")
    private String albumItemEditImgLocalDir = "";

    @Value("${dream.album.imghandletmppath}")
    private String imgHandleTmpPath = "";


    @Override
    public UploadFileSaveResp handleUserUploadImg(MultipartFile image) throws ServiceException {
        // 保存用户自己上传的图片
        String picName = "album_user_" + new Date().getTime() + ".png";
        File outputfile = new File(userAlbumItemUploadImgLocalPath + picName);
        try {
            ImageIO.write(ImageIO.read(image.getInputStream()), "png", outputfile);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        String picUrl = userAlbumItemUploadImgPrefixUrl + picName;
        return new UploadFileSaveResp(outputfile.getPath(), picUrl);
    }

    // @Override
    // public MergeImgFileResp mergeToPreviewImg(String editImePath, String
    // localPath, AlbumItemInfo albumItemInfo,
    // AlbumEditImgInfoModel model) throws ServiceException {
    // // Integer cssImgHeight = Math.round((float) (model.getCssImgHeight() *
    // // yTimes + 0.5));
    // ImagePsUtils img = new ImagePsUtils();
    // String picName = "album_item_pre_" + new Date().getTime() + ".png";
    // try {
    // img.mergeBothImage(editImePath, localPath, model.getCssElmMoveX(),
    // model.getCssElmMoveY(),
    // model.getCssElmWidth(), model.getCssElmHeight(), model.getCssElmRotate(),
    // userAlbumItemPreviewImgLocalDir + picName);
    // } catch (IOException e) {
    // log.info(e.getMessage());
    // }
    // return new MergeImgFileResp(userAlbumItemPreviewImgLocalDir + picName,
    // userAlbumItemPreviewImgUrlPrefix
    // + picName);
    // }

    @Override
    public JoinImgFileResp joinPreviewImg(int userAlbumId, List<String> prwImgList, String type) {
        EasyImage e = new EasyImage();
        String subDir = String.valueOf(userAlbumId);
        String fileName = System.currentTimeMillis() + "." + type;
        String productPreImg = IOUtils.spliceFileName(IOUtils.spliceDirPath(userAlbumPriviewImgLocalPath, subDir),
                fileName);
        // 纵向拼接成品相册预览图
        e.joinImageListVertical(prwImgList.toArray(new String[prwImgList.size()]), "png", productPreImg);
        return new JoinImgFileResp(productPreImg, productPreImg.replace(userAlbumPriviewImgLocalPath,
                userAlbumPriviewImgPrefixUrl));
    }

    @Override
    public String getAlbumItemEditImgPath(AlbumItemInfo info) {
        return StringUtils.replace(info.getEditImgUrl(), this.albumItemEditImgUrlPrefix, this.albumItemEditImgLocalDir);
    }

    @Override
    public String getUserAlbumItemPreviewImgPath(UserAlbumItemInfo g) {
        String previewImgUrl = g.getPreviewImgUrl();
        return previewImgUrl.replace(userAlbumItemPreviewImgUrlPrefix, userAlbumItemPreviewImgLocalDir);
    }

    //
    // imgInDeviceLeft= 34.992/imgInDeviceTop = 134.059 left/top
    // userScaleImageX= 0.913/userScaleImageY=0.337 scalex/scaley
    // uploadImgInDeviceWidth=261.96 uploadImgInDeviceHeight= 195.92
    @Override
    public MergeImgFileResp mergeToPreviewImg(int userId, String originBgImgPath,
            String userUploadOrginImgPath, int uploadImgInDeviceWidth, int uploadImgInDeviceHeight,
            float userScaleImageX, float userScaleImageY, int imgInDeviceLeft, int imgInDeviceTop) throws IOException {
        String ct = System.nanoTime() + ".png";
        String outPutImagePath = IOUtils.spliceFileName(IOUtils.spliceDirPath(this.imgHandleTmpPath, "bgDevice"), ct);
        String outPutFillImagePath = IOUtils.spliceFileName(
                IOUtils.spliceDirPath(this.imgHandleTmpPath, "userImgDevice"), ct);
        String outPutFilledImagePath = IOUtils.spliceFileName(
                IOUtils.spliceDirPath(this.imgHandleTmpPath, "userImgDeviceFilled"), ct);
        Point p = fitDeviceImgWH(originBgImgPath, userUploadOrginImgPath, uploadImgInDeviceWidth,
                uploadImgInDeviceHeight);
        // 将背景图按设备scale
        scaleImg(originBgImgPath, outPutImagePath, (int) p.getX(), (int) p.getY());
        // scale to filt the device shadow
        scaleImg(userUploadOrginImgPath, outPutFillImagePath, userScaleImageX, userScaleImageY);
        // fill find the fill image at the bg image x y //
        fillImg(outPutFillImagePath, outPutFilledImagePath, (int) p.getX(), (int) p.getY(), imgInDeviceLeft,
                imgInDeviceTop);
        String outPutFile = IOUtils.spliceFileName(IOUtils.spliceDirPath(this.imgHandleTmpPath, "filledBeforeScale"),
                ct);
        fillBgImg(outPutFilledImagePath, outPutImagePath, outPutFile);
        String picName = "album_item_pre_" + new Date().getTime() + ".png";
        String userFile = IOUtils.spliceFileName(
                IOUtils.spliceDirPath(userAlbumItemPreviewImgLocalDir, "" + userId), picName);
        Image image = ImageIO.read(new File(originBgImgPath));
        int originImageWidth = image.getWidth(null);
        int originImageHeight = image.getHeight(null);
        scaleImg(outPutFile, userFile, originImageWidth, originImageHeight);
        String finishedImgUrl = StringUtils.replace(userFile, userAlbumItemPreviewImgUrlPrefix,
                userAlbumItemPreviewImgLocalDir);
        return new MergeImgFileResp(userFile, finishedImgUrl);
    }

    private static void fillBgImg(String fillImg, String bgImg, String toPath) throws IOException {
        Image image = ImageIO.read(new File(fillImg));
        Image bgImage = ImageIO.read(new File(bgImg));
        // InputStream is = new FileInputStream(img);
        int w = bgImage.getWidth(null);
        int h = bgImage.getHeight(null);
        BufferedImage imageNew = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        imageNew.setRGB(0, 0, w, h, new int[w * h], 0, w);

        // BufferedImage image2 = ImageIO.read(is);
        Graphics2D gNew = (Graphics2D) imageNew.getGraphics();
        // 设置背景透明
        imageNew = gNew.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        gNew.dispose();

        gNew = (Graphics2D) imageNew.getGraphics();
        // 旋转
        // gNew.rotate(Math.toRadians(degree), x + width / 2, y + height / 2);
        // gNew.drawImage(image2, x, y, width, height, null);

        // 设置图片透明度alpha
        // gNew.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
        // 1.0f));
        // gNew.rotate(Math.toRadians(-degree), x + width / 2, y + height / 2);
        gNew.drawImage(image, 0, 0, w, h, null);
        gNew.drawImage(bgImage, 0, 0, w, h, null);
        File outFile = new File(toPath);
        ImageIO.write(imageNew, "png", outFile);

    }

    private static void fillImg(String fillImg, String toPath, int w, int h, int startX, int startY) throws IOException {
        Image image = ImageIO.read(new File(fillImg));
        // InputStream is = new FileInputStream(img);
        BufferedImage imageNew = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        imageNew.setRGB(0, 0, w, h, new int[w * h], 0, w);

        // BufferedImage image2 = ImageIO.read(is);
        Graphics2D gNew = (Graphics2D) imageNew.getGraphics();
        // 设置背景透明
        imageNew = gNew.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        gNew.dispose();

        gNew = (Graphics2D) imageNew.getGraphics();
        // 旋转
        // gNew.rotate(Math.toRadians(degree), x + width / 2, y + height / 2);
        // gNew.drawImage(image2, x, y, width, height, null);

        // 设置图片透明度alpha
        // gNew.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
        // 1.0f));
        // gNew.rotate(Math.toRadians(-degree), x + width / 2, y + height / 2);
        gNew.drawImage(image, startX, startY, image.getWidth(null), image.getHeight(null), null);
        File outFile = new File(toPath);
        ImageIO.write(imageNew, "png", outFile);

    }

    private static Point fitDeviceImgWH(String bgImagePath, String fillImagePath, int fillImageInUserDeviceWidth,
            int fillImageInUserDeviceHeight) throws IOException {
        InputStream is = null;
        InputStream is2 = null;
        try {
            is = new FileInputStream(bgImagePath);
            is2 = new FileInputStream(fillImagePath);
            BufferedImage image = ImageIO.read(is);
            BufferedImage image2 = ImageIO.read(is2);
            int fillImageWidth = image2.getWidth();
            int fillImageHeight = image2.getHeight();
            float ratioX = ((float) fillImageInUserDeviceWidth) / fillImageWidth;
            float ratioY = ((float) fillImageInUserDeviceHeight) / fillImageHeight;
            return new Point((int) (image.getWidth() * ratioX), (int) (image.getHeight() * ratioY));
        } finally {
            IOUtils.close(is);
            IOUtils.close(is2);
        }
    }

    private static void scaleImg(String img, String toPath, float wRatio, float hRatio) throws IOException {
        Image image = ImageIO.read(new File(img));
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        w = (int) (w * wRatio);
        h = (int) (h * hRatio);
        // InputStream is = new FileInputStream(img);
        BufferedImage imageNew = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        imageNew.setRGB(0, 0, w, h, new int[w * h], 0, w);

        // BufferedImage image2 = ImageIO.read(is);
        Graphics2D gNew = (Graphics2D) imageNew.getGraphics();
        // 设置背景透明
        imageNew = gNew.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        gNew.dispose();

        gNew = (Graphics2D) imageNew.getGraphics();
        // 旋转
        // gNew.rotate(Math.toRadians(degree), x + width / 2, y + height / 2);
        // gNew.drawImage(image2, x, y, width, height, null);

        // 设置图片透明度alpha
        // gNew.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
        // 1.0f));
        // gNew.rotate(Math.toRadians(-degree), x + width / 2, y + height / 2);
        gNew.drawImage(image, 0, 0, w, h, null);
        File outFile = new File(toPath);
        ImageIO.write(imageNew, "png", outFile);
    }

    private static void scaleImg(String img, String toPath, int w, int h) throws IOException {
        // InputStream is = new FileInputStream(img);
        BufferedImage imageNew = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        imageNew.setRGB(0, 0, w, h, new int[w * h], 0, w);

        // BufferedImage image2 = ImageIO.read(is);
        Graphics2D gNew = (Graphics2D) imageNew.getGraphics();
        // 设置背景透明
        imageNew = gNew.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        gNew.dispose();

        gNew = (Graphics2D) imageNew.getGraphics();
        Image image = ImageIO.read(new File(img));
        // 旋转
        // gNew.rotate(Math.toRadians(degree), x + width / 2, y + height / 2);
        // gNew.drawImage(image2, x, y, width, height, null);

        // 设置图片透明度alpha
        // gNew.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
        // 1.0f));
        // gNew.rotate(Math.toRadians(-degree), x + width / 2, y + height / 2);
        gNew.drawImage(image, 0, 0, w, h, null);
        File outFile = new File(toPath);
        ImageIO.write(imageNew, "png", outFile);
    }

    /**
     * 
     * @param bgImagePath
     * @param fillImagePath
     * @param toPath
     * @param fillCoorX
     * @param fillCoorY
     * @param filleImageInUserDeviceLeft
     * @param filleImageInUserDeviceTop
     * @param fillImageInUserDeviceWidth
     * @param fillImageInUserDeviceHeight
     * @param scaleX
     * @param sacleY
     */
    // public static void mergeImage(String bgImagePath, String fillImagePath,
    // String toPath, int fillCoorX,
    // int fillCoorY, int fillImageInUserDeviceWidth, int
    // fillImageInUserDeviceHeight, int scaleX, int sacleY) {
    // InputStream is = null;
    // InputStream is2 = null;
    // OutputStream os = null;
    // try {
    // is = new FileInputStream(bgImagePath);
    // is2 = new FileInputStream(fillImagePath);
    // BufferedImage image = ImageIO.read(is);
    // BufferedImage image2 = ImageIO.read(is2);
    // int fillImageWidth = image2.getWidth();
    // int fillImageHeight = image2.getHeight();
    // float ratioX = fillImageInUserDeviceWidth / fillImageWidth;
    // float ratioY = fillImageInUserDeviceHeight / fillImageHeight;
    //
    // int w = image.getWidth();// 图片宽度
    // int h = image.getHeight();// 图片高度
    // // 从图片中读取RGB
    // BufferedImage imageNew = new BufferedImage(w, h,
    // BufferedImage.TYPE_INT_RGB);
    // imageNew.setRGB(0, 0, w, h, new int[w * h], 0, w);
    //
    // Graphics2D gNew = (Graphics2D) imageNew.getGraphics();
    // // 设置背景透明
    // imageNew = gNew.getDeviceConfiguration().createCompatibleImage(w, h,
    // Transparency.TRANSLUCENT);
    // gNew.dispose();
    //
    // gNew = (Graphics2D) imageNew.getGraphics();
    // // 旋转
    // // gNew.rotate(Math.toRadians(degree), x + width / 2, y + height /
    // // 2);
    //
    // gNew.drawImage(image2, fillCoorX, fillCoorY, width, height, null);
    //
    // // 设置图片透明度alpha
    // // gNew.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
    // // 1.0f));
    // // gNew.rotate(Math.toRadians(-degree), x + width / 2, y + height /
    // // 2);
    // gNew.drawImage(image, 0, 0, w, h, null);
    // File outFile = new File(toPath);
    // ImageIO.write(imageNew, "png", outFile);
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // IOUtils.close(os);
    // IOUtils.close(is2);
    // IOUtils.close(is);
    // }
    //
    // }

    /**
     * 裁剪图片方法
     * 
     * @param bufferedImage 图像源
     * @param startX 裁剪开始x坐标
     * @param startY 裁剪开始y坐标
     * @param endX 裁剪结束x坐标
     * @param endY 裁剪结束y坐标
     * @return
     */
    public static BufferedImage cropImage(BufferedImage bufferedImage, int startX, int startY, int endX, int endY) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        if (startX == -1) {
            startX = 0;
        }
        if (startY == -1) {
            startY = 0;
        }
        if (endX == -1) {
            endX = width - 1;
        }
        if (endY == -1) {
            endY = height - 1;
        }
        BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                int rgb = bufferedImage.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }

    /**
     * 缩放图片方法
     * 
     * @param srcImageFile 要缩放的图片路径
     * @param result 缩放后的图片路径
     * @param height 目标高度像素
     * @param width 目标宽度像素
     * @param bb 是否补白
     */
    public final static void scale(String srcImageFile, String result, int width, int height, boolean bb) {
        try {
            double ratio = 0.0; // 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);// bi.SCALE_SMOOTH
                                                                                  // 选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                double ratioHeight = (new Integer(height)).doubleValue() / bi.getHeight();
                double ratioWhidth = (new Integer(width)).doubleValue() / bi.getWidth();
                if (ratioHeight > ratioWhidth) {
                    ratio = ratioHeight;
                } else {
                    ratio = ratioWhidth;
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform// 仿射转换
                        .getScaleInstance(ratio, ratio), null);// 返回表示剪切变换的变换
                itemp = op.filter(bi, null);// 转换源 BufferedImage 并将结果存储在目标
                                            // BufferedImage 中。
            }
            if (bb) {// 补白
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 构造一个类型为预定义图像类型之一的
                                                                                                   // BufferedImage。
                Graphics2D g = image.createGraphics();// 创建一个
                                                      // Graphics2D，可以将它绘制到此
                                                      // BufferedImage 中。
                g.setColor(Color.white);// 控制颜色
                g.fillRect(0, 0, width, height);// 使用 Graphics2D 上下文的设置，填充 Shape
                                                // 的内部区域。
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
                            itemp.getHeight(null), Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
                            itemp.getHeight(null), Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result)); // 输出压缩图片
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
