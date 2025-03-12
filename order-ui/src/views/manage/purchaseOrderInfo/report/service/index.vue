<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="渠道分类" prop="purchaseChannelType">
        <el-select v-model="queryParams.purchaseChannelType" placeholder="请选择采购渠道分类" clearable>
          <el-option
            v-for="dict in dict.type.o_purchase_channel_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="采购账号" prop="purchaseAccountId">
        <el-select
          v-model="queryParams.purchaseAccountId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入用户账号"
          :remote-method="selectPurchaseAccountInfoList"
          :loading="purchaseAccountInfoLoading"
        >
          <el-option
            v-for="item in purchaseAccountInfoList"
            :key="item.id"
            :label="item.purchaseAccount"
            :value="item.id"
          >
          </el-option>
        </el-select>
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
        <el-select v-model="queryParams.hasReturn" placeholder="请选择是否退货" clearable>
          <el-option
            v-for="dict in dict.type.o_common_whether"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <el-form-item label="是否售后" prop="hasAfterSale">
        <el-select v-model="queryParams.hasAfterSale" placeholder="请选择是否售后" clearable>
          <el-option
            v-for="dict in dict.type.o_common_whether"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
      <el-form-item label="采购日期">
        <el-date-picker
          v-model="daterangePurchaseTime"
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="20" style="margin-top:10px">
        <el-button
          type="success"
          plain
          size="mini"
        >总数：{{ purchaseOrderInfoCount.orderCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >订单总利润：{{ purchaseOrderInfoCount.orderProfitCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >订单平均利润率：{{ toPercentage(purchaseOrderInfoCount.avgOrderProfitRate) }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >总销售量：{{ purchaseOrderInfoCount.salesNumberCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >总销售价：{{ purchaseOrderInfoCount.salesPriceCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >采购总进价：{{ purchaseOrderInfoCount.purchasePriceCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >采购总补价：{{ purchaseOrderInfoCount.purchasePremiumCount }}
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reportList" :border="true"
    >
      <el-table-column label="客服" :show-overflow-tooltip="true" align="center"
                       prop="userName"
      />
      <el-table-column label="订单总数" sortable :show-overflow-tooltip="true" align="center"
                       prop="orderCount"
      />
      <el-table-column label="订单总利润" sortable :show-overflow-tooltip="true" align="center"
                       prop="orderProfitCount"
      />
      <el-table-column label="销售总数量" sortable :show-overflow-tooltip="true" align="center"
                       prop="salesNumberCount"
      />
      <el-table-column label="销售总价格" sortable :show-overflow-tooltip="true" align="center"
                       prop="salesPriceCount"
      />
      <el-table-column label="销售总进价" sortable :show-overflow-tooltip="true" align="center"
                       prop="purchasePriceCount"
      />
      <el-table-column label="采购总补价" sortable :show-overflow-tooltip="true" align="center"
                       prop="purchasePremiumCount"
      />
      <el-table-column label="订单平均利润率" sortable :show-overflow-tooltip="true" align="center"
                       prop="avgOrderProfitRate"
      >
        <template slot-scope="scope">
          <span>{{ toPercentage(scope.row.avgOrderProfitRate) }}</span>
        </template>
      </el-table-column>
    </el-table>


  </div>
</template>

<script>
import {
  addPurchaseOrderInfo,
  delPurchaseOrderInfo,
  getPurchaseOrderInfo, getPurchaseOrderInfoCount, getServiceReport,
  listPurchaseOrderInfo,
  updatePurchaseOrderInfo
} from '@/api/manage/purchaseOrderInfo'
import { allocatedUserList } from '@/api/system/role'
import { listStoreInfo } from '@/api/manage/storeInfo'
import { listPurchaseAccountInfo } from '@/api/manage/purchaseAccountInfo'
import { toPercentage } from '@/utils/common'
import { getToken } from '@/utils/auth'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { listDept } from '@/api/system/dept'
import { addOrUpdateReturnOrderInfo, getReturnOrderInfoByOrderNumber } from '@/api/manage/returnOrderInfo'
import { addOrUpdateBPOrderInfo, getBPOrderInfoByOrderNumber } from '@/api/manage/bPOrderInfo'
import { parseTime } from '@/utils/ruoyi'
import { listPurchaseChannelInfo } from '@/api/manage/purchaseChannelInfo'
import { addOrUpdateAfterSaleOrderInfo, getAfterSaleOrderInfoByOrderNumber } from '@/api/manage/afterSaleOrderInfo'
import { currentMonth, pickerOptions } from '@/constants/datetime'

export default {
  name: 'PurchaseOrderInfo',
  computed: {
    pickerOptions() {
      return pickerOptions
    }
  },
  components: { Treeselect },
  dicts: ['o_purchase_channel_type', 'o_purchase_channels', 'o_order_type', 'o_common_whether', 'o_return_order_status'],
  data() {
    return {
      //采购渠道相关信息
      purchaseChannelInfoOptions: [],
      purchaseChannelQuery: {
        channelType: null
      },
      //统计信息
      purchaseOrderInfoCount: {
        orderCount: 0,
        orderProfitCount: 0,
        salesNumberCount: 0,
        salesPriceCount: 0,
        purchasePriceCount: 0,
        purchasePremiumCount: 0,
        avgOrderProfitRate: 0
      },
      afterSaleOrderOpen: false,
      returnOrderOpen: false,
      bpOrderOpen: false,
      //部门相关信息
      deptOptions: [],
      //采购账号信息
      purchaseAccountInfoList: [],
      purchaseAccountInfoLoading: false,
      purchaseAccountInfoQueryParams: {
        pageNum: 1,
        pageSize: 100,
        purchaseAccount: '',
        purchaseAccountType: ''
      },
      //店铺信息
      storeInfoList: [],
      storeInfoLoading: false,
      storeInfoQueryParams: {
        pageNum: 1,
        pageSize: 100,
        storeName: ''
      },
      //客服相关信息
      serviceUserInfoList: [],
      serviceUserLoading: false,
      serviceUserQueryParams: {
        userName: '',
        roleId: 102,
        pageNum: 1,
        pageSize: 100
      },
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 采购发货信息表格数据
      reportList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 部门时间范围
      daterangePurchaseTime: (() => currentMonth())(),
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
        purchaseChannelsId: null,
        purchaseAccountId: null,
        purchaseOrder: null,
        supplierName: null,
        shipmentsOrder: null,
        hasReturn: null,
        hasBP: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        hasAfterSale: null,
        deptId: null
      }
    }
  },
  created() {
    this.getList()
    this.getStoreInfoList()
    this.getDeptList()
    this.getPurchaseAccountInfoList()
    this.getServiceUserInfoList()
    this.getChannelsTreeselect()
  },
  methods: {
    parseTime,
    toPercentage,
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
    },
    /** 查询采购渠道信息下拉树结构 */
    getChannelsTreeselect() {
      listPurchaseChannelInfo(this.purchaseChannelQuery).then(response => {
        this.purchaseChannelInfoOptions = []
        const data = { id: 0, channelName: '顶级节点', children: [] }
        data.children = this.handleTree(response.data, 'id', 'parentId')
        this.purchaseChannelInfoOptions.push(data)
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
    /**
     * 获取采购账号推荐列表
     */
    selectPurchaseAccountInfoList(query) {
      if (query !== '') {
        this.purchaseAccountInfoLoading = true
        this.purchaseAccountInfoQueryParams.purchaseAccount = query
        setTimeout(() => {
          this.getPurchaseAccountInfoList()
        }, 200)
      } else {
        this.purchaseAccountInfoList = []
      }
    },
    /** 获取采购账号信息列表 */
    getPurchaseAccountInfoList() {

      if (this.purchaseAccountInfoQueryParams.purchaseAccount !== '') {
        this.purchaseAccountInfoQueryParams.id = null
      }
      listPurchaseAccountInfo(this.purchaseAccountInfoQueryParams).then(res => {
        this.purchaseAccountInfoList = res?.rows
        this.purchaseAccountInfoLoading = false
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
        this.storeInfoQueryParams.id = null
      }
    },
    /**
     * 获取店铺信息列表
     */
    getStoreInfoList() {
      listStoreInfo(this.storeInfoQueryParams).then(res => {
        this.storeInfoList = res?.rows
        this.storeInfoLoading = false
      })
    },
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
      allocatedUserList(this.serviceUserQueryParams).then(res => {
        this.serviceUserInfoList = res?.rows
        this.serviceUserLoading = false
      })
    },
    /** 查询采购发货信息列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangePurchaseTime && '' != this.daterangePurchaseTime) {
        this.queryParams.params['beginPurchaseTime'] = this.daterangePurchaseTime[0]
        this.queryParams.params['endPurchaseTime'] = this.daterangePurchaseTime[1]
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      getServiceReport(this.queryParams).then(response => {
        this.reportList = response.rows
        this.loading = false
      })
      this.getCount()
    },
    getCount() {
      getPurchaseOrderInfoCount(this.queryParams).then(response => {
        this.purchaseOrderInfoCount = response.data
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangePurchaseTime = []
      this.daterangeCreateTime = []
      this.daterangeUpdateTime = []
      this.purchaseChannelQuery = {}
      this.queryParams.purchaseChannelsId = null
      this.getChannelsTreeselect()
      this.purchaseAccountInfoQueryParams = {}
      this.getPurchaseAccountInfoList()
      this.resetForm('queryForm')
      this.handleQuery()
    }
  }
}
</script>
<style>
.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 100%;
}
</style>
