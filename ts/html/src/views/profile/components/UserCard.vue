<template>
  <el-card style="margin-bottom:20px;">
    <div slot="header" class="clearfix">
      <span>个人信息</span>
    </div>

    <div class="user-profile">
      <div class="box-center">
        <pan-thumb :image="user.avatar" :height="'100px'" :width="'100px'" :hoverable="false">
          <el-upload
            class="avatar-uploader"
            action=""
            :auto-upload="true"
            :http-request="uploadAvatar"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
          >
            <i class="el-icon-upload2 avatar-uploader-icon" />
          </el-upload>
        </pan-thumb>
      </div>
      <div class="box-center">
        <div class="user-name text-center">{{ user.userName }}</div>
        <div class="user-role text-center text-muted">{{ user.roles | roleFilter }}</div>
      </div>
    </div>

    <!-- <div class="user-bio">
      <div class="user-education user-bio-section">
        <div class="user-bio-section-header"><svg-icon icon-class="education" /><span>Education</span></div>
        <div class="user-bio-section-body">
          <div class="text-muted">
            JS in Computer Science from the University of Technology
          </div>
        </div>
      </div>

      <div class="user-skills user-bio-section">
        <div class="user-bio-section-header"><svg-icon icon-class="skill" /><span>Skills</span></div>
        <div class="user-bio-section-body">
          <div class="progress-item">
            <span>Vue</span>
            <el-progress :percentage="70" />
          </div>
          <div class="progress-item">
            <span>JavaScript</span>
            <el-progress :percentage="18" />
          </div>
          <div class="progress-item">
            <span>Css</span>
            <el-progress :percentage="12" />
          </div>
          <div class="progress-item">
            <span>ESLint</span>
            <el-progress :percentage="100" status="success" />
          </div>
        </div>
      </div>
    </div> -->
  </el-card>
</template>

<script>
import PanThumb from '@/components/PanThumb'
import store from '@/store'
import { updateUser, uploadFile } from '@/api/sys'
import { roleKeyValue } from '@/utils/options'

export default {
  components: { PanThumb },
  filters: {
    roleFilter(role) {
      return roleKeyValue[role]
    }
  },
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          id: undefined,
          userName: '',
          avatar: '',
          roles: ''
        }
      }
    }
  },
  mounted() {
    // console.log(this.user)
  },
  methods: {
    beforeAvatarUpload(file) {
      // const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      // if (!isJPG) {
      //   this.$message.error('上传头像图片只能是 JPG 格式!')
      // }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      // return isJPG && isLt2M
      return isLt2M
    },
    uploadAvatar(res) {
      // Create new formData object
      const fd = new FormData()
      // append the file you want to upload
      fd.append('file', res.file)
      // add other data to the form data object if needed
      fd.append('tableName', 'user')
      fd.append('rowId', this.user.id)
      uploadFile(fd).then((response) => {
        // console.log(response)
        const { fullPath } = response
        const avatar = process.env.VUE_APP_BASE_API + fullPath
        this.user.avatar = avatar
        store.commit('user/SET_AVATAR', avatar)

        const tempData = { id: this.user.id, avatar: fullPath }
        updateUser(tempData)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.box-center {
  margin: 0 auto;
  display: table;
}

.text-muted {
  color: #777;
}

.user-profile {
  .user-name {
    font-weight: bold;
  }

  .box-center {
    padding-top: 10px;
  }

  .user-role {
    padding-top: 10px;
    font-weight: 400;
    font-size: 14px;
  }

  .box-social {
    padding-top: 30px;

    .el-table {
      border-top: 1px solid #dfe6ec;
    }
  }

  .user-follow {
    padding-top: 20px;
  }
}

.user-bio {
  margin-top: 20px;
  color: #606266;

  span {
    padding-left: 4px;
  }

  .user-bio-section {
    font-size: 14px;
    padding: 15px 0;

    .user-bio-section-header {
      border-bottom: 1px solid #dfe6ec;
      padding-bottom: 10px;
      margin-bottom: 10px;
      font-weight: bold;
    }
  }
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 50px;
  height: 50px;
  line-height: 50px;
  text-align: center;
}
</style>
