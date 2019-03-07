package com.baozi.manager.image.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baozi.manager.image.FlowChartManager;

/**
 * @author 包志晨 lovexy201211@gmail.com
 * @version 创建时间：2019年2月18日 下午6:28:45
 */
@Service
public class FlowChartManagerImpl implements FlowChartManager{

    private static final Logger LOGGER = LoggerFactory.getLogger(FlowChartManagerImpl.class);
    
    // 图片的宽度。
    private int width = 1200;

    // 图片的高度。
    private int height = 400;

    // 验证码干扰线数
    private int lineCount = 10;

    public String getFlowChart(){
        return null;
    }
    
    private BufferedImage drawCodeImage(){
        int x = 0;
        int codeY = 0;
        int red = 0;
        int green = 0;
        int blue = 0;

        // 图像buffer
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        //图片划线
        for (int i = 0; i < lineCount; i++){
            int xs = random.nextInt(width / 2);
            int ys = random.nextInt(height);
            int xe = random.nextInt(width / 2) + width / 2;
            int ye = random.nextInt(height);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }
        return bufferedImage;
    }
}
