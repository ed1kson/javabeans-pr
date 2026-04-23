package org.example.mybeans;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataSheetTable extends JPanel {

    private DataSheetTableModel tableModel = null;
    private JTable table = null;

    public DataSheetTable() {
        setLayout( new BorderLayout() );

        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new FlowLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("add(+)");
        JButton delButton = new JButton("delete(-)");

        buttonPanel.add(addButton);
        buttonPanel.add(delButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        tableModel = new DataSheetTableModel();
        table.setModel(tableModel);
        scrollPane.setViewportView(table);


        addButton.addActionListener(e -> addData());
        delButton.addActionListener(e -> deleteData());
    }

    public DataSheetTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DataSheetTableModel tableModel) {
        this.tableModel = tableModel;
        table.setModel(tableModel);
        table.revalidate();
    }

    public void addData() {
        tableModel.setRowCount(tableModel.getRowCount()+1);
        tableModel.getDataSheet().add(new Data());
        table.revalidate();
        tableModel.fireDataSheetChange();
    }

    public void deleteData() {
        int i = table.getSelectedRow();
        if ( i == -1 ) i = tableModel.getRowCount() - 1;

        if (tableModel.getRowCount() > 1) {
            tableModel.setRowCount(tableModel.getRowCount() - 1);
            tableModel.getDataSheet().remove(i);
            table.revalidate();
            tableModel.fireDataSheetChange();
        } else {
            tableModel.getDataSheet().get(0).setDate("");
            tableModel.getDataSheet().get(0).setX(0);
            tableModel.getDataSheet().get(0).setY(0);
            table.revalidate();
            table.repaint();
            tableModel.fireDataSheetChange();
        }
    }
}
