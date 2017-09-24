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

import com.aurum.ranger.BitConverter;
import com.aurum.ranger.ByteOrder;
import static com.aurum.ranger.ByteOrder.LITTLE_ENDIAN;
import java.io.OutputStream;
import java.io.IOException;

public class BinaryOutputStream extends OutputStream {
    protected volatile OutputStream out;
    protected ByteOrder endian;
    
    public BinaryOutputStream(OutputStream out) {
        this(out, LITTLE_ENDIAN);
    }
    
    public BinaryOutputStream(OutputStream out, ByteOrder endian) {
        this.out = out;
        this.endian = endian;
    }
    
    /**
     * Flushes this output stream and forces any buffered output bytes
     * to be written out.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public void flush() throws IOException {
        out.flush();
    }
    
    /**
     * Closes this output stream and releases any system resources associated
     * with this stream.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public void close() throws IOException {
        out.close();
    }
    
    /**
     * Writes the specified byte to this output stream.
     * @param b the byte
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }
    
    /**
     * Writes {@code b.length} bytes from the specified byte array
     * to this output stream.
     * @param b the byte array
     * @exception IOException  if an I/O error occurs.
     */
    @Override
    public void write(byte[] b) throws IOException {
        out.write(b);
    }
    
    /**
     * Writes {@code len} bytes from the specified byte array
     * starting at offset {@code off} to this output stream.
     * @param b the byte array
     * @exception IOException  if an I/O error occurs.
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
    }
    
    /**
     * Writes the specified byte to this output stream.
     * This is identical to {@code write(int b)}.
     * @param val the 8-bit integer value
     * @throws IOException if an I/O exception occurs.
     */
    public void writeByte(byte val) throws IOException {
        out.write(val);
    }
    
    /**
     * Writes the specified short value to this output stream.
     * @param val the 16-bit integer value
     * @throws IOException if an I/O exception occurs.
     */
    public void writeShort(short val) throws IOException {
        out.write(BitConverter.getBytes(val, endian));
    }
    
    /**
     * Writes the specified int value to this output stream.
     * @param val the 32-bit integer value
     * @throws IOException if an I/O exception occurs.
     */
    public void writeInt(int val) throws IOException {
        out.write(BitConverter.getBytes(val, endian));
    }
    
    /**
     * Writes the specified long value to this output stream.
     * @param val the 64-bit integer value
     * @throws IOException if an I/O exception occurs.
     */
    public void writeLong(long val) throws IOException {
        out.write(BitConverter.getBytes(val, endian));
    }
    
    /**
     * Writes the specified float value to this output stream.
     * @param val the float value
     * @throws IOException if an I/O exception occurs.
     */
    public void writeFloat(float val) throws IOException {
        out.write(BitConverter.getBytes(val, endian));
    }
    
    /**
     * Writes the specified double value to this output stream.
     * @param val the double value
     * @throws IOException if an I/O exception occurs.
     */
    public void writeDouble(double val) throws IOException {
        out.write(BitConverter.getBytes(val, endian));
    }
    
    /**
     * Writes the specified Unicode character to this output stream.
     * @param val the Unicode character
     * @throws IOException if an I/O exception occurs.
     */
    public void writeCharacter(char val) throws IOException {
        out.write(BitConverter.getBytes(val, endian));
    }
    
    /**
     * Writes the specified boolean value to this output stream.
     * @param val the boolean value
     * @throws IOException if an I/O exception occurs.
     */
    public void writeBoolean(boolean val) throws IOException {
        out.write(val ? 1 : 0);
    }
}