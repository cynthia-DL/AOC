package all;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGrid<T> {
    private final List<List<T>> internal;
    private final int height;
    private final int width;

    public AbstractGrid(int height, int width) {
        this.height = height;
        this.width = width;
        this.internal = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            internal.add(new ArrayList<>(width));
        }
    }

    protected boolean isValid(int line, int column) {
        return line >= 0
                && column >= 0
                && line < height
                && column < width;
    }

    public void set(int line, int column, T item) {
        if (!isValid(line, column)) return;

        this.internal.get(line).set(column, item);

    }

    public T get(int line, int column) {
        if (!isValid(line, column)) return null;

        return internal.get(line).get(column);
    }

    public void remove(int line, int column){
        if (!isValid(line, column)) return;

        internal.get(line).set(column, null);
    }

    public boolean isEmpty(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if(get(i, j) != null) return false;
            }
        }

        return true;
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    protected List<List<T>> internal(){
        return this.internal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                sb.append(get(i, j));
            }
        }

        return sb.toString();
    }
}