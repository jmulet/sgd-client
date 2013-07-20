/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.sgd7.actividades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josep
 */
public class DynamicTable {
    
    private List<ColumnModel> columnLabels;
    private List<List<CellModel>> columnValues;
    private int columns=0;
    private int rows=0;
    
    public DynamicTable()
    {
          columnLabels = new ArrayList<ColumnModel>();
          columnValues = new ArrayList<List<CellModel>>();
    }

    public void addRow(List<CellModel> row)
    {
        getColumnValues().add(row);
        rows +=1;
    }
    
    public void addRow(int idx, List<CellModel> row)
    {
        try
        {
            getColumnValues().add(idx,row);
            rows +=1;
        }
        catch(IndexOutOfBoundsException ex)
        {
            System.out.print(ex);
        }
    }
    
    public void removeRow(int idx)
    {
        try
        {
            getColumnValues().remove(idx);
            rows -=1;
        }
        catch(UnsupportedOperationException ex)
        {
            System.out.print(ex);
        }
        catch(IndexOutOfBoundsException ex)
        {
            System.out.print(ex);
        }
        
    }
    
    public void addRowLabel(String label1,String label2,String label3)
    {
        int pos = getColumnLabels().size();
        getColumnLabels().add(new ColumnModel( new Header(label1,label2,label3) ,pos+""));
    }
    
    public void addRowLabel(Header[] labels)
    {
        for(int i=0; i<labels.length; i++)
            addRowLabel(labels[i].getLine1(), labels[i].getLine2(), labels[i].getLine3());
    }
    
    public void addRowLabel(List<Header> labels)
    {
        for(int i=0; i<labels.size(); i++)
            addRowLabel(labels.get(i).getLine1(), labels.get(i).getLine2(), labels.get(i).getLine3());
    }
    
    
    public List<Object> getColumn(int col)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        for(int i=0; i<getColumnValues().size(); i++)
        {
            List<CellModel> row = this.getRow(i);
            if(row.size()<col)
            {
                list.add(row.get(i));
            }
            else
            {
                list.add(""); //blank value
            }
                
        }
        return list;
    }
    
    public List<CellModel> getRow(int row)
    {   
        return getColumnValues().get(row);
    }

    
    public CellModel getCellAt(int row, int col)
    {
        return columnValues.get(row).get(col);
    }
    
    public void addColumn(ArrayList<CellModel> newCol) {
        
        int nf = newCol.size();
        rows = columnValues.size();
        
        //acomoda el nombre de files
        while(rows < nf )
        {
            columnValues.add( new ArrayList<CellModel>() );
            rows +=1;
        }
        
        for(int i=0; i<rows; i++)
        {
            if(i<nf)
                columnValues.get(i).add(newCol.get(i));
            else
                columnValues.get(i).add(new CellModel());
        }
        columns +=1;
    }
    
    public String toString()
    {

        String txt="Dynamic table:: size=" + rows +" x "+ columns + "\n";
        for(int i=0; i<columnLabels.size();i++)
        {
            txt += "["+columnLabels.get(i).getHeader()+"]\t";
        }
        
        txt +="\n";
        for(int i=0; i<rows; i++)
        {
            List<CellModel> row = getRow(i);
            for(int j=0; j<row.size();j++)
            {
                txt += "`"+row.get(j).getValue()+"´\t";
            }
            txt +="\n";
        }
        
        return txt;
    }
    /**
     * @return the columnLabels
     */
    public List<ColumnModel> getColumnLabels() {
        return columnLabels;
    }

    /**
     * @param columnLabels the columnLabels to set
     */
    public void setColumnLabels(List<ColumnModel> columnLabels) {
        this.columnLabels = columnLabels;
    }

    /**
     * @return the columnValues
     */
    public List<List<CellModel>> getColumnValues() {
        return columnValues;
    }

    /**
     * @param columnValues the columnValues to set
     */
    public void setColumnValues(List<List<CellModel>> columnValues) {
        this.columnValues = columnValues;
    }

    /**
     * @return the columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

   
    
}
