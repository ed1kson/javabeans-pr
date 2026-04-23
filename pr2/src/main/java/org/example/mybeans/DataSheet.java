package org.example.mybeans;

import java.util.ArrayList;
import java.util.Collection;

public class DataSheet extends ArrayList<Data> {

    public DataSheet() {
        super();
    }

    public DataSheet(Collection<Data> collection) {
        this();
        for ( Data data : collection ) {
            add(data.copy());
        }
    }
}













