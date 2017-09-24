/*
 * Copyright (C) 2017 Aurum
 *
 * PokéSheet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PokéSheet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aurum.ranger.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Container {
    private List<DataEntry> entries;
    private DataSheet sheet;
    
    public Container(DataSheet sheet) {
        this.entries = new ArrayList();
        this.sheet = sheet;
    }
    
    @Override
    public String toString() {
        return sheet.getName();
    }
    
    public List<DataEntry> entries() {
        return entries;
    }
    
    public DataSheet sheet() {
        return sheet;
    }
    
    public static Container readXml(String f, DataSheet s) throws IOException, JDOMException {
        return readXml(new File(f), s);
    }
    
    public static Container readXml(File f, DataSheet s) throws IOException, JDOMException {
        Element root;
        
        try (FileInputStream in = new FileInputStream(f)) {
            SAXBuilder xml = new SAXBuilder();
            root = xml.build(in).getRootElement();
        }

        if (!root.getName().equals(s.getName()))
            throw new IllegalArgumentException("root element name does not correspond with sheet name");
        
        Container c = new Container(s);
        for (Element child : root.getChildren()) {
            DataEntry de = new DataEntry();
            for (String fe : c.sheet().values().keySet()) {
                Element field = child.getChild(fe);
                String value = field.getAttributeValue("value");
                
                switch(c.sheet().values().get(fe)) {
                    case INT8: de.put(fe, Byte.valueOf(value)); break;
                    case INT16: de.put(fe, Short.valueOf(value)); break;
                    case INT32: de.put(fe, Integer.valueOf(value)); break;
                    case INT64: de.put(fe, Long.valueOf(value)); break;
                    case FLOAT32: de.put(fe, Float.valueOf(value)); break;
                    case FLOAT64: de.put(fe, Double.valueOf(value)); break;
                    case BOOLEAN: de.put(fe, Boolean.valueOf(value)); break;
                }
            }
            c.entries().add(de);
        }
        
        return c;
    }
    
    public static void writeXml(String f, Container c) throws IOException {
        writeXml(new File(f), c);
    }
    
    public static void writeXml(File f, Container c) throws IOException {
        Element root = new Element(c.toString());
        for (DataEntry de : c.entries()) {
            Element child = new Element("entry");
            for (String fn : c.sheet().values().keySet()) {
                child.addContent(new Element(fn).setAttribute("value", de.get(fn).toString()));
            }
            root.addContent(child);
        }
        
        try (FileOutputStream out = new FileOutputStream(f)) {
            XMLOutputter xout = new XMLOutputter();
            xout.setFormat(Format.getPrettyFormat());
            xout.output(root, out);
            out.flush();
        }
    }
}