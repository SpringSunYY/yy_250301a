<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="采购编号" prop="orderNumber">
        <el-input
          v-model="queryParams.orderNumber"
          placeholder="请输入采购编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺名称" prop="storeId">
        <el-input
          v-model="queryParams.storeId"
          placeholder="请输入店铺名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="返款状态(0=已返 1=未返)" prop="returnStatus">
        <el-select v-model="queryParams.returnStatus" placeholder="请选择返款状态(0=已返 1=未返)" clearable>
          <el-option
            v-for="dict in dict.type.o_replacement_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="更新人" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间">
        <el-date-picker
          v-model="daterangeUpdateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
        >新增</el-button>
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
        >修改</el-button>
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
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:replacementOrderInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="replacementOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id" />
        <el-table-column label="采购编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="orderNumber" />
        <el-table-column label="店铺名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="storeId" />
        <el-table-column label="日期" align="center" v-if="columns[3].visible" prop="dateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="微信号" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible" prop="wxNumber" />
        <el-table-column label="旺旺号" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible" prop="tbNumber" />
        <el-table-column label="实付金额" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="actuallyPrice" />
        <el-table-column label="佣金" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible" prop="commission" />
        <el-table-column label="合计金额" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="totalPrice" />
        <el-table-column label="返款状态(0=已返 1=未返)" align="center" v-if="columns[9].visible" prop="returnStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_replacement_status" :value="scope.row.returnStatus"/>
        </template>
      </el-table-column>
        <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible" prop="userId" />
        <el-table-column label="创建时间" align="center" v-if="columns[11].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="updateBy" />
        <el-table-column label="更新时间" align="center" v-if="columns[13].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible" prop="remark" />
        <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible" prop="deptId" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:replacementOrderInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:replacementOrderInfo:remove']"
          >删除</el-button>
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
        <el-form-item label="采购编号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入采购编号" />
        </el-form-item>
        <el-form-item label="店铺名称" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入店铺名称" />
        </el-form-item>
        <el-form-item label="日期" prop="dateTime">
          <el-date-picker clearable
            v-model="form.dateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="微信号" prop="wxNumber">
          <el-input v-model="form.wxNumber" placeholder="请输入微信号" />
        </el-form-item>
        <el-form-item label="旺旺号" prop="tbNumber">
          <el-input v-model="form.tbNumber" placeholder="请输入旺旺号" />
        </el-form-item>
        <el-form-item label="实付金额" prop="actuallyPrice">
          <el-input v-model="form.actuallyPrice" placeholder="请输入实付金额" />
        </el-form-item>
        <el-form-item label="佣金" prop="commission">
          <el-input v-model="form.commission" placeholder="请输入佣金" />
        </el-form-item>
        <el-form-item label="合计金额" prop="totalPrice">
          <el-input v-model="form.totalPrice" placeholder="请输入合计金额" />
        </el-form-item>
        <el-form-item label="返款状态(0=已返 1=未返)" prop="returnStatus">
          <el-radio-group v-model="form.returnStatus">
            <el-radio
              v-for="dict in dict.type.o_replacement_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="创建人" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listReplacementOrderInfo, getReplacementOrderInfo, delReplacementOrderInfo, addReplacementOrderInfo, updateReplacementOrderInfo } from "@/api/manage/replacementOrderInfo";
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: "ReplacementOrderInfo",
  components: { Treeselect },
  dicts: ['o_replacement_status'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: true },
          { key: 1, label: '采购编号', visible: true },
          { key: 2, label: '店铺名称', visible: true },
          { key: 3, label: '日期', visible: true },
          { key: 4, label: '微信号', visible: true },
          { key: 5, label: '旺旺号', visible: true },
          { key: 6, label: '实付金额', visible: true },
          { key: 7, label: '佣金', visible: true },
          { key: 8, label: '合计金额', visible: true },
          { key: 9, label: '返款状态(0=已返 1=未返)', visible: true },
          { key: 10, label: '创建人', visible: true },
          { key: 11, label: '创建时间', visible: true },
          { key: 12, label: '更新人', visible: true },
          { key: 13, label: '更新时间', visible: true },
          { key: 14, label: '备注', visible: true },
          { key: 15, label: '部门', visible: true },
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
      title: "",
      // 是否显示弹出层
      open: false,
      // 部门时间范围
      daterangeDateTime: [],
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
          { required: true, message: "采购编号不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "创建人不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询补单明细列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeDateTime && '' != this.daterangeDateTime) {
        this.queryParams.params["beginDateTime"] = this.daterangeDateTime[0];
        this.queryParams.params["endDateTime"] = this.daterangeDateTime[1];
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params["beginUpdateTime"] = this.daterangeUpdateTime[0];
        this.queryParams.params["endUpdateTime"] = this.daterangeUpdateTime[1];
      }
      listReplacementOrderInfo(this.queryParams).then(response => {
        this.replacementOrderInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeDateTime = [];
      this.daterangeCreateTime = [];
      this.daterangeUpdateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加补单明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getReplacementOrderInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改补单明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReplacementOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReplacementOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除补单明细编号为"' + ids + '"的数据项？').then(function() {
        return delReplacementOrderInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/replacementOrderInfo/export', {
        ...this.queryParams
      }, `replacementOrderInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
