package com.zzq.poi.service.impl;

import com.zzq.poi.pojo.User;
import com.zzq.poi.service.PoiService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PoiServiceImpl implements PoiService {
    private static final String XLS_TYPE = ".xls";
    private static final String XLSX_TYPE = ".xlsx";

    @Override
    public Object read(MultipartFile file) {

        try {
            checkFile(file);
            Workbook workbook = getWorkBook(file);

            List<String[]> list = new ArrayList<>();

            if (workbook != null) {
                for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {

                    Sheet sheet = workbook.getSheetAt(sheetNum);
                    if (sheet == null) {
                        continue;
                    }

                    int firstRowNum = sheet.getFirstRowNum();
                    int lastRowNum = sheet.getLastRowNum();

                    for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                        // currentRow
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }

                        int firstCellNum = row.getFirstCellNum();
                        int lastCellNum = row.getLastCellNum();

                        String[] cells = new String[row.getLastCellNum()];
                        // for loop currentRow
                        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                            Cell cell = row.getCell(cellNum, Row.RETURN_BLANK_AS_NULL);
                            cells[cellNum] = getCellValue(cell);
                        }
                        list.add(cells);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public void download() {
        try {
            List<User> userList = new ArrayList<>();
            userList.add(new User("zzq", "123456"));
            userList.add(new User("dmx", "678910"));

            String[] headers = {"username", "password"};
            // type
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            // set width
            sheet.setDefaultColumnWidth(20);
            HSSFRow row = sheet.createRow(0);
            // first row
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);

                cell.setCellValue(text);
            }

            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                // second row
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(user.getUsername());
                row.createCell(1).setCellValue(user.getPassword());
            }

            String fileName = "test.xlsx";
            HttpServletResponse response =
                    ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
            RequestAttributes resetAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) resetAttributes).getRequest();

            final String userAgent = request.getHeader("USER-AGENT");

            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                // IE
                fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            } else if (userAgent.contains("Mozilla")) {
                // google,firefox
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
            } else {
                // other
                fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            }


            response.reset();

            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void checkFile(MultipartFile file) throws IOException {

        if (null == file) {
            throw new FileNotFoundException("file is not exist！");
        }

        String fileName = file.getOriginalFilename();

        if (!fileName.endsWith(XLS_TYPE) && !fileName.endsWith(XLSX_TYPE)) {
            throw new IOException(fileName + "is not excel file！");
        }
    }

    public static Workbook getWorkBook(MultipartFile file) {

        String fileName = file.getOriginalFilename();

        Workbook workbook = null;
        try {
            InputStream is = file.getInputStream();

            if (fileName.endsWith(XLS_TYPE)) {
                // 2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX_TYPE)) {
                // 2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }


    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // numeric to string
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        // check type
        switch (cell.getCellType()) {

            case Cell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;

            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;

            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;

            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;

            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;

            case Cell.CELL_TYPE_ERROR:
                cellValue = "Illegal character";
                break;
            default:
                cellValue = "unknown type";
                break;
        }
        return cellValue;
    }


}
