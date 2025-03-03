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
        <el-input
          v-model="queryParams.storeId"
          placeholder="请输入店铺名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="退货完成日期">
        <el-date-picker
          v-model="daterangeReturnAccomplishTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker clearable
          v-model="queryParams.updateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间">
        </el-date-picker>
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
          v-hasPermi="['manage:returnOrderInfo:add']"
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
          v-hasPermi="['manage:returnOrderInfo:edit']"
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
          v-hasPermi="['manage:returnOrderInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:returnOrderInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="returnOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id" />
        <el-table-column label="订单编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="orderNumber" />
        <el-table-column label="类型" align="center" v-if="columns[2].visible" prop="orderType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_order_type" :value="scope.row.orderType"/>
        </template>
      </el-table-column>
        <el-table-column label="店铺名称" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="storeId" />
        <el-table-column label="退货状态" align="center" v-if="columns[4].visible" prop="returnStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_return_order_status" :value="scope.row.returnStatus"/>
        </template>
      </el-table-column>
        <el-table-column label="客户退货金额" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible" prop="returnPrice" />
        <el-table-column label="上家退款金额" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="lastReturnPrice" />
        <el-table-column label="退货完成日期" align="center" v-if="columns[7].visible" prop="returnAccomplishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.returnAccomplishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="userId" />
        <el-table-column label="创建时间" align="center" v-if="columns[9].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible" prop="updateBy" />
        <el-table-column label="更新时间" align="center" v-if="columns[11].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="remark" />
        <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible" prop="deptId" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:returnOrderInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:returnOrderInfo:remove']"
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

    <!-- 添加或修改退货订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.o_order_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="店铺名称" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入店铺名称" />
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
          <el-input v-model="form.returnPrice" placeholder="请输入客户退货金额" />
        </el-form-item>
        <el-form-item label="上家退款金额" prop="lastReturnPrice">
          <el-input v-model="form.lastReturnPrice" placeholder="请输入上家退款金额" />
        </el-form-item>
        <el-form-item label="退货完成日期" prop="returnAccomplishTime">
          <el-date-picker clearable
            v-model="form.returnAccomplishTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择退货完成日期">
          </el-date-picker>
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
import { listReturnOrderInfo, getReturnOrderInfo, delReturnOrderInfo, addReturnOrderInfo, updateReturnOrderInfo } from "@/api/manage/returnOrderInfo";

export default {
  name: "ReturnOrderInfo",
  dicts: ['o_return_order_status', 'o_order_type'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: true },
          { key: 1, label: '订单编号', visible: true },
          { key: 2, label: '类型', visible: true },
          { key: 3, label: '店铺名称', visible: true },
          { key: 4, label: '退货状态', visible: true },
          { key: 5, label: '客户退货金额', visible: true },
          { key: 6, label: '上家退款金额', visible: true },
          { key: 7, label: '退货完成日期', visible: true },
          { key: 8, label: '创建人', visible: true },
          { key: 9, label: '创建时间', visible: true },
          { key: 10, label: '更新人', visible: true },
          { key: 11, label: '更新时间', visible: true },
          { key: 12, label: '备注', visible: true },
          { key: 13, label: '部门', visible: true },
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
      title: "",
      // 是否显示弹出层
      open: false,
      // 部门时间范围
      daterangeReturnAccomplishTime: [],
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
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
        orderType: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
        returnStatus: [
          { required: true, message: "退货状态不能为空", trigger: "change" }
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
    /** 查询退货订单信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeReturnAccomplishTime && '' != this.daterangeReturnAccomplishTime) {
        this.queryParams.params["beginReturnAccomplishTime"] = this.daterangeReturnAccomplishTime[0];
        this.queryParams.params["endReturnAccomplishTime"] = this.daterangeReturnAccomplishTime[1];
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listReturnOrderInfo(this.queryParams).then(response => {
        this.returnOrderInfoList = response.rows;
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
      this.daterangeReturnAccomplishTime = [];
      this.daterangeCreateTime = [];
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
      this.title = "添加退货订单信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getReturnOrderInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改退货订单信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReturnOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReturnOrderInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除退货订单信息编号为"' + ids + '"的数据项？').then(function() {
        return delReturnOrderInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/returnOrderInfo/export', {
        ...this.queryParams
      }, `returnOrderInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
