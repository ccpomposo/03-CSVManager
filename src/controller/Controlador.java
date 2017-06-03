/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.RegistroNoEncontradoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import model.Arbol;
import model.CSVReader;
import model.Registro;

/**
 *
 * @author Usuario
 */
public class Controlador {

    private File file;
    private CSVReader reader;
    private Arbol registros;

    public Controlador() throws FileNotFoundException {
        this.reader = new CSVReader(file);

    }

    public Controlador(File file) throws FileNotFoundException {
        this.file = file;
        this.reader = new CSVReader(file);
    }

    public void crearIndice() throws IOException, ClassNotFoundException {
        String nombre = this.file.getName().substring(0, this.file.getName().length() - 3) + "idx";
        File aIdx = new File(nombre);
        if (aIdx.exists()) {
            ObjectInputStream iStream = new ObjectInputStream(new FileInputStream(aIdx));
            this.registros = (Arbol) iStream.readObject();
            iStream.close();
        } else {
            this.indexar();
        }
    }

    private void indexar() throws IOException {
        this.registros = new Arbol();
        String registro = this.reader.readInicio();
        Integer id;
        System.out.println(file.length());
        int cont = registro.length();
        System.out.println(cont);
        while (this.reader.isListo()) {
            registro = this.reader.readRecord();
            id = Integer.valueOf(this.reader.cleanRecord(registro).get(0));
            Registro r = new Registro(id, cont + 1);
            registros.insertar(r);
            //System.out.println(cont);
            cont += registro.length() + 1;
        }
        this.reader.cerrar();
        String nombre = this.file.getName().substring(0, this.file.getName().length() - 3) + "idx";
        File aIdx = new File(nombre);
        ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream(aIdx));
        oStream.writeObject(this.registros);
        oStream.close();
    }

    public ArrayList<ArrayList<String>> getRegistros(Integer n) throws IOException {
        ArrayList<ArrayList<String>> rs = new ArrayList();
        for (int i = 0; i < n; i++) {
            rs.add(reader.cleanRecord(reader.readRecord()));

        }
        return rs;
    }

    private Registro buscarArbol(Integer id) throws RegistroNoEncontradoException {
        return this.registros.buscar(id);
    }

    public ArrayList<String> buscar(Integer id) throws IOException, RegistroNoEncontradoException {
        return this.reader.buscar(this.buscarArbol(id));
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}

class Test {

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, RegistroNoEncontradoException {
        Controlador controlador = new Controlador(new File("denue_inegi_20_.csv"));
        controlador.crearIndice();
        //3241113  6175875
        ArrayList<String> resultado = controlador.buscar(6165878);
        for (String string : resultado) {
            System.out.println(string);
        }
    }
}
