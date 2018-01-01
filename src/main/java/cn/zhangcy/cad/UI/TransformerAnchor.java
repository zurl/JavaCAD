package cn.zhangcy.cad.UI;

import cn.zhangcy.cad.Components.Element;
import cn.zhangcy.cad.Components.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransformerAnchor extends JPanel {

    public static final int SIZE = 10;

    private Transformer transformer;

    private MouseAdapter mouseAdapter;

    private Element parent;

    private int x, y;

    @Override
    protected void paintComponent(Graphics g) {
        g.drawRect(0, 0, SIZE, SIZE);
        super.paintComponent(g);
    }


    class DragMouseAdapter extends MouseAdapter{
        int kX, kY;

        public DragMouseAdapter(int kX, int kY) {
            this.kX = kX;
            this.kY = kY;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int eX = e.getX();
            int eY = e.getY();
            if(eX % 2 == 1) eX += 1;
            if(eY % 2 == 1) eY += 1;
            int newX = parent.X.getValue();
            int newY = parent.Y.getValue();
            if(kX != 0) {
                parent.width.setValue(parent.width.getValue() + (eX - TransformerAnchor.SIZE) * kX);
                newX += (eX - TransformerAnchor.SIZE) / 2;
            }
            if(kY != 0){
                parent.height.setValue(parent.height.getValue() + (eY - TransformerAnchor.SIZE) * kY);
                newY += (eY - TransformerAnchor.SIZE) / 2;
            }
            parent.setPosition(newX, newY);
            parent.repaint();
            transformer.updateAnchorPosition();
            super.mouseDragged(e);
        }
    }


    public TransformerAnchor(Transformer transformer, int kX, int kY) {
        this.transformer = transformer;
        setBackground(Color.WHITE);
        setOpaque(false);
        mouseAdapter = new DragMouseAdapter(kX, kY);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public void setPosition(Element parent, int x, int y){
        this.parent = parent;
        this.x = x;
        this.y = y;
        setBounds(x + SIZE/2, y + SIZE/2, SIZE + 1, SIZE + 1);
    }
}
