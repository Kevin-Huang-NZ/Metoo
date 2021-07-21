<template>
  <el-form ref="changeInfo" :rules="rules" :model="user" label-position="top">
    <el-form-item label="姓名" prop="userName">
      <el-input v-model.trim="user.userName" />
    </el-form-item>
    <el-form-item label="手机号" prop="phone">
      <el-input v-model.trim="user.phone" />
    </el-form-item>
    <el-form-item label="生日" prop="birthday">
      <el-date-picker v-model="user.birthday" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="" />
    </el-form-item>
    <el-form-item label="性别" prop="gender">
      <el-select v-model="user.gender" class="filter-item" placeholder="">
        <el-option v-for="item in genderOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">更新</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { validPhone } from '@/utils/validate'
import { genderOptions } from '@/utils/options'
import { updateUser } from '@/api/sys'

export default {
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          id: undefined,
          userName: '',
          phone: '',
          birthday: '',
          gender: ''
        }
      }
    }
  },
  data() {
    const validatePhone = (rule, value, callback) => {
      if (!validPhone(value)) {
        callback(new Error('请输入正确的手机号码'))
      } else {
        callback()
      }
    }

    const validateBirthday = (rule, value, callback) => {
      if (this.$moment(value, 'yyyy-MM-dd').isAfter(this.$moment())) {
        callback(new Error('出生日期不能晚于当前日期。'))
      } else {
        callback()
      }
    }

    return {
      genderOptions,
      rules: {
        phone: [{ required: true, trigger: 'blur', validator: validatePhone }],
        userName: [
          { required: true, message: '请填写用户名。', trigger: 'blur' },
          { max: 50, message: '用户名长度不能超过50字符。', trigger: 'blur' }
        ],
        birthday: [{ trigger: 'blur', validator: validateBirthday }]
      }
    }
  },
  methods: {
    submit() {
      this.$refs['changeInfo'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.user)
          updateUser(tempData).then(() => {
            this.$message({
              message: '修改成功。',
              type: 'success',
              duration: 1000
            })
          })
        }
      })
    }
  }
}
</script>
