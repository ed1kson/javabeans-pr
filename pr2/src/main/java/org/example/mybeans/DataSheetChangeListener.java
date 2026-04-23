package org.example.mybeans;

import java.util.EventListener;

public interface DataSheetChangeListener extends EventListener {
    public void dataChanged(DataSheetChangeEvent e);
}
