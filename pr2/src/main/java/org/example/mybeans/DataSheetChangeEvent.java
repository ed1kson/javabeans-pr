package org.example.mybeans;

import java.util.EventObject;

public class DataSheetChangeEvent extends EventObject {
    private static final long serialVersionID = 1L;

    public DataSheetChangeEvent(Object source) {
        super(source);
    }
}
