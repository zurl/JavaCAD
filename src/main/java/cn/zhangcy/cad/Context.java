package cn.zhangcy.cad;

import cn.zhangcy.cad.Models.Tool;
import cn.zhangcy.cad.Views.Canvas;
import cn.zhangcy.cad.Views.PropertyBar;

public class Context {

    private static Context ourInstance = new Context();

    public static Context getInstance() {
        return ourInstance;
    }


    private Class tool;

    private Tool current = null;

    private Canvas canvas;

    PropertyBar propertyBar;

    public Class getToolClass() {
        return tool;
    }

    public Tool getCurrentTool() {
        return current;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public PropertyBar getPropertyBar() {
        return propertyBar;
    }

    private Context() {
        tool = Settings.DEFAULT_TOOL;
        canvas = new Canvas();
        propertyBar = new PropertyBar();
    }

    public void setTool(Class tool) {
        if(current != null){
            current.onDisable();
            current = null;
        }
        this.tool = tool;
        createToolInstance();
    }

    public void createToolInstance(){
        try {
            if(current != null) current.onSleep();
            Tool savedInstance = current;
            current = (Tool) tool.newInstance();
            if(savedInstance != null
                && current.getClass().isInstance(savedInstance))
                current.hintOldTool(savedInstance);
            current.onActivate();
            propertyBar.setTool(current);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
