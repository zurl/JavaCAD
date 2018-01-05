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

public class CreateController implements ActionListener {

    Context context;




    public CreateController(Context context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(null,
                "确认新建文件吗？您还有未保存的数据",
                "确认新建文件",
                JOptionPane.YES_NO_OPTION);

        if (option == 0) {
            context.getCanvas().cleanUp();
            Context.getInstance().setTool(Settings.DEFAULT_TOOL);
            context.getCanvas().validate();
            context.getCanvas().repaint();
        }

    }
}
