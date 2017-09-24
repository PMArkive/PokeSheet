# PokeSheet
**PokeSheet** is a parser for **Pokémon Ranger: Guardian Signs**' container data files.
With the help of "data field sheets", the entries can be parsed and easily interpreted.
It is programmed in Java and developed using the Netbeans IDE.

# Commands
To extract data from a container file, use ```java -jar PokeSheet.jar -d -sheetfile -containerfile -xmlfile```

To import data from an xml file, use ```java -jar PokeSheet.jar -c -sheetfile -xmlfile -containerfile```

# Sheet files
Sheet files are templates used to interpret and parse the data. The basic structure of a sheet file looks like this:
```
<sheet name="TEST">
	<entry field="anintvalue" type="INT32"/>
</sheet>
```
Supported data types include:
- ```INT8```: An 8-bit integer value (byte).
- ```INT16```: A 16-bit integer value (short).
- ```INT32```: A 32-bit integer value (int).
- ```INT64```: A 64-bit integer value (long).
- ```FLOAT32```: A 32-bit floating point value (float).
- ```FLOAT64```: A 64-bit floating point value (double).
- ```BOOLEAN```: A boolean value (bool).

An example of a parsed XML file:
```
<ZKND>
  <entry>
    <index value="1" />
    <fpkd_index value="11" />
    <unk4 value="232" />
    <unk6 value="0" />
    <unk8 value="0" />
    <unk9 value="0" />
    <unkA value="0" />
    <unkB value="0" />
    <unkC value="8" />
    <unkD value="0" />
    <unkE value="0" />
    <unkF value="0" />
  </entry>
</ZKND>
```
