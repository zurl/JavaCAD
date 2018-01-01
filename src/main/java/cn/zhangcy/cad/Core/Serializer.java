package cn.zhangcy.cad.Core;

import cn.zhangcy.cad.Components.Circle;
import cn.zhangcy.cad.Components.Element;
import cn.zhangcy.cad.Components.Text;
import cn.zhangcy.cad.Components.Tool;
import cn.zhangcy.cad.Context;
import cn.zhangcy.cad.Main;
import cn.zhangcy.cad.Settings;
import cn.zhangcy.cad.UI.Canvas;

import java.awt.*;
import java.lang.reflect.Method;


public class Serializer {


    private String toString(Element element){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            java.lang.reflect.Field[] fields = element.getClass().getFields();
            stringBuilder.append(element.getClass().getName());
            for (java.lang.reflect.Field field : fields) {
                if( field.get(element) instanceof Field ){
                    Field data = (Field) field.get(element);
                    stringBuilder.append(" ");
                    stringBuilder.append(data.toString());
                }
            }
            return stringBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    private Element valueOf(String str) throws Exception{
        try {
            String[] tokens = str.split(" ");
            Element element = (Element) Class.forName(tokens[0]).newInstance();
            java.lang.reflect.Field[] fields = element.getClass().getFields();
            int now = 1;
            for (java.lang.reflect.Field field : fields) {
                Object o = field.get(element);
                if (o instanceof Field) {
                    Field f = (Field) o;
                    if (f.getValue() instanceof String) {
                        ((Field) o).setValue(tokens[now]);
                    } else {
                        Method valueOf = f.getValue().getClass().getMethod("valueOf", String.class);
                        ((Field) o).setValue(valueOf.invoke(f, tokens[now]));
                    }
                    now++;
                }
            }
            return element;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("FILE FORMAT INCORRECT");
        }
    }

    public String save(Canvas canvas){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Main.class.getName());
        stringBuilder.append(" ");
        stringBuilder.append(Settings.VERSION);
        stringBuilder.append("\n");
        Component[] components = canvas.getComponents();
        for(Component component : components){
            if(component instanceof Element){
                stringBuilder.append(toString((Element) component));
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public void load(Canvas canvas, String content) throws Exception{
        if( content == null) throw new RuntimeException("FILE FORMAT INCORRECT");
        String[] tokens = content.split("\n");
        if(tokens.length == 0) throw new RuntimeException("FILE FORMAT INCORRECT");
        String[] info = tokens[0].split(" ");
        if(info.length != 2) throw new RuntimeException("FILE FORMAT INCORRECT");
        if( !info[0].equals(Main.class.getName()) || !info[1].equals(Settings.VERSION) ){
            throw new RuntimeException("FILE FORMAT INCORRECT");
        }
        for(int i = 1; i < tokens.length; i++){
            Element element = valueOf(tokens[i]);
            if( element == Context.getInstance().getCurrentTool()) continue;
            canvas.add(element);
        }
    }
}
