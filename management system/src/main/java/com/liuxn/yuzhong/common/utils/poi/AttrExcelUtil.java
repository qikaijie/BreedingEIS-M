package com.liuxn.yuzhong.common.utils.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liuxn.yuzhong.common.exception.CustomException;
import com.liuxn.yuzhong.common.utils.StringUtils;
import com.liuxn.yuzhong.framework.config.YuZhongConfig;
import com.liuxn.yuzhong.framework.web.domain.AjaxResult;
import com.liuxn.yuzhong.project.os.domain.Attribute;
import com.liuxn.yuzhong.project.os.domain.AttributeGroupVo;
import com.liuxn.yuzhong.project.os.domain.PlantAttributeList;

/**
 * Excel相关处理
 * 
 * @author ruoyi
 */
public class AttrExcelUtil
{
    private static final Logger log = LoggerFactory.getLogger(AttrExcelUtil.class);

    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    /**
     * 工作薄对象
     */
    private XSSFWorkbook wb;

    /**
     * 工作表对象
     */
    private XSSFSheet sheet;

    /**
     * 实体对象
     */
    public List<AttributeGroupVo> attributeGroupVoList;

    public AttrExcelUtil(List<AttributeGroupVo> attributeGroupVoList)
    {
        this.attributeGroupVoList = attributeGroupVoList;
    }
    
    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public AjaxResult importTemplateExcel(String sheetName)
    {
    	OutputStream out = null;
        try
        {
	    	this.wb = new XSSFWorkbook();
	    	this.sheet = wb.createSheet();
	    	wb.setSheetName(0, sheetName);
	    	
	    	// 产生一行
	        XSSFRow oneRow = sheet.createRow(0);
	        XSSFRow twoRow = sheet.createRow(1);
	        
	        //设置第一行第一列为植物标记
	        XSSFCell cell = oneRow.createCell(0);
	        cell.setCellValue("短号");
	        cell.setCellStyle(createStyles(wb));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
	        
	        if(attributeGroupVoList != null && attributeGroupVoList.size() != 0)
	        {
	        	int index = 0;
	        	for(int i = 0; i < this.attributeGroupVoList.size(); i++)
	            {
	            	AttributeGroupVo attributeGroupVo = this.attributeGroupVoList.get(i);
	            	
	            	List<Attribute> AttributeList = attributeGroupVo.getAttributeList();
	            	if(AttributeList != null && AttributeList.size() != 0)
	            	{
	            		// 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
	                    // 行和列都是从0开始计数，且起始结束都会合并
	                    // 这里是合并excel中日期的两行为一行
	                    CellRangeAddress region = new CellRangeAddress(0, 0, index + 1, index + AttributeList.size());
	                    sheet.addMergedRegion(region);
	                    
	                    XSSFCell oneCell = oneRow.createCell(index + 1);
	            		oneCell.setCellValue(attributeGroupVo.getName());
	            		oneCell.setCellStyle(createStyles(wb));
	                    
	                    for(int j = 0; j < AttributeList.size(); j++)
	                    {
	                    	XSSFCell twoCell = twoRow.createCell(j+1 + index);
	                    	twoCell.setCellValue(AttributeList.get(j).getFieldName());
	                    	twoCell.setCellStyle(createStyles(wb));
	                    }
	                    
	                    index += AttributeList.size();
	            	}
	            }
	        }
        
	        String filename = encodingFilename(sheetName);
	        out = new FileOutputStream(getAbsoluteFile(filename));
	        wb.write(out);
	        return AjaxResult.success(filename);
	    }
	    catch (Exception e)
	    {
	        log.error("导出Excel异常{}", e.getMessage());
	        throw new CustomException("导出Excel失败，请联系网站管理员！");
	    }
	    finally
	    {
	        if (wb != null)
	        {
	            try
	            {
	                wb.close();
	            }
	            catch (IOException e1)
	            {
	                e1.printStackTrace();
	            }
	        }
	        if (out != null)
	        {
	            try
	            {
	                out.close();
	            }
	            catch (IOException e1)
	            {
	                e1.printStackTrace();
	            }
	        }
	    }
    }

    /**
     * 对excel表单指定表格索引名转换成list
     * 
     * @param sheetName 表格索引名
     * @param is 输入流
     * @return 转换后集合
     */
    public Map<Integer, PlantAttributeList> importExcel(InputStream is) throws Exception
    {
        this.wb = new XSSFWorkbook(is);
        
        
        XSSFSheet sheet = wb.getSheetAt(0);
        if (sheet == null)
        {
            throw new IOException("文件sheet不存在");
        }

        //总行数
        int rows = sheet.getPhysicalNumberOfRows();
        if (rows == 0)
        {
        	return null;
        }
        // 获取表头
        XSSFRow twoRow = sheet.getRow(1);
        //总列数
        int cells = twoRow.getLastCellNum();
        if (cells == 0)
        {
        	return null;
        }

        List<Attribute> attrList = new ArrayList<Attribute>();
        if(attributeGroupVoList != null && attributeGroupVoList.size() != 0)
        {
        	for(int i = 0; i < this.attributeGroupVoList.size(); i++)
            {
        		AttributeGroupVo attributeGroupVo = this.attributeGroupVoList.get(i);
        		attrList.addAll(attributeGroupVo.getAttributeList());
            }
        }
        
        if(attrList.size() == 0)
        {
        	return null;
        }
        
        Map<Integer, Attribute> attrMap = new HashMap<>();
    	for(int j = 1; j < cells; j++)
    	{
    		XSSFCell cell = twoRow.getCell(j);
    		
    		for(Attribute attribute : attrList)
        	{
        		if(attribute.getFieldName().equals(getCellValue(cell)))
        		{
        			attrMap.put(j, attribute);
        			
        			break;
        		}
        	}
    	}
    	
    	if(attrMap.isEmpty())
    	{
    		return null;
    	}
    	
    	Map<Integer, PlantAttributeList> recodeMap = new HashMap<>();
    	for (int i = 2; i < rows; i++)
        {
            // 从第2行开始取数据,默认第一行是表头.
        	XSSFRow row = sheet.getRow(i);
        	
        	PlantAttributeList obj = new PlantAttributeList();
        	obj.setCodeTwo(getCellValue(row.getCell(0)));
        	
        	List<Attribute> list = new ArrayList<>();
        	for(int j = 1; j < cells; j++)
        	{
        		XSSFCell cell = row.getCell(j);
        		
        		Attribute attribute = attrMap.get(j);
        		attribute.setValue(getCellValue(cell));
        		
        		list.add(attribute);
        	}
        	obj.setAttributeList(list);
        	
        	recodeMap.put(i, obj);
        }
    	
    	return recodeMap;
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename)
    {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }

    /**
     * 获取下载路径
     * 
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename)
    {
        String downloadPath = YuZhongConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }
    
    /**
     * 创建表格样式
     * 
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private CellStyle createStyles(XSSFWorkbook wb)
    {
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);

        return style;
    }

    /**
	 *	 获取普通单元格的值
	 * @param cell
	 * @return
	 */
    private String getCellValue(Cell cell)
	{
		if(cell == null)
		{
			return "";
		}
		
		Object val = "";
		
		try
        {
			if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA)
            {
                val = cell.getNumericCellValue();
                if (HSSFDateUtil.isCellDateFormatted(cell))
                {
                    val = DateUtil.getJavaDate((Double) val); // POI Excel 日期格式转换
                }
                else
                {
                    if ((Double) val % 1 > 0)
                    {
                        val = new DecimalFormat("0.00").format(val);
                    }
                    else
                    {
                        val = new DecimalFormat("0").format(val);
                    }
                }
            }
            else if (cell.getCellTypeEnum() == CellType.STRING)
            {
                val = cell.getStringCellValue();
            }
            else if (cell.getCellTypeEnum() == CellType.BOOLEAN)
            {
                val = cell.getBooleanCellValue();
            }
            else if (cell.getCellTypeEnum() == CellType.ERROR)
            {
                val = cell.getErrorCellValue();
            }
        }
        catch (Exception e)
        {
            return val.toString();
        }
		
        return val.toString();
	}
}