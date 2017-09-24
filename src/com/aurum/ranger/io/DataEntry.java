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

import java.util.Iterator;
import java.util.LinkedHashMap;

public class DataEntry extends LinkedHashMap<String, Object> {
    @Override
    public String toString() {
        String ret = "";
        
        Iterator<String> i = keySet().iterator();
        
        while(i.hasNext()) {
            String fn = i.next();
            ret += fn + " = " + get(fn).toString();
            
            if (i.hasNext())
                ret += "\n";
        }

        return ret;
    }
}