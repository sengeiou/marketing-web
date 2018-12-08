package com.zhongmei.yunfu.api.pos.vo;

import com.zhongmei.yunfu.api.PosReq;

import java.math.BigDecimal;

public class CustomerStoredBalanceResp extends PosReq {

    private int type; //记录类型1储值、2消费、3退款
    private int source;// 来源，1为Calm，2为手机app，3为其他系统导入，4为微信，5支付宝，6商家官网，7百度,8
    private Long userId;// 操作员账号
    private String userName;
    private Long createDateTime;// 创建时间
    private int paymentMode; //支付方式
    private String paymentModeName; //支付方式名称
    private BigDecimal addValuecard;// 本次操作新增的储值
    private BigDecimal sendValuecard;// 本次冲值送的钱
    private BigDecimal endValuecard;// 操作后会员的储值

    private String beforeValuecard;// 操作前会员的储值
    private String cashValuecard;// 现金冲入的钱
    private String bankValuecard;// 银行卡冲入的钱

    private BigDecimal beforeRealValue; // 操作前余额实储金额
    private BigDecimal beforeSendValue;    // 操作前余额赠送金额
    private BigDecimal currentSendValue; // 本次操作赠送金额
    private BigDecimal currentRealValue; // 本次操作实储金额
    private BigDecimal endRealValue; // 操作后实储金额
    private BigDecimal endSendValue; // 操作后赠送金额

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Long createDateTime) {
        this.createDateTime = createDateTime;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBeforeValuecard() {
        return beforeValuecard;
    }

    public void setBeforeValuecard(String beforeValuecard) {
        this.beforeValuecard = beforeValuecard;
    }

    public String getCashValuecard() {
        return cashValuecard;
    }

    public void setCashValuecard(String cashValuecard) {
        this.cashValuecard = cashValuecard;
    }

    public String getBankValuecard() {
        return bankValuecard;
    }

    public void setBankValuecard(String bankValuecard) {
        this.bankValuecard = bankValuecard;
    }

    public BigDecimal getAddValuecard() {
        return addValuecard;
    }

    public void setAddValuecard(BigDecimal addValuecard) {
        this.addValuecard = addValuecard;
    }

    public BigDecimal getSendValuecard() {
        return sendValuecard;
    }

    public void setSendValuecard(BigDecimal sendValuecard) {
        this.sendValuecard = sendValuecard;
    }

    public BigDecimal getEndValuecard() {
        return endValuecard;
    }

    public void setEndValuecard(BigDecimal endValuecard) {
        this.endValuecard = endValuecard;
    }

    public BigDecimal getBeforeRealValue() {
        return beforeRealValue;
    }

    public void setBeforeRealValue(BigDecimal beforeRealValue) {
        this.beforeRealValue = beforeRealValue;
    }

    public BigDecimal getBeforeSendValue() {
        return beforeSendValue;
    }

    public void setBeforeSendValue(BigDecimal beforeSendValue) {
        this.beforeSendValue = beforeSendValue;
    }

    public BigDecimal getCurrentSendValue() {
        return currentSendValue;
    }

    public void setCurrentSendValue(BigDecimal currentSendValue) {
        this.currentSendValue = currentSendValue;
    }

    public BigDecimal getCurrentRealValue() {
        return currentRealValue;
    }

    public void setCurrentRealValue(BigDecimal currentRealValue) {
        this.currentRealValue = currentRealValue;
    }

    public BigDecimal getEndRealValue() {
        return endRealValue;
    }

    public void setEndRealValue(BigDecimal endRealValue) {
        this.endRealValue = endRealValue;
    }

    public BigDecimal getEndSendValue() {
        return endSendValue;
    }

    public void setEndSendValue(BigDecimal endSendValue) {
        this.endSendValue = endSendValue;
    }

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentModeName() {
        return paymentModeName;
    }

    public void setPaymentModeName(String paymentModeName) {
        this.paymentModeName = paymentModeName;
    }
}
