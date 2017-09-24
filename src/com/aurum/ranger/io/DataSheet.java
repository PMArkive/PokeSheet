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
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class DataSheet {
    public enum FieldType {
        INT8, INT16, INT32, INT64, FLOAT32, FLOAT64, BOOLEAN;
    }

    private String sheetName;
    private HashMap<String, FieldType> fieldTypes;

    public DataSheet() {
        this("NULL");
    }

    public DataSheet(String name) {
        sheetName = name;
        fieldTypes = new LinkedHashMap();
    }

    public String getName() {
        return sheetName;
    }

    public void setName(String name) {
        sheetName = name;
    }

    @Override
    public String toString() {
        return sheetName;
    }

    public int byteSum() {
        int sum = 0;

        for (FieldType t : fieldTypes.values()) {
            switch(t) {
                case BOOLEAN:
                case INT8: sum += Byte.BYTES; break;
                case INT16: sum += Short.BYTES; break;
                case INT32:
                case FLOAT32: sum+= Integer.BYTES; break;
                case INT64:
                case FLOAT64: sum+= Long.BYTES; break;
            }
        }

        return sum;
    }

    public void add(String name, FieldType type) {
        fieldTypes.put(name, type);
    }

    public void delete(String name) {
        fieldTypes.remove(name);
    }

    public HashMap<String, FieldType> values() {
        return fieldTypes;
    }

    public static DataSheet open(String f) throws IOException, JDOMException {
        return open(new File(f));
    }

    public static DataSheet open(File f) throws IOException, JDOMException {
        Element root;
        
        try (FileInputStream in = new FileInputStream(f)) {
            SAXBuilder xml = new SAXBuilder();
            root = xml.build(in).getRootElement();
        }

        if (!root.getName().equals("sheet"))
            throw new IllegalArgumentException("root element is not sheet");

        DataSheet sheet = new DataSheet(root.getAttributeValue("name"));
        for (Element child : root.getChildren()) {
            sheet.add(child.getAttributeValue("field"), FieldType.valueOf(child.getAttributeValue("type")));
        }

        return sheet;
    }

    public static void save(String f, DataSheet s) throws IOException {
        save(new File(f), s);
    }

    public static void save(File f, DataSheet s) throws IOException {
        Element root = new Element("sheet").setAttribute("name", s.getName());
        for (String fn : s.values().keySet()) {
            root.addContent(new Element("entry").setAttribute("field", fn).setAttribute("type", s.values().get(fn).name()));
        }

        try (FileOutputStream out = new FileOutputStream(f)) {
            XMLOutputter xout = new XMLOutputter();
            xout.setFormat(Format.getPrettyFormat());
            xout.output(root, out);
            out.flush();
        }
    }
}