package cn.zhangcy.cad.Controllers;

import cn.zhangcy.cad.Core.Field;
import cn.zhangcy.cad.Core.FieldWatcher;
import cn.zhangcy.cad.Core.StringField;
import cn.zhangcy.cad.Models.Element;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StringController extends JPanel{

    public StringController(Field<String> field, StringField integerField, Element element) {

        setPreferredSize(new Dimension(120, 30));
        setMaximumSize(new Dimension(120, 30));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JLabel(integerField.Name() + ": "));
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

        add(jTextField);

    }
}
