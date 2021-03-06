package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.controller.model.ReportSalesExportModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.TradeItemService;
import com.zhongmei.yunfu.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/internal/dish")
public class ReportDishController {

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;

    @RequestMapping("/dishReport")
    public String reportAddCustomer(Model model, TradeModel mTradeModel) {

        try {
            //获取门店品牌和门店编号
//            queryShopMessage(model, mTradeModel);

            Date start = null;
            Date end = null;
            //设置默认查询时间
            if (mTradeModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                start = DateFormatUtil.parseDate(temp, DateFormatUtil.FORMAT_FULL_DATE);

                mTradeModel.setStartDate(temp);
            } else {
                start = DateFormatUtil.parseDate(mTradeModel.getStartDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }
            if (mTradeModel.getEndDate() == null) {
                end = new Date();
                mTradeModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));
            } else {
                end = DateFormatUtil.parseDate(mTradeModel.getEndDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }

            List<DishReport> listData = mTradeItemService.selectDishSalesReport(mTradeModel.getBrandIdenty(), mTradeModel.getShopIdenty(), start, end);

            List<String> listDishName = new LinkedList<>();
            List<BigDecimal> listSalesAmount = new LinkedList<>();
            List<Long> listSalesCount = new LinkedList<>();

            Long maxCount = 0l;
            Long maxAmount = 0l;

            BigDecimal totalCount = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;

            for (DishReport dp : listData) {

                listDishName.add(dp.getDishName());
                listSalesAmount.add(dp.getSalesAmount());
                listSalesCount.add(dp.getSalseCount().longValue());

                if (maxCount < dp.getSalseCount().longValue()) {
                    maxCount = dp.getSalseCount().longValue();
                }
                if (maxAmount < dp.getSalesAmount().longValue()) {
                    maxAmount = dp.getSalesAmount().longValue();
                }

                totalCount = totalCount.add(new BigDecimal(dp.getSalseCount()));
                totalAmount = totalAmount.add(dp.getSalesAmount());
            }

            maxCount = ToolsUtil.getMaxData(maxCount);
            maxAmount = ToolsUtil.getMaxData(maxAmount);

            model.addAttribute("maxCount", maxCount);
            model.addAttribute("intervalCount", maxCount / 10);
            model.addAttribute("maxAmount", maxAmount);
            model.addAttribute("intervalAmount", maxAmount / 10);

            model.addAttribute("mTradeModel", mTradeModel);
            model.addAttribute("listDishName", listDishName);
            model.addAttribute("listSalesAmount", listSalesAmount);
            model.addAttribute("listSalesCount", listSalesCount);

            model.addAttribute("listData", listData);

            model.addAttribute("totalCount", totalCount);
            model.addAttribute("totalAmount", totalAmount);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

        return "report_dish_sales";
    }

    public Model queryShopMessage(Model model, TradeModel mTradeModel) throws Exception {

        BrandEntity brand = mBrandService.queryBrandByAppId(mTradeModel.getBrandIdenty());

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());

        model.addAttribute("brand", brand);
        model.addAttribute("listCommercial", listCommercial);
        return model;
    }

    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, TradeModel mTradeModel) throws Exception{

        mTradeModel.setBusinessType(1);
        List<DishReport> listData = mTradeItemService.dishSalesExportExcel(mTradeModel);

        ExcelData data = new ExcelData();
        data.setSheetName("品项销售排行报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("品项名称");
        titles.add("品项单价");
        titles.add("销售数量");
        titles.add("销售金额");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listData != null){
            for (DishReport entity : listData) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getDishName());
                row.add(entity.getPrice());
                row.add(entity.getSalseCount());
                row.add(entity.getSalesAmount());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("品项销售排行报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }
}
