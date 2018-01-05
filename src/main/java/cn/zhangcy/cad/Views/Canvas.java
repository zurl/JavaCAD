package cn.zhangcy.cad.Views;

import cn.zhangcy.cad.Models.Element;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    public Canvas() {
        setLayout(null);
        setBackground(Color.WHITE);
    }

    public void cleanUp(){
        Component[] components = getComponents();
        for(Component component: components){
            if( component instanceof Element) {
                remove(component);
            }
        }
    }

}
