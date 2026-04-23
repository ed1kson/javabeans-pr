package org.example.mybeans.xmlS;

import org.example.mybeans.Data;
import org.example.mybeans.DataSheet;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;

public class DataSAXHandler extends DefaultHandler {
    private Data data = null;
    private DataSheet sheet = new DataSheet();
    private StringBuilder sb = new StringBuilder();

    public DataSheet getSheet() {
        return sheet;
    }

    public void setSheet(DataSheet sheet) {
        this.sheet = sheet;
    }


    @Override
    public void startElement( String uri, String localName, String qName, Attributes attributes ) {
        sb.setLength(0);
        if ( qName.equals("data") ) {
            data = new Data();
            for ( int i = 0 ; i < attributes.getLength() ; i++ ) {
                if ( attributes.getQName(i).equalsIgnoreCase("date")) {
                    data.setDate((String) attributes.getValue(i));
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        sb.append(new String(ch, start, length));
    }

    @Override
    public void endElement( String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("x")) {
            data.setX(Double.parseDouble(sb.toString().trim()));
        } else if (qName.equalsIgnoreCase("y")) {
            data.setY(Double.parseDouble(sb.toString().trim()));
        } else if (qName.equalsIgnoreCase("data")) {
            sheet.add(data);
        }
    }


}
