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
        <el-select :disabled="true" v-model="queryParams.orderType" placeholder="请选择销售类型" clearable>
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
      <el-form-item label="买家" prop="buyerNumber">
        <el-input
          v-model="queryParams.buyerNumber"
          placeholder="请输入买家"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="采购渠道" prop="purchaseChannelDetail">
        <el-select v-model="queryParams.purchaseChannelDetail" placeholder="请选择采购渠道" clearable>
          <el-option
            v-for="dict in dict.type.o_purchase_channels"
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
      <el-form-item label="订单编号" prop="purchaseOrder">
        <el-input
          v-model="queryParams.purchaseOrder"
          placeholder="请输入采购订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="supplierName">
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
      <!--      <el-form-item label="创建时间">-->
      <!--        <el-date-picker-->
      <!--          v-model="daterangeCreateTime"-->
      <!--          style="width: 240px"-->
      <!--          value-format="yyyy-MM-dd"-->
      <!--          type="daterange"-->
      <!--          range-separator="-"-->
      <!--          start-placeholder="开始日期"-->
      <!--          end-placeholder="结束日期"-->
      <!--        ></el-date-picker>-->
      <!--      </el-form-item>-->
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
      <el-form-item label="" prop="deptId" style="width: 25%">
        <el-row :gutter="24">
          <el-col :span="6">
            <span style=" font-weight: bold;color: rgb(96, 98, 102)">所属部门</span>
          </el-col>
          <el-col :span="18">
            <treeselect v-model="queryParams.deptId"
                        :options="deptOptions" :show-count="true" :normalizer="normalizer" placeholder="请选择所属位置"

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
          v-hasPermi="['manage:purchaseOrderInfo:add']"
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
          v-hasPermi="['manage:purchaseOrderInfo:edit']"
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
          v-hasPermi="['manage:purchaseOrderInfo:remove']"
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
          v-hasPermi="['manage:purchaseOrderInfo:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportDetail"
          v-hasPermi="['manage:purchaseOrderInfo:export']"
        >导出详情
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['manage:purchaseOrderInfo:import']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="15">
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
        >订单利润：{{ purchaseOrderInfoCount.orderProfitCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >销售量：{{ purchaseOrderInfoCount.salesNumberCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >销售价：{{ purchaseOrderInfoCount.salesPriceCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >采购进价：{{ purchaseOrderInfoCount.purchasePriceCount }}
        </el-button>

        <el-button
          type="success"
          plain
          size="mini"
        >采购补价：{{ purchaseOrderInfoCount.purchasePremiumCount }}
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseOrderInfoList" :border="true"
              @selection-change="handleSelectionChange"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand" label-width="100px">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="编号">
                  <span>{{ props.row.id }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="采购编号">
                  <span>{{ props.row.orderNumber }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="销售类型">
                  <span><dict-tag :options="dict.type.o_order_type" :value="props.row.orderType"/></span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="订单利润">
                  <span>{{ props.row.orderProfit }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="利润率">
                  <span>{{ toPercentage(props.row.orderProfitRate) }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="采购日期">
                  <span>{{ parseTime(props.row.purchaseTime, '{y}-{m}-{d}') }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="店铺名称">
                  <span>{{ props.row.storeName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="买家">
                  <span>{{ props.row.buyerNumber }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="销售量">
                  <span>{{ props.row.salesNumber }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="销售价">
                  <span>{{ props.row.salesPrice }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="采购渠道分类">
                    <span> <dict-tag :options="dict.type.o_purchase_channel_type"
                                     :value="props.row.purchaseChannelType"
                    /></span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="采购渠道">
                  <span>{{ props.row.purchaseChannelDetail }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="采购账号">
                  <span>{{ props.row.purchaseAccount }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="采购订单编号">
                  <span>{{ props.row.purchaseOrder }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="供应商名称">
                  <span>{{ props.row.supplierName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="采购进价">
                  <span>{{ props.row.purchasePrice }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="采购补价">
                  <span>{{ props.row.purchasePremium }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="发货单号">
                  <span>{{ props.row.shipmentsOrder }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="是否退货">
                  <span><dict-tag :options="dict.type.o_common_whether" :value="props.row.hasReturn"/></span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="是否白嫖">
                  <span><dict-tag :options="dict.type.o_common_whether" :value="props.row.hasBP"/></span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="退货状态">
                  <span><dict-tag :options="dict.type.o_return_order_status" :value="props.row.returnStatus"/></span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="客户退货金额">
                  <span>{{ props.row.returnPrice }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="上家退款金额">
                  <span>{{ props.row.lastReturnPrice }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="退货完成日期">
                  <span>{{ parseTime(props.row.returnAccomplishTime, '{y}-{m}-{d}') }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="白嫖退款金额">
                  <span>{{ props.row.bPPrice }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="白嫖退款日期">
                  <span>{{ parseTime(props.row.bPTime, '{y}-{m}-{d}') }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="售后金额">
                  <span>{{ props.row.afterSalePrice }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="售后日期">
                  <span>{{ parseTime(props.row.afterSaleTime, '{y}-{m}-{d}') }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="部门">
                  <span>{{ props.row.deptName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="客服">
                  <span>{{ props.row.userName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="创建时间">
                  <span>{{ parseTime(props.row.createTime, '{y}-{m}-{d}') }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="更新人">
                  <span>{{ props.row.updateBy }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="更新时间">
                  <span>{{ parseTime(props.row.updateTime, '{y}-{m}-{d}') }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="售后凭证">
                  <span><image-preview :src="props.row.afterSaleImage" :width="50" :height="50"/></span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="备注">
                  <span>{{ props.row.remark }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="采购编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="orderNumber"
      >
        <template slot="header">
          <div class="custom-header">
            <span>订单编号</span>
            <el-tooltip
              effect="light"
              placement="top"
              content="线下的订单直接用发货快递单号"
            >
              <!-- 红色问号图标 -->
              <i class="el-icon-question" style="color:#F56C6C;margin-left:5px;cursor:pointer"/>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="销售类型" align="center" v-if="columns[2].visible" prop="orderType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_order_type" :value="scope.row.orderType"/>
        </template>
      </el-table-column>
      <el-table-column label="订单利润" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="orderProfit"
      >
        <template slot="header">
          <div class="custom-header">
            <span>订单利润</span>
            <el-tooltip
              effect="light"
              placement="top"
              content="销售价格-采购进价-客户退货金额-客户白仅退款金额-售后补偿金额-采购补价+上家退款金额"
            >
              <!-- 红色问号图标 -->
              <i class="el-icon-question" style="color:#F56C6C;margin-left:5px;cursor:pointer"/>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="利润率" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="orderProfitRate"
      >
        <template slot="header">
          <div class="custom-header">
            <span>利润率</span>
            <el-tooltip
              effect="light"
              placement="top"
              content="利润/销售价格"
            >
              <!-- 红色问号图标 -->
              <i class="el-icon-question" style="color:#F56C6C;margin-left:5px;cursor:pointer"/>
            </el-tooltip>
          </div>
        </template>
        <template slot-scope="scope">
          <span>{{ toPercentage(scope.row.orderProfitRate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="采购日期" align="center" v-if="columns[5].visible" prop="purchaseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="店铺名称" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="storeName"
      />
      <el-table-column label="买家" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="buyerNumber"
      >
        <template slot="header">
          <div class="custom-header">
            <span>买家</span>
            <el-tooltip
              effect="light"
              placement="top"
              content="线上买家旺旺（id）/线下客户名称"
            >
              <!-- 红色问号图标 -->
              <i class="el-icon-question" style="color:#F56C6C;margin-left:5px;cursor:pointer"/>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="销售量" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="salesNumber"
      />
      <el-table-column label="销售价" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="salesPrice"
      >
        <template slot="header">
          <div class="custom-header">
            <span>销售价</span>
            <el-tooltip
              effect="light"
              placement="top"
              content="销售价 【含运费】"
            >
              <!-- 红色问号图标 -->
              <i class="el-icon-question" style="color:#F56C6C;margin-left:5px;cursor:pointer"/>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
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
      <el-table-column label="采购账号" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="purchaseAccount"
      />
      <el-table-column label="采购订单编号" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="purchaseOrder"
      />
      <el-table-column label="供应商名称" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="supplierName"
      />
      <el-table-column label="采购进价" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"
                       prop="purchasePrice"
      />
      <el-table-column label="采购补价" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="purchasePremium"
      >
        <template slot="header">
          <div class="custom-header">
            <span>采购补价</span>
            <el-tooltip
              effect="light"
              placement="top"
              content="采购补价（运费或者加收等【支出+，收入-}】）"
            >
              <!-- 红色问号图标 -->
              <i class="el-icon-question" style="color:#F56C6C;margin-left:5px;cursor:pointer"/>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="发货单号" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible"
                       prop="shipmentsOrder"
      />
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
      <el-table-column label="客服" :show-overflow-tooltip="true" align="center" v-if="columns[20].visible"
                       prop="userName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[21].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[22].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[23].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[24].visible"
                       prop="remark"
      />
      <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[25].visible"
                       prop="deptName"
      />
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:purchaseOrderInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:purchaseOrderInfo:remove']"
          >删除
          </el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)"
                       v-hasPermi="['manage:returnOrderInfo:add','manage:bPOrderInfo:add']"
          >
            <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleReturnOrder"
                                v-hasPermi="['manage:returnOrderInfo:add']"
              >退货订单
              </el-dropdown-item>
              <el-dropdown-item command="handleBPOrder"
                                v-hasPermi="['manage:bPOrderInfo:add']"
              >白嫖订单
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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
    <el-dialog :title="title" :visible.sync="open" width="1280px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="采购编号" prop="orderNumber">
              <el-input v-model="form.orderNumber" placeholder="请输入采购编号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销售类型" prop="orderType">
              <el-select v-model="form.orderType" :disabled="true" placeholder="请选择销售类型">
                <el-option
                  v-for="dict in dict.type.o_order_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="采购日期" prop="purchaseTime">
              <el-date-picker clearable
                              v-model="form.purchaseTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择采购日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
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
          </el-col>
          <el-col :span="8">
            <el-form-item label="采购渠道" prop="purchaseChannelDetail">
              <el-select v-model="form.purchaseChannelDetail"
                         v-if="form.purchaseChannelType==='1'"
                         placeholder="请选择采购渠道"
              >
                <el-option
                  v-for="dict in dict.type.o_purchase_channels"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
              <el-input v-model="form.purchaseChannelDetail"
                        v-else
                        placeholder="请输入采购渠道"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="采购账号" prop="purchaseAccountId">
              <el-select
                v-model="form.purchaseAccountId"
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
          </el-col>
          <el-col :span="8">
            <el-form-item label="采购订单编号" prop="purchaseOrder">
              <el-input v-model="form.purchaseOrder" placeholder="请输入采购订单编号"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="供应商名称" prop="supplierName">
              <el-input v-model="form.supplierName" placeholder="请输入供应商名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
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
          </el-col>
          <el-col :span="6">
            <el-form-item label="买家" prop="buyerNumber">
              <el-input v-model="form.buyerNumber" placeholder="请输入买家"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="发货单号" prop="shipmentsOrder">
              <el-input v-model="form.shipmentsOrder" placeholder="请输入发货单号"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客服" prop="userId">
              <el-select
                v-model="form.userId"
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
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="销售量" prop="salesNumber">
              <el-input-number :min="0" v-model="form.salesNumber" placeholder="请输入销售量"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="销售价" prop="salesPrice">
              <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.salesPrice"
                               placeholder="请输入销售价"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="采购进价" prop="purchasePrice">
              <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.purchasePrice"
                               placeholder="请输入采购进价"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="采购补价" prop="purchasePremium">
              <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.purchasePremium"
                               placeholder="请输入采购补价"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否退货" prop="hasReturn">
              <el-radio-group v-model="form.hasReturn">
                <el-radio
                  v-for="dict in dict.type.o_common_whether"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否白嫖" prop="hasBP">
              <el-radio-group v-model="form.hasBP">
                <el-radio
                  v-for="dict in dict.type.o_common_whether"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 采购订单导入对话框 -->
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
    <!-- 添加或修改退货订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="returnOrderOpen" width="600px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="订单编号" prop="orderNumber">
          <el-input :readonly="true" v-model="form.orderNumber" placeholder="请输入订单编号"/>
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
        <el-button type="primary" @click="submitReturnForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 添加或修改白嫖订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="bpOrderOpen" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="采购编号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入采购编号"/>
        </el-form-item>
        <el-form-item label="白嫖退款金额" prop="bpprice">
          <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.bpprice" placeholder="请输入白嫖退款金额"/>
        </el-form-item>
        <el-form-item label="白嫖退款日期" prop="bPTime">
          <el-date-picker clearable
                          v-model="form.bPTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择白嫖退款日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="售后金额" prop="afterSalePrice">
          <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.afterSalePrice"
                           placeholder="请输入售后金额"
          />
        </el-form-item>
        <el-form-item label="售后日期" prop="afterSaleTime">
          <el-date-picker clearable
                          v-model="form.afterSaleTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择售后日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="售后凭证" prop="afterSaleImage">
          <image-upload :limit="9" v-model="form.afterSaleImage"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBPForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addPurchaseOrderInfo,
  delPurchaseOrderInfo,
  getPurchaseOrderInfo, getPurchaseOrderInfoCount,
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

export default {
  name: 'PurchaseOrderInfo',
  components: { Treeselect },
  dicts: ['o_purchase_channel_type', 'o_purchase_channels', 'o_order_type', 'o_common_whether', 'o_return_order_status'],
  data() {
    return {
      //统计信息
      purchaseOrderInfoCount: {
        orderCount: 0,
        orderProfitCount: 0,
        salesNumberCount: 0,
        salesPriceCount: 0,
        purchasePriceCount: 0,
        purchasePremiumCount: 0
      },
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
        purchaseAccount: ''
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
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: false },
        { key: 1, label: '采购编号', visible: true },
        { key: 2, label: '销售类型', visible: true },
        { key: 3, label: '订单利润', visible: true },
        { key: 4, label: '利润率', visible: true },
        { key: 5, label: '采购日期', visible: true },
        { key: 6, label: '店铺名称', visible: true },
        { key: 7, label: '买家', visible: true },
        { key: 8, label: '销售量', visible: false },
        { key: 9, label: '销售价', visible: true },
        { key: 10, label: '采购渠道分类', visible: false },
        { key: 11, label: '采购渠道', visible: false },
        { key: 12, label: '采购账号', visible: true },
        { key: 13, label: '采购订单编号', visible: false },
        { key: 14, label: '供应商名称', visible: false },
        { key: 15, label: '采购进价', visible: false },
        { key: 16, label: '采购补价', visible: false },
        { key: 17, label: '发货单号', visible: false },
        { key: 18, label: '是否退货', visible: false },
        { key: 19, label: '是否白嫖', visible: false },
        { key: 20, label: '客服', visible: true },
        { key: 21, label: '创建时间', visible: false },
        { key: 22, label: '更新人', visible: false },
        { key: 23, label: '更新时间', visible: false },
        { key: 24, label: '备注', visible: false },
        { key: 25, label: '部门', visible: false }
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
      title: '',
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
        orderType: '1',
        purchaseTime: null,
        storeId: null,
        buyerNumber: null,
        purchaseChannelType: null,
        purchaseChannelDetail: null,
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
        deptId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNumber: [
          { required: true, message: '采购编号不能为空', trigger: 'blur' }
        ],
        orderType: [
          { required: true, message: '销售类型不能为空', trigger: 'change' }
        ],
        userId: [
          { required: true, message: '客服不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ]
      },
      // 采购账号导入参数
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
        url: process.env.VUE_APP_BASE_API + '/manage/purchaseOrderInfo/importData'
      }
    }
  },
  created() {
    this.getList()
    this.getStoreInfoList()
    this.getDeptList()
    this.getPurchaseAccountInfoList()
    this.getServiceUserInfoList()
  },
  methods: {
    parseTime,
    toPercentage,
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case 'handleReturnOrder':
          this.handleReturnOrder(row)
          break
        case 'handleBPOrder':
          this.handleBPOrder(row)
          break
        default:
          break
      }
    },
    /** 打开白嫖信息 */
    handleBPOrder(row) {
      this.reset()
      getBPOrderInfoByOrderNumber(row.orderNumber).then(res => {
        this.title = '新增或者修改白嫖信息'
        this.bpOrderOpen = true
        if (res.data) {
          this.form = res.data
        }
        this.form.orderNumber = row.orderNumber
      })
    },
    /**
     * 提交白嫖订单信息
     */
    submitBPForm() {
      addOrUpdateBPOrderInfo(this.form).then(res => {
        this.bpOrderOpen = false
        this.getList()
      })
    },
    /** 打开退货操作 */
    handleReturnOrder(row) {
      this.reset()
      getReturnOrderInfoByOrderNumber(row.orderNumber).then(res => {
        this.title = '新增或者修改退货信息'
        this.returnOrderOpen = true
        if (res.data) {
          this.form = res.data
        }
        this.form.orderNumber = row.orderNumber
      })
    },
    /**
     * 提交退货订单信息
     */
    submitReturnForm() {
      addOrUpdateReturnOrderInfo(this.form).then(res => {
        this.returnOrderOpen = false
        this.getList()
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
      //添加查询参数
      if (this.form.purchaseAccountId != null) {
        this.purchaseAccountInfoQueryParams.id = this.form.purchaseAccountId
      }
      if (this.purchaseAccountInfoQueryParams.purchaseAccountId !== '') {
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
      listPurchaseOrderInfo(this.queryParams).then(response => {
        this.purchaseOrderInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
      this.getCount()
    },
    getCount() {
      getPurchaseOrderInfoCount(this.queryParams).then(response => {
        this.purchaseOrderInfoCount = response.data
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.returnOrderOpen = false
      this.bpOrderOpen = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderNumber: null,
        orderType: '1',
        orderProfit: null,
        orderProfitRate: null,
        purchaseTime: null,
        storeId: null,
        buyerNumber: null,
        salesNumber: null,
        salesPrice: null,
        purchaseChannelType: null,
        purchaseChannelDetail: null,
        purchaseAccountId: null,
        purchaseOrder: null,
        supplierName: null,
        purchasePrice: null,
        purchasePremium: null,
        shipmentsOrder: null,
        hasReturn: '2',
        hasBP: '2',
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
      this.daterangePurchaseTime = []
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
      this.open = true
      this.title = '添加采购发货信息'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPurchaseOrderInfo(id).then(response => {
        this.form = response.data
        this.getStoreInfoList()
        this.getServiceUserInfoList()
        this.getPurchaseAccountInfoList()
        this.open = true
        this.title = '修改采购发货信息'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchaseOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPurchaseOrderInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除采购发货信息编号为"' + ids + '"的数据项？').then(function() {
        return delPurchaseOrderInfo(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/purchaseOrderInfo/export', {
        ...this.queryParams
      }, `purchaseOrderInfo_${new Date().getTime()}.xlsx`)
    },
    /** 导出按钮操作 */
    handleExportDetail() {
      this.download('manage/purchaseOrderInfo/export/detail', {
        ...this.queryParams
      }, `purchaseOrderInfo_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '采购订单导入'
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('manage/purchaseOrderInfo/importTemplate', {}, `purchaseOrderInfo_template_${new Date().getTime()}.xlsx`)
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
  width: 50%;
}
</style>
