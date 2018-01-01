package cn.zhangcy.cad;

import cn.zhangcy.cad.Components.Arrow;
import cn.zhangcy.cad.Components.Circle;
import cn.zhangcy.cad.Components.Transformer;
import cn.zhangcy.cad.UI.ToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static void createAndShowGUI() {

        Context context = Context.getInstance();

        context.createToolInstance();

        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        // 创建及设置窗口
        JFrame frame = new JFrame("HelloWorldSwing");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLayout(null);

        JPanel left = new ToolBar();
        left.setBounds(0, 0, (Settings.WIDTH - Settings.HEIGHT) / 2, Settings.HEIGHT);

        context.getCanvas().setBounds((Settings.WIDTH - Settings.HEIGHT) / 2, 0, Settings.HEIGHT, Settings.HEIGHT);
        context.propertyBar.setBounds((Settings.WIDTH + Settings.HEIGHT) / 2, 0, (Settings.WIDTH - Settings.HEIGHT) / 2, Settings.HEIGHT);


        frame.getContentPane().add(left, BorderLayout.WEST);
        frame.getContentPane().add(context.propertyBar, BorderLayout.EAST);
        frame.getContentPane().add(context.getCanvas(), BorderLayout.CENTER);


        // 显示窗口
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // 显示应用 GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
