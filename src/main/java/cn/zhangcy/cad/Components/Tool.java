package cn.zhangcy.cad.Components;

import java.awt.event.MouseAdapter;

public interface Tool {

    void hintOldTool(Tool tool);

    void onRefresh();

    void onActivate();

    void onSleep();

    void onDisable();

}
