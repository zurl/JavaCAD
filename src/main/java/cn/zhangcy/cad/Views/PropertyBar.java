package cn.zhangcy.cad.Views;

import cn.zhangcy.cad.Controllers.ButtonController;
import cn.zhangcy.cad.Controllers.ColorController;
import cn.zhangcy.cad.Controllers.IntegerController;
import cn.zhangcy.cad.Controllers.StringController;
import cn.zhangcy.cad.Models.Element;
import cn.zhangcy.cad.Models.Tool;
import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Core.*;
import cn.zhangcy.cad.Core.Color;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Formatter;

public class PropertyBar extends JPanel{

    JPanel content = null;

    public PropertyBar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(Box.createHorizontalStrut(20));
    }

    void createSeparator(){
        JSeparator sep = new JSeparator();
        sep.setPreferredSize(new Dimension(120, 15));
        sep.setMaximumSize(new Dimension(120, 15));
        content.add(sep);
    }

    void createLabel(String str){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(120, 30));
        jPanel.setMaximumSize(new Dimension(120, 30));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        JLabel label = new JLabel(str);
        jPanel.add(label);
        content.add(jPanel);
    }

    void createSliderField(Field<Double> field, DoubleField doubleField, Element element){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(120, 30));
        jPanel.setMaximumSize(new Dimension(120, 30));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));

        JSlider jSlider = new JSlider(0, 100);
        Double min = doubleField.Min();
        Double len = doubleField.Max() - min;
        double val = field.getValue();
        jSlider.setValue((int)((field.getValue() - min) / len * 100));
        JLabel jLabel = new JLabel(doubleField.Name() + ": ");
        JTextField jTextField = new JTextField();
        jTextField.setEditable(false);
        jTextField.setText(new Formatter().format("%.2f", val).toString());
        jPanel.add(jLabel);
        jPanel.add(jTextField);
        content.add(jPanel);

        jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(120, 30));
        jPanel.setMaximumSize(new Dimension(120, 30));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));

        jPanel.add(jSlider);
        content.add(jPanel);

        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double val = (len * jSlider.getValue() / 100 + min);
                jTextField.setText(new Formatter().format("%.2f", val).toString());
                field.setValue(val);
                element.onRefresh();
            }
        });

    }


    public void setTool(Tool tool){
        try {
            if (content != null) {
                remove(content);
            }
            content = new JPanel();
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            content.setAlignmentY(Component.TOP_ALIGNMENT);
            content.add(Box.createVerticalStrut(20));

            createLabel(
                    ((ToolClass) Context.getInstance()
                            .getToolClass()
                            .getAnnotation(ToolClass.class))
                            .Name());

            createSeparator();


            if(tool != null && tool instanceof Element) {

                createLabel(
                        tool.getClass().getAnnotation(ToolClass.class).InstanceName());

                createSeparator();

                java.lang.reflect.Field[] declaredFields = tool.getClass().getFields();
                ArrayList<Component> pos = new ArrayList<>();
                ArrayList<Component> com = new ArrayList<>();

                for (java.lang.reflect.Field field : declaredFields) {
                    IntegerField integerField = field.getAnnotation(IntegerField.class);
                    if (integerField != null) {
                        content.add(new IntegerController((Field<Integer>) field.get(tool), integerField, (Element) tool));
                    }

                    ColorField colorField = field.getAnnotation(ColorField.class);
                    if (colorField != null) {
                        content.add(new ColorController((Field<Color>) field.get(tool), colorField, (Element) tool));
                    }

                    DoubleField doubleField = field.getAnnotation(DoubleField.class);
                    if (doubleField != null) {
                        createSliderField(
                                (Field<Double>) field.get(tool), doubleField, (Element) tool);
                    }

                    StringField stringField = field.getAnnotation(StringField.class);
                    if (stringField != null) {
                        content.add(new StringController((Field<String>)field.get(tool), stringField, (Element) tool));
                    }
                }

                createSeparator();

                for (java.lang.reflect.Field field : declaredFields) {
                    Object o = field.get(tool);
                    if( o instanceof ButtonField){
                        content.add(new ButtonController((ButtonField)o, tool));
                    }
                }
            }
            add(content);
            validate();
            repaint();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
