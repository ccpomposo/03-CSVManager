/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class CSVReader {

    private File file;
    private FileReader reader;
    private BufferedReader buffer;

    public CSVReader(File file) throws FileNotFoundException {
        this.file = file;
        this.reader = new FileReader(this.file);
        this.buffer = new BufferedReader(this.reader);
    }

    public String readRecord() throws IOException {
        if (!this.canRead()){
            this.iniciar();
        }
        return this.buffer.readLine();
    }
    
    public String readInicio() throws IOException {
        this.cerrar();
        this.iniciar();
        return this.buffer.readLine();
    }

    public ArrayList<String> cleanRecord(String registro) throws IOException {
        String rs = registro;

        rs = rs.replaceAll(String.valueOf('"') + "," + String.valueOf('"'), "¿");
        rs = rs.replaceAll(String.valueOf('"'), "");
        //System.out.println(rs);
        String[] campos = rs.split("¿");
        ArrayList<String> resultado = new ArrayList();
        for (String campo : campos) {
            if (campo.contains(",,") && !campo.contains(", ")) {

                String[] aux = campo.split(",");
                for (String string : aux) {
                    resultado.add(string);
                }
            } else if (campo.contains(",,")) {
                String[] aux = campo.split(",,");
                resultado.add(aux[0]);
                resultado.add("");
                resultado.add(aux[1]);
            } else {
                resultado.add(campo);
            }
        }
        return resultado;
    }
    
    private boolean canRead() throws IOException {
        return this.buffer.ready();
    }

    public void cerrar() throws IOException {
        this.buffer.close();
        this.reader.close();
    }

    public boolean isListo() throws IOException {
        return this.buffer.ready();
    }

    public ArrayList<String> buscar(Registro registro) throws IOException {
        this.cerrar();
        this.iniciar();
        this.buffer.skip(registro.getInicio());
        String rs = this.readRecord();
        System.out.println(rs);
        return this.cleanRecord(rs);
    }

    private void iniciar() throws FileNotFoundException {
        this.buffer = new BufferedReader(new FileReader(this.file));
    }
}

class Foo {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        CSVReader r = new CSVReader(new File("denue_inegi_20_.csv"));
        System.out.println(r.cleanRecord(r.readRecord()));
    }
}
