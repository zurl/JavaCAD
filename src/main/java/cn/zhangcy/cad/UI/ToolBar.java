package cn.zhangcy.cad.UI;

import cn.zhangcy.cad.Components.Arrow;
import cn.zhangcy.cad.Components.Circle;
import cn.zhangcy.cad.Components.Text;
import cn.zhangcy.cad.Components.Transformer;
import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Core.Serializer;
import cn.zhangcy.cad.Core.ToolClass;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.annotation.Annotation;

public class ToolBar extends JPanel{

    public static final Class[] tools = new Class[]{
            Arrow.class,
            Circle.class,
            Transformer.class,
            Text.class
    };

    JButton createButton(String name, String icon){
        ImageIcon imageIcon = new ImageIcon(
                ToolBar.class.getResource("/icons/" + icon)
        );
        imageIcon.setImage(imageIcon.getImage().
                getScaledInstance(30, 30,
                        Image.SCALE_DEFAULT));
        JButton button = new JButton(name, imageIcon);
        return button;
    }

    public ToolBar() {
        JButton button;
        Context context = Context.getInstance();
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        button = createButton("新建文档", "new.png");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        add(button);
        button = createButton("打开文档", "open.png");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(context.getCanvas()) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        FileReader fileReader = new FileReader(file);
                        BufferedReader reader = new BufferedReader(fileReader);
                        String line;
                        StringBuilder stringBuilder = new StringBuilder();
                        boolean flag = false;
                        while ((line = reader.readLine()) != null) {
                            if(flag) stringBuilder.append("\n");
                            else flag = true;
                            stringBuilder.append(line);
                        }
                        context.getCanvas().cleanUp();
                        new Serializer().load(context.getCanvas(), stringBuilder.toString());
                        reader.close();
                    }
                    JOptionPane.showMessageDialog(null, "打开成功");
                }catch (Exception ee){
                    JOptionPane.showMessageDialog(null, "打开失败, 信息：" + ee.getMessage());
                }
            }
        });
        add(button);
        button = createButton("保存文档", "save.png");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showSaveDialog(context.getCanvas()) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(new Serializer().save(context.getCanvas()));
                        fileWriter.close();
                    }
                    JOptionPane.showMessageDialog(null, "保存成功");
                }catch (Exception ee){
                    JOptionPane.showMessageDialog(null, "保存失败, 信息：" + ee.getMessage());
                }
            }
        });
        add(button);
        for(Class c : tools){
            ToolClass annotation = (ToolClass) c.getAnnotation(ToolClass.class);
            button = createButton(annotation.Name(), annotation.Icon());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    context.setTool(c);
                }
            });
            add(button);
        }
    }
}
