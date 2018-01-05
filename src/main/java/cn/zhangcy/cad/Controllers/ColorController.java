package cn.zhangcy.cad.Controllers;

import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Core.ColorField;
import cn.zhangcy.cad.Core.Field;
import cn.zhangcy.cad.Core.FieldWatcher;
import cn.zhangcy.cad.Models.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorController extends JPanel{
    public ColorController(Field<cn.zhangcy.cad.Core.Color> field, ColorField colorField, Element element) {
        setPreferredSize(new Dimension(120, 30));
        setMaximumSize(new Dimension(120, 30));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JLabel(colorField.Name() + ": "));

        JPanel colorPanel = new JPanel(){};
        colorPanel.setBorder(BorderFactory.createLineBorder(cn.zhangcy.cad.Core.Color.black));
        colorPanel.setPreferredSize(new Dimension(20, 20));
        colorPanel.setMaximumSize(new Dimension(20, 20));
        colorPanel.setBackground(field.getValue());

        colorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                java.awt.Color newColor = JColorChooser.showDialog(
                        Context.getInstance().getCanvas(),
                        "Choose Background Color",
                        colorPanel.getBackground());
                if(newColor != null){
                    field.setValue(new cn.zhangcy.cad.Core.Color(newColor));
                    element.onRefresh();
                    super.mouseClicked(e);
                }
            }
        });

        field.setWatcher(new FieldWatcher<cn.zhangcy.cad.Core.Color>() {
            @Override
            public void onChange(cn.zhangcy.cad.Core.Color value) {
                colorPanel.setBackground(value);
            }
        });

        add(colorPanel);
    }
}
