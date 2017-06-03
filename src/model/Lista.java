/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Usuario
 * @param <T>
 */
public class Lista<T extends Comparable<T>> {
    private NodoLista<T> inicio;
    
    public Lista(){
        this.inicio = null;
    }
    
    public void insertarInicio(Comparable<T> e) {
        NodoLista<T> nuevo = new NodoLista(e);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
        }
    }
    
    public void insertarFinal(Comparable<T> e) {
        NodoLista<T> nuevo = new NodoLista(e);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            NodoLista<T> aux = inicio;
            while(aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
        }
    }
    
    public void imprimir(){
        NodoLista<T> aux = inicio;
        while (aux != null) {
            System.out.println(aux.getDato());
            aux = aux.getSiguiente();
        }
    }
    
    public void insertarOrdenado(Comparable<T> e) {
        NodoLista<T> nuevo = new NodoLista(e);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            NodoLista<T> aux = inicio;
            if (aux.getDato().compareTo(nuevo.getDato())>0){
                nuevo.setSiguiente(inicio);
                inicio = nuevo;
            } else {
                NodoLista<T> anterior = null;
                while(aux != null && nuevo.getDato().compareTo(aux.getDato())>0) {
                    anterior = aux;
                    aux = aux.getSiguiente();
                }
                nuevo.setSiguiente(aux);
                anterior.setSiguiente(nuevo);
            }
        }
    }

    public NodoLista<T> getInicio() {
        return inicio;
    }
    
    
}
