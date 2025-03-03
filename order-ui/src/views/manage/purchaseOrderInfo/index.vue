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
      <el-form-item label="销售类型" prop="orderType">
        <el-select v-model="queryParams.orderType" placeholder="请选择销售类型" clearable>
          <el-option
            v-for="dict in dict.type.o_order_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="采购日期">
        <el-date-picker
          v-model="daterangePurchaseTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="店铺名称" prop="storeId">
        <el-input
          v-model="queryParams.storeId"
          placeholder="请输入店铺名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="买家" prop="buyerNumber">
        <el-input
          v-model="queryParams.buyerNumber"
          placeholder="请输入买家"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购渠道分类" prop="purchaseChannelType">
        <el-select v-model="queryParams.purchaseChannelType" placeholder="请选择采购渠道分类" clearable>
          <el-option
            v-for="dict in dict.type.o_purchase_channel_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="采购渠道" prop="purchaseChannelDetail">
        <el-input
          v-model="queryParams.purchaseChannelDetail"
          placeholder="请输入采购渠道"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购账号" prop="purchaseAccount">
        <el-input
          v-model="queryParams.purchaseAccount"
          placeholder="请输入采购账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购订单编号" prop="purchaseOrder">
        <el-input
          v-model="queryParams.purchaseOrder"
          placeholder="请输入采购订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发货单号" prop="shipmentsOrder">
        <el-input
          v-model="queryParams.shipmentsOrder"
          placeholder="请输入发货单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否退货" prop="hasReturn">
        <el-input
          v-model="queryParams.hasReturn"
          placeholder="请输入是否退货"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否白嫖" prop="hasBP">
        <el-select v-model="queryParams.hasBP" placeholder="请选择是否白嫖" clearable>
          <el-option
            v-for="dict in dict.type.o_common_whether"
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
          v-hasPermi="['manage:purchaseOrderInfo:add']"
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
          v-hasPermi="['manage:purchaseOrderInfo:edit']"
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
          v-hasPermi="['manage:purchaseOrderInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:purchaseOrderInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id" />
        <el-table-column label="采购编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="orderNumber" />
        <el-table-column label="销售类型" align="center" v-if="columns[2].visible" prop="orderType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_order_type" :value="scope.row.orderType"/>
        </template>
      </el-table-column>
        <el-table-column label="订单利润" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="orderProfit" />
        <el-table-column label="利润率" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible" prop="orderProfitRate" />
        <el-table-column label="采购日期" align="center" v-if="columns[5].visible" prop="purchaseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="店铺名称" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="storeId" />
        <el-table-column label="买家" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible" prop="buyerNumber" />
        <el-table-column label="销售量" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="salesNumber" />
        <el-table-column label="销售价" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible" prop="salesPrice" />
        <el-table-column label="采购渠道分类" align="center" v-if="columns[10].visible" prop="purchaseChannelType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_purchase_channel_type" :value="scope.row.purchaseChannelType"/>
        </template>
      </el-table-column>
        <el-table-column label="采购渠道" align="center" v-if="columns[11].visible" prop="purchaseChannelDetail">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_purchase_channels" :value="scope.row.purchaseChannelDetail"/>
        </template>
      </el-table-column>
        <el-table-column label="采购账号" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="purchaseAccount" />
        <el-table-column label="采购订单编号" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible" prop="purchaseOrder" />
        <el-table-column label="供应商名称" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible" prop="supplierName" />
        <el-table-column label="采购进价" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible" prop="purchasePrice" />
        <el-table-column label="采购补价" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible" prop="purchasePremium" />
        <el-table-column label="发货单号" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible" prop="shipmentsOrder" />
        <el-table-column label="是否退货" align="center" v-if="columns[18].visible" prop="hasReturn">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_common_whether" :value="scope.row.hasReturn"/>
        </template>
      </el-table-column>
        <el-table-column label="是否白嫖" align="center" v-if="columns[19].visible" prop="hasBP">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_common_whether" :value="scope.row.hasBP"/>
        </template>
      </el-table-column>
        <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[20].visible" prop="userId" />
        <el-table-column label="创建时间" align="center" v-if="columns[21].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[22].visible" prop="updateBy" />
        <el-table-column label="更新时间" align="center" v-if="columns[23].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[24].visible" prop="remark" />
        <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[25].visible" prop="deptId" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:purchaseOrderInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:purchaseOrderInfo:remove']"
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

    <!-- 添加或修改采购发货信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="采购编号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入采购编号" />
        </el-form-item>
        <el-form-item label="销售类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="请选择销售类型">
            <el-option
              v-for="dict in dict.type.o_order_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单利润" prop="orderProfit">
          <el-input v-model="form.orderProfit" placeholder="请输入订单利润" />
        </el-form-item>
        <el-form-item label="利润率" prop="orderProfitRate">
          <el-input v-model="form.orderProfitRate" placeholder="请输入利润率" />
        </el-form-item>
        <el-form-item label="采购日期" prop="purchaseTime">
          <el-date-picker clearable
            v-model="form.purchaseTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择采购日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="店铺名称" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入店铺名称" />
        </el-form-item>
        <el-form-item label="买家" prop="buyerNumber">
          <el-input v-model="form.buyerNumber" placeholder="请输入买家" />
        </el-form-item>
        <el-form-item label="销售量" prop="salesNumber">
          <el-input v-model="form.salesNumber" placeholder="请输入销售量" />
        </el-form-item>
        <el-form-item label="销售价" prop="salesPrice">
          <el-input v-model="form.salesPrice" placeholder="请输入销售价" />
        </el-form-item>
        <el-form-item label="采购渠道分类" prop="purchaseChannelType">
          <el-select v-model="form.purchaseChannelType" placeholder="请选择采购渠道分类">
            <el-option
              v-for="dict in dict.type.o_purchase_channel_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采购渠道" prop="purchaseChannelDetail">
          <el-input v-model="form.purchaseChannelDetail" placeholder="请输入采购渠道" />
        </el-form-item>
        <el-form-item label="采购账号" prop="purchaseAccount">
          <el-input v-model="form.purchaseAccount" placeholder="请输入采购账号" />
        </el-form-item>
        <el-form-item label="采购订单编号" prop="purchaseOrder">
          <el-input v-model="form.purchaseOrder" placeholder="请输入采购订单编号" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="采购进价" prop="purchasePrice">
          <el-input v-model="form.purchasePrice" placeholder="请输入采购进价" />
        </el-form-item>
        <el-form-item label="采购补价" prop="purchasePremium">
          <el-input v-model="form.purchasePremium" placeholder="请输入采购补价" />
        </el-form-item>
        <el-form-item label="发货单号" prop="shipmentsOrder">
          <el-input v-model="form.shipmentsOrder" placeholder="请输入发货单号" />
        </el-form-item>
        <el-form-item label="是否退货" prop="hasReturn">
          <el-input v-model="form.hasReturn" placeholder="请输入是否退货" />
        </el-form-item>
        <el-form-item label="是否白嫖" prop="hasBP">
          <el-radio-group v-model="form.hasBP">
            <el-radio
              v-for="dict in dict.type.o_common_whether"
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
import { listPurchaseOrderInfo, getPurchaseOrderInfo, delPurchaseOrderInfo, addPurchaseOrderInfo, updatePurchaseOrderInfo } from "@/api/manage/purchaseOrderInfo";

export default {
  name: "PurchaseOrderInfo",
  dicts: ['o_purchase_channel_type', 'o_order_type', 'o_common_whether'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: true },
          { key: 1, label: '采购编号', visible: true },
          { key: 2, label: '销售类型', visible: true },
          { key: 3, label: '订单利润', visible: true },
          { key: 4, label: '利润率', visible: true },
          { key: 5, label: '采购日期', visible: true },
          { key: 6, label: '店铺名称', visible: true },
          { key: 7, label: '买家', visible: true },
          { key: 8, label: '销售量', visible: true },
          { key: 9, label: '销售价', visible: true },
          { key: 10, label: '采购渠道分类', visible: true },
          { key: 11, label: '采购渠道', visible: true },
          { key: 12, label: '采购账号', visible: true },
          { key: 13, label: '采购订单编号', visible: true },
          { key: 14, label: '供应商名称', visible: true },
          { key: 15, label: '采购进价', visible: true },
          { key: 16, label: '采购补价', visible: true },
          { key: 17, label: '发货单号', visible: true },
          { key: 18, label: '是否退货', visible: true },
          { key: 19, label: '是否白嫖', visible: true },
          { key: 20, label: '创建人', visible: true },
          { key: 21, label: '创建时间', visible: true },
          { key: 22, label: '更新人', visible: true },
          { key: 23, label: '更新时间', visible: true },
          { key: 24, label: '备注', visible: true },
          { key: 25, label: '部门', visible: true },
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
      // 采购发货信息表格数据
      purchaseOrderInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 部门时间范围
      daterangePurchaseTime: [],
      // 部门时间范围
      daterangeCreateTime: [],
      // 部门时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: null,
        orderType: null,
        purchaseTime: null,
        storeId: null,
        buyerNumber: null,
        purchaseChannelType: null,
        purchaseChannelDetail: null,
        purchaseAccount: null,
        purchaseOrder: null,
        supplierName: null,
        shipmentsOrder: null,
        hasReturn: null,
        hasBP: null,
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
        orderType: [
          { required: true, message: "销售类型不能为空", trigger: "change" }
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
    /** 查询采购发货信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangePurchaseTime && '' != this.daterangePurchaseTime) {
        this.queryParams.params["beginPurchaseTime"] = this.daterangePurchaseTime[0];
        this.queryParams.params["endPurchaseTime"] = this.daterangePurchaseTime[1];
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params["beginUpdateTime"] = this.daterangeUpdateTime[0];
        this.queryParams.params["endUpdateTime"] = this.daterangeUpdateTime[1];
      }
      listPurchaseOrderInfo(this.queryParams).then(response => {
        this.purchaseOrderInfoList = response.rows;
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
        orderProfit: null,
        orderProfitRate: null,
        purchaseTime: null,
        storeId: null,
        buyerNumber: null,
        salesNumber: null,
        salesPrice: null,
        purchaseChannelType: null,
        purchaseChannelDetail: null,
        purchaseAccount: null,
        purchaseOrder: null,
        supplierName: null,
        purchasePrice: null,
        purchasePremium: null,
        shipmentsOrder: null,
        hasReturn: null,
        hasBP: null,
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
      this.daterangePurchaseTime = [];
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
      this.title = "添加采购发货信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPurchaseOrderInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购发货信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchaseOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPurchaseOrderInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除采购发货信息编号为"' + ids + '"的数据项？').then(function() {
        return delPurchaseOrderInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/purchaseOrderInfo/export', {
        ...this.queryParams
      }, `purchaseOrderInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
