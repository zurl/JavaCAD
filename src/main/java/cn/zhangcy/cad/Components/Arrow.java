package cn.zhangcy.cad.Components;

import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Core.ToolClass;
import cn.zhangcy.cad.UI.Canvas;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@ToolClass(Name = "选择工具", Icon = "cursor.png")
public class Arrow implements Tool{

    @Override
    public void hintOldTool(Tool tool) {

    }

    @Override
    public void onRefresh() {

    }

    class CanvasMouseAdapter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(selected != null){
                selected.setSelected(false);
            }
            super.mouseClicked(e);
        }

    }

    class ElementMouseAdapter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(selected != null){
                selected.setSelected(false);
            }
            selected = (Element) e.getComponent();
            selected.setSelected(true);
            super.mouseClicked(e);
        }

        int stX, stY;

        @Override
        public void mousePressed(MouseEvent e) {
            stX = e.getX();
            stY = e.getY();
            super.mousePressed(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(selected == e.getComponent()){
                selected.setPosition(
                        e.getX() - stX + selected.X.getValue(),
                        e.getY() - stY + selected.Y.getValue());
                selected.repaint();
            }
            super.mouseDragged(e);
        }
    }

    protected Element selected = null;
    protected MouseAdapter elementMouseAdapter;
    protected MouseAdapter canvasMouseAdapter;

    public Arrow() {
        elementMouseAdapter = new ElementMouseAdapter();
        canvasMouseAdapter = new CanvasMouseAdapter();
    }

    @Override
    public void onActivate() {
        Canvas canvas = Context.getInstance().getCanvas();
        canvas.addMouseListener(canvasMouseAdapter);
        Component[] components = canvas.getComponents();
        for(Component component : components){
            Element element = (Element) component;
            if(element != null){
                element.addMouseListener(elementMouseAdapter);
                element.addMouseMotionListener(elementMouseAdapter);
            }
        }
    }

    @Override
    public void onSleep() {

    }

    @Override
    public void onDisable() {


        Canvas canvas = Context.getInstance().getCanvas();
        canvas.removeMouseListener(canvasMouseAdapter);
        Component[] components = canvas.getComponents();
        for(Component component : components){
            if( component instanceof Element) {
                Element element = (Element) component;
                element.setSelected(false);
                element.removeMouseListener(elementMouseAdapter);
                element.removeMouseMotionListener(elementMouseAdapter);
            }
            else{
                canvas.remove(component);
                canvas.repaint();
            }
        }
    }
}
