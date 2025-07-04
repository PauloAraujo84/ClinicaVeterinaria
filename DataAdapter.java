package br.com.veterinaria.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataAdapter {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String formatarData(Date data) {
        if (data == null) {
            return null;
        }
        return formatter.format(data);
    }
}