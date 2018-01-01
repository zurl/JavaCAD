package cn.zhangcy.cad.Components;

import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Core.ButtonField;
import cn.zhangcy.cad.Core.ColorField;
import cn.zhangcy.cad.Core.Field;
import cn.zhangcy.cad.Core.IntegerField;
import cn.zhangcy.cad.Settings;

import javax.swing.*;
import java.awt.*;

abstract public class Element
    extends JPanel implements Tool {


    @IntegerField(Name = "PosX")
    public Field<Integer> X = new Field<>(0);

    @IntegerField(Name = "PosY")
    public Field<Integer> Y = new Field<>(0);

    @IntegerField(Name = "高度")
    public Field<Integer> height = new Field<>(0);

    @IntegerField(Name = "宽度")
    public Field<Integer> width = new Field<>(0);

    public ButtonField destroyButton = new ButtonField("删除物体", null) {
        @Override
        public void onEvent(Tool tool) {
            setSelected(false);
            Context context = Context.getInstance();
            context.getCurrentTool().onRefresh();
            context.getCanvas().remove((Element)tool);
            context.getCanvas().repaint();
            context.getPropertyBar().setTool(null);
        }
    };

    public void setPosition(Integer x, Integer y){
        X.setValue(x);
        Y.setValue(y);
        onRefresh();
    }

    protected boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        if(isSelected){
            Context.getInstance().getPropertyBar().setTool(this);
        }
        repaint();
    }

    @Override
    public void onRefresh() {
        setBounds(
                X.getValue() - width.getValue() / 2,
                Y.getValue() - height.getValue() / 2,
                width.getValue() + 1,
                height.getValue() + 1
        );
        validate();
        repaint();
    }
}
