package cn.zhangcy.cad.Models;

public interface Tool {

    void hintOldTool(Tool tool);

    void onRefresh();

    void onActivate();

    void onSleep();

    void onDisable();

}
