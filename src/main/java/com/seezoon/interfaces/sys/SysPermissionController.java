package com.seezoon.interfaces.sys;

import com.seezoon.application.sys.dto.AddPermissionCmd;
import com.seezoon.application.sys.dto.DeletePermissionCmd;
import com.seezoon.application.sys.dto.ModifyPermissionCmd;
import com.seezoon.application.sys.dto.PermissionListQry;
import com.seezoon.application.sys.dto.clientobject.PermissionCO;
import com.seezoon.application.sys.executor.AddPermissionCmdExe;
import com.seezoon.application.sys.executor.DeletePermissionCmdExe;
import com.seezoon.application.sys.executor.ModifyPermissionCmdExe;
import com.seezoon.application.sys.executor.PermissionListQryExe;
import com.seezoon.infrastructure.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dfenghuang
 * @date 2023/9/25 23:00
 */
@Tag(name = "系统权限管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

    private final PermissionListQryExe permissionListQryExe;
    private final AddPermissionCmdExe addPermissionCmdExe;
    private final ModifyPermissionCmdExe modifyPermissionCmdExe;
    private final DeletePermissionCmdExe deletePermissionCmdExe;

    @Operation(summary = "查询权限列表")
    @PostMapping("/list")
    public Response<List<PermissionCO>> qry(@RequestBody PermissionListQry qry) {
        return permissionListQryExe.execute(qry);
    }

    @Operation(summary = "添加权限")
    @PreAuthorize("hasAuthority('sys:permission:add')")
    @PostMapping("/add")
    public Response addPermission(@RequestBody AddPermissionCmd cmd) {
        return addPermissionCmdExe.execute(cmd);
    }

    @Operation(summary = "修改权限")
    @PreAuthorize("hasAuthority('sys:permission:modify')")
    @PostMapping("/modify")
    public Response modifyPermission(@RequestBody ModifyPermissionCmd cmd) {
        return modifyPermissionCmdExe.execute(cmd);
    }

    @Operation(summary = "删除权限")
    @PreAuthorize("hasAuthority('sys:permission:delete')")
    @PostMapping("/delete")
    public Response deletePermission(@RequestBody DeletePermissionCmd cmd) {
        return deletePermissionCmdExe.execute(cmd);
    }
}
