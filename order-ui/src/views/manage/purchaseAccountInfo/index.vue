<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号类型" prop="accountType">
        <el-select v-model="queryParams.accountType" placeholder="请选择账号类型" clearable>
          <el-option
            v-for="dict in dict.type.o_purchase_channel_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="deptId" style="width: 25%">
        <el-row :gutter="24">
          <el-col :span="6">
            <span style=" font-weight: bold;color: rgb(96, 98, 102)">采购渠道</span>
          </el-col>
          <el-col :span="18">
            <treeselect v-model="queryParams.purchaseChannelsId" :options="purchaseChannelInfoOptions"
                        :normalizer="normalizerChannels" placeholder="请选择渠道"
            />
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item label="采购账号" prop="purchaseAccount">
        <el-input
          v-model="queryParams.purchaseAccount"
          placeholder="请输入采购账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客服" prop="userId">
        <el-select
          v-model="queryParams.userId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入用户账号"
          :remote-method="selectUserInfoList"
          :loading="userLoading"
        >
          <el-option
            v-for="item in userInfoList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="" prop="deptId" style="width: 25%">
        <el-row :gutter="24">
          <el-col :span="6">
            <span style=" font-weight: bold;color: rgb(96, 98, 102)">所属部门</span>
          </el-col>
          <el-col :span="18">
            <treeselect v-model="queryParams.deptId"
                        :options="deptOptions" :show-count="true" :normalizer="normalizer" placeholder="请选择所属部门"

            />
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:purchaseAccountInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:purchaseAccountInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:purchaseAccountInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:purchaseAccountInfo:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['manage:purchaseAccountInfo:import']"
        >导入
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseAccountInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="名称" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="nickName"
      />
      <el-table-column label="账号类型" align="center" v-if="columns[2].visible" prop="accountType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_purchase_channel_type" :value="scope.row.accountType"/>
        </template>
      </el-table-column>
      <el-table-column label="采购渠道" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="purchaseChannelsName"
      />
      <el-table-column label="采购账号" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="purchaseAccount"
      />
      <el-table-column label="客服" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="userName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[6].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[8].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="remark"
      />
      <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="deptName"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:purchaseAccountInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:purchaseAccountInfo:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改采购账号信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="账号类型" prop="accountType">
          <el-select v-model="form.accountType" @change="accountTypeSelectChange"
                     placeholder="请选择账号类型"
          >
            <el-option
              v-for="dict in dict.type.o_purchase_channel_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采购渠道" prop="purchaseChannelsId">
          <treeselect v-model="form.purchaseChannelsId" :options="purchaseChannelInfoOptions"
                      :normalizer="normalizerChannels" placeholder="请选择渠道"
          />
        </el-form-item>
        <el-form-item label="采购账号" prop="purchaseAccount">
          <el-input v-model="form.purchaseAccount" placeholder="请输入采购账号"/>
        </el-form-item>
        <el-form-item label="客服" prop="userId">
          <el-select
            v-model="form.userId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入用户账号"
            :remote-method="selectUserInfoList"
            :loading="userLoading"
          >
            <el-option
              v-for="item in userInfoList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 采购账号导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listPurchaseAccountInfo,
  getPurchaseAccountInfo,
  delPurchaseAccountInfo,
  addPurchaseAccountInfo,
  updatePurchaseAccountInfo
} from '@/api/manage/purchaseAccountInfo'
import { allocatedUserList, myAllocatedUserList } from '@/api/system/role'
import { listDept } from '@/api/system/dept'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { getToken } from '@/utils/auth'
import { listPurchaseChannelInfo } from '@/api/manage/purchaseChannelInfo'
import { currentMonth, pickerOptions } from '@/constants/datetime'

export default {
  name: 'PurchaseAccountInfo',
  computed: {
    pickerOptions() {
      return pickerOptions
    }
  },
  components: { Treeselect },
  dicts: ['o_purchase_channel_type'],
  data() {
    return {
      //部门相关信息
      deptOptions: [],
      //采购渠道相关信息
      purchaseChannelInfoOptions: [],
      purchaseChannelQuery: {
        channelType: null
      },
      //用户相关信息
      userInfoList: [],
      userLoading: false,
      userQueryParams: {
        userName: '',
        roleId: 102,
        pageNum: 1,
        pageSize: 100
      },
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: false },
        { key: 1, label: '名称', visible: true },
        { key: 2, label: '账号类型', visible: true },
        { key: 3, label: '采购渠道', visible: true },
        { key: 4, label: '采购账号', visible: true },
        { key: 5, label: '客服', visible: true },
        { key: 6, label: '创建时间', visible: false },
        { key: 7, label: '更新人', visible: false },
        { key: 8, label: '更新时间', visible: false },
        { key: 9, label: '备注', visible: false },
        { key: 10, label: '部门', visible: true }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 采购账号信息表格数据
      purchaseAccountInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 部门时间范围
      daterangeCreateTime: (() => currentMonth())(),
      // 部门时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nickName: null,
        purchaseAccount: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        deptId: null,
        purchaseChannelsId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nickName: [
          { required: true, message: '名称不能为空', trigger: 'blur' }
        ],
        purchaseAccount: [
          { required: true, message: '采购账号不能为空', trigger: 'blur' }
        ],
        purchaseChannelsId: [
          { required: true, message: '采购渠道不能为空', trigger: 'blur' }
        ],
        accountType: [
          { required: true, message: '账号类型不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '客服不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ]
      },
      // 采购账号导入参数
      upload:
        {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title:
            '',
          // 是否禁用上传
          isUploading:
            false,
          // 设置上传的请求头部
          headers:
            {
              Authorization: 'Bearer ' + getToken()
            }
          ,
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + '/manage/purchaseAccountInfo/importData'
        }
    }
  }
  ,
  created() {
    this.getList()
    this.getDeptList()
    this.getUserInfoList()
    this.getChannelsTreeselect()
  }
  ,
  methods: {
    accountTypeSelectChange() {
      this.purchaseChannelQuery.channelType = this.form.accountType
      this.form.purchaseChannelsId = null
      this.getChannelsTreeselect()
    }
    ,
    /** 转换采购渠道信息数据结构 */
    normalizerChannels(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.id,
        label: node.channelName,
        children: node.children
      }
    }
    ,
    /** 查询采购渠道信息下拉树结构 */
    getChannelsTreeselect() {
      listPurchaseChannelInfo(this.purchaseChannelQuery).then(response => {
        this.purchaseChannelInfoOptions = []
        const data = { id: 0, channelName: '顶级节点', children: [] }
        data.children = this.handleTree(response.data, 'id', 'parentId')
        this.purchaseChannelInfoOptions.push(data)
      })
    }
    ,
    /** 查询部门列表 */
    getDeptList() {
      listDept().then(response => {
        this.deptOptions = this.handleTree(response.data, 'deptId')
      })
    }
    ,
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      }
    }
    ,
    /**
     * 获取主管用户列表推荐
     * @param query
     */
    selectUserInfoList(query) {
      if (query !== '') {
        this.userLoading = true
        this.userQueryParams.userName = query
        setTimeout(() => {
          this.getUserInfoList()
        }, 200)
      } else {
        this.userInfoList = []
        this.userQueryParams.userName = null
      }
    }
    ,
    /**
     * 获取主管用户信息列表
     */
    getUserInfoList() {
      //添加查询参数
      if (this.form.userId != null) {
        this.userQueryParams.userId = this.form.userId
      } else {
        this.userQueryParams.userId = null
      }
      if (this.userQueryParams.userName !== '') {
        this.userQueryParams.userId = null
      }
      allocatedUserList(this.userQueryParams).then(res => {
        this.userInfoList = res?.rows
        this.userLoading = false
      })
    }
    ,
    /** 查询采购账号信息列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      listPurchaseAccountInfo(this.queryParams).then(response => {
        this.purchaseAccountInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
    }
    ,
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    }
    ,
    // 表单重置
    reset() {
      this.form = {
        id: null,
        nickName: null,
        purchaseAccount: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        deptId: null,
        accountType: null,
        purchaseChannelsId: null
      }
      this.resetForm('form')
    }
    ,
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    }
    ,
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = []
      this.daterangeUpdateTime = []
      this.resetForm('queryForm')
      this.queryParams.purchaseChannelsId = null
      this.purchaseChannelQuery = {}
      this.getChannelsTreeselect()
      this.handleQuery()
    }
    ,
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    }
    ,
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.form.userId = this.$store.state.user.id
      this.title = '添加采购账号信息'
    }
    ,
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPurchaseAccountInfo(id).then(response => {
        this.form = response.data
        this.getUserInfoList()
        this.purchaseChannelQuery = {}
        this.getChannelsTreeselect()
        this.open = true
        this.title = '修改采购账号信息'
      })
    }
    ,
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchaseAccountInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPurchaseAccountInfo(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    }
    ,
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除采购账号信息编号为"' + ids + '"的数据项？').then(function() {
        return delPurchaseAccountInfo(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    }
    ,
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/purchaseAccountInfo/export', {
        ...this.queryParams
      }, `purchaseAccountInfo_${new Date().getTime()}.xlsx`)
    }
    ,
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '采购账号导入'
      this.upload.open = true
    }
    ,
    /** 下载模板操作 */
    importTemplate() {
      this.download('manage/purchaseAccountInfo/importTemplate', {}, `purchaseAccountInfo_template_${new Date().getTime()}.xlsx`)
    }
    ,
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    }
    ,
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert(response.msg, '导入结果', { dangerouslyUseHTMLString: true })
      this.getList()
    }
    ,
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
    }
  }
}
</script>
