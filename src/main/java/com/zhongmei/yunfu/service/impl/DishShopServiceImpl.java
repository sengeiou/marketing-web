package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.domain.entity.PushPlanActivityEntity;
import com.zhongmei.yunfu.domain.mapper.DishShopMapper;
import com.zhongmei.yunfu.service.DishShopService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 门店菜品 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
@Service
public class DishShopServiceImpl extends ServiceImpl<DishShopMapper, DishShopEntity> implements DishShopService {

    @Override
    public Page<DishShopEntity> queryDishShopList(CardTimeModel mCardTimeModel) throws Exception{

        DishShopEntity mDishShopEntity = new DishShopEntity();
        mDishShopEntity.setBrandIdenty(mCardTimeModel.getBrandIdenty());
        mDishShopEntity.setShopIdenty(mCardTimeModel.getShopIdenty());
        mDishShopEntity.setStatusFlag(1);

        Page<DishShopEntity> listPage = new Page<>(mCardTimeModel.getPageNo(), mCardTimeModel.getPageSize());
        EntityWrapper<DishShopEntity> eWrapper = new EntityWrapper<>(mDishShopEntity);
        eWrapper.setSqlSelect("id,name,market_price,dish_increase_unit,valid_time,unvalid_time");
        eWrapper.in("type","3,4");
        eWrapper.orderBy("server_create_time", false);
        Page<DishShopEntity> listData = selectPage(listPage, eWrapper);

        return listData;

    }

    @Override
    public DishShopEntity queryDishShopById(CardTimeModel mCardTimeModel) throws Exception {
        EntityWrapper<DishShopEntity> eWrapper = new EntityWrapper<>(new DishShopEntity());
        eWrapper.eq("brand_identy",mCardTimeModel.getBrandIdenty());
        eWrapper.eq("shop_identy",mCardTimeModel.getShopIdenty());
        eWrapper.eq("id",mCardTimeModel.getId());
        DishShopEntity mDishShopEntity = selectOne(eWrapper);
        return mDishShopEntity;
    }

    @Override
    public DishShopEntity addDishShop(CardTimeModel mCardTimeModel) throws Exception {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorName = LoginManager.get().getUser().getCreatorName();

        DishShopEntity mDishShopEntity = new DishShopEntity();
        mDishShopEntity.setUuid(ToolsUtil.genOnlyIdentifier());
        mDishShopEntity.setName(mCardTimeModel.getName());
        mDishShopEntity.setType(mCardTimeModel.getType());
        mDishShopEntity.setMarketPrice(mCardTimeModel.getMarketPrice());

        if(mCardTimeModel.getDishIncreaseUnit() == null || mCardTimeModel.getDishIncreaseUnit().equals("")){//如果起卖份数为空 则表示不限次数使用，我们使用默认值1000
            mDishShopEntity.setDishIncreaseUnit(new BigDecimal(1000));
            mDishShopEntity.setStepNum(new BigDecimal(1000));
        }else{
            mDishShopEntity.setDishIncreaseUnit(mCardTimeModel.getDishIncreaseUnit());
            mDishShopEntity.setStepNum(mCardTimeModel.getDishIncreaseUnit());
        }

        mDishShopEntity.setMaxNum(mCardTimeModel.getMaxNum());
        mDishShopEntity.setMinNum(mCardTimeModel.getMinNum());
        mDishShopEntity.setShopIdenty(shopIdentity);
        mDishShopEntity.setBrandIdenty(brandIdentity);
        mDishShopEntity.setCreatorId(creatorId);
        mDishShopEntity.setCreatorName(creatorName);
//        mDishShopEntity.setSkuKey();
        mDishShopEntity.setDishQty(mCardTimeModel.getDishQty());
        mDishShopEntity.setStatusFlag(1);

        mDishShopEntity.setBrandDishId(1l);
        mDishShopEntity.setBrandDishUuid(ToolsUtil.genOnlyIdentifier());
        mDishShopEntity.setSort(1);
        mDishShopEntity.setSingle(1);
        mDishShopEntity.setDiscountAll(1);
        mDishShopEntity.setSource(1);
        mDishShopEntity.setOrder(1);
        mDishShopEntity.setSendOutside(1);
        mDishShopEntity.setDefProperty(1);
        mDishShopEntity.setClearStatus(1);
        mDishShopEntity.setSaleTotal(mCardTimeModel.getDishQty());
        mDishShopEntity.setResidueTotal(mCardTimeModel.getDishQty());
        mDishShopEntity.setSaleTotalWechat(mCardTimeModel.getDishQty());
        mDishShopEntity.setResidueTotalWechat(mCardTimeModel.getDishQty());
        mDishShopEntity.setValidTime(new Date());
        mDishShopEntity.setUnvalidTime(new Date());
        mDishShopEntity.setScene("1");
        mDishShopEntity.setHasStandard(2);
        mDishShopEntity.setBoxQty(0);

        EntityWrapper<DishShopEntity> eWrapper = new EntityWrapper<>(mDishShopEntity);

        insert(mDishShopEntity);

        return mDishShopEntity;
    }
}
