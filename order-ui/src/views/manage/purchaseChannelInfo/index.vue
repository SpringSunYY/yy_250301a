<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="channelName">
        <el-input
          v-model="queryParams.channelName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="渠道类型" prop="channelType">
        <el-select v-model="queryParams.channelType" placeholder="请选择渠道类型" clearable>
          <el-option
            v-for="dict in dict.type.o_purchase_channel_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="创建人" prop="userId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.userId"-->
      <!--          placeholder="请输入创建人"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
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
      <!--      <el-form-item label="更新人" prop="updateBy">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.updateBy"-->
      <!--          placeholder="请输入更新人"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="更新时间">-->
      <!--        <el-date-picker-->
      <!--          v-model="daterangeUpdateTime"-->
      <!--          style="width: 240px"-->
      <!--          value-format="yyyy-MM-dd"-->
      <!--          type="daterange"-->
      <!--          range-separator="-"-->
      <!--          start-placeholder="开始日期"-->
      <!--          end-placeholder="结束日期"-->
      <!--        ></el-date-picker>-->
      <!--      </el-form-item>-->
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
          v-hasPermi="['manage:purchaseChannelInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="purchaseChannelInfoList"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="父级" :show-overflow-tooltip="true" v-if="columns[0].visible" prop="parentId"/>
      <el-table-column label="编号" align="center" :show-overflow-tooltip="true" v-if="columns[1].visible" prop="id"/>
      <el-table-column label="名称" align="center" :show-overflow-tooltip="true" v-if="columns[2].visible"
                       prop="channelName"
      />
      <el-table-column label="渠道类型" align="center" v-if="columns[3].visible" prop="channelType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_purchase_channel_type" :value="scope.row.channelType"/>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" :show-overflow-tooltip="true" v-if="columns[4].visible"
                       prop="userName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[5].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" :show-overflow-tooltip="true" v-if="columns[6].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[7].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" :show-overflow-tooltip="true" v-if="columns[8].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:purchaseChannelInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['manage:purchaseChannelInfo:add']"
          >新增
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:purchaseChannelInfo:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改采购渠道信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父级" prop="parentId">
          <treeselect v-model="form.parentId" :options="purchaseChannelInfoOptions" :normalizer="normalizer"
                      placeholder="请选择父级"
          />
        </el-form-item>
        <el-form-item label="名称" prop="channelName">
          <el-input v-model="form.channelName" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="渠道类型" prop="channelType">
          <el-select v-model="form.channelType" placeholder="请选择渠道类型">
            <el-option
              v-for="dict in dict.type.o_purchase_channel_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input-number :min="0" :step="1" v-model="form.orderNum" placeholder="请输入排序"/>
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
  </div>
</template>

<script>
import {
  listPurchaseChannelInfo,
  getPurchaseChannelInfo,
  delPurchaseChannelInfo,
  addPurchaseChannelInfo,
  updatePurchaseChannelInfo
} from '@/api/manage/purchaseChannelInfo'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { currentMonth, pickerOptions } from '@/constants/datetime'

export default {
  name: 'PurchaseChannelInfo',
  computed: {
    pickerOptions() {
      return pickerOptions
    }
  },
  dicts: ['o_purchase_channel_type'],
  components: {
    Treeselect
  },
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '父级', visible: true },
        { key: 1, label: '编号', visible: false },
        { key: 2, label: '名称', visible: true },
        { key: 3, label: '渠道类型', visible: true },
        { key: 4, label: '创建人', visible: true },
        { key: 5, label: '创建时间', visible: true },
        { key: 6, label: '更新人', visible: false },
        { key: 7, label: '更新时间', visible: false },
        { key: 8, label: '备注', visible: false }
      ],
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 采购渠道信息表格数据
      purchaseChannelInfoList: [],
      // 采购渠道信息树选项
      purchaseChannelInfoOptions: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 备注时间范围
      daterangeCreateTime: (() => currentMonth())(),
      // 备注时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        channelName: null,
        channelType: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: '父级不能为空', trigger: 'blur' }
        ],
        channelName: [
          { required: true, message: '名称不能为空', trigger: 'blur' }
        ],
        channelType: [
          { required: true, message: '渠道类型不能为空', trigger: 'change' }
        ],
        orderNum: [
          { required: true, message: '排序不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询采购渠道信息列表 */
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
      listPurchaseChannelInfo(this.queryParams).then(response => {
        this.purchaseChannelInfoList = this.handleTree(response.data, 'id', 'parentId')
        this.loading = false
      })
    },
    /** 转换采购渠道信息数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.id,
        label: node.channelName,
        children: node.children
      }
    },
    /** 查询采购渠道信息下拉树结构 */
    getTreeselect() {
      listPurchaseChannelInfo().then(response => {
        this.purchaseChannelInfoOptions = []
        const data = { id: 0, channelName: '顶级节点', children: [] }
        data.children = this.handleTree(response.data, 'id', 'parentId')
        this.purchaseChannelInfoOptions.push(data)
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
        parentId: null,
        channelName: null,
        channelType: null,
        orderNum: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = []
      this.daterangeUpdateTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      this.getTreeselect()
      if (row != null && row.id) {
        this.form.parentId = row.id
      } else {
        this.form.parentId = 0
      }
      this.open = true
      this.title = '添加采购渠道信息'
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.getTreeselect()
      if (row != null) {
        this.form.parentId = row.parentId
      }
      getPurchaseChannelInfo(row.id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改采购渠道信息'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchaseChannelInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPurchaseChannelInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除采购渠道信息编号为"' + row.id + '"的数据项？').then(function() {
        return delPurchaseChannelInfo(row.id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    }
  }
}
</script>
