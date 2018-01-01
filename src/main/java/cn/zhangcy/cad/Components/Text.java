package cn.zhangcy.cad.Components;

import cn.zhangcy.cad.Core.Field;
import cn.zhangcy.cad.Core.StringField;
import cn.zhangcy.cad.Core.ToolClass;

import javax.swing.*;

@ToolClass(Name = "文字工具", InstanceName = "文字", Icon = "text.png")
public class Text extends RectBoundElement {

    @StringField(Name = "文本")
    public Field<String> text = new Field<>("Text");

    JLabel jLabel = new JLabel("Text");

    public Text() {
        add(jLabel);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        jLabel.setText(text.getValue());
    }
}
