package com.seezoon.interfaces.sys;

import com.seezoon.application.sys.dto.AddUserCmd;
import com.seezoon.application.sys.dto.ModifyMyPwdCmd;
import com.seezoon.application.sys.dto.ModifyUserCmd;
import com.seezoon.application.sys.dto.ModifyUserPwdCmd;
import com.seezoon.application.sys.dto.UserDetailQry;
import com.seezoon.application.sys.dto.UserPageQry;
import com.seezoon.application.sys.dto.clientobject.MyInfoCO;
import com.seezoon.application.sys.dto.clientobject.UserCO;
import com.seezoon.application.sys.dto.clientobject.UserDetailCO;
import com.seezoon.application.sys.executor.AddUserCmdExe;
import com.seezoon.application.sys.executor.ModifyMyPwdCmdExe;
import com.seezoon.application.sys.executor.ModifyUserCmdExe;
import com.seezoon.application.sys.executor.ModifyUserPwdCmdExe;
import com.seezoon.application.sys.executor.MyInfoQryExe;
import com.seezoon.application.sys.executor.UserDetailQryExe;
import com.seezoon.application.sys.executor.UserPageQryExe;
import com.seezoon.infrastructure.dto.Page;
import com.seezoon.infrastructure.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "系统用户管理", description = "管理用户信息及用户周边数据")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final AddUserCmdExe addUserCmdExe;
    private final ModifyUserCmdExe modifyUserCmdExe;
    private final ModifyUserPwdCmdExe modifyUserPwdCmdExe;
    private final UserPageQryExe userPageQryExe;
    private final UserDetailQryExe userDetailQryExe;
    private final MyInfoQryExe myInfoQryExe;
    private final ModifyMyPwdCmdExe modifyMyPwdCmdExe;

    @Operation(summary = "添加系统用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @PostMapping("/add")
    public Response addUser(@RequestBody AddUserCmd cmd) {
        return addUserCmdExe.execute(cmd);
    }

    @PreAuthorize("hasAuthority('sys:user:modify')")
    @Operation(summary = "修改用户信息")
    @PostMapping("/modify")
    public Response modifyUser(@RequestBody ModifyUserCmd cmd) {
        return modifyUserCmdExe.execute(cmd);
    }

    @PreAuthorize("hasAuthority('sys:user:modify_pwd')")
    @Operation(summary = "修改用户密码")
    @PostMapping("/modify_pwd")
    public Response modifyUserPwd(@RequestBody ModifyUserPwdCmd cmd) {
        return modifyUserPwdCmdExe.execute(cmd);
    }

    @PreAuthorize("hasAuthority('sys:user:qry')")
    @Operation(summary = "个人信息查询")
    @GetMapping("/detail/{uid}")
    public Response<UserDetailCO> userDetailQry(@PathVariable Integer uid) {
        UserDetailQry qry = new UserDetailQry(uid);
        return userDetailQryExe.execute(qry);
    }

    @PreAuthorize("hasAuthority('sys:user:qry')")
    @Operation(summary = "分页查询用户信息")
    @PostMapping("/page")
    public Response<Page<UserCO>> userPageQry(@RequestBody UserPageQry qry) {
        return userPageQryExe.execute(qry);
    }

    @Operation(summary = "我的个人信息")
    @GetMapping("/my")
    public Response<MyInfoCO> my() {
        return myInfoQryExe.execute();
    }

    @Operation(summary = "修改我的密码")
    @PostMapping("/modify_my_pwd")
    public Response<MyInfoCO> modifyMyPwd(@RequestBody ModifyMyPwdCmd cmd) {
        return modifyMyPwdCmdExe.execute(cmd);
    }
}
