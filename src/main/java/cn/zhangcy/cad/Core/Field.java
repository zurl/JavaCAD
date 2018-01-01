package cn.zhangcy.cad.Core;

public class Field <T> {

    private T value;

    private FieldWatcher<T> watcher;

    public Field(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if( watcher != null) watcher.onChange(value);
        this.value = value;
    }

    public void setWatcher(FieldWatcher<T> watcher) {
        this.watcher = watcher;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
