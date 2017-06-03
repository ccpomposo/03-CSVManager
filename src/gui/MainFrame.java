/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.Controlador;
import exceptions.RegistroNoEncontradoException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Usuario
 */
public class MainFrame extends JFrame{
    private JMenuBar mnbMenu;
    private JButton btnCargar;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JPanel pnlTabla;
    private Controlador controlador;
    private JTable tblRegistros;
    
    public MainFrame() {
        super("CSVManager v1.0");
        super.setSize(500,500);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());
        JPanel pnlAux = new JPanel();
        pnlAux.setLayout(new BorderLayout());
        this.crearMenu();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(this.crearPnlCargar());
        panel.add(this.crearPnlBuscar());
        pnlAux.add(this.mnbMenu,BorderLayout.NORTH);
        pnlAux.add(panel);
        
        super.add(pnlAux,BorderLayout.NORTH);
        try {
            this.crearPnlTabla(null);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.add(this.pnlTabla);
        super.setVisible(true);
    }
    
    private void crearMenu() {
        this.mnbMenu = new JMenuBar();
        JMenu mnArchivo = new JMenu("Archivo");
        JMenuItem mniMostrar = new JMenuItem("Mostrar archivo completo");
        mniMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(controlador != null) {
                    try {
                        MainFrame.super.remove(pnlTabla);
                        crearPnlTabla(controlador.getRegistros(100));
                        MainFrame.super.add(pnlTabla,BorderLayout.CENTER);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Error inesperado");
                    }
                }
            }
        });
        mnArchivo.add(mniMostrar);
        JMenu mnAyuda = new JMenu("Ayuda");
        JMenuItem mniAbout = new JMenuItem("Acerca de...");
        mnAyuda.add(mniAbout);
        this.mnbMenu.add(mnArchivo);
        this.mnbMenu.add(mnAyuda);
    }
    
    private JPanel crearPnlCargar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JFileChooser fchChooser = new JFileChooser();
        JLabel lblArchivo = new JLabel("No se ha seleccionado ningún archivo");
        this.btnCargar = new JButton("Cargar archivo");
        this.btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fchChooser.showOpenDialog(MainFrame.this);
                File file = fchChooser.getSelectedFile();
                try {
                    controlador = new Controlador(file);
                    lblArchivo.setText(file.getName()+" seleccionado");
                    btnBuscar.setEnabled(true);
                    MainFrame.super.remove(pnlTabla);
                    crearPnlTabla(controlador.getRegistros(100));
                    MainFrame.super.add(pnlTabla,BorderLayout.CENTER);
                    pnlTabla.revalidate();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Ocurrió un error al abrir el archivo");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Ocurrió un error al abrir el archivo");
                }
                panel.revalidate();
                MainFrame.super.revalidate();
            }
        });
        panel.add(this.btnCargar);
        panel.add(lblArchivo);
        
        return panel;
    }
    
    private JPanel crearPnlBuscar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        this.txtBuscar = new JTextField(10);
        this.btnBuscar = new JButton("Buscar");
        this.btnBuscar.setEnabled(false);
        this.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ArrayList<ArrayList<String>> aux = new ArrayList();
                try {
                    controlador.crearIndice();
                    aux.add(controlador.buscar(Integer.valueOf(txtBuscar.getText())));
                    MainFrame.super.remove(pnlTabla);
                    crearPnlTabla(aux);
                    MainFrame.super.add(pnlTabla,BorderLayout.CENTER);
                    pnlTabla.revalidate();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Error en el archivo");
                } catch (RegistroNoEncontradoException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "No se encuentra el registro");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "No se permiten letras");
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Error inesperado");
                }
            }
        });
        
        panel.add(new JLabel("ID: "));
        panel.add(this.txtBuscar);
        panel.add(this.btnBuscar);
        
        return panel;
    }
    
    private void crearPnlTabla(ArrayList<ArrayList<String>> arr) throws IOException {
        this.pnlTabla = new JPanel();
        this.pnlTabla.setLayout(new BorderLayout());
        if (this.controlador != null) {
            this.tblRegistros = new JTable(new RegistroModel(arr));
            this.tblRegistros.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            this.pnlTabla.add(new JScrollPane(this.tblRegistros));
        }
    }
}

class Test {
    public static void main(String[] args) {
        new MainFrame();
    }
}
