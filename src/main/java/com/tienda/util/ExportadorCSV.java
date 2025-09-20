package com.tienda.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.tienda.modelo.Cliente;
import com.tienda.modelo.Pedido;
import com.tienda.modelo.Producto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Exportador CSV usando Apache Commons CSV
 */
public class ExportadorCSV {
    public static void exportarPedidos(String ruta, Cliente cliente) throws IOException {
        try (FileWriter out = new FileWriter(ruta);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader("pedido_id","fecha","producto","cantidad","total"))) {
            int pid = 1;
            for (Pedido p : cliente.getPedidos()) {
                for (Map.Entry<Producto, Integer> e : p.getProductos().entrySet()) {
                    printer.printRecord(pid++, p.getFecha(), e.getKey().getNombre(), e.getValue(), p.getTotal());
                }
            }
        }
    }
}
