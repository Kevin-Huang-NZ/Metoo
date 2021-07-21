import request from '@/utils/request'
// [=ucTableName] start
export function list[=ucTableName](query) {
  return request({
    url: '/api/[=lowerTableName]/l',
    method: 'get',
    params: query
  })
}

export function get[=ucTableName](id) {
  return request({
    url: '/api/[=lowerTableName]/r',
    method: 'get',
    params: { id }
  })
}

export function create[=ucTableName](data) {
  return request({
    url: '/api/[=lowerTableName]/c',
    method: 'post',
    data
  })
}

export function update[=ucTableName](data) {
  return request({
    url: '/api/[=lowerTableName]/u',
    method: 'post',
    data
  })
}

export function delete[=ucTableName](id) {
  return request({
    url: '/api/[=lowerTableName]/d',
    method: 'post',
    data: { id }
  })
}
// [=ucTableName] end
