<template>
  <el-form ref="changePasswordForm" :rules="rules" :model="temp" label-position="top">

    <el-form-item label="原密码" prop="oldPwd">
      <el-input v-model="temp.oldPwd" show-password />
    </el-form-item>
    <el-form-item label="新密码" prop="newPwd">
      <el-input v-model="temp.newPwd" show-password />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submit">确认修改</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { validPassword } from '@/utils/validate'
import { changePassword } from '@/api/user'

const validatePassword = (rule, value, callback) => {
  if (!validPassword(value)) {
    callback(new Error('密码必须包含大、小写字母和数组，长度为8到16位。'))
  } else {
    callback()
  }
}

export default {
  // props: {
  //   user: {
  //     type: Object,
  //     default: () => {
  //       return {
  //         newPwd: '',
  //         oldPwd: ''
  //       }
  //     }
  //   }
  // },
  data() {
    return {
      temp: {
        newPwd: '',
        oldPwd: ''
      },
      rules: {
        newPwd: [{ required: true, trigger: 'blur', validator: validatePassword }],
        oldPwd: [{ required: true, message: '请输入原密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    resetUser() {
      this.temp = {
        newPwd: '',
        oldPwd: ''
      }
    },
    submit() {
      this.$refs['changePasswordForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          // tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          changePassword(tempData).then(() => {
            this.$message({
              message: '密码修改成功。',
              type: 'success',
              duration: 1000
            })
            this.resetUser()
          })
        }
      })
    }
  }
}
</script>
