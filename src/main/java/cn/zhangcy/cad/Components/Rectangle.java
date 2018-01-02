package cn.zhangcy.cad.Components;

import cn.zhangcy.cad.Core.*;
import cn.zhangcy.cad.Core.Color;

import java.awt.*;

@ToolClass(Name = "矩形工具", InstanceName = "矩形", Icon = "square.png")
public class Rectangle extends RectBoundElement {

    @DoubleField(Name = "线框粗细", Min = 1.0, Max = 10.0)
    public Field<Double> lineSize = new Field<>(1.0);

    @ColorField(Name = "线框颜色")
    public Field<Color> lineColor = new Field<>(new Color(0,0,0,255));

    @ColorField(Name = "填充颜色")
    public Field<Color> fillColor = new Field<>(new Color(0,0,0,0));

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(fillColor.getValue());
        g.fillRect((int)(lineSize.getValue() / 2),
                (int)(lineSize.getValue() / 2),
                (int)(width.getValue() - lineSize.getValue()),
                (int)(height.getValue() - lineSize.getValue()));
        g2.setStroke(new BasicStroke((float)(double) lineSize.getValue()));
        g.setColor(lineColor.getValue());
        g.drawRect(
                (int)(lineSize.getValue() / 2),
                (int)(lineSize.getValue() / 2),
                (int)(width.getValue() - lineSize.getValue()),
                (int)(height.getValue() - lineSize.getValue()));
    }

    @Override
    public void hintOldTool(Tool tool) {
        Rectangle circle = (Rectangle) tool;
        lineColor.setValue(circle.lineColor.getValue());
        fillColor.setValue(circle.fillColor.getValue());
        lineSize.setValue(circle.lineSize.getValue());

    }
}
