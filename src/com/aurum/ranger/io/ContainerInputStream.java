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

import com.aurum.ranger.io.DataSheet.FieldType;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ContainerInputStream extends InputStream {
    protected volatile BinaryInputStream in;
    
    public ContainerInputStream(BinaryInputStream in) {
        this.in = in;
    }
    
    @Override
    public int read() throws IOException {
        return in.read();
    }
    
    @Override
    public int read(byte[] b) throws IOException {
        return in.read(b);
    }
    
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return in.read(b, off, len);
    }
    
    @Override
    public long skip(long n) throws IOException {
        return in.skip(n);
    }
    
    @Override
    public int available() throws IOException {
        return in.available();
    }
    
    @Override
    public void close() throws IOException {
        in.close();
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public synchronized void mark(int readLimit) {
        // nope...
    }
    
    @Override
    public void reset() {
        // nope...
    }
    
    public void read(Container c) throws IOException {
        in.readInt();   // useless magic
        int size = in.readInt();
        int count = in.readInt();
        in.readInt();   // padding...
        
        int sheetsize = c.sheet().byteSum();
        if (size != sheetsize) {
            throw new IllegalArgumentException("Entry sheet size does not equal entry size (" + size + " != " + sheetsize + ")");
        }
        
        for (int i = 0 ; i < count ; i++) {
            DataEntry de = new DataEntry();
            HashMap<String, FieldType> val = c.sheet().values();
            
            for (String fn : val.keySet()) {
                FieldType ft = val.get(fn);
                
                switch(ft) {
                    case INT8: de.put(fn, in.readByte()); break;
                    case INT16: de.put(fn, in.readShort()); break;
                    case INT32: de.put(fn, in.readInt()); break;
                    case INT64: de.put(fn, in.readLong()); break;
                    case FLOAT32: de.put(fn, in.readFloat()); break;
                    case FLOAT64: de.put(fn, in.readDouble()); break;
                    case BOOLEAN: de.put(fn, in.readBoolean()); break;
                    default: throw new IllegalArgumentException("Unknown/unsupported field type " + ft.name());
                }
            }
            
            c.entries().add(de);
        }
    }
}