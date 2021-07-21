<template>
  <div class="app-container">
    <div class="filter-container">

      <el-select v-model="listQuery.pageName" class="filter-item" clearable filterable placeholder="全部页面" @change="handleFilter">
        <el-option v-for="item in pageNameOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>

      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        新建
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
      <!-- <el-table-column label="用户名" prop="user_name" sortable="custom" align="center" width="160px" :class-name="getSortClass('user_name')">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.userName }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="页面" align="center" prop="t_page_name" sortable="custom" :class-name="getSortClass('t_page_name')">
        <template slot-scope="{row}">
          <span>{{ getPageTitle(row.pageName) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="动作类型" align="center">
        <template slot-scope="{row}">
          <span>{{ row.actionType | actionTypeFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="动作编号" align="center">
        <template slot-scope="{row}">
          <span>{{ row.actionNo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="名称" align="center">
        <template slot-scope="{row}">
          <span>{{ row.actionName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center">
        <template slot-scope="{row}">
          <span>{{ row.memo }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="角色" align="center">
        <template slot-scope="{row}">
          <span>{{ row.roles | roleFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" width="100px">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusClassFilter">
            {{ row.status | statusFilter }}
          </el-tag>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" width="230px" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <!-- <el-button v-if="row.status!='1'" size="mini" type="success" @click="handleModifyStatus(row,'1')">
            启用
          </el-button>
          <el-button v-if="row.status!='0'" size="mini" @click="handleModifyStatus(row,'0')">
            停用
          </el-button> -->
          <el-button size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="120px" style="margin-left:50px;margin-right:50px;">
        <el-form-item label="页面" prop="pageName">
          <el-select v-model="temp.pageName" class="filter-item" placeholder="请选择" :disabled="dialogStatus!=='create'">
            <el-option v-for="item in pageNameOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item label="动作类型" prop="actionType">
          <el-select v-model="temp.actionType" class="filter-item" placeholder="请选择" :disabled="dialogStatus!=='create'" @change="actionTypeChange">
            <el-option v-for="item in actionTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item v-show="temp.showActionNo" label="动作编号" prop="actionNo">
          <el-input v-model="temp.actionNo" :readonly="dialogStatus!=='create'" />
        </el-form-item>
        <el-form-item label="名称" prop="actionName">
          <el-input v-model="temp.actionName" />
        </el-form-item>
        <el-form-item label="备注" prop="memo">
          <el-input v-model="temp.memo" type="textarea" :row="4" />
        </el-form-item>
        <!-- <el-form-item label="生日" prop="birthday">
          <el-date-picker v-model="user.birthday" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="temp.status" class="filter-item" placeholder="请选择">
            <el-option v-for="item in userStatusOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item> -->
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
import { listSysFun, getSysFun, createSysFun, updateSysFun, deleteSysFun, listSysPage } from '@/api/super'
import waves from '@/directive/waves' // waves directive
// import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
// import { validPhone } from '@/utils/validate'
import { actionTypeOptions, actionTypeKeyValue } from '@/utils/options'

export default {
  name: 'Fun',
  components: { Pagination },
  directives: { waves },
  filters: {
    actionTypeFilter(actionType) {
      return actionTypeKeyValue[actionType]
    }
  },
  data() {
    const validateActionNo = (rule, value, callback) => {
      if (!/^[a-zA-Z0-9]{1,30}$/.test(value)) {
        callback(new Error('请填写正确格式的动作编号,动作编号由1~30位字母或数字组成。'))
      } else {
        callback()
      }
    }
    return {
      initCount: 0,
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        pageName: undefined,
        pageNum: 1,
        pageSize: 10,
        orderBy: 't_id',
        ascDesc: 'desc'
      },
      actionTypeOptions,
      pageNameOptions: [],
      pageNameKeyValue: {},
      // genderOptions,
      temp: {
        id: undefined,
        pageName: '',
        actionType: '',
        actionNo: '',
        actionName: '',
        memo: undefined,
        showActionNo: false
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '新建'
      },
      dialogPvVisible: false,
      rules: {
        pageName: [
          { required: true, message: '请选择页面。', trigger: 'blur' }
        ],
        actionType: [
          { required: true, message: '请选择动作类型。', trigger: 'blur' }
        ],
        actionNo: [
          { required: true, trigger: 'blur', validator: validateActionNo }
        ],
        actionName: [
          { required: true, message: '请填写名称。', trigger: 'blur' },
          { max: 50, message: '名称长度不能超过50字符。', trigger: 'blur' }
        ],
        memo: [
          { max: 500, message: '备注长度不能超过500字符。', trigger: 'blur' }
        ]
        // phone: [{ required: true, trigger: 'blur', validator: validatePhone }],
      },
      downloadLoading: false
    }
  },
  computed: {
    getPageTitle: function() {
      return function(pageName) {
        return this.pageNameKeyValue[pageName]
      }
    }
  },
  watch: {
    initCount() {
      if (this.initCount === 2) {
        this.getList()
      }
    }
  },
  beforeCreate() {
    listSysPage({ pageNum: -1 }).then(response => {
      const { dataList } = response
      dataList.forEach(ele => {
        const { pageName, pageTitle } = ele
        this.pageNameOptions.push({ key: pageName, display_name: pageTitle })
        this.pageNameKeyValue[pageName] = pageTitle
      })
      this.initCount++
    })
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listSysFun(this.listQuery).then(response => {
        const { pagination, dataList } = response
        this.list = dataList
        this.total = pagination.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    // handleModifyStatus(row, status) {
    //   const tempData = ((r) => ({ id: r.id, status: r.status }))(row)
    //   tempData.status = status
    //   updateSysFun(tempData).then(() => {
    //     this.$message({
    //       message: '更新成功',
    //       type: 'success',
    //       duration: 1000
    //     })
    //     row.status = status
    //   })
    // },
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
    actionTypeChange() {
      const { actionType } = this.temp
      if (actionType === 'e') {
        this.temp.showActionNo = true
        this.temp.actionNo = ''
      } else {
        this.temp.showActionNo = false
        this.temp.actionNo = actionType
      }
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        pageName: '',
        actionType: '',
        actionNo: '',
        actionName: '',
        memo: undefined,
        showActionNo: false
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
          createSysFun(this.temp).then(() => {
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
      getSysFun(id).then(response => {
        // this.temp = Object.assign({}, response) // copy obj
        this.temp = ((r) => ({
          id: r.id,
          pageName: r.pageName,
          actionType: r.actionType,
          actionNo: r.actionNo,
          actionName: r.actionName,
          memo: r.memo,
          showActionNo: r.actionType === 'e'
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
          updateSysFun(tempData).then(() => {
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
      deleteSysFun(id).then(() => {
        this.$message({
          message: '删除成功。',
          type: 'success',
          duration: 1000
        })
        this.getList()
      })
    },
    // handleDownload() {
    //   this.downloadLoading = true
    //   import('@/vendor/Export2Excel').then(excel => {
    //     const tHeader = ['phone', 'userName', 'roles', 'status']
    //     const filterVal = ['phone', 'userName', 'roles', 'status']
    //     const data = this.formatJson(filterVal)
    //     excel.export_json_to_excel({
    //       header: tHeader,
    //       data,
    //       filename: 'table-list'
    //     })
    //     this.downloadLoading = false
    //   })
    // },
    // formatJson(filterVal) {
    //   return this.list.map(v => filterVal.map(j => {
    //     return v[j]
    //     // if (j === 'timestamp') {
    //     //   return parseTime(v[j])
    //     // } else {
    //     //   return v[j]
    //     // }
    //   }))
    // },
    getSortClass: function(key) {
      const { orderBy, ascDesc } = this.listQuery
      if (orderBy === key) {
        return ascDesc === 'asc' ? 'ascending' : 'descending'
      }
      return ''
    }
  }
}
</script>
