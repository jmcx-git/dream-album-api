package com.dreambox.core.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 * @author liuxinglong
 * @date 2016年12月7日
 */
public class ImagePsUtils {
    private static final Logger log = Logger.getLogger(ImagePsUtils.class);
    private Font font = new Font("宋体", Font.BOLD, 14);// 添加字体的属性设置

    private Graphics2D g = null;

    private int fontsize = 0;

    private int x = 0;

    private int y = 0;

    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入图片到缓冲区
     */
    public BufferedImage loadImageLocal(MultipartFile file) {
        try {
            return ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     */
    public BufferedImage loadImageUrl(String imgName) {
        try {
            URL url = new URL(imgName);
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 设定文字的字体等
     */
    public void setFont(String fontStyle, int fontSize) {
        this.fontsize = fontSize;
        this.font = new Font(fontStyle, Font.PLAIN, fontSize);
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     */
    public BufferedImage modifyImage(BufferedImage img, Object content, int x, int y) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.black);// 设置字体颜色
            if (this.font != null)
                g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标
            if (x >= w || y >= h) {
                this.x = w - this.fontsize + 2;
                this.y = h;
            } else {
                this.x = x;
                this.y = y;
            }
            if (content != null) {
                g.drawString(content.toString(), this.x, this.y);
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出
     */
    public BufferedImage modifyImage(BufferedImage img, Object[] contentArr, int x, int y, boolean xory) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.RED);
            if (this.font != null)
                g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标
            if (x >= w || y >= h) {
                this.x = h - this.fontsize + 2;
                this.y = h;
            } else {
                this.x = x;
                this.y = y;
            }
            if (contentArr != null) {
                int arrlen = contentArr.length;
                if (xory) {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.x += contentArr[i].toString().length() * this.fontsize / 2 + 5;// 重新计算文本输出位置
                    }
                } else {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.y += this.fontsize + 2;// 重新计算文本输出位置
                    }
                }
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     * 
     * 时间:2007-10-8
     * 
     * @param img
     * @return
     */
    public BufferedImage modifyImageYe(BufferedImage img) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.blue);// 设置字体颜色
            if (this.font != null)
                g.setFont(this.font);
            g.drawString("reyo.cn", w - 85, h - 5);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return img;
    }

    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d, int x, int y, int width, int height) {
        try {
            g = d.createGraphics();
            g.drawImage(b, x, y, width, height, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return d;
    }

    public static void main(String[] args) {
        EasyImage image = new EasyImage();
        try {
            // 读取第一张图片
            File fileOne = new File(
                    "/Users/liuxinglong/git/dream-album-api/dream-album/src/main/webapp/images/1/detail/2.png");
            BufferedImage imageOne = ImageIO.read(fileOne);
            int width = imageOne.getWidth();// 图片宽度
            int height = imageOne.getHeight();// 图片高度
            // 从图片中读取RGB
            int[] imageArrayOne = new int[width * height];
            imageArrayOne = imageOne.getRGB(0, 0, width, height, imageArrayOne, 0, width);

            File fileTwo = new File("/Users/liuxinglong/Desktop/证书/images/handsome.jpg");
            BufferedImage imageTwo = ImageIO.read(fileTwo);
            int width2 = imageTwo.getWidth();// 图片宽度
            int height2 = imageTwo.getHeight();// 图片高度
            // 从图片中读取RGB
            int[] imageArrayTwo = new int[width2 * height2];
            imageArrayTwo = imageTwo.getRGB(0, 0, width2, height2, imageArrayTwo, 0, width2);

            BufferedImage imageNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageNew.setRGB(0, 0, width, height, imageArrayOne, 0, width);
            imageNew.setRGB(108, 252, width2, height2, imageArrayTwo, 0, width2);
            File outFile = new File("/Users/liuxinglong/Desktop/test.png");
            ImageIO.write(imageNew, "png", outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // try {
        // image.mergeBothImage(
        // "/Users/liuxinglong/git/dream-album-api/dream-album/src/main/webapp/images/1/detail/2.png",
        // "/Users/liuxinglong/Desktop/证书/images/handsome.jpg", 108, 252, 200,
        // 200,
        // "/Users/liuxinglong/Desktop/test.jpg");
        // } catch (IOException e) {
        // System.out.println(e.getMessage());
        // }
        // ImagePsUtils tt = new ImagePsUtils();
        // BufferedImage background = tt
        // .loadImageLocal("/Users/liuxinglong/git/dream-album-api/dream-album/src/main/webapp/images/1/detail/2.png");
        // // 将多张图片合在一起
        // tt.writeImageLocal("/Users/liuxinglong/Desktop/back.png",
        // background);
        // BufferedImage image =
        // tt.loadImageLocal("/Users/liuxinglong/Desktop/证书/images/handsome.jpg");
        // tt.writeImageLocal("/Users/liuxinglong/Desktop/test.png",
        // tt.modifyImagetogeter(image, background, 108, 252, 200, 200));
        System.out.println("success!");
    }
}
