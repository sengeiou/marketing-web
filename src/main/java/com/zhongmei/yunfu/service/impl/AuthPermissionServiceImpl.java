package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.AuthPermissionEntity;
import com.zhongmei.yunfu.domain.mapper.AuthPermissionMapper;
import com.zhongmei.yunfu.service.AuthPermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
@Service
public class AuthPermissionServiceImpl extends ServiceImpl<AuthPermissionMapper, AuthPermissionEntity> implements AuthPermissionService {

}
