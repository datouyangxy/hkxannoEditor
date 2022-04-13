package com.xy.hkxannoeditor.core.ListExecutor;

import com.xy.hkxannoeditor.entity.bo.FilePath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ListFixExecutor {

    /**the txt file to deal with */
    File _txt;
    /**self evident */
    Scanner _reader;
    /** after which line to add anno*/
    String _line;
    /**template of anno */
    String _template;
    /**temporary storage for lines in TXT*/
    ArrayList<String> _lines;
    boolean _fixed;

    public ListFixExecutor(File _txt, Scanner _reader, String _line, String _template, ArrayList<String> _lines) {
        this._txt = _txt;
        this._reader = _reader;
        this._line = _line;
        this._template = _template;
        this._lines = _lines;
    }

    public ListFixExecutor(File txt, String line, String template) throws FileNotFoundException {
        _txt = txt;
        _reader = new Scanner(txt);
        _line = line;
        _template = template;
        _lines = new ArrayList<String>();
        _fixed = false;
        System.out.println("\nstart fixing: " + _txt.getName());
    }

    public abstract boolean fix(List<FilePath> filePaths) throws IOException;

    /**write annotations in the list back to the txt*/
    void writeAnno() throws FileNotFoundException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(_txt);
            for (String line : _lines) {
                pw.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * return timestamp of the specific anno line
     */
    public String getTime(String line) {
        return line.split(" ")[0];
    }
    /**
     * return text of specific anno line
     */
    public String getText(String line) {
        return line.split(" ")[1];
    }




}
