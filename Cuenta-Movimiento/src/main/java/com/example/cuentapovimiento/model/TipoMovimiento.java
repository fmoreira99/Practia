package com.example.cuentapovimiento.model;

public enum TipoMovimiento {
    DEBITO, CREDITO;

    public static TipoMovimiento fromString(String text) {
        for (TipoMovimiento tipo : TipoMovimiento.values()) {
            if (tipo.name().equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se encontró TipoMovimiento con texto: " + text);
    }
}
