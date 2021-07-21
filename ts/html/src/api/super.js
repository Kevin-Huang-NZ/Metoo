import request from '@/utils/request'
// sys page start
export function listSysPage(query) {
  return request({
    url: '/api/syspage/l',
    method: 'get',
    params: query
  })
}

export function getSysPage(id) {
  return request({
    url: '/api/syspage/r',
    method: 'get',
    params: { id }
  })
}

export function createSysPage(data) {
  return request({
    url: '/api/syspage/c',
    method: 'post',
    data
  })
}

export function updateSysPage(data) {
  return request({
    url: '/api/syspage/u',
    method: 'post',
    data
  })
}

export function deleteSysPage(id) {
  return request({
    url: '/api/syspage/d',
    method: 'post',
    data: { id }
  })
}
// sys page end

// sys role start
export function listSysRole(query) {
  return request({
    url: '/api/sysrole/l',
    method: 'get',
    params: query
  })
}

export function getSysRole(id) {
  return request({
    url: '/api/sysrole/r',
    method: 'get',
    params: { id }
  })
}

export function createSysRole(data) {
  return request({
    url: '/api/sysrole/c',
    method: 'post',
    data
  })
}

export function updateSysRole(data) {
  return request({
    url: '/api/sysrole/u',
    method: 'post',
    data
  })
}

export function deleteSysRole(id) {
  return request({
    url: '/api/sysrole/d',
    method: 'post',
    data: { id }
  })
}

export function getSysRoleByNo(roleNo) {
  return request({
    url: '/api/sysrole/gbuk',
    method: 'get',
    params: { roleNo }
  })
}
// sys role end

// sys fun start
export function listSysFun(query) {
  return request({
    url: '/api/sysfun/l',
    method: 'get',
    params: query
  })
}

export function getSysFun(id) {
  return request({
    url: '/api/sysfun/r',
    method: 'get',
    params: { id }
  })
}

export function createSysFun(data) {
  return request({
    url: '/api/sysfun/c',
    method: 'post',
    data
  })
}

export function updateSysFun(data) {
  return request({
    url: '/api/sysfun/u',
    method: 'post',
    data
  })
}

export function deleteSysFun(id) {
  return request({
    url: '/api/sysfun/d',
    method: 'post',
    data: { id }
  })
}
// sys fun end

// sys role vs fun start

export function listSysRoleFun(roleNo) {
  return request({
    url: '/api/sysrolefun/l',
    method: 'get',
    params: { roleNo }
  })
}

export function createSysRoleFun(data) {
  return request({
    url: '/api/sysrolefun/c',
    method: 'post',
    data
  })
}

export function deleteSysRoleFun(data) {
  return request({
    url: '/api/sysrolefun/d',
    method: 'post',
    data
  })
}
// sys role vs fun end
