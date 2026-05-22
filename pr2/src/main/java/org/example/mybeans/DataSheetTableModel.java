package org.example.mybeans;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Iterator;

public class DataSheetTableModel extends AbstractTableModel {

    @Serial
    private static final long serialVersionUID = 1L;

    private int columnCount = 3;
    private int rowCount = 1;

    private DataSheet dataSheet = null;


    String[] columnNames = {"Date", "X Value", "Y Value"};

    private final ArrayList<DataSheetChangeListener> listenerList = new ArrayList<DataSheetChangeListener>();
    private final DataSheetChangeEvent event = new DataSheetChangeEvent(this);
    public void addDataSheetChangeListener(DataSheetChangeListener l) {
        listenerList.add(l);
    }
    public void removeDataSheetChangeListener(DataSheetChangeListener l) {
        listenerList.remove(l);
    }
    protected void fireDataSheetChange() {
        for (DataSheetChangeListener listener : listenerList) listener.dataChanged(event);
    }

    public DataSheetTableModel() {}
    public DataSheetTableModel(DataSheet ds) {
        dataSheet = ds;
    }

    public DataSheet getDataSheet() {
        return dataSheet;
    }

    public void setDataSheet(DataSheet dataSheet) {
        this.dataSheet = dataSheet;
        rowCount = this.dataSheet.size();
        fireDataSheetChange();
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return dataSheet.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        if ( columnCount > 0 )
            this.columnCount = columnCount;
    }

    public void setRowCount(int rowCount) {
        if ( rowCount > 0 )
            this.rowCount = rowCount;
    }

    @Override
    public Object getValueAt(int ri, int ci) {
        if ( dataSheet != null) {
            if ( ci == 0) {
                return dataSheet.get(ri).getDate();
            } else if ( ci == 1 ) {
                return dataSheet.get(ri).getX();
            } else if ( ci == 2 ) {
                return dataSheet.get(ri).getY();
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object obj, int ri, int ci) {
        try {
            if ( dataSheet != null) {
                if ( ci == 0) {
                    dataSheet.get(ri).setDate( (String) obj );
                } else if ( ci == 1 ) {
                    if ( obj instanceof String ) {
                        obj = Double.parseDouble((String) obj);
                    }
                    dataSheet.get(ri).setX( (Double) obj );
                } else if ( ci == 2 ) {
                    if ( obj instanceof String ) {
                        obj = Double.parseDouble((String) obj);
                    }
                    dataSheet.get(ri).setY( (Double) obj );
                }
            }
            fireDataSheetChange();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 0;
    }

}

















