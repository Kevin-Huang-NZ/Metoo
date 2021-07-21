/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const superRouter = {
  path: '/super',
  component: Layout,
  redirect: '/super/page',
  alwaysShow: true,
  name: 'Super',
  meta: {
    title: '超级权限',
    icon: 'bug',
    roles: ['Z']
  },
  children: [
    {
      path: 'page',
      component: () => import('@/views/super/page'),
      name: 'Page',
      meta: { title: '模块管理' }
    },
    {
      path: 'fun',
      component: () => import('@/views/super/fun'),
      name: 'Fun',
      meta: { title: '系统功能' }
    },
    {
      path: 'role',
      component: () => import('@/views/super/role'),
      name: 'Role',
      meta: { title: '角色管理' }
    },
    {
      path: 'choose-fun/:roleNo',
      component: () => import('@/views/super/choose-fun'),
      name: 'ChooseFun',
      meta: { title: '选择功能', noCache: true, activeMenu: '/super/role' },
      hidden: true
    }
  ]
}
export default superRouter
