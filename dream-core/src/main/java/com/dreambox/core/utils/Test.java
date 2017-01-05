package com.dreambox.core.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;

import com.dreambox.web.utils.CollectionUtils;

public class Test {
    public static void main(String[] args) {
        String[] files = new String[] { "/Users/luofei/Desktop/01.png", "/Users/luofei/Desktop/02.png",
                "/Users/luofei/Desktop/03.png", "/Users/luofei/Desktop/04.png" };
        // Get Image
        for (String file : files) {
            ImageIcon icon = new ImageIcon(file);
            Image image = icon.getImage();
            // Create empty BufferedImage, sized to Image
            BufferedImage buffImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics g = buffImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            // Dispose the Graphics
            g.dispose();
            // Here 2 for loops used for iterate through each and every pixel in
            // image
            int width = buffImage.getWidth();
            int height = buffImage.getHeight();
            boolean[][] alphas = new boolean[width][height];
            List<Point> alphaPoints = new ArrayList<Point>();
            for (int i = 0; i < buffImage.getWidth(); i++) {
                for (int j = 0; j < buffImage.getHeight(); j++) {
                    int alpha = (buffImage.getRGB(i, j) >> 24) & 0xff;
                    if (alpha != 255) {
                        alphas[i][j] = true;
                        alphaPoints.add(new Point(i, j));
                        buffImage.setRGB(i, j, Color.BLUE.getRGB());
                    } else {

                    }
                }
            }
            try {
                ImageIO.write(buffImage, "png", new File(StringUtils.replace(file, ".png", "_t.png")));
            } catch (Exception e) {
            }
            List<GapRect> gapRects = init(alphaPoints);
            System.out.println(gapRects);
        }
    }

    private static List<GapRect> init(List<Point> alphaPoints) {
        if (CollectionUtils.emptyOrNull(alphaPoints)) {
            return null;
        }
        List<GapRect> ret = new ArrayList<GapRect>();
        GapRect gapRect = findRect(alphaPoints);
        if (gapRect.bottomRight != null) {
            ret.add(gapRect);
            return ret;
        }
        List<Point> lessScap = new ArrayList<Point>();
        List<Point> inScap = new ArrayList<Point>();
        List<Point> moreScap = new ArrayList<Point>();
        reinitRect(alphaPoints, gapRect, lessScap, inScap, moreScap);
        List<GapRect> r1 = init(lessScap);
        List<GapRect> r2 = init(inScap);
        List<GapRect> r3 = init(moreScap);
        if (r1 != null) {
            ret.addAll(r1);
        }
        if (r2 != null) {
            ret.addAll(r2);
        }
        if (r3 != null) {
            ret.addAll(r3);
        }
        return ret;
    }

    private static void reinitRect(List<Point> alphaPoints, GapRect gapRect, List<Point> lessScap, List<Point> inScap,
            List<Point> moreScap) {
        for (Point point : alphaPoints) {
            if (gapRect.maxGapY >= 0) {
                if (point.getY() >= gapRect.maxGapY) {
                    lessScap.add(point);
                } else if (point.getY() <= gapRect.minGapY) {
                    moreScap.add(point);
                } else {
                    inScap.add(point);
                }
            } else if (gapRect.maxGapX >= 0) {
                if (point.getX() >= gapRect.maxGapX) {
                    lessScap.add(point);
                } else if (point.getX() <= gapRect.minGapX) {
                    moreScap.add(point);
                } else {
                    inScap.add(point);
                }
            }
        }
    }

    static class GapRect {
        double minGapX = -1;
        double maxGapX = -1;
        double minGapY = -1;
        double maxGapY = -1;
        Point leftTop;
        Point bottomRight;

        @Override
        public String toString() {
            return "GapRect [minGapX=" + minGapX + ", maxGapX=" + maxGapX + ", minGapY=" + minGapY + ", maxGapY="
                    + maxGapY + ", leftTop=" + leftTop + ", bottomRight=" + bottomRight + "]";
        }
    }

    private static GapRect findRect(List<Point> alphaPoints) {
        double minX = 0;
        double minY = 0;
        double maxX = 0;
        double maxY = 0;
        for (Point point : alphaPoints) {
            if (minX == 0 || minX > point.getX()) {
                minX = point.getX();
            }
            if (minY == 0 || minY > point.getY()) {
                minY = point.getY();
            }
            if (maxX == 0 || maxX < point.getX()) {
                maxX = point.getX();
            }
            if (maxY == 0 || maxY < point.getY()) {
                maxY = point.getY();
            }
        }

        double minGapX = -1;
        double maxGapX = -1;
        // 是否有间隔
        for (double i = minX; i < maxX; i++) {
            boolean inRect = false;
            for (Point point : alphaPoints) {
                if (point.getX() == i) {
                    inRect = true;
                    break;
                }
            }
            if (!inRect) {
                if (minGapX == -1 || minGapX > i) {
                    minGapX = i;
                }
                if (maxGapX == -1 || maxGapX < i) {
                    maxGapX = i;
                }
            }
        }
        double minGapY = -1;
        double maxGapY = -1;
        // 是否有间隔
        for (double i = minY; i < maxY; i++) {
            boolean inRect = false;
            for (Point point : alphaPoints) {
                if (point.getY() == i) {
                    inRect = true;
                    break;
                }
            }
            if (!inRect) {
                if (minGapY == -1 || minGapY > i) {
                    minGapY = i;
                }
                if (maxGapY == -1 || maxGapY < i) {
                    maxGapY = i;
                }
            }
        }
        GapRect ret = new GapRect();
        if (minGapX == -1 && maxGapX == -1 && minGapY == -1 && maxGapY == -1) {
            // find
            ret.leftTop = new Point((int) minX, (int) minY);
            ret.bottomRight = new Point((int) maxX, (int) maxY);
        } else {
            ret.minGapX = minGapX;
            ret.maxGapX = maxGapX;
            ret.minGapY = minGapY;
            ret.maxGapY = maxGapY;
        }
        return ret;
    }
}
