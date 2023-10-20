package com.seezoon.interfaces.sys;

import com.seezoon.application.sys.dto.AddRoleCmd;
import com.seezoon.application.sys.dto.DeleteRoleCmd;
import com.seezoon.application.sys.dto.ModifyRoleCmd;
import com.seezoon.application.sys.dto.RoleDetailQry;
import com.seezoon.application.sys.dto.RoleListQry;
import com.seezoon.application.sys.dto.clientobject.RoleCO;
import com.seezoon.application.sys.dto.clientobject.RoleDetailCO;
import com.seezoon.application.sys.executor.AddRoleCmdExe;
import com.seezoon.application.sys.executor.DeleteRoleCmdExe;
import com.seezoon.application.sys.executor.ModifyRoleCmdExe;
import com.seezoon.application.sys.executor.RoleDetailQryExe;
import com.seezoon.application.sys.executor.RoleListQryExe;
import com.seezoon.infrastructure.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dfenghuang
 * @date 2023/9/25 23:00
 */
@Tag(name = "系统角色管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    private final RoleDetailQryExe roleDetailQryExe;
    private final RoleListQryExe roleListQryExe;
    private final AddRoleCmdExe addRoleCmdExe;
    private final ModifyRoleCmdExe modifyRoleCmdExe;
    private final DeleteRoleCmdExe deleteRoleCmdExe;

    @Operation(summary = "查询角色")
    @GetMapping("/detail/{roleId}")
    public Response<RoleDetailCO> detail(@PathVariable Integer roleId) {
        return roleDetailQryExe.execute(new RoleDetailQry(roleId));
    }

    @Operation(summary = "查询角色列表")
    @PostMapping("/list")
    public Response<List<RoleCO>> qry(@RequestBody RoleListQry qry) {
        return roleListQryExe.execute(qry);
    }

    @Operation(summary = "添加角色")
    @PreAuthorize("hasAuthority('sys:role:add')")
    @PostMapping("/add")
    public Response addRole(@RequestBody AddRoleCmd cmd) {
        return addRoleCmdExe.execute(cmd);
    }

    @Operation(summary = "修改角色")
    @PreAuthorize("hasAuthority('sys:role:modify')")
    @PostMapping("/modify")
    public Response modifyRole(@RequestBody ModifyRoleCmd cmd) {
        return modifyRoleCmdExe.execute(cmd);
    }

    @Operation(summary = "删除角色")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @PostMapping("/delete")
    public Response deleteRole(@RequestBody DeleteRoleCmd cmd) {
        return deleteRoleCmdExe.execute(cmd);
    }
}
