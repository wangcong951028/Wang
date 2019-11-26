package com.kgc.service.impl;
import com.github.pagehelper.PageHelper;
import com.kgc.dao.IUserDao;
import com.kgc.domain.Role;
import com.kgc.domain.UserInfo;
import com.kgc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证管理器
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IUserDao userdao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try{
            userInfo = userdao.findByUsername(username);
        }catch (Exception e){
            e.printStackTrace();
        }
        //把userInfo对象封装成userDetails  我们现在还要考虑用户的状态是否已经开启  开启才能登路
        /*User user = new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthority(userInfo.getRoles()));*/
        User user = new User(userInfo.getUsername(),/*"{noop}"+*/userInfo.getPassword(),userInfo.getStatus()==0?false:true,true,true,true,getAuthority(userInfo.getRoles()));
        return user;
    }

    private List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        return list;
    }

    @Override
    public List<UserInfo> findAll(int page,int size) {
        PageHelper.startPage(page,size);
        return userdao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) {
        //在service中对密码进行加密  然后在传递给dao
        //MD5  不严格  被人破解了
        String encode = bCryptPasswordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encode);
        userdao.save(userInfo);
    }

    @Override
    public UserInfo findById(String id) throws Exception {
        return userdao.findById(id);
    }

    @Override
    public List<Role> findOtherRoles(String userId) throws Exception {
        return userdao.findOtherRoles(userId);
    }

    @Override
    public void addRoleToUser(String userId, String[] roleIds) throws Exception {
        for (String roleId : roleIds) {
            userdao.addRoleToUser(userId,roleId);
        }
    }
}
