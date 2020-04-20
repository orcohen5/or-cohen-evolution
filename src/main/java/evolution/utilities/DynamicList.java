package evolution.utilities;

import java.util.*;

public class DynamicList<T> extends LinkedList {

    @Override
    public boolean add(Object o) {
        super.add(o);
        Collections.sort(this);
        return true;
    }

    public void sortByComparable() {
        Collections.sort(this);
    }
}
