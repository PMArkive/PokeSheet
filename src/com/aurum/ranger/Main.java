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

package com.aurum.ranger;

import com.aurum.ranger.io.Container;
import com.aurum.ranger.io.BinaryInputStream;
import com.aurum.ranger.io.BinaryOutputStream;
import com.aurum.ranger.io.ContainerInputStream;
import com.aurum.ranger.io.ContainerOutputStream;
import com.aurum.ranger.io.DataSheet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom2.JDOMException;

public class Main {
    public static void main(String[] args) throws IOException, JDOMException {
        if (args.length != 4)
            return;
        
        switch(args[0]) {
            case "-d": {
                DataSheet s = DataSheet.open(args[1]);
                Container c = new Container(s);
                ContainerInputStream in = new ContainerInputStream(new BinaryInputStream(new FileInputStream(new File(args[2]))));
                in.read(c);
                Container.writeXml(args[3], c);
                break;
            }
            case "-c": {
                DataSheet s = DataSheet.open(args[1]);
                Container c = Container.readXml(args[2], s);
                ContainerOutputStream out = new ContainerOutputStream(new BinaryOutputStream(new FileOutputStream(new File(args[3]))));
                out.write(c);
                break;
            }
        }
    }
}