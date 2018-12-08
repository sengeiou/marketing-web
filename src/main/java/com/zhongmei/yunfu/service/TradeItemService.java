package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.domain.entity.TradeItemEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 交易明细 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-17
 */
public interface TradeItemService extends IService<TradeItemEntity> {

    /**
     * 添加tradeItem
     *
     * @param mTradeItem
     * @return
     * @throws Exception
     */
    Boolean addTradeItem(TradeItemEntity mTradeItem) throws Exception;

    /**
     * 根据订单查询tradeItem
     *
     * @param tradeId
     * @return
     */
    List<TradeItemEntity> querTradeItemByTradeId(Long tradeId);

    /**
     * 根据tradeId查询item所有信息
     * @param tradeId
     * @return
     */
    List<TradeItemEntity> queryTradeItemAllByTradeId(Long tradeId);

    /**
     * 查收商品销售排行榜
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param start
     * @param end
     * @return
     */
    List<DishReport> selectDishSalesReport(Long brandIdenty, Long shopIdenty, Date start, Date end) throws Exception;

}
