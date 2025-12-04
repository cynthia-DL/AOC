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

        for (int i = 0; i < this.height; i++) {
            internal.add(new ArrayList<>());
        }
    }

    protected boolean isPresent(int line, int column) {
        if (line < 0 || line >= width) {
            return false;
        }

        if (column < 0 || column >= height) {
            return false;
        }

        return true;
    }

    public void set(int line, int column, T item) {
        if (!isPresent(line, column)) return;

        this.internal.get(line).set(column, item);

    }

    public T get(int line, int column) {
        if (!isPresent(line, column)) return null;

        return internal.get(line).get(column);
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
}