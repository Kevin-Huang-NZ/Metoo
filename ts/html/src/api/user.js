import request from '@/utils/request'

export function login(data) {
  // console.log('processing user login')
  return request({
    url: '/api/auth/signin',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/api/auth/currentUser',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/api/auth/signout',
    method: 'post'
  })
}

export function changePassword(data) {
  return request({
    url: '/api/auth/chgpwd',
    method: 'post',
    data
  })
}
