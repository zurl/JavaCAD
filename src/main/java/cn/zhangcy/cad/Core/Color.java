package cn.zhangcy.cad.Core;

public class Color extends java.awt.Color{

    public Color(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public static Color valueOf(String value){
        String[] rgba = value.split(",");
        return new Color(Integer.valueOf(rgba[0]),
                Integer.valueOf(rgba[1]),
                Integer.valueOf(rgba[2]),
                Integer.valueOf(rgba[3]));
    }

    @Override
    public String toString() {
        return getRed() + "," + getGreen() + "," + getBlue() + "," + getAlpha();
    }
}
