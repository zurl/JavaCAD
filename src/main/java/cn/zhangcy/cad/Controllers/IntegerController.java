package cn.zhangcy.cad.Controllers;

import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Core.Field;
import cn.zhangcy.cad.Core.FieldWatcher;
import cn.zhangcy.cad.Core.IntegerField;
import cn.zhangcy.cad.Models.Element;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class IntegerController extends JPanel{

    public IntegerController(Field<Integer> field, IntegerField integerField, Element element) {
        setPreferredSize(new Dimension(120, 30));
        setMaximumSize(new Dimension(120, 30));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JLabel(integerField.Name() + ": "));
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

        add(jTextField);
    }
}
