package cn.zhangcy.cad.UI;

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
            remove(component);
        }
    }

}
