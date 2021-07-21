/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const sysRouter = {
  path: '/sys',
  component: Layout,
  redirect: '/sys/user',
  alwaysShow: true,
  name: 'Sys',
  meta: {
    title: '系统管理',
    icon: 'table',
    roles: ['Z', 'M']
  },
  children: [
    {
      path: 'user',
      component: () => import('@/views/sys/user'),
      name: 'User',
      meta: { title: '用户' }
    }
  ]
}
export default sysRouter
