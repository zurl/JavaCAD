package cn.zhangcy.cad.Views;

import cn.zhangcy.cad.Controllers.CreateController;
import cn.zhangcy.cad.Controllers.OpenController;
import cn.zhangcy.cad.Controllers.SaveController;
import cn.zhangcy.cad.Models.*;
import cn.zhangcy.cad.Models.Rectangle;
import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Core.Serializer;
import cn.zhangcy.cad.Core.ToolClass;
import cn.zhangcy.cad.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ToolBar extends JPanel{

    public static final Class[] tools = new Class[]{
            Arrow.class,
            Transformer.class,
            Circle.class,
            Text.class,
            Line.class,
            Rectangle.class
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
        button.addActionListener(new CreateController(context));
        add(button);
        button = createButton("打开文档", "open.png");
        button.addActionListener(new OpenController(context));
        add(button);
        button = createButton("保存文档", "save.png");
        button.addActionListener(new SaveController(context));
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
