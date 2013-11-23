/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.ritz.music.facet.FacetException;
import org.ritz.music.model.Identifiable;
import org.ritz.music.model.QueryResult;
import org.ritz.music.service.MusicServiceException;
import org.ritz.music.web.data.DataFormat;
import org.ritz.music.web.data.ModelWriter;

/**
 *
 * @author hans
 */
public class DataExporter<Model extends Identifiable> implements Serializable{
    
    private String fileName;
    private FacetedDataModel<Model> dataModel;
    private boolean includeHeader;
    private DataFormat dataFormat;

    public DataExporter(FacetedDataModel<Model> dataModel, String fileName, boolean includeHeader, DataFormat dataFormat) {
        this.fileName = fileName;
        this.dataModel = dataModel;
        this.includeHeader = includeHeader;
        this.dataFormat = dataFormat;
    }
    
    public StreamedContent getContent() throws FacetException, MusicServiceException, IOException{
        StringWriter buffer = new StringWriter();
        ModelWriter<Model> writer = new ModelWriter<Model>(buffer, dataFormat, this.dataModel.getFacets());
        QueryResult<Model> result = dataModel.getSearchService().search(this.dataModel.getSearchTerms(), this.dataModel.getSortFacet(), this.dataModel.getSortOrder());
        writer.write(result.getResults(), includeHeader);
        buffer.close();
        return new DefaultStreamedContent(new ByteArrayInputStream(buffer.getBuffer().toString().getBytes()), dataFormat.getMimeType(), String.format("%s.%s", fileName,dataFormat.getFileExtension()), "utf-8");
    }

    public FacetedDataModel<Model> getDataModel() {
        return dataModel;
    }

    public void setDataModel(FacetedDataModel<Model> dataModel) {
        this.dataModel = dataModel;
    }

    public boolean isIncludeHeader() {
        return includeHeader;
    }

    public void setIncludeHeader(boolean includeHeader) {
        this.includeHeader = includeHeader;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }
}
