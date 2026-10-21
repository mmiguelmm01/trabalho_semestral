package model.enums;


public enum NiveisRisco {
    ZERO_URGENCIA("A"),
    NAO_URGENCIA("B"),
    POUCA_URGENCIA("C"),
    URGENTE("D"),
    EMERGENCIA("E");

    private final String letra;

    NiveisRisco(String letra) {
        this.letra = letra;
    }

    public String getLetra() {
        return letra;
    }

    public static NiveisRisco deLetra(String letra) {
        for (NiveisRisco n : values()) {
            if (n.letra.equals(letra)) return n;
        }
        throw new IllegalArgumentException("Nível de risco desconhecido: " + letra);
    }
}
