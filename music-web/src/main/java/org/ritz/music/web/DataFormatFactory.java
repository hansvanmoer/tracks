/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.ritz.music.web.data.DataFormat;

/**
 *
 * @author hans
 */
public class DataFormatFactory implements Converter, Serializable{

    private List<DataFormat> dataFormats;
    
    public DataFormatFactory(){
        this.dataFormats = new LinkedList<DataFormat>();
        this.dataFormats.add(new DataFormat("CSV", ',','\\','\"',"\n","csv","text/csv"));
        this.dataFormats.add(new DataFormat("Excel 2003", ';', '\\', '\"', "\n", "xls", "application/vnd.ms-excel"));
    }
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        for(DataFormat format : dataFormats){
            if(format.getName().equals(string)){
                return format;
            }
        }
        return dataFormats.get(0);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return ((DataFormat)o).getName();
    }

    public List<DataFormat> getDataFormats() {
        return dataFormats;
    }
    
    public DataFormat getDefaultDataFormat(){
        return dataFormats.get(0);
    }
    
}
