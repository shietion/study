package edu.devlopment.cms.excel.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import edu.development.util.StringUtil;

/**
 * 
 * Excel 注解操作类 
 *
 * @author dujianqiao
 * @version 1.0
 */
public class ExcelUtil<T> implements Serializable {

    /**
     * <b>Fields</b> serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -8030064814731848352L;

    private Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 将excel表单数据源的数据导入到list
     * 
     * @param sheetName
     *            工作表的名称
     * @param output
     *            java输入流
     */
    public List<T> getExcelToList(String sheetName, InputStream input) {
        List<T> list = new ArrayList<T>();
        HSSFWorkbook book = null;
        try {
            book = new HSSFWorkbook(input);
            HSSFSheet sheet = null;
            // 如果指定sheet,则取指定sheet中的内容.
            if (!StringUtil.isEmpty(sheetName)) {
                sheet = book.getSheet(sheetName);
            }
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            if (sheet == null) {
                sheet = book.getSheetAt(0);
            }
            // 得到数据的行
            int rows = sheet.getPhysicalNumberOfRows();
            // 有数据时才处
            if (rows > 0) {
                // 得到类的所有field
                Field[] allFields = clazz.getDeclaredFields();
                // 定义一个map用于存放列的序号和field
                Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
                for (int i = 0, index = 0; i < allFields.length; i++) {
                    Field field = allFields[i];
                    // 将有注解的field存放到map集合
                    if (field.isAnnotationPresent(ExcelAttribute.class)) {
                        // 设置类的私有字段属性可访问
                        field.setAccessible(true);
                        fieldsMap.put(index, field);
                        index++;
                    }
                }
                // 从第3行开始取数据,默认第二行是表头
                for (int i = 2, len = rows; i < len; i++) {
                    // 得到对应行中的所有单元格对象.
                    HSSFRow row = sheet.getRow(i);
                    if (null == row) {
                        continue;
                    }
                    T entity = null;
                    int index = 0;
                    int x = fieldsMap.size() ;
                    for (int k=0;k<x;k++) {
                        Cell cell = row.getCell(k) ;
                        // 单元格中的内容
                        String c = "";

                        if(null != cell) {
                            boolean isMerge = isMergedRegion(sheet, i, cell.getColumnIndex());
                            // 判断是否具有合并单元格
                            if (isMerge) {
                                c = getMergedRegionValue(sheet, row.getRowNum(), cell.getColumnIndex());
                            } else {
                                c = cell.getStringCellValue();

                            }
                            if (StringUtil.isEmpty(c)) {
                                c = "";
                            }
                        } 

                        // 如果不存在实例则新建
                        entity = (entity == null ? clazz.newInstance() : entity);
                        // 从map中得到对应列的field
                        Field field = fieldsMap.get(index);
                        if (field == null) {
                            continue;
                        }
                        // 取得类型,并根据对象类型设置�??.
                        Class<?> fieldType = field.getType();
                        if (fieldType == null) {
                            continue;
                        }
                        if (String.class == fieldType) {
                            field.set(entity, String.valueOf(c));
                        } else if (BigDecimal.class == fieldType) {
                            field.set(entity, BigDecimal.valueOf(Double.valueOf(c)));
                        } else if (Date.class == fieldType) {
                            field.set(entity, c);
                        } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                            field.set(entity, parseInteger(c));
                        } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                            field.set(entity, Long.valueOf(c));
                        } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                            field.set(entity, Float.valueOf(c));
                        } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                            field.set(entity, Short.valueOf(c));
                        } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                            field.set(entity, Double.valueOf(c));
                        } else if (Character.TYPE == fieldType) {
                            if ((c != null) && (c.length() > 0)) {
                                field.set(entity, Character.valueOf(c.charAt(0)));
                            }
                        }
                        index++;

                    }
                    if (entity != null) {
                        list.add(entity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                book.close();
            } catch (Exception e) {

            }
        }
        return list;
    }

    /**
     * 
     * <p>
     * <b>Title:</b> parseInteger
     * </p>
     * <p>
     * <b>Description:</b> TODO(这里用一句话描述这个方法的作用)
     * </p>
     * <b>DATE:</b> 2017年6月22日 下午3:04:16
     * 
     * @author dujianqiao
     * @param c
     * @return
     */
    private Integer parseInteger(String c) {
        int result = 0;
        try {
            result = Integer.parseInt(c);
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * 
     * <p>
     * <b>Title:</b> isMergedRegion
     * </p>
     * <p>
     * <b>Description:</b> TODO(这里用一句话描述这个方法的作用)
     * </p>
     * <b>DATE:</b> 2017年6月22日 下午3:04:21
     * 
     * @author dujianqiao
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * <p>
     * <b>Title:</b> getMergedRegionValue
     * </p>
     * <p>
     * <b>Description:</b> TODO(获取合并单元格的值)
     * </p>
     * <b>DATE:</b> 2017年6月22日 下午3:04:51
     * 
     * @author dujianqiao
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {

                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return fCell.getStringCellValue();
                }
            }
        }

        return null;
    }
    
    public boolean writeListToExcel(List<T> list, String sheetName, OutputStream output,Map<String,Object> config) {
        HSSFWorkbook wb = null ;
        try {
            wb = getWorkBook(wb,list,sheetName,output, config);
            wb.write(output);
            output.flush();
            output.close();
            wb.close();
        } catch (Exception e) {
            
        }
        return true ;
    }

    /**
     * 
     * <p><b>Title:</b> getListToExcel</p>
     * <p><b>Description:</b> TODO(导出EXCEL)</p>
     * <b>DATE:</b> 2017年6月22日 下午3:27:01
     * @author dujianqiao
     * @param list
     * @param sheetName
     * @param output
     * @param config
     * @return
     */
    public HSSFWorkbook getWorkBook(HSSFWorkbook wb,List<T> list, String sheetName, OutputStream output,Map<String,Object> config) {
        try {
            // excel中每个sheet中最多有65536行
            int sheetSize = 65536;
            // 得到所有定义字段  
            Field[] allFields = clazz.getDeclaredFields();  
            List<Field> fields = new ArrayList<Field>(); 
            for (Field field : allFields) {  
                if (field.isAnnotationPresent(ExcelAttribute.class)) {  
                    fields.add(field);  
                }  
            }
            // 产生工作薄对象  
            if(null == wb) {
                wb = new HSSFWorkbook(); 
            } 
            // 取出一共有多少个sheet  
            int listSize = 0;  
            if (list != null && list.size() >= 0) {  
                listSize = list.size();  
            }
            //计算共创建多少个sheet
            double sheetNo = Math.ceil(listSize / sheetSize);  
            for (int index = 0; index <= sheetNo; index++) { 
                // 产生工作表对象  
                Sheet sheet = wb.createSheet(); 
                Row row = sheet.createRow(0);
                CreationHelper createHelper = wb.getCreationHelper();
                // 申明单元格
                Cell cell = null;
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                
                // 设置字体
                Font font = wb.createFont();
                font.setFontName("宋体");
                font.setBold(true);
                font.setFontHeightInPoints((short) 18);

                cellStyle.setFont(font);

                int columnIndex = 0;

                // 标题
                Row rowC = sheet.createRow(0);
                Cell cell_c0 = rowC.createCell(0);
                rowC.setHeight((short) (30 * 25));
                cell_c0.setCellValue(sheetName);
                cell_c0.setCellStyle(cellStyle);

                CellRangeAddress cellRowC = new CellRangeAddress(0, 0, 0, (Integer) config.get("title"));
                sheet.addMergedRegion(cellRowC);
                
                // 表头标题样式
                CellStyle titleStyle = wb.createCellStyle();

                // 设置背景色
                titleStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // 设置边框
                titleStyle.setBorderBottom(BorderStyle.THIN);
                titleStyle.setBorderLeft(BorderStyle.THIN);
                titleStyle.setBorderTop(BorderStyle.THIN);
                titleStyle.setBorderRight(BorderStyle.THIN);

                // 设置居中
                titleStyle.setAlignment(HorizontalAlignment.CENTER);

                // 设置字体
                Font fontN = wb.createFont();
                fontN.setFontName("宋体");
                fontN.setBold(true);
                fontN.setFontHeightInPoints((short) 11);
                titleStyle.setFont(fontN);
                
                 
                row = sheet.createRow(1);
                ExcelAttribute excel = null;
                for (Field field : fields) {
                    field.setAccessible(true);
                    excel = field.getAnnotation(ExcelAttribute.class);
                    if (excel == null) {
                        continue;
                    }
                    // 列宽注意乘256
                    sheet.setColumnWidth(columnIndex, excel.width() * 256);
                    // 写入标题
                    cell = row.createCell(columnIndex);
                    cell.setCellStyle(titleStyle);
                    cell.setCellValue(excel.name());
                    columnIndex++;
                }
                
                int rowIndex = 2;
                int merc = 2;
                int merc2 = 2 ;
                int merc3 = 2 ;
                int merc4 = 2 ;
                int startNo = index * sheetSize;  
                int endNo = Math.min(startNo + sheetSize, listSize);  
                CellStyle cs = wb.createCellStyle();
                cs.setWrapText(true); 
                for (int i = startNo; i < endNo; i++) {  
                    T t = list.get(i);
                    row = sheet.createRow(rowIndex);
                    columnIndex = 0;
                    Object o = null;
                    for (Field field : fields) {
                        field.setAccessible(true);
                        excel = field.getAnnotation(ExcelAttribute.class);
                        if (excel == null) {
                            continue;
                        }
                        // 数据
                        cell = row.createCell(columnIndex);
                        o = field.get(t);
                        String val = "";
                        
                        if (excel.isMer()) {
                            String value = field.get(t) + "" ;
                            int ci = 0 ;
                            if(config.containsKey(field.get(t) + "")) {
                                ci  = (Integer) config.get(value) ;
                            }
                            value = value.contains("&") ? value.split("&")[0] :value;
                            if(rowIndex == 2 && columnIndex == 0) {
                                CellStyle scs = wb.createCellStyle();
                                scs.setVerticalAlignment(VerticalAlignment.CENTER);
                                scs.setAlignment(HorizontalAlignment.CENTER);
                                int firstRow = merc ;
                                int lastRow = firstRow+ci-1 ;
                                if(firstRow == lastRow) {
                                    cell.setCellStyle(scs);
                                    cell.setCellValue(value);
                                } else {
                                    CellRangeAddress cllra = new CellRangeAddress(firstRow, lastRow, columnIndex, columnIndex);
                                    cell.setCellStyle(scs);
                                    cell.setCellValue(value);
                                    sheet.addMergedRegion(cllra);
                                }
                                merc = merc+ci ;
                            }
                            if(rowIndex == merc2 && columnIndex == 1) {
                                CellStyle scs = wb.createCellStyle();
                                scs.setVerticalAlignment(VerticalAlignment.CENTER);
                                scs.setAlignment(HorizontalAlignment.CENTER);
                                int firstRow = merc2 ;
                                int lastRow = firstRow+ci-1 ;
                                if(firstRow == lastRow) {
                                    cell.setCellStyle(scs);
                                    cell.setCellValue(value);
                                } else {
                                    CellRangeAddress cllra = new CellRangeAddress(firstRow, lastRow, columnIndex, columnIndex);
                                    cell.setCellStyle(scs);
                                    cell.setCellValue(value);
                                    sheet.addMergedRegion(cllra);
                                }
                                merc2 = merc2+ci ;
                            }
                            if(rowIndex == merc3 && columnIndex == 2) {
                                CellStyle scs = wb.createCellStyle();
                                scs.setVerticalAlignment(VerticalAlignment.CENTER);
                                scs.setAlignment(HorizontalAlignment.CENTER);
                                int firstRow = merc3 ;
                                int lastRow = firstRow+ci-1 ;
                                if(firstRow == lastRow) {
                                    cell.setCellStyle(scs);
                                    cell.setCellValue(value);
                                } else {
                                    CellRangeAddress cllra = new CellRangeAddress(firstRow, lastRow, columnIndex, columnIndex);
                                    cell.setCellStyle(scs);
                                    cell.setCellValue(value);
                                    sheet.addMergedRegion(cllra);
                                }
                                
                                merc3 = merc3+ci  ;
                            }
                            if(rowIndex == merc4 && columnIndex == 3) {
                                CellStyle scs = wb.createCellStyle();
                                scs.setVerticalAlignment(VerticalAlignment.CENTER);
                                scs.setAlignment(HorizontalAlignment.CENTER);
                                int firstRow = merc4 ;
                                int lastRow = firstRow+ci-1 ;
                                if(firstRow == lastRow) {
                                    cell.setCellStyle(scs);
                                    cell.setCellValue(value);
                                } else {
                                    CellRangeAddress cllra = new CellRangeAddress(firstRow, lastRow, columnIndex, columnIndex);
                                    cell.setCellStyle(scs);
                                    cell.setCellValue(value);
                                    sheet.addMergedRegion(cllra);
                                }
                                merc4 = merc4+ci  ;
                            }

                        } else {
                            // 如果数据为空，跳过
                            if (o == null) {
                                cell.setCellValue(val);
                            }
                            // 处理日期类型
                            if (o instanceof Date || o instanceof Timestamp) {
                                cs.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
                                cell.setCellStyle(cs);
                                cell.setCellValue((Date) field.get(t));
                            } else if (o instanceof Double || o instanceof Float) {
                                cell.setCellValue((Double) field.get(t));
                            } else if (o instanceof Boolean) {
                                Boolean bool = (Boolean) field.get(t);
                                cell.setCellValue(bool);

                            } else if (o instanceof Integer) {
                                Integer intValue = (Integer) field.get(t);
                                cell.setCellValue(intValue);
                            } else {
                                String value = field.get(t) + "";
                                cell.setCellStyle(cs);
                                cell.setCellValue(new HSSFRichTextString(value));
                            }
                        }
                        columnIndex++;
                    }
                    rowIndex++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb ;
    }

}

