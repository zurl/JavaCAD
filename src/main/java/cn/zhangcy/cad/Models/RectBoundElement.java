package cn.zhangcy.cad.Models;

import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Core.Field;
import cn.zhangcy.cad.Views.Canvas;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

abstract public class RectBoundElement
        extends Element {

    protected Field<Boolean> isBottomUp = new Field<>(false);

    DrawMouseAdapter drawMouseAdapter = new DrawMouseAdapter();

    protected boolean onCreate = false;

    class DrawMouseAdapter extends MouseAdapter {

        int stX, stY;

        @Override
        public void mousePressed(MouseEvent e) {
            onCreate = true;
            stX = e.getX();
            stY = e.getY();
            super.mousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            onCreate = false;
            repaint();
            Context.getInstance().createToolInstance();
            super.mouseReleased(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            width.setValue(Math.abs(e.getX() - stX));
            height.setValue(Math.abs(e.getY() - stY));
            setPosition((e.getX() + stX) / 2, (e.getY() + stY) / 2);
            if( (e.getX() - stX) * (e.getY() - stY) > 0){
                isBottomUp.setValue(false);
            }
            else{
                isBottomUp.setValue(true);
            }
            repaint();
            super.mouseDragged(e);
        }
    }


    @Override
    public void onActivate() {
        Canvas canvas = Context.getInstance().getCanvas();
        canvas.add(this, 0);
        canvas.addMouseListener(drawMouseAdapter);
        canvas.addMouseMotionListener(drawMouseAdapter);
    }

    @Override
    public void onSleep() {
        Canvas canvas = Context.getInstance().getCanvas();
        canvas.removeMouseListener(drawMouseAdapter);
        canvas.removeMouseMotionListener(drawMouseAdapter);
    }

    @Override
    public void onDisable() {
        Canvas canvas = Context.getInstance().getCanvas();
        canvas.remove(this);
        canvas.removeMouseListener(drawMouseAdapter);
        canvas.removeMouseMotionListener(drawMouseAdapter);
    }

    @Override
    protected void paintBorder(Graphics g) {
        if(isSelected || onCreate) {
            Graphics2D g2 = (Graphics2D)g;
            Stroke stroke = g2.getStroke();
            float[] arr = {4.0f,2.0f};
            g2.setStroke(new BasicStroke(1,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL,
                    1.0f,arr,0));
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, width.getValue(), height.getValue());
            g2.setStroke(stroke);
        }
        super.paintBorder(g);
    }

    @Override
    public void hintOldTool(Tool tool) {

    }

    public RectBoundElement() {
        setBackground(Color.WHITE);
        setOpaque(false);
    }
}
