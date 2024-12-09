package com.linktic.app.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

public class ExportFileXlsx {

    public static  <T> InputStream createExport(List<T> entities) throws IOException, IllegalAccessException
            , NoSuchFieldException {
        if (entities == null || entities.isEmpty()) {
            throw new IllegalArgumentException("Lista de entidades vacía");
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        Row headerRow = sheet.createRow(0);
        Field[] fields = entities.get(0).getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(fields[i].getName());
        }

        int rowNum = 1;
        for (T entity : entities) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(entity);
                Cell cell = row.createCell(i);
                if (value != null) {
                    if (isCustomClass(value)) {

                        Field idField = value.getClass().getDeclaredField("id");
                        idField.setAccessible(true);
                        Object idValue = idField.get(value);
                        cell.setCellValue(idValue != null ? idValue.toString() : "");

                    } else {
                        cell.setCellValue(value.toString());
                    }
                } else {
                    cell.setCellValue("");
                }
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static boolean isCustomClass(Object obj) {

        Class<?> clazz = obj.getClass();
        if (clazz.isEnum()) {
            return false;
        }
        return !obj.getClass().getPackage().getName().startsWith("java.");
    }

}
