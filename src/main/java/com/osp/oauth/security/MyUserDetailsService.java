package com.osp.oauth.security;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.osp.oauth.model.SystemUser;
import com.osp.oauth.model.UserRole;
import com.osp.oauth.service.SystemUserService;
import com.osp.oauth.service.UserRoleService;
import com.osp.oauth.service.impl.SystemUserServiceImpl;
import com.osp.oauth.service.impl.UserRoleServiceImpl;


@Service("MyUserDetailsImpl")
public class MyUserDetailsService implements UserDetailsService {
	
    
	@Resource(name = "SystemUserServiceImpl")
    private SystemUserService systemUserService;

    @Resource(name = "UserRoleServiceImpl")
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SystemUser user;
        try {
            user = systemUserService.findByName(userName);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user select fail");
        }
        if(user == null){
            throw new UsernameNotFoundException("no user found");
        } else {
            try {
                List<UserRole> roles = userRoleService.getRoleByUser(user);
                return new MyUserDetails(user, roles);
            } catch (Exception e) {
                throw new UsernameNotFoundException("user role select fail");
            }
        }
    }

}
