package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CustomerEditModel;
import com.zhongmei.yunfu.controller.model.CustomerSearchModel;
import com.zhongmei.yunfu.core.security.Password;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerSearchRuleEntity;
import com.zhongmei.yunfu.service.AuthUserService;
import com.zhongmei.yunfu.service.CustomerSearchRuleService;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.LoginManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-29
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    AuthUserService authUserService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerSearchRuleService customerSearchRuleService;

    @RequestMapping
    public String modle() {
        return "custmermodle";
    }

    @RequestMapping("/list")
    public String list(Model model, CustomerSearchModel searchModel) {
        LoginManager.setUser(searchModel);
        Page<CustomerEntity> listPage = null;
        CustomerSearchRuleEntity searchRuleEntity = getCustomerSearchRuleEntity(searchModel);
        if (searchModel.getType() != null) {
            switch (searchModel.getType()) {
                case CustomerSearchModel.consumptionMainCount:
                    listPage = customerService.selectByTrade(searchModel,
                            searchRuleEntity.getConsumptionMainDay(),
                            searchRuleEntity.getConsumptionMainAmount(),
                            searchRuleEntity.getConsumptionMainNumber());
                    break;
                case CustomerSearchModel.membersWillCount:
                    listPage = customerService.selectByTrade(searchModel,
                            searchRuleEntity.getMembersWillDay(),
                            searchRuleEntity.getMembersWillAmount(),
                            searchRuleEntity.getMembersWillNumber());
                    break;
                case CustomerSearchModel.membersLossCount:
                    listPage = customerService.selectByTrade(searchModel,
                            searchRuleEntity.getMembersLossDay(),
                            searchRuleEntity.getMembersLossAmount(),
                            searchRuleEntity.getMembersLossNumber());
                    break;
                case CustomerSearchModel.membersNewIntervalCount:
                    listPage = customerService.selectByNewMember(searchModel, searchRuleEntity.getMembersNewIntervalDay());
                    break;
                case CustomerSearchModel.membersBirthdayCount:
                    listPage = customerService.selectByBirthday(searchModel, searchRuleEntity.getMembersBirthdayBeforeDay());
                    break;
                case CustomerSearchModel.membersAnniversaryCount:
                    listPage = customerService.selectByAnniversary(searchModel, searchRuleEntity.getMembersAnniversaryBeforeDay());
                    break;
            }
        }

        if (listPage == null) {
            listPage = customerService.findListPage(searchModel, searchModel.getPageSize());
        }

        setWebPage(model, "/customer/list", listPage, searchModel);
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("list", listPage.getRecords());

        Integer consumptionMainCount = customerService.selectCountByTrade(searchRuleEntity.getShopIdenty(),
                searchRuleEntity.getConsumptionMainDay(),
                searchRuleEntity.getConsumptionMainAmount(),
                searchRuleEntity.getConsumptionMainNumber());

        Integer membersWillCount = customerService.selectCountByTrade(searchRuleEntity.getShopIdenty(),
                searchRuleEntity.getMembersWillDay(),
                searchRuleEntity.getMembersWillAmount(),
                searchRuleEntity.getMembersWillNumber());

        Integer membersLossCount = customerService.selectCountByTrade(searchRuleEntity.getShopIdenty(),
                searchRuleEntity.getMembersLossDay(),
                searchRuleEntity.getMembersLossAmount(),
                searchRuleEntity.getMembersLossNumber());

        Integer membersNewIntervalCount = customerService.selectCountByNewMember(searchRuleEntity.getShopIdenty(), searchRuleEntity.getMembersNewIntervalDay());
        Integer membersBirthdayCount = customerService.selectCountByBirthday(searchRuleEntity.getShopIdenty(), searchRuleEntity.getMembersBirthdayBeforeDay());
        Integer membersAnniversaryCount = customerService.selectCountByAnniversary(searchRuleEntity.getShopIdenty(), searchRuleEntity.getMembersAnniversaryBeforeDay());

        int memberCount = customerService.selectCountByShop(searchModel.getUser().getShopIdenty());
        model.addAttribute("membersCount", memberCount);
        model.addAttribute("consumptionMainCount", consumptionMainCount);
        model.addAttribute("consumptionMainCountPercent", consumptionMainCount * 100 / Math.max(memberCount, 1));
        model.addAttribute("membersWillCount", membersWillCount);
        model.addAttribute("membersWillCountPercent", membersWillCount * 100 / Math.max(memberCount, 1));
        model.addAttribute("membersLossCount", membersLossCount);
        model.addAttribute("membersLossCountPercent", membersLossCount * 100 / Math.max(memberCount, 1));
        model.addAttribute("membersNewIntervalCount", membersNewIntervalCount);
        model.addAttribute("membersNewIntervalCountPercent", membersNewIntervalCount * 100 / Math.max(memberCount, 1));
        model.addAttribute("membersBirthdayCount", membersBirthdayCount);
        model.addAttribute("membersAnniversaryCount", membersAnniversaryCount);

        return "customerlist";
    }

    private CustomerSearchRuleEntity getCustomerSearchRuleEntity(CustomerSearchModel searchModel) {
        CustomerSearchRuleEntity searchRuleEntity = customerSearchRuleService.selectByShopId(
                searchModel.getUser().getBrandIdenty(),
                searchModel.getUser().getShopIdenty());
        if (searchRuleEntity == null) {
            searchRuleEntity = new CustomerSearchRuleEntity();
            searchRuleEntity.setShopIdenty(searchModel.getUser().getShopIdenty());
            searchRuleEntity.setBrandIdenty(searchModel.getUser().getBrandIdenty());
            searchRuleEntity.setConsumptionMainDay(365);
            searchRuleEntity.setConsumptionMainAmount(5000);
            searchRuleEntity.setConsumptionMainNumber(12);
            searchRuleEntity.setMembersWillDay(365);
            searchRuleEntity.setMembersWillAmount(2000);
            searchRuleEntity.setMembersWillNumber(6);
            searchRuleEntity.setMembersLossDay(365);
            searchRuleEntity.setMembersLossAmount(1000);
            searchRuleEntity.setMembersLossNumber(3);
            searchRuleEntity.setMembersNewIntervalDay(0);
            searchRuleEntity.setMembersBirthdayBeforeDay(0);
            searchRuleEntity.setMembersAnniversaryBeforeDay(0);
        }
        return searchRuleEntity;
    }

    @RequestMapping(value = {"/{id}/edit", "/edit"})
    public String edit(Model model, @PathVariable(required = false) Long id, CustomerEditModel editModel) {
        LoginManager.setUser(editModel);
        if (id != null) {
            CustomerEntity coupon = customerService.selectById(id);
            editModel.setId(coupon.getId());
            editModel.setName(coupon.getName());
            editModel.setBirthday(formatDateString(coupon.getBirthday()));
            editModel.setGender(coupon.getGender());
            editModel.setGroupLevel(coupon.getGroupLevel());
            editModel.setMobile(coupon.getMobile());
            editModel.setTelephone(coupon.getTelephone());
            editModel.setAddress(coupon.getAddress());
            editModel.setHobby(coupon.getHobby());
            editModel.setProfile(coupon.getProfile());
            editModel.setPassword(coupon.getPassword());
            editModel.setCheckPassword(coupon.getPassword());
        }
        model.addAttribute("editModel", editModel);
        return "customer_modify";
    }

    @RequestMapping("/save")
    public String save(CustomerEditModel editModel) {
        AuthUserEntity loginUser = LoginManager.get().getUser();
        CustomerEntity customer = new CustomerEntity();
        customer.setId(editModel.getId());
        if (customer.getId() == null) {
            customer.baseCreate(loginUser.getCreatorId(), loginUser.getCreatorName());
        }
        customer.setShopIdenty(loginUser.getShopIdenty());
        customer.setBrandIdenty(loginUser.getBrandIdenty());
        customer.baseUpdate(loginUser.getCreatorId(), loginUser.getCreatorName());
        customer.setName(editModel.getName());
        customer.setBirthday(stringToDate(editModel.getBirthday()));
        customer.setGender(editModel.getGender());
        customer.setGroupLevel(editModel.getGroupLevel());
        customer.setMobile(editModel.getMobile());
        customer.setTelephone(editModel.getTelephone());
        customer.setAddress(editModel.getAddress());
        customer.setHobby(editModel.getHobby());
        customer.setProfile(editModel.getProfile());
        String password = editModel.getPassword();
        if (StringUtils.isBlank(editModel.getCheckPassword()) || !editModel.getCheckPassword().equals(editModel.getPassword())) {
            password = Password.create().generate(editModel.getMobile(), password);
        }

        customer.setPassword(password);
        customerService.insertOrUpdate(customer);
        return redirect("/customer/list");
    }

}

