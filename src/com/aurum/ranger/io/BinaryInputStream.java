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

import static com.aurum.ranger.ByteOrder.*;
import com.aurum.ranger.BitConverter;
import com.aurum.ranger.ByteOrder;
import java.io.InputStream;
import java.io.IOException;

public class BinaryInputStream extends InputStream {
    protected volatile InputStream in;
    protected ByteOrder endian;
    
    private final byte[] buff2 = new byte[2];
    private final byte[] buff4 = new byte[4];
    private final byte[] buff8 = new byte[8];
    
    public BinaryInputStream(InputStream in) {
        this(in, LITTLE_ENDIAN);
    }
    
    public BinaryInputStream(InputStream in, ByteOrder endian) {
        this.in = in;
        this.endian = endian;
    }
    
    /**
     * Closes this input stream and releases any system resources associated
     * with the stream.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public void close() throws IOException {
        in.close();
    }
    
    /**
     * Reads the next byte of data from the input stream.
     * @return the next byte.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }
    
    /**
     * Reads {@code b.length} bytes from the input stream and stores them into
     * the buffer array {@code b}.
     * @param b the buffer into which the data is read
     * @return the total number of bytes read into the buffer.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public int read(byte[] b) throws IOException {
        return in.read(b);
    }
    
    /**
     * Reads up to {@code len} bytes from the input stream into an array of bytes.
     * @param b the buffer into which the data is read
     * @param off the start offset in the array
     * @param len the number of bytes to be read
     * @return the total number of bytes read into the buffer.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return in.read(b, off, len);
    }
    
    /**
     * Reads the next byte of data from the input stream.
     * @return the 8-bit value.
     * @throws IOException if an I/O exception occurs.
     */
    public byte readByte() throws IOException {
        return (byte) in.read();
    }
    
    /**
     * Reads the next short value of data from the input stream.
     * @return the 16-bit value.
     * @throws IOException if an I/O exception occurs.
     */
    public short readShort() throws IOException {
        read(buff2, 0, 2);
        return BitConverter.toShort(buff2, endian, 0);
    }
    
    /**
     * Reads the next int value of data from the input stream.
     * @return the 32-bit value.
     * @throws IOException if an I/O exception occurs.
     */
    public int readInt() throws IOException {
        read(buff4, 0, 4);
        return BitConverter.toInt(buff4, endian, 0);
    }
    
    /**
     * Reads the next long value of data from the input stream.
     * @return the 64-bit value.
     * @throws IOException if an I/O exception occurs.
     */
    public long readLong() throws IOException {
        read(buff8, 0, 8);
        return BitConverter.toLong(buff8, endian, 0);
    }
    
    /**
     * Reads the next float value of data from the input stream.
     * @return the float value.
     * @throws IOException if an I/O exception occurs.
     */
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }
    
    /**
     * Reads the next double value of data from the input stream.
     * @return the double value.
     * @throws IOException if an I/O exception occurs.
     */
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }
    
    /**
     * Reads the next Unicode character of data from the input stream.
     * @return the Unicode character.
     * @throws IOException if an I/O exception occurs.
     */
    public char readChar() throws IOException {
        return (char) readShort();
    }
    
    /**
     * Reads the next boolean value of data from the input stream.
     * @return the boolean value.
     * @throws IOException if an I/O exception occurs.
     */
    public boolean readBoolean() throws IOException {
        return in.read() != 0x0;
    }
    
    /**
     * Returns the number of available bytes in this input stream.
     * @return the number of available bytes.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public int available() throws IOException {
        return in.available();
    }
    
    /**
     * Skips over and discards {@code a} bytes of data from this input stream.
     * @param n the number of bytes to be skipped
     * @return the number of actual bytes skipped.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public long skip(long n) throws IOException {
        return in.skip(n);
    }
    
    /**
     * Tests if this input stream supports marked positions.
     * @return {@code true} if this input stream supports marked positions.
     */
    @Override
    public boolean markSupported() {
        return in.markSupported();
    }
    
    /**
     * Marks the current position in this input stream.
     * @param readLimit the maximum limit of bytes that can be read
     *                  before the mark position becomes invalid
     */
    @Override
    public synchronized void mark(int readLimit) {
        in.mark(readLimit);
    }
    
    /**
     * Repositions this stream to the position where the input stream was last called.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public synchronized void reset() throws IOException {
        in.reset();
    }
}