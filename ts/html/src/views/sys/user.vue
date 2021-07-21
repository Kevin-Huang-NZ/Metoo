<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.phone" placeholder="手机号" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.userName" placeholder="用户名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        新建
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        导出Excel
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" align="center" width="80px">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center" width="160px" prop="t_phone" sortable="custom" :class-name="getSortClass('t_phone')">
        <template slot-scope="{row}">
          <span>{{ row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" align="center" prop="t_user_name" sortable="custom" :class-name="getSortClass('t_user_name')">
        <template slot-scope="{row}">
          <span>{{ row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="角色" align="center">
        <template slot-scope="{row}">
          <span>{{ row.roles | roleFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="100px" prop="t_status" sortable="custom" :class-name="getSortClass('t_status')">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusClassFilter">
            {{ row.status | statusFilter }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230px" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button v-if="row.status!='1'" size="mini" type="success" @click="handleModifyStatus(row,'1')">
            启用
          </el-button>
          <el-button v-if="row.status!='0'" size="mini" @click="handleModifyStatus(row,'0')">
            停用
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="120px" style="margin-left:50px;margin-right:50px;">
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="temp.phone" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="temp.password" show-password />
        </el-form-item>
        <el-form-item label="姓名" prop="userName">
          <el-input v-model="temp.userName" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="temp.roles" class="filter-item" placeholder="请选择">
            <el-option v-for="item in roleOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="temp.status" class="filter-item" placeholder="请选择">
            <el-option v-for="item in userStatusOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确认
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, createUser, updateUser, deleteUser } from '@/api/sys'
import waves from '@/directive/waves' // waves directive
// import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { validPhone, validPassword } from '@/utils/validate'
import { roleOptions, genderOptions, userStatusOptions, roleKeyValue, genderKeyValue, userStatusKeyValue } from '@/utils/options'

export default {
  name: 'User',
  components: { Pagination },
  directives: { waves },
  filters: {
    roleFilter(role) {
      return roleKeyValue[role]
    },
    statusFilter(status) {
      return userStatusKeyValue[status]
    },
    genderFilter(gender) {
      return genderKeyValue[gender]
    },
    statusClassFilter(status) {
      const statusClassMap = {
        '0': 'danger',
        '1': 'success'
      }
      return statusClassMap[status]
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
    const validatePassword = (rule, value, callback) => {
      if (!validPassword(value)) {
        callback(new Error('密码必须包含大、小写字母和数组，长度为8到16位。'))
      } else {
        callback()
      }
    }
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        userName: undefined,
        phone: undefined,
        pageNum: 1,
        pageSize: 10,
        orderBy: 't_id',
        ascDesc: 'desc'
      },
      roleOptions,
      userStatusOptions,
      genderOptions,
      // showReviewer: false,
      temp: {
        id: undefined,
        password: '',
        phone: '',
        userName: '',
        roles: '',
        status: '1'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '新建'
      },
      dialogPvVisible: false,
      rules: {
        phone: [{ required: true, trigger: 'blur', validator: validatePhone }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }],
        userName: [
          { required: true, message: '请填写用户名。', trigger: 'blur' },
          { max: 50, message: '用户名长度不能超过50字符。', trigger: 'blur' }
        ],
        roles: [{ required: true, message: '请选择用户角色。', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listUser(this.listQuery).then(response => {
        const { pagination, dataList } = response
        this.list = dataList
        this.total = pagination.total
        this.listLoading = false
        // Just to simulate the time of the request
        // setTimeout(() => {
        //   this.listLoading = false
        // }, 1.5 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    handleModifyStatus(row, status) {
      const tempData = ((r) => ({ id: r.id, status: r.status }))(row)
      tempData.status = status
      updateUser(tempData).then(() => {
        this.$message({
          message: '更新成功',
          type: 'success',
          duration: 1000
        })
        row.status = status
      })
    },
    sortChange(data) {
      const { prop, order } = data

      if (order === 'ascending') {
        this.listQuery.ascDesc = 'asc'
      } else {
        this.listQuery.ascDesc = 'desc'
      }
      this.listQuery.orderBy = prop
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        phone: '',
        password: '',
        userName: '',
        roles: '',
        status: '1'
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          createUser(this.temp).then(() => {
            this.dialogFormVisible = false
            this.$message({
              message: '新建成功。',
              type: 'success',
              duration: 1000
            })
            this.getList()
          })
        }
      })
    },
    handleUpdate(row) {
      const { id } = row
      getUser(id).then(response => {
        // this.temp = Object.assign({}, response) // copy obj
        this.temp = ((r) => ({
          id: r.id,
          phone: r.phone,
          password: r.password,
          userName: r.userName,
          roles: r.roles,
          status: r.status
        }))(response)
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          updateUser(tempData).then(() => {
            this.dialogFormVisible = false
            this.$message({
              message: '更新成功。',
              type: 'success',
              duration: 1000
            })

            this.getList()
          })
        }
      })
    },
    handleDelete(row, index) {
      const { id } = row
      deleteUser(id).then(() => {
        this.$message({
          message: '删除成功。',
          type: 'success',
          duration: 1000
        })
        this.getList()
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['phone', 'userName', 'roles', 'status']
        const filterVal = ['phone', 'userName', 'roles', 'status']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        return v[j]
        // if (j === 'timestamp') {
        //   return parseTime(v[j])
        // } else {
        //   return v[j]
        // }
      }))
    },
    getSortClass: function(key) {
      const { orderBy, ascDesc } = this.listQuery
      if (orderBy === key) {
        // if (key === 't_status') {
        //   return ascDesc === 'asc' ? 'ascending, status-col' : 'descending, status-col'
        // } else {
        return ascDesc === 'asc' ? 'ascending' : 'descending'
        // }
      }
      // else {
      //   if (key === 't_status') {
      //     return 'status-col'
      //   }
      // }
      return ''
    }
  }
}
</script>
