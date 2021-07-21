import request from '@/utils/request'

// user start
export function listUser(query) {
  return request({
    url: '/api/user/l',
    method: 'get',
    params: query
  })
}

export function getUser(id) {
  return request({
    url: '/api/user/r',
    method: 'get',
    params: { id }
  })
}

export function createUser(data) {
  return request({
    url: '/api/user/c',
    method: 'post',
    data
  })
}

export function updateUser(data) {
  return request({
    url: '/api/user/u',
    method: 'post',
    data
  })
}

export function deleteUser(id) {
  return request({
    url: '/api/user/d',
    method: 'post',
    data: { id }
  })
}
// user end

// upload file start
export function uploadFile(data) {
  return request({
    url: '/api/file/upload',
    method: 'post',
    data
  })
}
// upload file end
