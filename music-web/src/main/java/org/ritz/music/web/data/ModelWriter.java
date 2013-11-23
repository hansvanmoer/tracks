/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web.data;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;
import org.ritz.music.facet.Facet;
import org.ritz.music.facet.FacetException;

/**
 *
 * @author hans
 */
public class ModelWriter<Model extends Serializable> {
    
    private List<Facet<Model> > facets;
    private CSVWriter writer;
    
    public ModelWriter(Writer writer, DataFormat format, List<Facet<Model> > facets){
        this.facets = facets;
        this.writer = new CSVWriter(writer, format.getColumnDelimiter(), format.getQuoteChar(), format.getEscapeChar(), format.getLineDelimiter());
    }
    
    public ModelWriter(OutputStream output, DataFormat format, List<Facet<Model> > facets){
        this.facets = facets;
        this.writer = new CSVWriter(new OutputStreamWriter(output), format.getColumnDelimiter(), format.getQuoteChar(), format.getEscapeChar(), format.getLineDelimiter());
    }
    
    public void writeHeader(){
        String[] line = new String[facets.size()];
        for(int i = 0;i<line.length; i++){
            line[i] = facets.get(i).getName();
        }
        writer.writeNext(line);
    }
    
    public void write(List<Model> models, boolean includeHeader) throws FacetException, IOException{
        String[] line = new String[facets.size()];
        if(includeHeader){
            writeHeader();
        }
        OutputHandle[] outputFacets = new OutputHandle[facets.size()];
        for(int i = 0;i<outputFacets.length; i++){
            outputFacets[i] = facets.get(i).getHandle(OutputHandle.class);
        }
        for(Model model : models){
            for(int i = 0;i<outputFacets.length; i++){
                line[i] = outputFacets[i].getValue(model).toString();
            }
            writer.writeNext(line);
        }
    }
    
    public void close() throws IOException{
        this.writer.close();
    }
    
}
