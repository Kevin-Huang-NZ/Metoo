<template>
  <div class="app-container">
    <div class="filter-container">
      <el-alert type="info" :closable="false">
        您当前正在为<el-tag>{{ currentSysRole.roleName }} - {{ currentSysRole.roleNo }}</el-tag>配置权限，勾选功能后当前角色能够访问目标功能，取消勾选后不能访问目标功能。
      </el-alert>
    </div>
    <div class="filter-container">

      <el-select v-model="listQuery.pageName" class="filter-item" clearable filterable placeholder="全部页面" @change="handleFilter">
        <el-option v-for="item in pageNameOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>

      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
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
      <el-table-column label="勾选功能" align="center" width="120px">
        <template slot-scope="{row}">
          <el-checkbox v-model="row.isSelected" @change="handleSelectionChange(row.funNo)" />
        </template>
      </el-table-column>
      <el-table-column label="ID" align="center" width="80px">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="页面" prop="t_page_name" sortable="custom" align="center" :class-name="getSortClass('t_page_name')">
        <template slot-scope="{row}">
          <span>{{ getPageTitle(row.pageName) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="功能名称" align="center">
        <template slot-scope="{row}">
          <span>{{ row.actionName }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="用户名" prop="user_name" sortable="custom" align="center" width="160px" :class-name="getSortClass('user_name')">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.userName }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="备注" align="center">
        <template slot-scope="{row}">
          <span>{{ row.memo }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getList" />

  </div>
</template>

<script>
import { listSysFun, listSysPage, listSysRoleFun, createSysRoleFun, deleteSysRoleFun, getSysRoleByNo } from '@/api/super'
import waves from '@/directive/waves' // waves directive
// import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
// import { validPhone } from '@/utils/validate'
// import { actionTypeOptions, actionTypeKeyValue } from '@/utils/options'

export default {
  name: 'ChooseFun',
  components: { Pagination },
  directives: { waves },
  data() {
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
        orderBy: 't_page_name',
        ascDesc: 'asc'
      },
      // actionTypeOptions,
      pageNameOptions: [],
      pageNameKeyValue: {},
      selectedFunNos: [],
      currentSysRole: {
        roleNo: '',
        roleName: '',
        memo: ''
      }
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
      if (this.initCount === 3) {
        this.getList()
      }
    }
  },
  beforeCreate() {
    getSysRoleByNo(this.$route.params.roleNo).then(response => {
      this.currentSysRole = ((r) => ({
        roleNo: r.roleNo,
        roleName: r.roleName,
        memo: r.memo
      }))(response)
      this.initCount++
    })
    listSysPage({ pageNum: -1 }).then(response => {
      const { dataList } = response
      dataList.forEach(ele => {
        const { pageName, pageTitle } = ele
        this.pageNameOptions.push({ key: pageName, display_name: pageTitle })
        this.pageNameKeyValue[pageName] = pageTitle
      })
      this.initCount++
    })
    listSysRoleFun(this.$route.params.roleNo).then(response => {
      const { dataList } = response
      dataList.forEach(ele => {
        const { funNo } = ele
        this.selectedFunNos.push(funNo)
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
        this.list = dataList.map(d => Object.assign({ isSelected: this.selectedFunNos.includes(d.funNo) }, d))
        this.total = pagination.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    handleSelectionChange(funNo) {
      const index = this.selectedFunNos.findIndex((f) => f === funNo)
      if (index === -1) {
        createSysRoleFun({ roleNo: this.currentSysRole.roleNo, funNo: funNo }).then(() => {
        })
        this.selectedFunNos.push(funNo)
      } else {
        deleteSysRoleFun({ roleNo: this.currentSysRole.roleNo, funNo: funNo }).then(() => {
        })
        this.selectedFunNos.splice(index, 1)
      }
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
