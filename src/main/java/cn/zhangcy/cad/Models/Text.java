package cn.zhangcy.cad.Models;

import cn.zhangcy.cad.Core.DoubleField;
import cn.zhangcy.cad.Core.Field;
import cn.zhangcy.cad.Core.StringField;
import cn.zhangcy.cad.Core.ToolClass;

import javax.swing.*;
import java.awt.*;

@ToolClass(Name = "文本工具", InstanceName = "文本", Icon = "text.png")
public class Text extends RectBoundElement {

    @StringField(Name = "文本")
    public Field<String> text = new Field<>("Text");

    @DoubleField(Name = "字体大小", Min = 0.5, Max = 10.0)
    public Field<Double> textSize = new Field<>(1.0);


    JLabel jLabel = new JLabel("Text");

    public Text() {
        add(jLabel);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        jLabel.setFont(new Font("Serif", Font.PLAIN, (int)(12 * textSize.getValue())));
        jLabel.setText(text.getValue());
    }

    @Override
    protected void printBorder(Graphics g) {
        super.printBorder(g);
    }
}
