package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.domain.entity.DishSetmealEntity;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.DishSetmealGroupEntity;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface DishSetmealService extends IService<DishSetmealEntity> {

    /**
     * 插入子品项管理关系
     * @param listDishId
     * @param mDishShopEntity
     * @param mDishSetmealGroupEntity
     * @return
     */
    Boolean addSetmeal(List<Long> listDishId, DishShopEntity mDishShopEntity, DishSetmealGroupEntity mDishSetmealGroupEntity);
}
