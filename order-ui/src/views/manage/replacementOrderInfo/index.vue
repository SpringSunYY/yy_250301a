<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNumber">
        <el-input
          v-model="queryParams.orderNumber"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺名称" prop="storeId">
        <el-select
          v-model="queryParams.storeId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入店铺名称"
          :remote-method="selectStoreInfoList"
          :loading="storeInfoLoading"
        >
          <el-option
            v-for="item in storeInfoList"
            :key="item.id"
            :label="item.storeName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="日期">
        <el-date-picker
          v-model="daterangeDateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="微信号" prop="wxNumber">
        <el-input
          v-model="queryParams.wxNumber"
          placeholder="请输入微信号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="旺旺号" prop="tbNumber">
        <el-input
          v-model="queryParams.tbNumber"
          placeholder="请输入旺旺号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="返款状态" prop="returnStatus">
        <el-select v-model="queryParams.returnStatus" placeholder="请选择返款状态" clearable>
          <el-option
            v-for="dict in dict.type.o_replacement_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作人" prop="userId">
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
          v-hasPermi="['manage:replacementOrderInfo:add']"
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
          v-hasPermi="['manage:replacementOrderInfo:edit']"
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
          v-hasPermi="['manage:replacementOrderInfo:remove']"
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
          v-hasPermi="['manage:replacementOrderInfo:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['manage:replacementOrderInfo:import']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="15">
        <el-button
          type="success"
          plain
          size="mini"
        >总数：{{ replacementOrderCount.orderCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >实付金额：{{ replacementOrderCount.actuallyPriceCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >佣金：{{ replacementOrderCount.commissionCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >合计金额：{{ replacementOrderCount.totalPriceCount }}
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="replacementOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="订单编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="orderNumber"
      />
      <el-table-column label="店铺名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="storeName"
      />
      <el-table-column label="日期" align="center" v-if="columns[3].visible" prop="dateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="微信号" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="wxNumber"
      />
      <el-table-column label="旺旺号" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="tbNumber"
      />
      <el-table-column label="实付金额" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="actuallyPrice"
      />
      <el-table-column label="佣金" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="commission"
      />
      <el-table-column label="合计金额" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="totalPrice"
      />
      <el-table-column label="返款状态" align="center" v-if="columns[9].visible" prop="returnStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_replacement_status" :value="scope.row.returnStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="userName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[11].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[13].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="remark"
      />
      <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"
                       prop="deptName"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:replacementOrderInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:replacementOrderInfo:remove']"
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

    <!-- 添加或修改补单明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单编号"/>
        </el-form-item>
        <el-form-item label="店铺名称" prop="storeId">
          <el-select
            v-model="form.storeId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入店铺名称"
            :remote-method="selectStoreInfoList"
            :loading="storeInfoLoading"
          >
            <el-option
              v-for="item in storeInfoList"
              :key="item.id"
              :label="item.storeName"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期" prop="dateTime">
          <el-date-picker clearable
                          v-model="form.dateTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="微信号" prop="wxNumber">
          <el-input v-model="form.wxNumber" placeholder="请输入微信号"/>
        </el-form-item>
        <el-form-item label="旺旺号" prop="tbNumber">
          <el-input v-model="form.tbNumber" placeholder="请输入旺旺号"/>
        </el-form-item>
        <el-form-item label="实付金额" prop="actuallyPrice">
          <el-input-number :precision="2" :step="0.1" v-model="form.actuallyPrice"
                           placeholder="请输入实付金额"
          />
        </el-form-item>
        <el-form-item label="佣金" prop="commission">
          <el-input-number :precision="2" :step="0.1" v-model="form.commission" placeholder="请输入佣金"/>
        </el-form-item>
        <!--        <el-form-item label="合计金额" prop="totalPrice">-->
        <!--          <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.totalPrice" placeholder="请输入合计金额"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="返款状态" prop="returnStatus">
          <el-radio-group v-model="form.returnStatus">
            <el-radio
              v-for="dict in dict.type.o_replacement_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <!--        <el-form-item label="操作人" prop="userId">-->
        <!--          <el-select-->
        <!--            v-model="form.userId"-->
        <!--            filterable-->
        <!--            remote-->
        <!--            reserve-keyword-->
        <!--            placeholder="请输入用户账号"-->
        <!--            :remote-method="selectUserInfoList"-->
        <!--            :loading="userLoading"-->
        <!--          >-->
        <!--            <el-option-->
        <!--              v-for="item in userInfoList"-->
        <!--              :key="item.userId"-->
        <!--              :label="item.userName"-->
        <!--              :value="item.userId"-->
        <!--            >-->
        <!--            </el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 白嫖订单导入对话框 -->
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
  listReplacementOrderInfo,
  getReplacementOrderInfo,
  delReplacementOrderInfo,
  addReplacementOrderInfo,
  updateReplacementOrderInfo, getReplacementOrderCount
} from '@/api/manage/replacementOrderInfo'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { listStoreInfo } from '@/api/manage/storeInfo'
import { listDept } from '@/api/system/dept'
import { allocatedUserList } from '@/api/system/role'
import { getToken } from '@/utils/auth'
import { currentMonth, pickerOptions } from '@/constants/datetime'

export default {
  name: 'ReplacementOrderInfo',
  computed: {
    pickerOptions() {
      return pickerOptions
    }
  },
  components: { Treeselect },
  dicts: ['o_replacement_status'],
  data() {
    return {
      replacementOrderCount: {
        orderCount: 0,
        actuallyPriceCount: 0,
        commissionCount: 0,
        totalPriceCount: 0
      },
      //店铺信息
      storeInfoList: [],
      storeInfoLoading: false,
      storeInfoQueryParams: {
        pageNum: 1,
        pageSize: 100,
        storeName: ''
      },
      //用户相关信息
      userInfoList: [],
      userLoading: false,
      userQueryParams: {
        userName: '',
        // roleId: 102,
        pageNum: 1,
        pageSize: 100
      },
      //部门相关信息
      deptOptions: [],
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: false },
        { key: 1, label: '订单编号', visible: true },
        { key: 2, label: '店铺名称', visible: true },
        { key: 3, label: '日期', visible: true },
        { key: 4, label: '微信号', visible: true },
        { key: 5, label: '旺旺号', visible: true },
        { key: 6, label: '实付金额', visible: true },
        { key: 7, label: '佣金', visible: true },
        { key: 8, label: '合计金额', visible: true },
        { key: 9, label: '返款状态', visible: true },
        { key: 10, label: '操作人', visible: true },
        { key: 11, label: '创建时间', visible: false },
        { key: 12, label: '更新人', visible: false },
        { key: 13, label: '更新时间', visible: false },
        { key: 14, label: '备注', visible: false },
        { key: 15, label: '部门', visible: true }
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
      // 补单明细表格数据
      replacementOrderInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 部门时间范围
      daterangeDateTime:  (() => currentMonth())(),
      // 部门时间范围
      daterangeCreateTime: [],
      // 部门时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: null,
        storeId: null,
        dateTime: null,
        wxNumber: null,
        tbNumber: null,
        returnStatus: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        deptId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNumber: [
          { required: true, message: '订单编号不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '操作人不能为空', trigger: 'blur' }
        ],
        dateTime: [
          { required: true, message: '日期不能为空', trigger: 'blur' }
        ],
        actuallyPrice: [
          { required: true, message: '实付金额不能为空', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              // 将值转换为数值类型后检查是否为 0
              if (Number(value) === 0) {
                callback(new Error('实付金额不能为0'))
              } else {
                callback()
              }
            },
            trigger: 'blur' // 触发时机与必填规则一致
          }
        ],
        returnStatus: [
          { required: true, message: '退货状态不能为空', trigger: 'change' }
        ],
        storeId: [
          { required: true, message: '店铺名称不能为空', trigger: 'blur' }
        ],
        tbNumber: [
          { required: true, message: '旺旺号不能为空', trigger: 'blur' }
        ]
      },
      // 退货订单导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: '',
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: 'Bearer ' + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + '/manage/replacementOrderInfo/importData'
      }
    }
  },
  created() {
    this.getList()
    this.getDeptList()
    this.getUserInfoList()
    this.getStoreInfoList()
  },
  methods: {
    /**
     * 获取店铺列表推荐
     * @param query
     */
    selectStoreInfoList(query) {
      if (query !== '') {
        this.storeInfoLoading = true
        this.storeInfoQueryParams.storeName = query
        setTimeout(() => {
          this.getStoreInfoList()
        }, 200)
      } else {
        this.storeInfoList = []
        this.storeInfoQueryParams.id = null
      }
    },
    /**
     * 获取店铺信息列表
     */
    getStoreInfoList() {
      //添加查询参数
      if (this.form.storeId != null) {
        this.storeInfoQueryParams.storeId = this.form.storeId
      } else {
        this.storeInfoQueryParams.id = null
      }
      if (this.storeInfoQueryParams.storeName !== '') {
        this.storeInfoQueryParams.id = null
      }
      listStoreInfo(this.storeInfoQueryParams).then(res => {
        this.storeInfoList = res?.rows
        this.storeInfoLoading = false
      })
    },
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
    },
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
    },
    /** 查询部门列表 */
    getDeptList() {
      listDept().then(response => {
        this.deptOptions = this.handleTree(response.data, 'deptId')
      })
    },
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
    },
    /** 查询补单明细列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeDateTime && '' != this.daterangeDateTime) {
        this.queryParams.params['beginDateTime'] = this.daterangeDateTime[0]
        this.queryParams.params['endDateTime'] = this.daterangeDateTime[1]
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      listReplacementOrderInfo(this.queryParams).then(response => {
        this.replacementOrderInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
      this.getCount()
    },
    getCount() {
      getReplacementOrderCount(this.queryParams).then(res => {
        this.replacementOrderCount = res.data
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderNumber: null,
        storeId: null,
        dateTime: null,
        wxNumber: null,
        tbNumber: null,
        actuallyPrice: null,
        commission: null,
        totalPrice: null,
        returnStatus: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        deptId: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeDateTime = []
      this.daterangeCreateTime = []
      this.daterangeUpdateTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.form.userId = this.$store.state.user.id
      this.open = true
      this.title = '添加补单明细'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getReplacementOrderInfo(id).then(response => {
        this.form = response.data
        this.getUserInfoList()
        this.getStoreInfoList()
        this.open = true
        this.title = '修改补单明细'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReplacementOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addReplacementOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除补单明细编号为"' + ids + '"的数据项？').then(function() {
        return delReplacementOrderInfo(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/replacementOrderInfo/export', {
        ...this.queryParams
      }, `replacementOrderInfo_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '补货订单导入'
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('manage/replacementOrderInfo/importTemplate', {}, `replacementOrderInfo_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert(response.msg, '导入结果', { dangerouslyUseHTMLString: true })
      this.getList()
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
    }
  }
}
</script>
