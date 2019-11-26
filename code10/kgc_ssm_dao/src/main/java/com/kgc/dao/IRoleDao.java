package com.kgc.dao;
import com.kgc.domain.Permission;
import com.kgc.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IRoleDao {
    //根据用户的id去中间表中查询角色的id  再根据角色的id查询角色信息
    @Select("select * from role where id in(select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "desc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",
                    javaType = java.util.List.class,
                    many = @Many(select = "com.kgc.dao.IPermissionsDao.findPermissionByRoleId")
            ),
    })
    List<Role> findRoleByUserId(String userId) throws Exception;

    @Select("select * from role")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "desc",column = "roleDesc")
    })
    List<Role> findAll();

    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{desc})")
    void save(Role role) throws Exception;

    @Select("select * from role where id=#{roleId}")
    Role findById(String roleId);

    @Select("select * from Permission where id not in(select permissionId from role_permission where roleId=#{roleId})")
    List<Permission> findOtherPermission(String roleId);

    @Insert("insert into role_permission(permissionId,roleId) values(#{permissionId},#{roleId})")
    void addPermissionToRole(@Param("roleId") String roleId,@Param("permissionId") String permissionId);
}
