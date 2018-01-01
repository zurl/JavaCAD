package cn.zhangcy.cad.Components;
import cn.zhangcy.cad.Core.ToolClass;

import java.awt.*;


@ToolClass(Name = "直线工具", InstanceName = "直线", Icon = "line.png")
public class Line
        extends RectBoundElement {

    @Override
    public void hintOldTool(Tool tool) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
