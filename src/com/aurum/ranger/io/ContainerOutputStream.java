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
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;

public class ContainerOutputStream extends OutputStream {
    protected volatile BinaryOutputStream out;
    
    public ContainerOutputStream(BinaryOutputStream out) {
        this.out = out;
    }
    
    @Override
    public void write(int val) throws IOException {
        out.write(val);
    }
    
    @Override
    public void write(byte[] b) throws IOException {
        out.write(b);
    }
    
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
    }
    
    @Override
    public void flush() throws IOException {
        out.flush();
    }
    
    @Override
    public void close() throws IOException {
        out.close();
    }
    
    public void write(Container c) throws IOException {
        byte[] magic = c.sheet().getName().getBytes(Charset.forName("UTF-8"));
        if (magic.length != 4)
            throw new IllegalArgumentException("sheet name bytes is not 4 bytes long");
        
        out.write(magic);
        out.writeInt(c.sheet().byteSum());
        out.writeInt(c.entries().size());
        out.writeInt(0x0);
        
        for (DataEntry e : c.entries()) {
            HashMap<String, FieldType> val = c.sheet().values();
            
            for (String fn : val.keySet()) {
                FieldType ft = val.get(fn);
                
                switch(ft) {
                    case INT8: out.writeByte((byte) e.get(fn)); break;
                    case INT16: out.writeShort((short) e.get(fn)); break;
                    case INT32: out.writeInt((int) e.get(fn)); break;
                    case INT64: out.writeLong((long) e.get(fn)); break;
                    case FLOAT32: out.writeFloat((float) e.get(fn)); break;
                    case FLOAT64: out.writeDouble((double) e.get(fn)); break;
                    case BOOLEAN: out.writeBoolean((boolean) e.get(fn)); break;
                }
            }
        }
    }
}