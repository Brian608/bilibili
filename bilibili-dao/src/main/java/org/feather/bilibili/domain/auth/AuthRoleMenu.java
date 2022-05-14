package org.feather.bilibili.domain.auth;

import java.util.Date;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-05-03 20:57
 **/
public class AuthRoleMenu {
    private Long id;

    private Long roleId;

    private Long menuId;

    private Date createTime;

    private AuthMenu authMenu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public AuthMenu getAuthMenu() {
        return authMenu;
    }

    public void setAuthMenu(AuthMenu authMenu) {
        this.authMenu = authMenu;
    }
}
