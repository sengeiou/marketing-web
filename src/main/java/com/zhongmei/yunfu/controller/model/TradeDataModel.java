package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.*;

import java.util.List;
import java.util.Map;

public class TradeDataModel {

    private TradeEntity trade;

    List<Map<String,Object>> listTradeItem;

    private List<PaymentItemEntity> mPaymentItemEntity;

    public TradeEntity getTrade() {
        return trade;
    }

    public void setTrade(TradeEntity trade) {
        this.trade = trade;
    }

    public List<Map<String, Object>> getListTradeItem() {
        return listTradeItem;
    }

    public void setListTradeItem(List<Map<String, Object>> listTradeItem) {
        this.listTradeItem = listTradeItem;
    }

    public List<PaymentItemEntity> getmPaymentItemEntity() {
        return mPaymentItemEntity;
    }

    public void setmPaymentItemEntity(List<PaymentItemEntity> mPaymentItemEntity) {
        this.mPaymentItemEntity = mPaymentItemEntity;
    }
}
