
const tokens = {
  13811650908: {
    token: 'admin-token'
  },
  13466667007: {
    token: 'not-admin-token'
  }
}

const users = {
  'admin-token': {
    id: 1,
    phone: '13811650908',
    password: '123',
    status: '1',
    roles: 'Z;M',
    userName: 'Kevin',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    gender: '1',
    birthday: '1981-11-24'
  },
  'not-admin-token': {
    id: 2,
    phone: '13466667007',
    password: '123',
    status: '1',
    roles: 'M;A',
    userName: 'Lina',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    gender: '2',
    birthday: '1982-10-31'
  }
}

module.exports = [
  // user login
  {
    url: '/api/auth/signin',
    type: 'post',
    response: config => {
      const { phone } = config.body
      const { token } = tokens[phone]
      // mock error
      if (!token) {
        return {
          status: 'error',
          hasData: '1',
          data: {
            errCode: 900001,
            errMsg:'登录失败'
          }
        }
      }

      return {
        status: 'ok',
        hasData: '1',
        data: {
          token: token
        }
      }
    }
  },

  // get user info
  {
    url: '/api/auth/currentUser',
    type: 'get',
    response: config => {
      const token = config.headers['x-auth-token']
      const info = users[token]
      // mock error
      if (!info) {
        return {
          status: 'error',
          hasData: '1',
          data: {
            errCode: 900401,
            errMsg:'没有登录'
          }
        }
      }

      return  {
        status: 'ok',
        hasData: '1',
        data: info
      }
    }
  },

  // user logout
  {
    url: '/api/auth/signout',
    type: 'post',
    response: _ => {
      return {
        status: 'ok',
        hasData: '1'
      }
    }
  }
]
