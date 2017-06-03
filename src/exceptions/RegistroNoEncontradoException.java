/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Usuario
 */
public class RegistroNoEncontradoException extends Exception {

    /**
     * Creates a new instance of <code>RegistroNoEncontradoException</code>
     * without detail message.
     */
    public RegistroNoEncontradoException() {
    }

    /**
     * Constructs an instance of <code>RegistroNoEncontradoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public RegistroNoEncontradoException(String msg) {
        super(msg);
    }
}
