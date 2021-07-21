<template>
  <div class="app-container">
    <div v-if="accountData.id">
      <el-row :gutter="20">

        <el-col :span="6" :xs="24">
          <user-card :user="userCardData" />
        </el-col>

        <el-col :span="18" :xs="24">
          <el-card>
            <el-tabs v-model="activeTab">
              <el-tab-pane label="基本信息" name="userInfo">
                <account :user="accountData" />
              </el-tab-pane>
              <el-tab-pane label="修改密码" name="changePwd">
                <change-password />
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>

      </el-row>
    </div>
  </div>
</template>

<script>
// import { mapGetters } from 'vuex'
import UserCard from './components/UserCard'
import Account from './components/Account'
import ChangePassword from './components/ChangePassword'
import { getInfo } from '@/api/user'

export default {
  name: 'Profile',
  components: { UserCard, Account, ChangePassword },
  data() {
    return {
      userCardData: {},
      accountData: {},
      activeTab: 'userInfo'
    }
  },
  // computed: {
  //   ...mapGetters([
  //     'phone',
  //     'userName',
  //     'avatar',
  //     'roles'
  //   ])
  // },
  created() {
    this.getUser()
  },
  methods: {
    getUser() {
      // this.user = {
      //   userName: this.userName,
      //   roles: this.roles.join(' | '),
      //   phone: this.phone,
      //   avatar: this.avatar
      // }

      getInfo().then((response) => {
        const { id, phone, userName, avatar, roles, birthday, gender } = response
        const avatarFullPath = process.env.VUE_APP_BASE_API + avatar
        this.userCardData = { id, userName, avatar: avatarFullPath, roles }
        this.accountData = { id, phone, userName, birthday, gender }
      })
    }
  }
}
</script>
