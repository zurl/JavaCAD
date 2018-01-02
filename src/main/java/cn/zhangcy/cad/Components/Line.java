package cn.zhangcy.cad.Components;
import cn.zhangcy.cad.Core.Color;
import cn.zhangcy.cad.Core.ColorField;
import cn.zhangcy.cad.Core.DoubleField;
import cn.zhangcy.cad.Core.Field;
import cn.zhangcy.cad.Core.ToolClass;

import java.awt.*;


@ToolClass(Name = "直线工具", InstanceName = "直线", Icon = "line.png")
public class Line
        extends RectBoundElement {

    @ColorField(Name = "颜色")
    public Field<cn.zhangcy.cad.Core.Color> lineColor = new Field<>(new Color(0,0,0,255));

    @DoubleField(Name = "粗细", Min = 1.0, Max = 10.0)
    public Field<Double> lineSize = new Field<>(1.0);


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke((float)(double) lineSize.getValue()));
        g.setColor(lineColor.getValue());
        if( isBottomUp.getValue() ){
            g.drawLine(
                    0,
                    height.getValue(),
                    width.getValue(),
                    0
            );
        }
        else{
            g.drawLine(
                    0,
                    0,
                    width.getValue(),
                    height.getValue()
            );
        }

    }

}
