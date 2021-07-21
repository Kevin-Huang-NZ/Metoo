const steps = [
  {
    element: '#side-bar',
    popover: {
      title: '左侧菜单',
      description: '树形结构的功能菜单',
      position: 'right'
    }
  },
  {
    element: '#hamburger-container',
    popover: {
      title: '菜单开关',
      description: '打开/关闭左侧菜单',
      position: 'bottom'
    }
  },
  {
    element: '#breadcrumb-container',
    popover: {
      title: '面包屑',
      description: '显示当前页面路径',
      position: 'bottom'
    }
  },
  {
    element: '#screenfull',
    popover: {
      title: '全屏',
      description: '进入/退出全屏模式',
      position: 'left'
    }
  },
  {
    element: '#header-guide',
    popover: {
      title: '页面导航',
      description: '简要介绍页面结构',
      position: 'left'
    }
  },
  {
    element: '#person-center',
    popover: {
      title: '用户中心',
      description: '更新用户信息，查看帮助文档，退出登录',
      position: 'left'
    }
  }
]

export default steps
