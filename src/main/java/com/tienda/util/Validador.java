package com.tienda.util;

/**
 * Validaciones con expresiones regulares (regex)
 */
public class Validador {
    public static boolean validarNombre(String nombre) {
        return nombre != null && nombre.matches("^[A-Za-z ]{3,30}$");
    }

    public static boolean validarClave(String clave) {
        return clave != null && clave.matches("^[A-Za-z0-9@#]{4,20}$");
    }

    public static boolean validarPrecio(String s) {
        if (s == null) return false;
        try {
            double v = Double.parseDouble(s);
            return v >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarEntero(String s) {
        if (s == null) return false;
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
