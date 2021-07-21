export const roleOptions = [
  { key: 'Z', display_name: '系统管理员' },
  { key: 'M', display_name: '管理员' },
  { key: 'A', display_name: '专家' },
  // { key: 'O', display_name: '外包审核员' },
  { key: 'S', display_name: '共享用户' }
]

export const roleKeyValue = roleOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const genderOptions = [
  { key: '0', display_name: '未提供' },
  { key: '1', display_name: '男' },
  { key: '2', display_name: '女' }
]

export const genderKeyValue = genderOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const userStatusOptions = [
  { key: '0', display_name: '冻结' },
  { key: '1', display_name: '正常' }
]

export const userStatusKeyValue = userStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const actionTypeOptions = [
  { key: 'l', display_name: '列表' },
  { key: 'c', display_name: '创建' },
  { key: 'r', display_name: '详情' },
  { key: 'u', display_name: '更新' },
  { key: 'd', display_name: '删除' },
  { key: 'e', display_name: '自定义' }
]

export const actionTypeKeyValue = actionTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const yesOrNoOptions = [
  { key: '0', display_name: '否' },
  { key: '1', display_name: '是' }
]

export const yesOrNoKeyValue = yesOrNoOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const ctStatusOptions = [
  { key: '0', display_name: '停用' },
  { key: '1', display_name: '启用' }
]

export const ctStatusKeyValue = ctStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const originalTypeOptions = [
  { key: '1', display_name: '人名' },
  { key: '2', display_name: '通名' },
  { key: '3', display_name: '形容词' },
  { key: 'x', display_name: '其它' }
]

export const originalTypeKeyValue = originalTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const matchWayOptions = [
  { key: '1', display_name: '精确' },
  { key: '2', display_name: '前缀（词头）' },
  { key: '3', display_name: '后缀（词尾）' },
  { key: '4', display_name: '前置（在xxx之前）' },
  { key: '5', display_name: '后置（在xxx之后）' }
]

export const matchWayKeyValue = matchWayOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const projectStatusOptions = [
  { key: '1', display_name: '执行中' },
  { key: '9', display_name: '已结束' }
]

export const projectStatusKeyValue = projectStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const taskStatusOptions = [
  { key: '1', display_name: '执行中' },
  { key: '2', display_name: '已完成' },
  { key: '9', display_name: '已取消' }
]

export const taskStatusKeyValue = taskStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const needRomanOptions = [
  { key: '0', display_name: '不需要' },
  { key: '1', display_name: '需要' }
]

export const needRomanKeyValue = needRomanOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const romanStatusOptions = [
  { key: '0', display_name: '未执行' },
  { key: '1', display_name: '已执行' },
  { key: '9', display_name: '未涉及' }
]

export const romanStatusKeyValue = romanStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const transStatusOptions = [
  { key: '0', display_name: '未执行' },
  { key: '1', display_name: '已翻译' }
]

export const transStatusKeyValue = transStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const checkStatusOptions = [
  { key: '0', display_name: '未执行' },
  { key: '1', display_name: '已核对' }
]

export const checkStatusKeyValue = checkStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const checkRoundStatusOptions = [
  { key: '0', display_name: '未开始' },
  { key: '1', display_name: '执行中' },
  { key: '9', display_name: '已结束' }
]

export const checkRoundStatusKeyValue = checkRoundStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const assignStatusOptions = [
  { key: '0', display_name: '未分配' },
  { key: '1', display_name: '已分配' }
]

export const assignStatusKeyValue = assignStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export const adoptionStatusOptions = [
  { key: '0', display_name: '未处理' },
  { key: '1', display_name: '已采纳' },
  { key: '2', display_name: '已拒绝' }
]

export const adoptionStatusKeyValue = adoptionStatusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})
