package cn.zhangcy.cad.Core;

public interface FieldWatcher<T> {
    void onChange(T value);
}
