package model.enums;

public enum GrupoSanguineo {
    A_POSITIVO("A+"),
    A_NEGATIVO("A-"),
    B_POSITIVO("B+"),
    B_NEGATIVO("B-"),
    AB_POSITIVO("AB+"),
    AB_NEGATIVO("AB-"),
    O_POSITIVO("O+"),
    O_NEGATIVO("O-");

    private final String simbolo;

    GrupoSanguineo(String simbolo) {
        this.simbolo = simbolo;
    }

   
    public String getSimbolo() {
        return simbolo;
    }

   
    public static GrupoSanguineo deSimbolo(String simbolo) {
        if (simbolo == null) return null;
        for (GrupoSanguineo g : values()) {
            if (g.simbolo.equals(simbolo)) return g;
        }
        throw new IllegalArgumentException("Grupo sanguíneo desconhecido: " + simbolo);
    }
}
