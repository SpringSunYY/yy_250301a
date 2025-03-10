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
      <el-form-item label="类型" prop="orderType">
        <el-select v-model="queryParams.orderType" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.o_order_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <el-form-item label="客服" prop="userId">
        <el-select
          v-model="queryParams.userId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入用户账号"
          :remote-method="selectServiceUserInfoList"
          :loading="serviceUserLoading"
        >
          <el-option
            v-for="item in serviceUserInfoList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="退货状态" prop="returnStatus">
        <el-select v-model="queryParams.returnStatus" placeholder="请选择退货状态" clearable>
          <el-option
            v-for="dict in dict.type.o_return_order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="完成日期">
        <el-date-picker
          v-model="daterangeReturnAccomplishTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
        ></el-date-picker>
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
          v-hasPermi="['manage:returnOrderInfo:add']"
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
          v-hasPermi="['manage:returnOrderInfo:edit']"
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
          v-hasPermi="['manage:returnOrderInfo:remove']"
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
          v-hasPermi="['manage:returnOrderInfo:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['manage:returnOrderInfo:import']"
        >导入
        </el-button>
      </el-col>

      <el-col :span="15">
        <el-button
          type="success"
          plain
          size="mini"
        >总数：{{ returnOrderCount.orderCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >退款金额：{{ returnOrderCount.returnPriceCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >上家退款金额：{{ returnOrderCount.lastReturnPriceCount }}
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="returnOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="订单编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="orderNumber"
      />
      <el-table-column label="类型" align="center" v-if="columns[2].visible" prop="orderType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_order_type" :value="scope.row.orderType"/>
        </template>
      </el-table-column>
      <el-table-column label="店铺名称" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="storeName"
      />
      <el-table-column label="退货状态" align="center" v-if="columns[4].visible" prop="returnStatus">
        <template slot="header">
          <div class="custom-header">
            <span>退货状态</span>
            <el-tooltip
              effect="light"
              placement="top"
              content="退货状态 【有退货标退货，没退货不用标】"
            >
              <!-- 红色问号图标 -->
              <i class="el-icon-question" style="color:#F56C6C;margin-left:5px;cursor:pointer"/>
            </el-tooltip>
          </div>
        </template>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_return_order_status" :value="scope.row.returnStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="客户退货金额" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="returnPrice"
      />
      <el-table-column label="上家退款金额" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="lastReturnPrice"
      />
      <el-table-column label="退货完成日期" align="center" v-if="columns[7].visible" prop="returnAccomplishTime"
                       width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.returnAccomplishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客服" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="userName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[9].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[11].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="remark"
      />
      <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="deptName"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:returnOrderInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:returnOrderInfo:remove']"
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

    <!-- 添加或修改退货订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="订单编号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单编号"/>
        </el-form-item>
        <el-form-item label="退货状态" prop="returnStatus">
          <el-select v-model="form.returnStatus" placeholder="请选择退货状态">
            <el-option
              v-for="dict in dict.type.o_return_order_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户退货金额" prop="returnPrice">
          <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.returnPrice"
                           placeholder="请输入客户退货金额"
          />
        </el-form-item>
        <el-form-item label="上家退款金额" prop="lastReturnPrice">
          <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.lastReturnPrice"
                           placeholder="请输入上家退款金额"
          />
        </el-form-item>
        <el-form-item label="退货完成日期" prop="returnAccomplishTime">
          <el-date-picker clearable
                          v-model="form.returnAccomplishTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择退货完成日期"
          >
          </el-date-picker>
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
    <!-- 退货订单导入对话框 -->
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
  listReturnOrderInfo,
  getReturnOrderInfo,
  delReturnOrderInfo,
  addReturnOrderInfo,
  updateReturnOrderInfo, getReturnOrderCount
} from '@/api/manage/returnOrderInfo'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { listDept } from '@/api/system/dept'
import { getToken } from '@/utils/auth'
import { listStoreInfo } from '@/api/manage/storeInfo'
import { allocatedUserList } from '@/api/system/role'
import { currentMonth, pickerOptions } from '@/constants/datetime'

export default {
  name: 'ReturnOrderInfo',
  computed: {
    pickerOptions() {
      return pickerOptions
    }
  },
  components: { Treeselect },
  dicts: ['o_return_order_status', 'o_order_type'],
  data() {
    return {
      //客服相关信息
      serviceUserInfoList: [],
      serviceUserLoading: false,
      serviceUserQueryParams: {
        userName: '',
        roleId: 102,
        pageNum: 1,
        pageSize: 100
      },
      returnOrderCount: {
        orderCount: 0,
        returnPriceCount: 0,
        lastReturnPriceCount: 0
      },
      //店铺信息
      storeInfoList: [],
      storeInfoLoading: false,
      storeInfoQueryParams: {
        pageNum: 1,
        pageSize: 100,
        storeName: ''
      },
      //部门相关信息
      deptOptions: [],
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: false },
        { key: 1, label: '订单编号', visible: true },
        { key: 2, label: '类型', visible: true },
        { key: 3, label: '店铺名称', visible: true },
        { key: 4, label: '退货状态', visible: true },
        { key: 5, label: '客户退货金额', visible: true },
        { key: 6, label: '上家退款金额', visible: true },
        { key: 7, label: '退货完成日期', visible: true },
        { key: 8, label: '客服', visible: true },
        { key: 9, label: '创建时间', visible: false },
        { key: 10, label: '更新人', visible: false },
        { key: 11, label: '更新时间', visible: false },
        { key: 12, label: '备注', visible: false },
        { key: 13, label: '部门', visible: true }
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
      // 退货订单信息表格数据
      returnOrderInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 部门时间范围
      daterangeReturnAccomplishTime:  (() => currentMonth())(),
      // 部门时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: null,
        orderType: null,
        storeId: null,
        returnStatus: null,
        returnAccomplishTime: null,
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
        orderType: [
          { required: true, message: '类型不能为空', trigger: 'change' }
        ],
        returnStatus: [
          { required: true, message: '退货状态不能为空', trigger: 'change' }
        ],
        userId: [
          { required: true, message: '客服不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
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
        url: process.env.VUE_APP_BASE_API + '/manage/returnOrderInfo/importData'
      }
    }
  },
  created() {
    this.getList()
    this.getDeptList()
    this.getStoreInfoList()
    this.getServiceUserInfoList()
  },
  methods: {
    /**
     * 获取客服用户列表推荐
     * @param query
     */
    selectServiceUserInfoList(query) {
      if (query !== '') {
        this.serviceUserLoading = true
        this.serviceUserQueryParams.userName = query
        setTimeout(() => {
          this.getServiceUserInfoList()
        }, 200)
      } else {
        this.serviceUserInfoList = []
        this.serviceUserQueryParams.userName = null
      }
    },
    /**
     * 获取客服用户信息列表
     */
    getServiceUserInfoList() {
      //添加查询参数
      if (this.form.userId != null) {
        this.serviceUserQueryParams.userId = this.form.serviceId
      } else {
        this.serviceUserQueryParams.userId = null
      }
      if (this.serviceUserQueryParams.userName !== '') {
        this.serviceUserQueryParams.userId = null
      }
      allocatedUserList(this.serviceUserQueryParams).then(res => {
        this.serviceUserInfoList = res?.rows
        this.serviceUserLoading = false
      })
    },
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
        this.storeInfoQueryParams.storeId = null
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
        this.storeInfoQueryParams.storeId = null
      }
      if (this.storeInfoQueryParams.storeName !== '') {
        this.storeInfoQueryParams.storeId = null
      }
      listStoreInfo(this.storeInfoQueryParams).then(res => {
        this.storeInfoList = res?.rows
        this.storeInfoLoading = false
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
    /** 查询退货订单信息列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeReturnAccomplishTime && '' != this.daterangeReturnAccomplishTime) {
        this.queryParams.params['beginReturnAccomplishTime'] = this.daterangeReturnAccomplishTime[0]
        this.queryParams.params['endReturnAccomplishTime'] = this.daterangeReturnAccomplishTime[1]
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      listReturnOrderInfo(this.queryParams).then(response => {
        this.returnOrderInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
      this.getCount()
    },
    getCount() {
      getReturnOrderCount(this.queryParams).then(response => {
        this.returnOrderCount = response.data
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
        orderType: null,
        storeId: null,
        returnStatus: null,
        returnPrice: null,
        lastReturnPrice: null,
        returnAccomplishTime: null,
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
      this.daterangeReturnAccomplishTime = []
      this.daterangeCreateTime = []
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
      this.open = true
      this.title = '添加退货订单信息'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getReturnOrderInfo(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改退货订单信息'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReturnOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addReturnOrderInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除退货订单信息编号为"' + ids + '"的数据项？').then(function() {
        return delReturnOrderInfo(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/returnOrderInfo/export', {
        ...this.queryParams
      }, `returnOrderInfo_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '退货订单导入'
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('manage/returnOrderInfo/importTemplate', {}, `returnOrder_template_${new Date().getTime()}.xlsx`)
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
