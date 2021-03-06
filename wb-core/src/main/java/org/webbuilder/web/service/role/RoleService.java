package org.webbuilder.web.service.role;

import org.webbuilder.web.core.service.GenericService;
import org.webbuilder.web.core.utils.RandomUtil;
import org.webbuilder.web.dao.module.ModuleMapper;
import org.webbuilder.web.dao.role.RoleModuleMapper;
import org.webbuilder.web.po.role.Role;
import org.webbuilder.web.dao.role.RoleMapper;
import org.webbuilder.web.po.role.RoleModule;
import org.springframework.stereotype.Service;
import org.webbuilder.web.service.module.ModuleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台管理角色服务类
 * Created by generator
 */
@Service
public class RoleService extends GenericService<Role, String> {

    //默认数据映射接口
    @Resource
    protected RoleMapper roleMapper;

    @Resource
    protected RoleModuleMapper roleModuleMapper;

    @Resource
    protected ModuleService moduleService;

    @Override
    protected RoleMapper getMapper() {
        return this.roleMapper;
    }

    @Override
    public int insert(Role data) throws Exception {
        int l = super.insert(data);
        List<RoleModule> roleModule = data.getModules();
        if (roleModule != null && roleModule.size() > 0) {
            //保存角色模块关联
            for (RoleModule module : roleModule) {
                module.setU_id(RandomUtil.randomChar(6));
                module.setRole_id(data.getU_id());
                roleModuleMapper.insert(module);
            }
        }
        return l;
    }

    @Override
    public int update(Role data) throws Exception {
        int l = super.update(data);
        List<RoleModule> roleModule = data.getModules();
        if (roleModule != null && roleModule.size() > 0) {
            //先删除所有roleModule
            roleModuleMapper.deleteByRoleId(data.getU_id());
            //保存角色模块关联
            for (RoleModule module : roleModule) {
                module.setU_id(RandomUtil.randomChar(6));
                module.setRole_id(data.getU_id());
                roleModuleMapper.insert(module);
            }
        }
        return l;
    }
}
