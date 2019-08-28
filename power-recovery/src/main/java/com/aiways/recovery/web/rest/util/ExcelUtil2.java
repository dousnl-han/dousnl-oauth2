package com.aiways.recovery.web.rest.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class ExcelUtil2 {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil2.class);
    public static String NO_DEFINE = "no_define";//未定义的字段
    public static String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";//默认日期格式
    public static int DEFAULT_COLOUMN_WIDTH = 17;
    public static  String[] p_static;
    public static String[] h_static;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static ZoneId zone = ZoneId.systemDefault();
    /**
     * 导出Excel 97(.xls)格式 ，少量数据
     *
     * @param title       标题行
     * @param headMap     属性-列名
     * @param jsonArray   数据集
     * @param datePattern 日期格式，null则用默认日期格式
     * @param colWidth    列宽 默认 至少17个字节
     * @param out         输出流
     */
    public static void exportExcel(String title, Map<String, String> headMap, JSONArray jsonArray, String datePattern, int colWidth, OutputStream out) {
        if (datePattern == null) datePattern = DEFAULT_DATE_PATTERN;
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        workbook.createInformationProperties();
        workbook.getDocumentSummaryInformation().setCompany("*****公司");
        SummaryInformation si = workbook.getSummaryInformation();
        si.setAuthor("JACK");  //填加xls文件作者信息
        si.setApplicationName("导出程序"); //填加xls文件创建程序信息
        si.setLastAuthor("最后保存者信息"); //填加xls文件最后保存者信息
        si.setComments("JACK is a programmer!"); //填加xls文件作者信息
        si.setTitle("POI导出Excel"); //填加xls文件标题信息
        si.setSubject("POI导出Excel");//填加文件主题信息
        si.setCreateDateTime(new Date());
        //表头样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight((short) 700);
        titleStyle.setFont(titleFont);
        // 列头样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        // 单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont cellFont = workbook.createFont();
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
        HSSFSheet sheet = workbook.createSheet();
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
            0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("JACK");
        //设置列宽
        int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH : colWidth;//至少字节数
        int[] arrColWidth = new int[headMap.size()];
        // 产生表格标题行,以及设置列宽
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];
        int ii = 0;
        for (Iterator<String> iter = headMap.keySet().iterator(); iter
            .hasNext(); ) {
            String fieldName = iter.next();

            properties[ii] = fieldName;
            headers[ii] = fieldName;

            int bytes = fieldName.getBytes().length;
            arrColWidth[ii] = bytes < minBytes ? minBytes : bytes;
            sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
            ii++;
        }
        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        for (Object obj : jsonArray) {
            if (rowIndex == 65535 || rowIndex == 0) {
                if (rowIndex != 0) sheet = workbook.createSheet();//如果数据超过了，则在第二页显示

                HSSFRow titleRow = sheet.createRow(0);//表头 rowIndex=0
                titleRow.createCell(0).setCellValue(title);
                titleRow.getCell(0).setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));

                HSSFRow headerRow = sheet.createRow(1); //列头 rowIndex =1
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                    headerRow.getCell(i).setCellStyle(headerStyle);

                }
                rowIndex = 2;//数据内容从 rowIndex=2开始
            }
            JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
            HSSFRow dataRow = sheet.createRow(rowIndex);
            for (int i = 0; i < properties.length; i++) {
                HSSFCell newCell = dataRow.createCell(i);

                Object o = jo.get(properties[i]);
                String cellValue = "";
                if (o == null) cellValue = "";
                else if (o instanceof Date) cellValue = new SimpleDateFormat(datePattern).format(o);
                else cellValue = o.toString();

                newCell.setCellValue(cellValue);
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
        // 自动调整宽度
        /*for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }*/
        try {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出Excel 2007 OOXML (.xlsx)格式
     *
     * @param titles       标题行
     * @param headMaps     属性-列头
     * @param jsonArray   数据集
     * @param datePattern 日期格式，传null值则默认 年月日
     * @param colWidth    列宽 默认 至少17个字节
     */
    public static SXSSFWorkbook  exportExcelX(String[] titles, Map<String, String>[] headMaps, JSONArray[] jsonArray, String datePattern, int colWidth) {
        if (datePattern == null) datePattern = DEFAULT_DATE_PATTERN;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
        workbook.setCompressTempFiles(true);
        //表头样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight((short) 700);
        titleStyle.setFont(titleFont);
        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        // 单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
        SXSSFSheet sheet = workbook.createSheet();
        sheet = assistant(headMaps[0],sheet,colWidth);
        workbook.setSheetName(0,titles[0]);
            // 遍历集合数据，产生数据行
            int rowIndex = 0;
            int currentCount = 0;
            boolean initSheet = false;
            while (currentCount<titles.length) {
                for (Object obj : jsonArray[currentCount]) {
                    if (rowIndex-1 > jsonArray[currentCount].size()) {
                        rowIndex = 0;
                        currentCount++;
                        initSheet = true;
                        break;
                    }
                    if (rowIndex == 65535 || rowIndex == 0) {
                        if (rowIndex != 0 || initSheet) {
                            sheet = workbook.createSheet();//如果数据超过了，则在第二页显示或操作对象发生变化
                            sheet = assistant(headMaps[currentCount],sheet,colWidth);
                            if(rowIndex !=0)
                                workbook.setSheetName(workbook.getNumberOfSheets()-1,titles[currentCount]);
                            else
                                workbook.setSheetName(currentCount,titles[currentCount]);
                        }

                        SXSSFRow titleRow = sheet.createRow(0);//表头 rowIndex=0
                        titleRow.createCell(0).setCellValue(titles[currentCount]);
                        titleRow.getCell(0).setCellStyle(titleStyle);
                        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMaps[currentCount].size() - 1));

                        SXSSFRow headerRow = sheet.createRow(1); //列头 rowIndex =1
                        for (int i = 0; i < h_static.length; i++) {
                            headerRow.createCell(i).setCellValue(h_static[i]);
                            headerRow.getCell(i).setCellStyle(headerStyle);

                        }
                        rowIndex = 2;//数据内容从 rowIndex=2开始
                    }
                    JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
                    SXSSFRow dataRow = sheet.createRow(rowIndex);
                    for (int i = 0; i < p_static.length; i++) {
                        SXSSFCell newCell = dataRow.createCell(i);

                        Object o = jo.get(p_static[i]);
                        String cellValue = "";
                        if (o == null) cellValue = "";
                        else if (o instanceof Date) cellValue = new SimpleDateFormat(datePattern).format(o);
                        else if (o instanceof Float || o instanceof Double)
                            cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                        else cellValue = o.toString();

                        newCell.setCellValue(cellValue);
                        newCell.setCellStyle(cellStyle);
                    }
                    rowIndex++;
                }
            }
            return workbook;
            // 自动调整宽度
        /*for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }*/
            /*try {
                workbook.write(out);
                workbook.close();
                workbook.dispose();
            } catch (IOException e) {
                logger.error("导出excel异常", e);
            }*/
        }

    public static SXSSFSheet assistant(Map<String,String> headMap,SXSSFSheet sheet,int colWidth) {
        //设置列宽
        int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH : colWidth;//至少字节数
        int[] arrColWidth = new int[headMap.size()];
        // 产生表格标题行,以及设置列宽
        p_static = new String[headMap.size()];
        h_static = new String[headMap.size()];
        int ii = 0;
        for (Iterator<String> iter = headMap.keySet().iterator(); iter
            .hasNext(); ) {
            String fieldName = iter.next();

            p_static[ii] = fieldName;
            h_static[ii] = headMap.get(fieldName);

            int bytes = fieldName.getBytes().length;
            arrColWidth[ii] = bytes < minBytes ? minBytes : bytes;
            sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
            ii++;
        }
        return sheet;
    }

    //Web 导出excel
    public static void downloadExcelFile(String[] titles, Map<String,String>[] headMaps, JSONArray[] ja, HttpServletResponse response) throws Exception {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            SXSSFWorkbook workbook = ExcelUtil2.exportExcelX(titles, headMaps, ja, null, 0);
            try {
                workbook.write(os);
                workbook.close();
                workbook.dispose();
            } catch (IOException e) {
                logger.error("导出excel异常", e);
            }
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="
//                + new String((titles[0] + ".xlsx").getBytes(), "iso-8859-1"));
                + URLEncoder.encode(titles[0],"UTF-8")+".xlsx");
            response.setContentLength(content.length);
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);

            }
            bis.close();
            bos.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("导出excel异常", e);
            throw new Exception("export excel error:"+e.getMessage());
        }
    }

    /**
     * @Author: weilf
     * @Date 2018/8/23 17:22
     * @Description 导出导入模板
     * @Param
    */
    public static String downloadTemplate(String title,HttpServletResponse response,String tempPath){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ServletOutputStream sos = null;
        try {
            Resource rs = new ClassPathResource(tempPath);
            bis = new BufferedInputStream(rs.getInputStream());
            sos = response.getOutputStream();
            bos = new BufferedOutputStream(sos);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((title + ".xlsx").getBytes(), "iso-8859-1"));
            response.setContentLength(bis.available());
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.flush();
            bos.close();
            sos.flush();
            sos.close();
            return "successed";
        } catch (Exception e) {
            logger.error("下载模板失败：",e);
            return "failed";
        }

    }

    public static ResolveExcelDTO readExcel(Object[] objs, InputStream fileStream, String type, int startRowNum, Map[] formatMap) {
        ResolveExcelDTO result = new ResolveExcelDTO();
        result.setStatus(ResolveStatus.SUCCESS);
        result.setMessage(new StringBuffer());
        try {
            // 创建工作簿
            Workbook workBook = getWorkbook(fileStream, type);
            if (workBook != null) {
                // 获取sheet
                List<Sheet> sheets = getSheet(workBook);
                StringBuffer message = new StringBuffer();
                Map<Class,List> resultMap = new HashMap<>();
                int index = 0;
                //for (Sheet sheet : sheets) {
                    List rel = parseSheetToList(objs[index], sheets.get(0), startRowNum, message, formatMap[index]);
                    if (CollectionUtils.isEmpty(rel) || StringUtils.isNotEmpty(message)) {
                        result.getMessage().append("工作簿【" + sheets.get(0).getSheetName() + "】解析错误：\r\n" + message);
                    } else {
                        resultMap.put(objs[index].getClass(),rel);
                        result.setResultMap(resultMap);
                    }
                    //index++;
                //}
                if (CollectionUtils.isEmpty(result.getResultMap())) {
                    result.setStatus(ResolveStatus.ERROR);
                }
            } else {
                result.setStatus(ResolveStatus.ERROR);
            }
        } catch (Exception e) {
            logger.error("exe readExcel error:", e);
            result.setStatus(ResolveStatus.ERROR);
            result.setMessage(result.getMessage().append(e.getMessage()));
        }
        return result;
    }

    private static Workbook getWorkbook(InputStream fileStream, String type) throws IOException {
        Workbook workbook = null;
        if (null != fileStream) {
            if ("xls".equals(type.trim().toLowerCase())) {
                // 创建 Excel 2003 工作簿对象
                workbook = new HSSFWorkbook(fileStream);
                // 创建 Excel 2007 工作簿对象
            } else if ("xlsx".equals(type.trim().toLowerCase())) {
                workbook = new XSSFWorkbook(fileStream);
            }
        }
        return workbook;
    }

    private static List<Sheet> getSheet(Workbook workbook) {
        List<Sheet> sheets = new ArrayList<Sheet>();
        int sheetNum = workbook.getNumberOfSheets();
        for (int index = 0; index < sheetNum; index++) {
            sheets.add(workbook.getSheetAt(index));
        }
        return sheets;
    }

    private static <T> List<T> parseSheetToList(T modal, Sheet sheet, int startRowNum,
                                                StringBuffer errorMsg, Map<String, DataFormat> formatMap)
        throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<T> list = new ArrayList<>();
        String sheetName = sheet.getSheetName();
        // 获取最后一行的行数，非索引值
        int rowNum = sheet.getLastRowNum() + 1;
        if (rowNum <= startRowNum + 1) {
            logger.error("ship sheet: " + sheetName + " ,don't meet the requirements");
            errorMsg.append("工作簿【" + sheetName + "】为空\r\n");
            return list;
        }
        // 获取表头所对应列
        Map<Integer, DataFormat> headMap = getSheetHeadData(sheet, startRowNum, formatMap);
        if (headMap.size() < formatMap.size()) {
            logger.error("ship sheet: " + sheetName + " ,head is error");
            errorMsg.append("工作簿【" + sheetName + "】列名不对应\r\n");
            return list;
        }
        int requireNum = 0;
        if (!CollectionUtils.isEmpty(formatMap)) {
            for (DataFormat df : formatMap.values()) {
                if (df.isRequired()) {
                    requireNum++;
                }
            }
        }
        // 校验解析excel
        Row row = null;
        Cell cell = null;
        Object value = null;
        int cellNum = 0;
        StringBuffer rowMsg = null;
        for (int i = startRowNum + 1; i < rowNum; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                rowMsg = new StringBuffer();
                // 列索引加1
                cellNum = row.getLastCellNum();
                if(cellNum ==0 || cellNum <  requireNum){
                    //continue;
                }
                T rowData = (T) modal.getClass().newInstance(); ;
                DataFormat dataFormat = null;
                for (int index = 0; index < cellNum; index++) {
                     dataFormat = headMap.get(index);
                    cell = row.getCell(index);
                    if(null==cell){
                        value="";
                    }else {
                        value = getCellStringValue(cell);
                    }
                    /*System.out.println("********CellType:"+cell.getCellType());
                    System.out.println("**********currentValue:"+value);*/
                    // todo String[] prores = parseProperty(i + 1, value.toString(), dataFormat);
                    String[] prores = parseProperty(i + 1, String.valueOf(value), dataFormat);
                    if (prores.length == 2) {
                        rowMsg.append(prores[1]);
                        break;
                    } else {
                        PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(rowData.getClass(), dataFormat.getProperty());
                        if (targetPd != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            // todo value = typeCast(writeMethod.getParameterTypes(),value.toString());
                            value = typeCast(writeMethod.getParameterTypes(),String.valueOf(value));
                            writeMethod.invoke(rowData, value);
                        }
                    }
                }
                if (StringUtils.isEmpty(rowMsg)) {
                    list.add(rowData);
                } else {
                    errorMsg.append(rowMsg);
                }
            }
        }
        return list;
    }

    /**
     * @Author: weilf
     * @Date 2018/8/21 15:01
     * @Description 根据DTO属性类型对cell值进行类型转换
     * @Param parameterTypes
     * @Param value
    */
    private static Object typeCast(Class<?>[] parameterTypes,String value){
        if(parameterTypes[0].equals(Integer.class)){
            return Integer.parseInt(value);
        }else if(parameterTypes[0].equals(BigDecimal.class)){
            return new BigDecimal(value);
        }else if(parameterTypes[0].equals(Timestamp.class)){
            return Timestamp.valueOf(LocalDateTime.parse(value,dateTimeFormatter));
        }else if(parameterTypes[0].equals(Double.class)){
            return Double.parseDouble(value);
        } else{
            return value;
        }
    }

    private static String getCellStringValue(Cell cell) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cellValue = null;
        if (null != cell){
            if (0 == cell.getCellType() && HSSFDateUtil.isCellDateFormatted(cell)){
                cellValue = sdf.format(cell.getDateCellValue());
                return cellValue;
            }else {
                // 强制设为文本
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cellValue = cell.getStringCellValue();
            }
        }

       /* if(cell != null) {
            switch(cell.getCellType()) {
    			case Cell.CELL_TYPE_BOOLEAN:
    				cellValue = cell.getDateCellValue();
    				break;
    			case Cell.CELL_TYPE_FORMULA:
    				cellValue = cell.getCellFormula();
    				break;
    			case Cell.CELL_TYPE_NUMERIC:
    				cellValue = cell.getNumericCellValue();
    				break;
    			case Cell.CELL_TYPE_STRING:
    				cellValue = cell.getStringCellValue();
    				break;
    			default:
    				cellValue = "";
    		}
    	}*/
        return cellValue;
    }

    private static Map<Integer, DataFormat> getSheetHeadData(Sheet sheet, int startRowNum, Map<String, DataFormat> formatMap) {
        Map<Integer, DataFormat> headMap = new HashMap<Integer, DataFormat>();
        Row fristRow = sheet.getRow(startRowNum);
        // 获取最后一列index，再加上一
        int columnNum = fristRow.getLastCellNum();
        Cell cell = null;
        String value = null;
        DataFormat format = null;
        for (int i = 0; i < columnNum; i++) {
            cell = fristRow.getCell(i);
            if (cell != null) {
                value = cell.getStringCellValue() == null ? null : cell.getStringCellValue().trim();
                format = formatMap.get(value);
                if (format != null) {
                    format.setRowIndex(i + 1);
                    headMap.put(i, format);
                }
            }
        }
        return headMap;
    }

    private static String[] parseProperty(int rowNum, String propertyValue, DataFormat dataFormat) {
        String[] result = {propertyValue};
        if (dataFormat.isRequired()) {
            if (StringUtils.isEmpty(propertyValue)) {
                result = new String[]{propertyValue, "第" + rowNum + "行，第" + dataFormat.getRowIndex() + "列数据：" + dataFormat.getColumn() + "不能为空\r\n"};
            }
        }
        if (StringUtils.isNotEmpty(propertyValue)
            && StringUtils.isNotEmpty(dataFormat.getFormat())
            && !Pattern.matches(dataFormat.getFormat(), propertyValue)) {
            result = new String[]{propertyValue, "第" + rowNum + "行，第" + dataFormat.getRowIndex() + "列数据：" + dataFormat.getMessage() + "\r\n"};
        }
        return result;
    }

    /**
     * 内部类
     */
    public static class DataFormat {

        private int rowIndex;

        private String property;

        private String column;

        private boolean isRequired;

        private String format;

        private String message;

        public DataFormat() {
        }

        public DataFormat(String property, String column, boolean isRequired, String format, String message) {
            this.property = property;
            this.column = column;
            this.isRequired = isRequired;
            this.format = format;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public boolean isRequired() {
            return isRequired;
        }

        public void setRequired(boolean required) {
            isRequired = required;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
        }

        @Override
        public String toString() {
            return "DataFormat [rowIndex=" + rowIndex + ", property=" + property + ", column=" + column + ", isRequired="
                + isRequired + ", format=" + format + ", message=" + message + "]";
        }


    }


    public static class ResolveExcelDTO{

        private ResolveStatus status;

        private StringBuffer message;

        private Map<Class,List> resultMap;

        public ResolveStatus getStatus() {
            return status;
        }

        public void setStatus(ResolveStatus status) {
            this.status = status;
        }

        public StringBuffer getMessage() {
            return message;
        }

        public void setMessage(StringBuffer message) {
            this.message = message;
        }

        public Map<Class, List> getResultMap() {
            return resultMap;
        }

        public void setResultMap(Map<Class, List> resultMap) {
            this.resultMap = resultMap;
        }

        @Override
        public String toString() {
            return "ResolveExcelDTO [status=" + status + ", message=" + message + ", resultMap=" + resultMap + "]";
        }
    }

    public enum ResolveStatus {
        ERROR, SUCCESS
    }

   /* private static Map formatMap = null;

    static{
        // 真实姓名	用户名	角色编码	组织机构编码	是否启用	邮箱	手机号码	摘要信息
        formatMap = new HashMap();
        formatMap.put("真实姓名",new DataFormat("realname","真实姓名",true,"^.{1,50}$","真实姓名长度不能超过50"));
        formatMap.put("用户名",new DataFormat("login","用户名",true,"^[_'.@A-Za-z0-9-]{1,50}$","用户名必须由字母、数字、下划线、点或者@组成,长度为1~50的字符串"));
        formatMap.put("角色编码",new DataFormat("roles","角色编码",true,"^.{1,36}$","角色编码长度不能超过36"));
        formatMap.put("组织机构编码",new DataFormat("organizationid","组织机构编码",true,"^.{1,36}$","组织机构编码长度不能超过36"));
        formatMap.put("是否启用",new DataFormat("enable","是否启用",false,null,null));
        formatMap.put("邮箱",new DataFormat("email","邮箱",false,"^.{1,100}$","邮箱长度不能超过36"));
        formatMap.put("手机号码",new DataFormat("phone","手机号码",false,"^.{1,20}$","手机号码不能超过20"));
        formatMap.put("摘要信息",new DataFormat("bewrite","摘要信息",false,"^.{1,200}$","摘要信息不能超过200"));
    }

    public static void main(String[] args) throws FileNotFoundException {
    	FileInputStream fis = new FileInputStream(new File("C:\\Users\\master\\Desktop\\用户导入模板.xlsx"));
    	System.out.println(ExcelUtil.readExcel(new ResolveExcelRowDTO(),fis,"xlsx",0,formatMap));
    	//System.out.println(Pattern.matches("asdasd","^[a-zA-Z0-9''-'\\s]{1,30}$"));
	}*/


}
