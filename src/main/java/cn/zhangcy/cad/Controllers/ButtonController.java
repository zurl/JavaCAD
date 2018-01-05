package cn.zhangcy.cad.Controllers;

import cn.zhangcy.cad.Core.ButtonField;
import cn.zhangcy.cad.Models.Tool;
import cn.zhangcy.cad.Views.PropertyBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController extends JPanel {

    public ButtonController(ButtonField buttonField, Tool tool) {
        setPreferredSize(new Dimension(120, 30));
        setMaximumSize(new Dimension(120, 30));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));


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
        add(jButton);
    }

}
