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
            return Double.parseDouble(faturamento.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
}