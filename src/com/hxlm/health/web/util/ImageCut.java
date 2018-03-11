package com.hxlm.health.web.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;

/**
 * Created by dengyang on 16/1/15.
 * 剪裁图片方法
 */
public class ImageCut {

    /**
     * 截取图片
     * @param srcImageFile  原图片地址
     * @param x    截取时的x坐标
     * @param y    截取时的y坐标
     * @param desWidth   截取的宽度
     * @param desHeight   截取的高度
     */

    public static void imgCut(String srcImageFile, int x, int y, int desWidth, int desHeight) {
        try {
            Image img;
            ImageFilter cropFilter;
            BufferedImage bi = ImageIO.read(new File(srcImageFile + "_src.jpg"));
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            if (srcWidth >= desWidth && srcHeight >= desHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);
                cropFilter = new CropImageFilter(x, y, desWidth, desHeight);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(desWidth, desHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                // 输出文件
                ImageIO.write(tag, "JPEG", new File(srcImageFile + "_cut.jpg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //删除原本的文件。
            //FileDelete.deleteFile(srcImageFile+"_src.jpg");
        }
    }

    /**
     * 截取图片
     * @param suffix  图片格式
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @param sourcePath 原始图片的存储路径
     * @param targetPath 切割后图片的保存路径
     */
    public static void cutImage(String suffix, String sourcePath, String targetPath, int x1, int y1, int x2, int y2) {
        try {
            Image img;
            ImageFilter cropFilter;
            File sourceImgFile = new File(sourcePath);
            BufferedImage bi = ImageIO.read(sourceImgFile);
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            int destWidth = x2 - x1;
            int destHeight = y2 - y1;
            if (srcWidth >= destWidth && srcHeight >= destHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                cropFilter = new CropImageFilter(x1, y1, destWidth, destHeight);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(destWidth, destHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                ImageIO.write(tag, suffix, new File(targetPath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
