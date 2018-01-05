package com.jsontocsv.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.jsontocsv.converter.model.Product;

import java.io.*;
import java.util.List;

public class App {
    private static final String CSV_SEPARATOR = ",";
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new Exception("Sufficient parameters are not present");
        }
        String jsonFilePath = args[0];
        String csvFilePath = args[1];
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<Product>>() {});
        final List<Product> products = reader.readValue(new File(jsonFilePath));
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFilePath), "UTF-8"));
            writeToCSV(bw, "ID", "NAME", "COST", "VATAPPLICABLE");
            for (Product product : products)
            {
                writeToCSV(bw, product.getId(), product.getName(), product.getCostPrice(), product.isVatApplicable());
            }
            bw.flush();
            bw.close();
            System.out.println("CSV file path : " + csvFilePath);
        } catch(Exception e) {
            System.out.println("Converting json to csv failed");
        }
    }

    private static void writeToCSV(BufferedWriter bw, Object column1, Object column2, Object column3, Object column4) throws IOException {
        StringBuffer oneLine = new StringBuffer();
        oneLine.append(column1);
        oneLine.append(CSV_SEPARATOR);
        oneLine.append(column2);
        oneLine.append(CSV_SEPARATOR);
        oneLine.append(column3);
        oneLine.append(CSV_SEPARATOR);
        oneLine.append(column4);
        bw.write(oneLine.toString());
        bw.newLine();
    }
}
