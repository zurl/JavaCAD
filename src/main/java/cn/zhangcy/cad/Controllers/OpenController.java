package cn.zhangcy.cad.Controllers;

import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Core.Serializer;
import cn.zhangcy.cad.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class OpenController implements ActionListener {

    Context context;

    public OpenController(Context context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(context.getCanvas()) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                boolean flag = false;
                while ((line = reader.readLine()) != null) {
                    if(flag) stringBuilder.append("\n");
                    else flag = true;
                    stringBuilder.append(line);
                }
                context.getCanvas().cleanUp();
                new Serializer().load(context.getCanvas(), stringBuilder.toString());
                Context.getInstance().setTool(Settings.DEFAULT_TOOL);
                context.getCanvas().validate();
                context.getCanvas().repaint();
                reader.close();
                JOptionPane.showMessageDialog(null, "打开成功");
            }
        }catch (Exception ee){
            JOptionPane.showMessageDialog(null, "打开失败, 信息：" + ee.getMessage());
        }
    }
}
