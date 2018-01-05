package cn.zhangcy.cad.Core;

import cn.zhangcy.cad.Models.Tool;

public abstract class ButtonField{
    String name;
    String icon;

    public ButtonField(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public abstract void onEvent(Tool tool);

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}
