package com.template.converter;

public class TaylorToursConverter {

    public static int converterQtdeShows(String shows) {
        try {
            return Integer.parseInt(shows.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public static double converterFaturamentoEstimado(String faturamento) {
        try {
            String textoLimpo = faturamento.trim().replace(",", ".");
            return Double.parseDouble(textoLimpo);
        } catch (Exception e) {
            return 0.0;
        }
    }
}