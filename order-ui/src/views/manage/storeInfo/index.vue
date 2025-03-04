<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="主管" prop="principalId">
        <el-select
          v-model="queryParams.principalId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入用户账号"
          :remote-method="selectPrincipalUserInfoList"
          :loading="principalUserLoading"
        >
          <el-option
            v-for="item in principalUserInfoList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="运营" prop="operationId">
        <el-select
          v-model="queryParams.operationId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入用户账号"
          :remote-method="selectOperationUserInfoList"
          :loading="operationUserLoading"
        >
          <el-option
            v-for="item in operationUserInfoList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="客服" prop="serviceId">
        <el-select
          v-model="queryParams.serviceId"
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
      <el-form-item label="店铺名称" prop="storeName">
        <el-input
          v-model="queryParams.storeName"
          placeholder="请输入店铺名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择店铺状态" clearable>
          <el-option
            v-for="dict in dict.type.o_store_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="手机号" prop="storePhone">
        <el-input
          v-model="queryParams.storePhone"
          placeholder="请输入店铺手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付宝" prop="alipayAccount">
        <el-input
          v-model="queryParams.alipayAccount"
          placeholder="请输入支付宝账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="支付宝手机号" prop="alipayPhone">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.alipayPhone"-->
      <!--          placeholder="请输入支付宝手机号"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="支付宝认证人" prop="alipayAuthenticator">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.alipayAuthenticator"-->
      <!--          placeholder="请输入支付宝认证人"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="支付宝提供人" prop="alipayProvider">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.alipayProvider"-->
      <!--          placeholder="请输入支付宝提供人"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="服务器IP" prop="serverIp">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.serverIp"-->
      <!--          placeholder="请输入服务器IP"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="渠道" prop="channels">
        <el-input
          v-model="queryParams.channels"
          placeholder="请输入渠道"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否结佣" prop="isCommissionSettlement">
        <el-select v-model="queryParams.isCommissionSettlement" placeholder="请选择是否结佣" clearable>
          <el-option
            v-for="dict in dict.type.o_common_whether"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="下店时间">
        <el-date-picker
          v-model="daterangeDepartureTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <!--      <el-form-item label="营业执照名称" prop="businessLicenseName">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.businessLicenseName"-->
      <!--          placeholder="请输入营业执照名称"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="法人" prop="legalPerson">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.legalPerson"-->
      <!--          placeholder="请输入法人"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="到期时间">-->
      <!--        <el-date-picker-->
      <!--          v-model="daterangeExpireTime"-->
      <!--          style="width: 240px"-->
      <!--          value-format="yyyy-MM-dd"-->
      <!--          type="daterange"-->
      <!--          range-separator="-"-->
      <!--          start-placeholder="开始日期"-->
      <!--          end-placeholder="结束日期"-->
      <!--        ></el-date-picker>-->
      <!--      </el-form-item>-->
      <el-form-item label="店铺订单" prop="isOrderAccomplish">
        <el-select v-model="queryParams.isOrderAccomplish" placeholder="请选择店铺订单是否处理完毕" clearable>
          <el-option
            v-for="dict in dict.type.o_common_whether"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="诚意赊" prop="isBonaFideRedemption">
        <el-select v-model="queryParams.isBonaFideRedemption" placeholder="请选择诚意赊是否关闭" clearable>
          <el-option
            v-for="dict in dict.type.o_common_whether"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="保证金" prop="isBail">
        <el-select v-model="queryParams.isBail" placeholder="请选择保证金是否退出" clearable>
          <el-option
            v-for="dict in dict.type.o_common_whether"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="支付宝" prop="isAlipay">
        <el-select v-model="queryParams.isAlipay" placeholder="请选择支付宝是否解绑" clearable>
          <el-option
            v-for="dict in dict.type.o_common_whether"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
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
          v-hasPermi="['manage:storeInfo:add']"
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
          v-hasPermi="['manage:storeInfo:edit']"
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
          v-hasPermi="['manage:storeInfo:remove']"
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
          v-hasPermi="['manage:storeInfo:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['manage:storeInfo:import']"
        >导入
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="storeInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="主管" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="principalName"
      />
      <el-table-column label="运营" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="operationName"
      />
      <el-table-column label="客服" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="serviceName"
      />
      <el-table-column label="店铺名称" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="storeName"
      />
      <el-table-column label="店铺状态" align="center" v-if="columns[5].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_store_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="店铺密码" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="storePassword"
      />
      <el-table-column label="店铺手机号" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="storePhone"
      />
      <el-table-column label="支付宝账号" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="alipayAccount"
      />
      <el-table-column label="支付宝登录密码" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="alipayPassword"
      />
      <el-table-column label="支付宝支付密码" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="alipayPayPassword"
      />
      <el-table-column label="支付宝手机号" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="alipayPhone"
      />
      <el-table-column label="支付宝认证人" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="alipayAuthenticator"
      />
      <el-table-column label="支付宝提供人" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="alipayProvider"
      />
      <el-table-column label="服务器IP" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="serverIp"
      />
      <el-table-column label="服务器费用" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"
                       prop="serverCost"
      />
      <el-table-column label="服务器密码" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="serverPassword"
      />
      <el-table-column label="渠道" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible"
                       prop="channels"
      />
      <el-table-column label="开通金额" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible"
                       prop="openPrice"
      />
      <el-table-column label="是否结佣" align="center" v-if="columns[19].visible" prop="isCommissionSettlement">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_common_whether" :value="scope.row.isCommissionSettlement"/>
        </template>
      </el-table-column>
      <el-table-column label="下店时间" align="center" v-if="columns[20].visible" prop="departureTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.departureTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="营业执照名称" :show-overflow-tooltip="true" align="center" v-if="columns[21].visible"
                       prop="businessLicenseName"
      />
      <el-table-column label="法人" :show-overflow-tooltip="true" align="center" v-if="columns[22].visible"
                       prop="legalPerson"
      />
      <el-table-column label="到期时间" align="center" v-if="columns[23].visible" prop="expireTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expireTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="店铺订单是否处理完毕" align="center" v-if="columns[24].visible" prop="isOrderAccomplish">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_common_whether" :value="scope.row.isOrderAccomplish"/>
        </template>
      </el-table-column>
      <el-table-column label="诚意赊是否关闭" align="center" v-if="columns[25].visible" prop="isBonaFideRedemption">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_common_whether" :value="scope.row.isBonaFideRedemption"/>
        </template>
      </el-table-column>
      <el-table-column label="保证金是否退出" align="center" v-if="columns[26].visible" prop="isBail">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_common_whether" :value="scope.row.isBail"/>
        </template>
      </el-table-column>
      <el-table-column label="支付宝是否解绑" align="center" v-if="columns[27].visible" prop="isAlipay">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.o_common_whether" :value="scope.row.isAlipay"/>
        </template>
      </el-table-column>
      <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[28].visible"
                       prop="deptName"
      />
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[29].visible"
                       prop="userName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[30].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[31].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[32].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[33].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:storeInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:storeInfo:remove']"
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

    <!-- 添加或修改店铺信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1100px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="部门" prop="deptId">
              <treeselect v-model="form.deptId"
                          :options="deptOptions" :show-count="true" :normalizer="normalizer"
                          placeholder="请选择所属位置"

              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="主管" prop="principalId">
              <el-select
                v-model="form.principalId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入用户账号"
                :remote-method="selectPrincipalUserInfoList"
                :loading="principalUserLoading"
              >
                <el-option
                  v-for="item in principalUserInfoList"
                  :key="item.userId"
                  :label="item.userName"
                  :value="item.userId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="运营" prop="operationId">
              <el-select
                v-model="form.operationId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入用户账号"
                :remote-method="selectOperationUserInfoList"
                :loading="operationUserLoading"
              >
                <el-option
                  v-for="item in operationUserInfoList"
                  :key="item.userId"
                  :label="item.userName"
                  :value="item.userId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客服" prop="serviceId">
              <el-select
                v-model="form.serviceId"
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
        <el-row :gutter="10">
          <el-col :span="6">
            <el-form-item label="店铺名称" prop="storeName">
              <el-input v-model="form.storeName" placeholder="请输入店铺名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="店铺状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择店铺状态">
                <el-option
                  v-for="dict in dict.type.o_store_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="店铺密码" prop="storePassword">
              <el-input show-password v-model="form.storePassword" placeholder="请输入店铺密码"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="店铺手机号" prop="storePhone">
              <el-input v-model="form.storePhone" placeholder="请输入店铺手机号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="支付宝账号" prop="alipayAccount">
              <el-input v-model="form.alipayAccount" placeholder="请输入支付宝账号"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="登录密码" prop="alipayPassword">
              <el-input v-model="form.alipayPassword" placeholder="请输入支付宝登录密码" show-password/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="支付密码" prop="alipayPayPassword">
              <el-input show-password v-model="form.alipayPayPassword" placeholder="请输入支付宝支付密码"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="支付宝手机号" prop="alipayPhone">
              <el-input v-model="form.alipayPhone" placeholder="请输入支付宝手机号"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="支付宝认证人" prop="alipayAuthenticator">
              <el-input v-model="form.alipayAuthenticator" placeholder="请输入支付宝认证人"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="支付宝提供人" prop="alipayProvider">
              <el-input v-model="form.alipayProvider" placeholder="请输入支付宝提供人"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="服务器IP" prop="serverIp">
              <el-input v-model="form.serverIp" placeholder="请输入服务器IP"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="服务器费用" prop="serverCost">
              <el-input v-model="form.serverCost" placeholder="请输入服务器费用"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="服务器密码" prop="serverPassword">
              <el-input v-model="form.serverPassword" show-password placeholder="请输入服务器密码"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="6">
            <el-form-item label="营业执照名称" prop="businessLicenseName">
              <el-input v-model="form.businessLicenseName" placeholder="请输入营业执照名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="法人" prop="legalPerson">
              <el-input v-model="form.legalPerson" placeholder="请输入法人"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="开通金额" prop="openPrice">
              <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.openPrice"
                               placeholder="请输入开通金额"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="渠道" prop="channels">
              <el-input v-model="form.channels" placeholder="请输入渠道"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="下店时间" prop="departureTime">
              <el-date-picker clearable
                              v-model="form.departureTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择下店时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到期时间" prop="expireTime">
              <el-date-picker clearable
                              v-model="form.expireTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择到期时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!--          <el-col :span="12">-->
          <!--            <el-form-item label="创建人" prop="userId">-->
          <!--              <el-input v-model="form.userId" placeholder="请输入创建人"/>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
        </el-row>
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="是否结佣" prop="isCommissionSettlement">
              <el-radio-group v-model="form.isCommissionSettlement">
                <el-radio
                  v-for="dict in dict.type.o_common_whether"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="店铺订单" prop="isOrderAccomplish">
              <el-radio-group v-model="form.isOrderAccomplish">
                <el-radio
                  v-for="dict in dict.type.o_common_whether"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="诚意赊" prop="isBonaFideRedemption">
              <el-radio-group v-model="form.isBonaFideRedemption">
                <el-radio
                  v-for="dict in dict.type.o_common_whether"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="保证金" prop="isBail">
              <el-radio-group v-model="form.isBail">
                <el-radio
                  v-for="dict in dict.type.o_common_whether"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="支付宝" prop="isAlipay">
              <el-radio-group v-model="form.isAlipay">
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
        <el-row :gutter="10">
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
    <!-- 店铺导入对话框 -->
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
import { listStoreInfo, getStoreInfo, delStoreInfo, addStoreInfo, updateStoreInfo } from '@/api/manage/storeInfo'
import { allocatedUserList } from '@/api/system/role'
import { listDept } from '@/api/system/dept'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { getToken } from '@/utils/auth'

export default {
  name: 'StoreInfo',
  dicts: ['o_store_status', 'o_common_whether'],
  components: { Treeselect },
  data() {
    return {
      //部门相关信息
      deptOptions: [],
      //主管相关信息
      principalUserInfoList: [],
      principalUserLoading: false,
      principalUserQueryParams: {
        userName: '',
        roleId: 100,
        pageNum: 1,
        pageSize: 10
      },
      //运营相关信息
      operationUserInfoList: [],
      operationUserLoading: false,
      operationUserQueryParams: {
        userName: '',
        roleId: 101,
        pageNum: 1,
        pageSize: 10
      },
      //客服相关信息
      serviceUserInfoList: [],
      serviceUserLoading: false,
      serviceUserQueryParams: {
        userName: '',
        roleId: 102,
        pageNum: 1,
        pageSize: 10
      },
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: false },
        { key: 1, label: '主管', visible: true },
        { key: 2, label: '运营', visible: false },
        { key: 3, label: '客服', visible: false },
        { key: 4, label: '店铺名称', visible: true },
        { key: 5, label: '店铺状态', visible: true },
        { key: 6, label: '店铺密码', visible: false },
        { key: 7, label: '店铺手机号', visible: true },
        { key: 8, label: '支付宝账号', visible: false },
        { key: 9, label: '支付宝登录密码', visible: false },
        { key: 10, label: '支付宝支付密码', visible: false },
        { key: 11, label: '支付宝手机号', visible: false },
        { key: 12, label: '支付宝认证人', visible: false },
        { key: 13, label: '支付宝提供人', visible: false },
        { key: 14, label: '服务器IP', visible: false },
        { key: 15, label: '服务器费用', visible: false },
        { key: 16, label: '服务器密码', visible: false },
        { key: 17, label: '渠道', visible: true },
        { key: 18, label: '开通金额', visible: false },
        { key: 19, label: '是否结佣', visible: true },
        { key: 20, label: '下店时间', visible: true },
        { key: 21, label: '营业执照名称', visible: true },
        { key: 22, label: '法人', visible: false },
        { key: 23, label: '到期时间', visible: false },
        { key: 24, label: '店铺订单是否处理完毕', visible: false },
        { key: 25, label: '诚意赊是否关闭', visible: false },
        { key: 26, label: '保证金是否退出', visible: false },
        { key: 27, label: '支付宝是否解绑', visible: false },
        { key: 28, label: '部门', visible: true },
        { key: 29, label: '创建人', visible: false },
        { key: 30, label: '创建时间', visible: false },
        { key: 31, label: '更新人', visible: false },
        { key: 32, label: '更新时间', visible: false },
        { key: 33, label: '备注', visible: false }
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
      // 店铺信息表格数据
      storeInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeDepartureTime: [],
      // 备注时间范围
      daterangeExpireTime: [],
      // 备注时间范围
      daterangeCreateTime: [],
      // 备注时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        principalId: null,
        operationId: null,
        serviceId: null,
        storeName: null,
        status: null,
        storePhone: null,
        alipayAccount: null,
        alipayPhone: null,
        alipayAuthenticator: null,
        alipayProvider: null,
        serverIp: null,
        channels: null,
        isCommissionSettlement: null,
        departureTime: null,
        businessLicenseName: null,
        legalPerson: null,
        expireTime: null,
        isOrderAccomplish: null,
        isBonaFideRedemption: null,
        isBail: null,
        isAlipay: null,
        deptId: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        principalId: [
          { required: true, message: '主管不能为空', trigger: 'blur' }
        ],
        storeName: [
          { required: true, message: '店铺名称不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '店铺状态不能为空', trigger: 'change' }
        ],
        channels: [
          { required: true, message: '渠道不能为空', trigger: 'blur' }
        ],
        departureTime: [
          { required: true, message: '下店时间不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '部门不能为空', trigger: 'blur' }
        ]
      },
      // 店铺导入参数
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
        url: process.env.VUE_APP_BASE_API + '/manage/storeInfo/importData'
      },
    }
  },
  created() {
    this.getList()
    this.getDeptList()
  },
  methods: {
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
     * 获取主管用户列表推荐
     * @param query
     */
    selectPrincipalUserInfoList(query) {
      if (query !== '') {
        this.principalUserLoading = true
        this.principalUserQueryParams.userName = query
        setTimeout(() => {
          this.getPrincipalUserInfoList()
        }, 200)
      } else {
        this.principalUserInfoList = []
        this.principalUserQueryParams.userName = null
      }
    },
    /**
     * 获取主管用户信息列表
     */
    getPrincipalUserInfoList() {
      //添加查询参数
      if (this.form.principalId != null) {
        this.principalUserQueryParams.userId = this.form.principalId
      } else {
        this.principalUserQueryParams.userId = null
      }
      if (this.principalUserQueryParams.userName !== '') {
        this.principalUserQueryParams.userId = null
      }
      allocatedUserList(this.principalUserQueryParams).then(res => {
        this.principalUserInfoList = res?.rows
        this.principalUserLoading = false
      })
    },
    /**
     * 获取运营用户列表推荐
     * @param query
     */
    selectOperationUserInfoList(query) {
      if (query !== '') {
        this.operationUserLoading = true
        this.operationUserQueryParams.userName = query
        setTimeout(() => {
          this.getOperationUserInfoList()
        }, 200)
      } else {
        this.operationUserInfoList = []
        this.operationUserQueryParams.userName = null
      }
    },
    /**
     * 获取运营用户信息列表
     */
    getOperationUserInfoList() {
      //添加查询参数
      if (this.form.operationId != null) {
        this.operationUserQueryParams.userId = this.form.operationId
      } else {
        this.operationUserQueryParams.userId = null
      }
      if (this.operationUserQueryParams.userName !== '') {
        this.operationUserQueryParams.userId = null
      }
      allocatedUserList(this.operationUserQueryParams).then(res => {
        this.operationUserInfoList = res?.rows
        this.operationUserLoading = false
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
      if (this.form.serviceId != null) {
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
    /** 查询店铺信息列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeDepartureTime && '' != this.daterangeDepartureTime) {
        this.queryParams.params['beginDepartureTime'] = this.daterangeDepartureTime[0]
        this.queryParams.params['endDepartureTime'] = this.daterangeDepartureTime[1]
      }
      if (null != this.daterangeExpireTime && '' != this.daterangeExpireTime) {
        this.queryParams.params['beginExpireTime'] = this.daterangeExpireTime[0]
        this.queryParams.params['endExpireTime'] = this.daterangeExpireTime[1]
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      listStoreInfo(this.queryParams).then(response => {
        this.storeInfoList = response.rows
        this.total = response.total
        this.loading = false
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
        principalId: null,
        operationId: null,
        serviceId: null,
        storeName: null,
        status: null,
        storePassword: null,
        storePhone: null,
        alipayAccount: null,
        alipayPassword: null,
        alipayPayPassword: null,
        alipayPhone: null,
        alipayAuthenticator: null,
        alipayProvider: null,
        serverIp: null,
        serverCost: null,
        serverPassword: null,
        channels: null,
        openPrice: null,
        isCommissionSettlement: null,
        departureTime: null,
        businessLicenseName: null,
        legalPerson: null,
        expireTime: null,
        isOrderAccomplish: null,
        isBonaFideRedemption: null,
        isBail: null,
        isAlipay: null,
        deptId: null,
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
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeDepartureTime = []
      this.daterangeExpireTime = []
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
      this.title = '添加店铺信息'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getStoreInfo(id).then(response => {
        this.form = response.data
        this.getServiceUserInfoList()
        this.getOperationUserInfoList()
        this.getPrincipalUserInfoList()
        this.open = true
        this.title = '修改店铺信息'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStoreInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addStoreInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除店铺信息编号为"' + ids + '"的数据项？').then(function() {
        return delStoreInfo(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/storeInfo/export', {
        ...this.queryParams
      }, `storeInfo_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '店铺导入'
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('manage/storeInfo/importTemplate', {}, `store_template_${new Date().getTime()}.xlsx`)
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
    },
  }
}
</script>
