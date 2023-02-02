package com.drugms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.drugms.common.MSGlobalObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DailySales implements Serializable{

    public DailySales() {
        this.day = LocalDateTime.now();
        this.salesAmount = BigDecimal.valueOf(0);
        this.salesNum = 0;
        this.orderNum = 0;
        this.retNum = 0;
        this.income = BigDecimal.valueOf(0);
    }

    private static final long serialVersionUID = 11L;
    /**
     * ID
     */
    @TableId(value = "saleid", type = IdType.AUTO)
    private Integer saleid;

    /**
     * 日期
     */
    private LocalDateTime day;

    /**
     * 销售金额
     */
    private BigDecimal salesAmount;

    /**
     * 销售数
     */
    private Integer salesNum;

    /**
     * 订单数
     */
    private Integer orderNum;

    /**
     * 退货数
     */
    private Integer retNum;

    /**
     * 收入
     */
    private BigDecimal income;

    /**
     * 增加销售额
     */
    public void addSalesAmount(int salesNum,BigDecimal price){
        //增加的金额
        BigDecimal value = price.multiply(BigDecimal.valueOf(salesNum)).setScale(2, RoundingMode.HALF_UP);
        //更新销售额(四舍五入保留2位小数)
        setSalesAmount(value.add(getSalesAmount()).setScale(2, RoundingMode.HALF_UP));
    }
    /**
     * 减少销售额
     */
    public void subSalesAmount(int salesNum,BigDecimal price){
        //减少的金额
        BigDecimal value = price.multiply(BigDecimal.valueOf(salesNum)).setScale(2, RoundingMode.HALF_UP);
        //更新销售额(四舍五入保留2位小数)
        setSalesAmount(getSalesAmount().subtract(value).setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * 增加收入
     */
    public void addIncome(int salesNum,BigDecimal profit){
        //增加的金额
        BigDecimal value = profit.multiply(BigDecimal.valueOf(salesNum)).setScale(2, RoundingMode.HALF_UP);
        //更新收入(四舍五入保留2位小数)
        setIncome(value.add(getIncome()).setScale(2, RoundingMode.HALF_UP));
    }
    /**
     * 减少收入
     */
    public void subIncome(int salesNum,BigDecimal profit){
        //增加的金额
        BigDecimal value = profit.multiply(BigDecimal.valueOf(salesNum)).setScale(2, RoundingMode.HALF_UP);
        //更新收入(四舍五入保留2位小数)
        setIncome(getIncome().subtract(value).setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * 订单数加1
     */
    public void incOrder(){
        orderNum++;
    }
    /**
     * 订单数减1
     */
    public void decOrder(){
        orderNum--;
    }
    /**
     * 增加销售数
     */
    public void addSalesNum(int num){
        salesNum+=num;
    }

    /**
     * 减少销售数
     */
    public void subSalesNum(int num){
        salesNum-=num;
    }

    /**
     * 增加退货数
     */
    public void addRetNum(int num){
        retNum+=num;
    }

    /**
     * 购买商品
     */
    public void purchaseItem(int num,BigDecimal price,BigDecimal profit){
        //增加销售额
        MSGlobalObject.dailySales.addSalesAmount(num, price);
        //增加收入
        MSGlobalObject.dailySales.addIncome(num,profit);
        //增加销售量
        MSGlobalObject.dailySales.addSalesNum(num);
        //增加订单量
        MSGlobalObject.dailySales.incOrder();
    }
    /**
     * 修改购买订单
     */
    public void updPurchaseItem(int num,BigDecimal price,BigDecimal profit){
        //增加销售额
        MSGlobalObject.dailySales.addSalesAmount(num, price);
        //增加收入
        MSGlobalObject.dailySales.addIncome(num,profit);
        //增加销售量
        MSGlobalObject.dailySales.addSalesNum(num);
    }
    /**
     * 商品退货
     */
    public void retItem(int num,BigDecimal price,BigDecimal profit){
        //减少销售额
        MSGlobalObject.dailySales.subSalesAmount(num, price);
        //减少收入
        MSGlobalObject.dailySales.subIncome(num,profit);
        //减少销售量
        MSGlobalObject.dailySales.subSalesNum(num);
        //增加退货量
        MSGlobalObject.dailySales.addRetNum(num);
    }
}
