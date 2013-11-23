/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web.data;

/**
 *
 * @author hans
 */
public class DataFormat {
    
    private String name;
    private char columnDelimiter;
    private char escapeChar;
    private char quoteChar;
    private String lineDelimiter;
    private String fileExtension;
    private String mimeType;

    public DataFormat(String name, char columnDelimiter, char escapeChar, char quoteChar, String lineDelimiter, String fileExtension, String mimeType) {
        this.name = name;
        this.columnDelimiter = columnDelimiter;
        this.escapeChar = escapeChar;
        this.quoteChar = quoteChar;
        this.lineDelimiter = lineDelimiter;
        this.fileExtension = fileExtension;
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public char getColumnDelimiter() {
        return columnDelimiter;
    }

    public char getEscapeChar() {
        return escapeChar;
    }

    public char getQuoteChar() {
        return quoteChar;
    }

    public String getLineDelimiter() {
        return lineDelimiter;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getMimeType() {
        return mimeType;
    }
    
}
