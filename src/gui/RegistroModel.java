/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Usuario
 */
public class RegistroModel extends AbstractTableModel{
    
    private ArrayList<ArrayList<String>> lista;
    
    public RegistroModel(ArrayList<ArrayList<String>> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return this.lista.get(0).size();
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return this.lista.get(i).get(i1);
    }
    
}
