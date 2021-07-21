import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'

const state = {
  token: getToken(),
  id: null,
  phone: '',
  // password: '',
  userName: '',
  avatar: '',
  // gender: '',
  // birthday: '',
  // status: '',
  roles: ''
}

const mutations = {
  SET_USER_ID: (state, userId) => {
    state.id = userId
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_PHONE: (state, phone) => {
    state.phone = phone
  },
  SET_USER_NAME: (state, userName) => {
    state.userName = userName
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { phone, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ phone: phone.trim(), password: password }).then(response => {
        const { token } = response
        commit('SET_TOKEN', token)
        setToken(token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const data = response

        if (!data) {
          reject('登录权限校验失败，请联系管理。')
        }

        const { id, phone, userName, avatar, roles } = data
        const avatarFullPath = process.env.VUE_APP_BASE_API + avatar

        // roles must be a non-empty array
        if (!roles || roles.length <= 0) {
          reject('登录权限校验失败，请联系管理。')
        }

        const roleArr = roles.split(';')
        data.roles = roleArr

        commit('SET_USER_ID', id)
        commit('SET_PHONE', phone)
        commit('SET_USER_NAME', userName)
        commit('SET_AVATAR', avatarFullPath)
        commit('SET_ROLES', roleArr)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resetRouter()

        // reset visited views and cached views
        // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
        dispatch('tagsView/delAllViews', null, { root: true })

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    // TODO
    const token = role + '-token'

    commit('SET_TOKEN', token)
    setToken(token)

    const { roles } = await dispatch('getInfo')

    resetRouter()

    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })
    // dynamically add accessible routes
    router.addRoutes(accessRoutes)

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, { root: true })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
