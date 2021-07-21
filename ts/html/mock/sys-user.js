const Mock = require('mockjs')

let List = []
let count = 100

const avatar = 'https://wpimg.wallstcn.com/e4558086-631c-425c-9430-56ffb46e70b3'
// const userNames = [
//   'Kevin',
//   'Lina',
//   '大力水手',
//   '张三丰',
//   '张无忌',
//   '扫地僧',
//   '赵敏',
//   '王一奇',
//   '李管事',
//   '风清扬',
//   '西门吹牛'
// ]
const phones = [
  '13811650908',
  '13466667007',
  '13125681021',
  '13205051111',
  '15688880202',
  '13909090808',
  '15833334444',
  '15988886666',
  '13000001111',
  '13400002212',
  '13602021547'
]

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    id: '@increment',
    'phone|1': phones,
    password: '1234Qwer',
    userName: '@first',
    avatar,
    'gender|1': ['0', '1', '2'],
    birthday: '@date("yyyy-MM-dd")',
    'roles|1': ['Z', 'M', 'A', 'O', 'S'],
    'status|1': ['0', '1']
  }))
}

module.exports = [
  // list user
  {
    url: '/api/user/l',
    type: 'get',
    response: config => {
      const { userName, phone, pageNum, pageSize, orderBy='id', ascDesc='asc' } = config.query

      let mockList = List.filter(item => {
        if (userName && item.userName.indexOf(userName) != 0) return false
        if (phone && item.phone.indexOf(phone) != 0) return false
        return true
      })

      const sorting = (s1, s2) => {
        if (ascDesc === 'desc') {
          if (s1[orderBy] < s2[orderBy]) {
            return -1;
          } else {
            return 1;
          }
        } else {
          if (s1[orderBy] < s2[orderBy]) {
            return 1;
          } else {
            return -1;
          }
        }
      }
      mockList.sort(sorting)
      const pageList = mockList.filter((item, index) => index < pageSize * pageNum && index >= pageSize * (pageNum - 1))

      return {
        status: 'ok',
        data: {
          pagination: {
            pageNum: pageNum,
            pageSize: pageSize,
            startRow: pageSize * (pageNum - 1),
            endRow: Math.min(pageSize * pageNum, count),
            total: count,
            pages: Math.ceil(count/pageSize)
          },
          dataList: pageList
        }
      }
    }
  },

  // get user
  {
    url: '/api/user/r',
    type: 'get',
    response: config => {
      const { id } = config.query
      for (const user of List) {
        if (user.id === +id) {
          return {
            status: 'ok',
            data: user
          }
        }
      }

      return {
        status: 'error',
        data: {
          errCode: 900100,
          errMsg: '数据不存在'
        }
      }
    }
  },

  // create user
  {
    url: '/api/user/c',
    type: 'post',
    response: config => {
      const { phone, password, userName, avatar, gender, birthday, roles } = config.body
      
      let maxId = -1;
      for (const user of List) {
        maxId = Math.max(maxId, user.id)
        if (user.phone === phone) {
          return {
            status: 'error',
            data: {
              errCode: 900101,
              errMsg: '手机号重复。'
            }
          }
        }
      }

      List.push({
        id: maxId+1,
        phone: phone,
        password: password,
        userName: userName,
        avatar: avatar,
        gender: gender,
        birthday: birthday,
        roles: roles,
        status: '1'
      })
      count++

      return {
        status: 'ok',
        data: 1
      }
    }
  },

  // update user
  {
    url: '/api/user/u',
    type: 'post',
    response: config => {
      const { id, phone, password, userName, avatar, gender, birthday, roles, status } = config.body
      let userIndex = -1
      for (let i = 0; i < List.length; i++) {
        const user = List[i]
        if (user.id === +id) {
          userIndex = i
        }
      }

      if (userIndex !== -1) {
        List.splice(userIndex, 1, {
          id: id,
          phone: phone,
          password: password,
          userName: userName,
          avatar: avatar,
          gender: gender,
          birthday: birthday,
          roles: roles,
          status: status
        })
        return {
          status: 'ok',
          data: 1
        }
      }

      return {
        status: 'error',
        data: {
          errCode: 900100,
          errMsg: '数据不存在'
        }
      }
    }
  },

  // delete user
  {
    url: '/api/user/d',
    type: 'post',
    response: config => {
      const { id } = config.body
      let userIndex = -1
      for (let i = 0; i < List.length; i++) {
        const user = List[i]
        if (user.id === +id) {
          userIndex = i
        }
      }

      if (userIndex !== -1) {
        List.splice(userIndex, 1)
        count--
        return {
          status: 'ok',
          data: 1
        }
      }

      return {
        status: 'error',
        data: {
          errCode: 900100,
          errMsg: '数据不存在'
        }
      }
    }
  }
]

