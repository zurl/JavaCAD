package cn.zhangcy.cad.Models;


import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Core.ToolClass;
import cn.zhangcy.cad.Views.Canvas;
import cn.zhangcy.cad.Views.TransformerAnchor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@ToolClass(Name = "自由变换工具", Icon = "move.png")
public class Transformer extends Arrow{


    @Override
    public void onRefresh() {
        updateAnchorPosition();
    }

    class CanvasMouseAdapter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            Canvas canvas = Context.getInstance().getCanvas();
            if(selected != null){
                canvas.remove(anchorPanel);
                selected.setSelected(false);
                selected.addMouseListener( elementMouseAdapter);
            }
            canvas.repaint();
            Context.getInstance().getPropertyBar().setTool(null);
            super.mouseClicked(e);
        }

    }

    public void updateAnchorPosition(){
        if(selected == null || !selected.isSelected()){
            Canvas canvas = Context.getInstance().getCanvas();
            canvas.remove(anchorPanel);
            return;
        }
        transformerAnchors[0].setPosition(selected, 0, 0);
        transformerAnchors[1].setPosition(selected, 0, selected.height.getValue());
        transformerAnchors[2].setPosition(selected, selected.width.getValue(), 0);
        transformerAnchors[3].setPosition(selected, selected.width.getValue(), selected.height.getValue());
        transformerAnchors[4].setPosition(selected, selected.width.getValue() / 2, 0);
        transformerAnchors[5].setPosition(selected, selected.width.getValue() / 2, selected.height.getValue());
        transformerAnchors[6].setPosition(selected, 0, selected.height.getValue() / 2);
        transformerAnchors[7].setPosition(selected, selected.width.getValue(), selected.height.getValue() / 2);
        anchorPanel.setBounds(
                selected.X.getValue() - selected.width.getValue() / 2 - TransformerAnchor.SIZE,
                selected.Y.getValue() - selected.height.getValue() / 2 - TransformerAnchor.SIZE,
                selected.width.getValue() + TransformerAnchor.SIZE * 2 + 1,
                selected.height.getValue() + TransformerAnchor.SIZE * 2 + 1);
        anchorPanel.repaint();
    }

    class ElementMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Canvas canvas = Context.getInstance().getCanvas();
            if(selected != null){
                canvas.remove(anchorPanel);
                selected.setSelected(false);
                selected.addMouseListener( elementMouseAdapter);
            }
            selected = (Element) e.getComponent();
            selected.setSelected(true);
            selected.removeMouseListener(elementMouseAdapter);
            canvas.add(anchorPanel);
            updateAnchorPosition();
            super.mouseClicked(e);
        }

        int stX, stY;

        @Override
        public void mousePressed(MouseEvent e) {
            stX = e.getX();
            stY = e.getY();
            super.mousePressed(e);
        }
    }

    private static JPanel anchorPanel;
    private static TransformerAnchor[] transformerAnchors;

    public Transformer() {
        elementMouseAdapter = new ElementMouseAdapter();
        canvasMouseAdapter = new CanvasMouseAdapter();
        if(anchorPanel == null) {
            transformerAnchors = new TransformerAnchor[8];
            transformerAnchors[0] = new TransformerAnchor(this, -1, -1);
            transformerAnchors[1] = new TransformerAnchor(this, -1, 1);
            transformerAnchors[2] = new TransformerAnchor(this, 1, -1);
            transformerAnchors[3] = new TransformerAnchor(this, 1, 1);
            transformerAnchors[4] = new TransformerAnchor(this, 0, -1);
            transformerAnchors[5] = new TransformerAnchor(this, 0, 1);
            transformerAnchors[6] = new TransformerAnchor(this, -1, 0);
            transformerAnchors[7] = new TransformerAnchor(this, 1, 0);
            anchorPanel = new JPanel();
            anchorPanel.setLayout(null);
            anchorPanel.setBackground(Color.WHITE);
            anchorPanel.setOpaque(false);
            for(int i = 0; i < 8; i++) anchorPanel.add(transformerAnchors[i]);
        }
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
            }
        }
    }

    @Override
    public void onSleep() {
        super.onSleep();
    }

    @Override
    public void onDisable() {
        Canvas canvas = Context.getInstance().getCanvas();
        canvas.remove(anchorPanel);
        if(selected != null){
            selected.setSelected(false);
        }
        canvas.removeMouseListener(canvasMouseAdapter);
        Component[] components = canvas.getComponents();
        for(Component component : components){
            if( component instanceof Element) {
                Element element = (Element) component;
                element.setSelected(false);
                element.removeMouseListener(elementMouseAdapter);
            }
            else{
                canvas.remove(component);
                canvas.repaint();
            }
        }
        canvas.repaint();
    }
}
