package org.example;

import org.example.mybeans.*;
import org.example.mybeans.xmlS.DataSAXHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JFrame {
    private DataSheetTable tablePanel;
    private DataSheetGraph graphPanel;
    private JFileChooser chooser = new JFileChooser();
    {
        chooser.setFileFilter(
                new FileNameExtensionFilter("XML Files (*.xml)", "xml")
        );
    }

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setSize(800, 500);

        DataSheet sheet = new DataSheet();

        tablePanel = new DataSheetTable();
        tablePanel.setTableModel(new DataSheetTableModel(sheet));

        graphPanel = new DataSheetGraph();
        graphPanel.setDataSheet(sheet);
        tablePanel.getTableModel().addDataSheetChangeListener(graphPanel);

        JPanel buttonPanel = new JPanel();
        JButton openB = new JButton("Open");
        JButton saveB = new JButton("Save");
        JButton clearB = new JButton("Clear");

        openB.addActionListener(e -> read());
        saveB.addActionListener(e -> write());
        clearB.addActionListener((e -> clear()));

        buttonPanel.add(openB);
        buttonPanel.add(saveB);
        buttonPanel.add(clearB);

        mainPanel.add(tablePanel, BorderLayout.WEST);
        mainPanel.add(graphPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        tablePanel.getTableModel().getDataSheet().add(new Data());
        setVisible(true);
    }

    public File getFile() {
        if ( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public void read() {
        File file = getFile();
        if ( file == null ) return;

        SAXParserFactory factory = SAXParserFactory.newInstance();
        DataSAXHandler handler = null;

        try {
            SAXParser parser = factory.newSAXParser();
            handler = new DataSAXHandler();
            InputStream is = new BufferedInputStream( new FileInputStream(file) );
            parser.parse(is, handler);
            DataSheet newSheet = handler.getSheet();
            setSheet(newSheet);
        } catch ( SAXException e ) {
            System.out.println(e.getMessage());
        } catch ( Exception e ) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            File file = getFile();
            if ( file == null ) return;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("dataset");
            doc.appendChild(root);

            for (Data d : tablePanel.getTableModel().getDataSheet()) {
                Element row = doc.createElement("data");
                row.setAttribute("data", d.getDate());
                root.appendChild(row);

                Element x = doc.createElement("x");
                x.appendChild(doc.createTextNode(String.valueOf(d.getX())));
                row.appendChild(x);

                Element y = doc.createElement("y");
                y.appendChild(doc.createTextNode(String.valueOf(d.getY())));
                row.appendChild(y);
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);
        } catch ( Exception e ) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }

    public void clear() {
        setSheet(new DataSheet());
        tablePanel.addData();
    }

    public void setSheet(DataSheet newSheet) {
        graphPanel.setDataSheet(newSheet);
        tablePanel.getTableModel().setDataSheet(newSheet);
        tablePanel.getTableModel().fireTableDataChanged();
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
