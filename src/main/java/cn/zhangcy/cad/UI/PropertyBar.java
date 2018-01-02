package cn.zhangcy.cad.UI;

import cn.zhangcy.cad.Components.Element;
import cn.zhangcy.cad.Components.Tool;
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

    void createButton(ButtonField buttonField, Tool tool){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(120, 30));
        jPanel.setMaximumSize(new Dimension(120, 30));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));


        JButton jButton;
        if(buttonField.getIcon() != null){
            jButton = new JButton("", new ImageIcon(
                    PropertyBar.class
                            .getResource("/icons/" + buttonField.getIcon())) );
        }
        else{
            jButton = new JButton(buttonField.getName());
        }
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonField.onEvent(tool);
            }
        });
        jButton.setPreferredSize(new Dimension(120, 30));
        jButton.setMaximumSize(new Dimension(120, 30));
        jPanel.add(jButton);
        content.add(jPanel);
    }

    void createStringField(Field<String> field, StringField integerField, Element element){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(120, 30));
        jPanel.setMaximumSize(new Dimension(120, 30));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        jPanel.add(new JLabel(integerField.Name() + ": "));
        JTextField jTextField = new JFormattedTextField();

        jTextField.setText(field.getValue());
        jTextField.setColumns(10);
        jTextField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                field.setValue(jTextField.getText());
                element.onRefresh();
            }
        });

        field.setWatcher(new FieldWatcher<String>() {
            @Override
            public void onChange(String value) {
                jTextField.setText(value);
            }
        });

        jPanel.add(jTextField);
        content.add(jPanel);
    }

    void createIntField(Field<Integer> field, IntegerField integerField, Element element){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(120, 30));
        jPanel.setMaximumSize(new Dimension(120, 30));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        jPanel.add(new JLabel(integerField.Name() + ": "));
        JFormattedTextField jTextField = new JFormattedTextField();

        jTextField.setValue(field.getValue());
        jTextField.setColumns(10);
        jTextField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                field.setValue(((Number)jTextField.getValue()).intValue());
                element.onRefresh();
                Context.getInstance().getCurrentTool().onRefresh();
            }
        });

        field.setWatcher(new FieldWatcher<Integer>() {
            @Override
            public void onChange(Integer value) {
                jTextField.setText("" + value);
            }
        });

        jPanel.add(jTextField);
        content.add(jPanel);
    }

    void createColorField(Field<Color> field, ColorField colorField, Element element){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(120, 30));
        jPanel.setMaximumSize(new Dimension(120, 30));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        jPanel.add(new JLabel(colorField.Name() + ": "));

        JPanel colorPanel = new JPanel(){};
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.black));
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
                    field.setValue(new Color(newColor));
                    element.onRefresh();
                    super.mouseClicked(e);
                }
            }
        });

        field.setWatcher(new FieldWatcher<Color>() {
            @Override
            public void onChange(Color value) {
                colorPanel.setBackground(value);
            }
        });

        jPanel.add(colorPanel);
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
                        createIntField(
                                (Field<Integer>) field.get(tool), integerField, (Element) tool);
                    }

                    ColorField colorField = field.getAnnotation(ColorField.class);
                    if (colorField != null) {
                        createColorField(
                                (Field<Color>) field.get(tool), colorField, (Element) tool);
                    }

                    DoubleField doubleField = field.getAnnotation(DoubleField.class);
                    if (doubleField != null) {
                        createSliderField(
                                (Field<Double>) field.get(tool), doubleField, (Element) tool);
                    }

                    StringField stringField = field.getAnnotation(StringField.class);
                    if (stringField != null) {
                        createStringField(
                                (Field<String>) field.get(tool), stringField, (Element) tool);
                    }
                }

                createSeparator();

                for (java.lang.reflect.Field field : declaredFields) {
                    Object o = field.get(tool);
                    if( o instanceof ButtonField){
                        createButton((ButtonField)o, (Tool) tool);
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
