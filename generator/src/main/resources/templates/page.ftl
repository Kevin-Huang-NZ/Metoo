<template>
  <div class="app-container">
    <div class="filter-container">
      <!--
      <el-select v-model="listQuery.projectId" class="filter-item" style="width:400px;" filterable placeholder="请选择项目">
        <el-option v-for="item in projectOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>
      <el-select v-model="listQuery.taskId" class="filter-item" style="width:200px;" clearable filterable placeholder="全部任务" @change="handleFilter">
        <el-option v-for="item in projectTaskOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>
      <el-input v-model="listQuery.original" placeholder="" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-date-picker v-model="listQuery.startDate" type="date" placeholder="选择开始日期"></el-date-picker>
      <el-date-picker v-model="listQuery.endDate" type="date" placeholder="选择结束日期"></el-date-picker>
      -->
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        新建
      </el-button>
      <!--
      <el-button class="filter-item" style="margin-left: 10px;" type="info" icon="el-icon-download" @click="handleDownload">
        导出Excel
      </el-button>
      <el-popover
        ref="popoverImport"
        placement="bottom"
        title="导入Excel功能"
        width="300"
        trigger="hover"
        content="选择任务后，可以执行导入Excel，从excel导入的外文地名会保存在当前任务下。"
      />
      <span v-popover:popoverImport>
        <el-upload
          :disabled="!listQuery.taskId"
          class="import-excel"
          action=""
          :auto-upload="true"
          :http-request="importExcel"
          :show-file-list="false"
          :before-upload="beforeImportExcel"
        >
          <el-button :disabled="!listQuery.taskId" class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-upload2">
            导入Excel
          </el-button>
        </el-upload>
      </span>
      <el-popover
        ref="popoverClear"
        placement="bottom"
        title="清空功能"
        width="300"
        trigger="hover"
        content="选择任务后，可以执行清空，系统会清空该任务下的所有外文地名。"
      />
      <span v-popover:popoverClear>
        <el-button :disabled="!listQuery.taskId" class="filter-item" style="margin-left: 10px;" type="danger" icon="el-icon-delete" @click="handleClear">
          清空
        </el-button>
      </span>
      -->
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
      <#list fields as field>
      <el-table-column label="[=field.columnTitle]" align="center">
        <template slot-scope="{row}">
          <span>{{ row.[=field.lcColumnName] }}</span>
        </template>
      </el-table-column>
      </#list>
      <!--
      <el-table-column label="ID" align="center" width="80px">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名" align="center" prop="t_user_name" sortable="custom" :class-name="getSortClass('t_user_name')">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="角色" align="center">
        <template slot-scope="{row}">
          <span>{{ row.roles | roleFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="专家" align="center">
        <template slot-scope="{row}">
          <span>{{ getUserName(row.userId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" width="100px">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusClassFilter">
            {{ row.status | statusFilter }}
          </el-tag>
        </template>
      </el-table-column>
      -->
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
        <#list fields as field>
        <el-form-item label="[=field.columnTitle]" prop="[=field.lcColumnName]">
          <el-input v-model="temp.[=field.lcColumnName]" />
        </el-form-item>
        </#list>
        <!--
        <el-form-item label="生日" prop="birthday">
          <el-date-picker v-model="user.birthday" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="temp.status" class="filter-item" placeholder="请选择">
            <el-option v-for="item in userStatusOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
        -->
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
import { list[=ucTableName], get[=ucTableName], create[=ucTableName], update[=ucTableName], delete[=ucTableName] } from '@/api/[=lowerTableName]'
import waves from '@/directive/waves' // waves directive
// import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
// import { validPhone } from '@/utils/validate'
// import { userStatusOptions, userStatusKeyValue } from '@/utils/options'

export default {
  name: '[=ucTableName]',
  components: { Pagination },
  directives: { waves },
  // filters: {
  //   statusFilter(status) {
  //     return userStatusKeyValue[status]
  //   },
  //   statusClassFilter(status) {
  //     const statusClassMap = {
  //       '0': 'danger',
  //       '1': 'success'
  //     }
  //     return statusClassMap[status]
  //   }
  // },
  data() {
    // const validatePhone = (rule, value, callback) => {
    //   if (!validPhone(value)) {
    //     callback(new Error('请输入正确的手机号码'))
    //   } else {
    //     callback()
    //   }
    // }
    return {
      // initCount: 0,
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        // projectId: undefined,
        // taskId: undefined,
        // original: '',
        // startDate: '',
        // endDate: '',
        pageNum: 1,
        pageSize: 10,
        orderBy: 't_id',
        ascDesc: 'desc'
      },
      // projectOptions: [],
      // taskOptions: [],
      // taskKeyValue: {},
      temp: {
        id: undefined,
        <#list fields as field>
          <#if field.isNullable == 'YES' >
        [=field.lcColumnName]: undefined<#sep>,</#sep>
          <#else>
            <#if field.maxLen?has_content >
        [=field.lcColumnName]: ''<#sep>,</#sep>
            <#else>
        [=field.lcColumnName]: undefined<#sep>,</#sep>
            </#if>
          </#if>
        </#list>
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '新建'
      },
      dialogPvVisible: false,
      rules: {
        <#list fields as field>
        [=field.lcColumnName]: [
          <#if field.isNullable == 'NO' >
          { required: true, message: '请填写[=field.columnTitle]。', trigger: 'blur' }<#if field.maxLen?has_content >,</#if>
          </#if>
          <#if field.maxLen?has_content >
          { max: [=field.maxLen], message: '[=field.columnTitle]长度不能超过[=field.maxLen]字符。', trigger: 'blur' }
          </#if>
        ]<#sep>,</#sep>
        </#list>
        // phone: [{ required: true, trigger: 'blur', validator: validatePhone }],
      },
      downloadLoading: false
    }
  },
  // computed: {
  //   getTaskName: function() {
  //     return function(taskId) {
  //       return this.taskKeyValue[taskId]
  //     }
  //   }
  // },
  // watch: {
  //   taskOptions(newVal, oldVal) {
  //     const { projectId } = this.listQuery
  //     if (typeof projectId !== 'undefined') {
  //       this.projectTaskOptions = this.taskOptions.filter(t => t.projectId === projectId)
  //     }
  //   },
  //   'listQuery.projectId'(newVal, oldVal) {
  //     if (this.taskOptions.length > 0) {
  //       this.projectTaskOptions = this.taskOptions.filter(t => t.projectId === newVal)
  //     }
  //   },
  //   initCount() {
  //     if (this.initCount === 1) {
  //       this.getList()
  //     }
  //   }
  // },
  // beforeCreate() {
  //   listProject({ pageNum: -1, orderBy: 't_status', ascDesc: 'asc' }).then(response => {
  //     const { dataList } = response
  //     dataList.forEach((ele, index) => {
  //       const { id, projectName } = ele
  //       this.projectOptions.push({ key: id, display_name: projectName })
  //       if (index === 0) {
  //         this.listQuery.projectId = id
  //       }
  //     })
  //     // if (typeof this.$route.params.projectId !== 'undefined') {
  //     //   this.listQuery.projectId = parseInt(this.$route.params.projectId)
  //     // }
  //     this.initCount++
  //   })
  //   listTask({ pageNum: -1 }).then(response => {
  //     const { dataList } = response
  //     dataList.forEach((ele, index) => {
  //       const { id, taskName, projectId } = ele
  //       this.taskOptions.push({ key: id, display_name: taskName, projectId: projectId })
  //       this.taskKeyValue[id] = taskName
  //     })
  //   })
  // },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      list[=ucTableName](this.listQuery).then(response => {
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
    //   update[=ucTableName](tempData).then(() => {
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
    resetTemp() {
      this.temp = {
        id: undefined,
        <#list fields as field>
          <#if field.isNullable == 'YES' >
        [=field.lcColumnName]: undefined<#sep>,</#sep>
          <#else>
            <#if field.maxLen?has_content >
        [=field.lcColumnName]: ''<#sep>,</#sep>
            <#else>
        [=field.lcColumnName]: undefined<#sep>,</#sep>
            </#if>
          </#if>
        </#list>
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
          create[=ucTableName](this.temp).then(() => {
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
      get[=ucTableName](id).then(response => {
        // this.temp = Object.assign({}, response) // copy obj
        this.temp = ((r) => ({
          id: r.id,
          <#list fields as field>
          [=field.lcColumnName]: r.[=field.lcColumnName]<#sep>,</#sep>
          </#list>
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
          update[=ucTableName](tempData).then(() => {
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
      delete[=ucTableName](id).then(() => {
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

    //   const tempData = {
    //     projectId: this.listQuery.projectId,
    //     pageNum: 1,
    //     pageSize: 99999,
    //     orderBy: 't_task_id asc, t_original',
    //     ascDesc: 'asc'
    //   }
    //   list[=ucTableName](tempData).then(response => {
    //     const { dataList } = response
    //     this.allList = dataList
    //     import('@/vendor/Export2Excel').then(excel => {
    ////         { v: '备注', c: '外文地名、国别、语种、地理实体类别、备注5列，是导入数据列。其他列在导入时不读取。' },
    //       const tHeader = [
               <#list fields as field>
    //         '[=field.columnTitle]'<#sep>, </#sep>
               </#list>
    //         ]
    //       const filterVal = [
               <#list fields as field>
    //         '[=field.lcColumnName]'<#sep>, </#sep>
               </#list>
    //         ]
    //       const data = this.formatJson(filterVal)
    //       excel.export_json_to_excel({
    //         header: tHeader,
    //         data,
    //         filename: '[=lhTableName]'
    //       })
    //       this.allList = null
    //       this.downloadLoading = false
    //     })
    //   })
    // },
    // formatJson(filterVal) {
    //   return this.allList.map(v => filterVal.map(j => {
    //     if (j === 'romanStatusName') {
    //       return romanStatusKeyValue[v['romanStatus']]
    //     } else if (j === 'transStatusName') {
    //       return transStatusKeyValue[v['transStatus']]
    //     } else if (j === 'checkStatusName') {
    //       return checkStatusKeyValue[v['checkStatus']]
    //     } else {
    //       return v[j]
    //     }
    //   }))
    // },
    // beforeImportExcel(file) {
    //   const isExcel = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    //   const isLt10M = file.size / 1024 / 1024 / 10
    //   if (!isExcel) {
    //     this.$message.error('上传文件格式不正确，请使用 导出 的excel编辑数据并上传!')
    //   }
    //   if (!isLt10M) {
    //     this.$message.error('上传的excel文件大小不能超过 10MB!')
    //   }
    //   return isExcel && isLt10M
    // },
    // importExcel(res) {
    //   const { projectId, taskId } = this.listQuery
    //   // if (!taskId) {
    //   //   this.$alert('导入数据时，请先选择任务。', '提示', {
    //   //     type: 'error'
    //   //   })
    //   // } else {
    //   // Create new formData object
    //   const fd = new FormData()
    //   // append the file you want to upload
    //   fd.append('file', res.file)
    //   // add other data to the form data object if needed
    //   fd.append('projectId', projectId)
    //   fd.append('taskId', taskId)
    //   import[=ucTableName](fd).then((response) => {
    //     this.$message({
    //       message: '导入成功。',
    //       type: 'success',
    //       duration: 1000
    //     })
    //     this.getList()
    //   })
    //   // }
    // },
    // handleClear() {
    //   const { projectId, taskId } = this.listQuery
    //   // if (!taskId) {
    //   //   this.$alert('清空数据时，请先选择任务。', '提示', {
    //   //     type: 'error'
    //   //   })
    //   // } else {
    //   this.$confirm('此操作将清空当前任务的所有数据, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     const tempData = {
    //       projectId: projectId,
    //       taskId: taskId
    //     }
    //     clear[=ucTableName](tempData).then(() => {
    //       this.$message({
    //         message: '清空成功。',
    //         type: 'success',
    //         duration: 1000
    //       })
    //       this.list = null
    //     })
    //   }).catch(() => {
    //   })
    //   // }
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
