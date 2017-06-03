/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class Registro implements Comparable<Registro>, Serializable{
    private Integer inicio;
    private Integer campo;
    
    public Registro (Integer campo, Integer inicio) {
        this.campo = campo;
        this.inicio = inicio;
    }
    
    public Registro (Integer campo) {
        this.campo = campo;
        this.inicio = -1;
    }
    
    public Registro() {
        this.campo = null;
        this.inicio = -1;
    }

    public Integer getInicio() {
        return inicio;
    }

    public void setInicio(Integer inicio) {
        this.inicio = inicio;
    }

    public Integer getCampo() {
        return campo;
    }

    public void setCampo(Integer campo) {
        this.campo = campo;
    }

    @Override
    public int compareTo(Registro t) {
        return this.campo.compareTo(t.getCampo());
    }

    @Override
    public String toString() {
        return "Registro{" + "inicio=" + inicio + ", campo=" + campo + '}';
    }
}
