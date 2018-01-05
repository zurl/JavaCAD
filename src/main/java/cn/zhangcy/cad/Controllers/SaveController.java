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
import java.io.FileWriter;

public class SaveController implements ActionListener {

    Context context;

    public SaveController(Context context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(context.getCanvas()) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(new Serializer().save(context.getCanvas()));
                fileWriter.close();
                JOptionPane.showMessageDialog(null, "保存成功");
            }
        }catch (Exception ee){
            JOptionPane.showMessageDialog(null, "保存失败, 信息：" + ee.getMessage());
        }
    }
}
