<template>
  <div class="app-container home">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="24" :sm="24" :lg="24" class="card-panel-col">
        <el-date-picker
          v-model="daterangePurchaseTime"
          style="width: 240px;float: right;margin-bottom: 10px"
          value-format="yyyy-MM-dd"
          type="daterange"
          @change="changeDateRange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
        ></el-date-picker>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="count" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              订单总数
            </div>
            <count-to :start-val="0"
                      :end-val="Number(Number(onlineOrderCount.orderCount)+Number(offlineOrderCount.orderCount))"
                      :duration="2600"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="ratePrice" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              订单总利润
            </div>
            <count-to :start-val="0"
                      :decimals="2"
                      :end-val="parseFloat(parseFloat(onlineOrderCount.orderProfitCount)+parseFloat(offlineOrderCount.orderProfitCount))"
                      :duration="3000"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="money" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              总销售价
            </div>
            <count-to :start-val="0"
                      :decimals="2"
                      :end-val="parseFloat(parseFloat(onlineOrderCount.salesPriceCount)+parseFloat(offlineOrderCount.salesPriceCount))"
                      :duration="3200"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="shopping" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              订单总销量
            </div>
            <count-to :start-val="0"
                      :end-val="Number(Number(onlineOrderCount.salesNumberCount)+Number(offlineOrderCount.salesNumberCount))"
                      :duration="3600"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="count" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              线上订单总数
            </div>
            <count-to :start-val="0" :end-val="Number(onlineOrderCount.orderCount)" :duration="2600"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="ratePrice" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              线上订单总利润
            </div>
            <count-to :decimals="2" :start-val="0" :end-val="parseFloat(onlineOrderCount.orderProfitCount)"
                      :duration="3000"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="money" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              线上总销售价
            </div>
            <count-to :decimals="2" :start-val="0" :end-val="parseFloat(onlineOrderCount.salesPriceCount)"
                      :duration="3200"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="shopping" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              线上订单总销量
            </div>
            <count-to :start-val="0" :end-val="Number(onlineOrderCount.salesNumberCount)" :duration="3600"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
    </el-row>
    <div style="margin-top: 10px"></div>
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="count" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              线下订单总数
            </div>
            <count-to :start-val="0" :end-val="Number(offlineOrderCount.orderCount)" :duration="2600"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="ratePrice" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              线下订单总利润
            </div>
            <count-to :start-val="0" :decimals="2" :end-val="parseFloat(offlineOrderCount.orderProfitCount)"
                      :duration="3000"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="money" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              线下总销售价
            </div>
            <count-to :start-val="0" :decimals="2" :end-val="parseFloat(offlineOrderCount.salesPriceCount)"
                      :duration="3200" class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="shopping" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              线下订单总销量
            </div>
            <count-to :start-val="0" :end-val="Number(offlineOrderCount.salesNumberCount)" :duration="3600"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
    </el-row>
    <div class="chart-wrapper">
      <LineChartComponent height="500px" :chart-data="chartData" chart-name="采购订单统计"/>
    </div>
  </div>
</template>

<script>
import CountTo from 'vue-count-to'
import LineChartComponent from '@/views/dashboard/LineChartComponent.vue'
import { getUserProfile } from '@/api/system/user'
import { getPurchaseOrderInfoCount } from '@/api/manage/purchaseOrderInfo'
import { getPurchaseOrderStaticData } from '@/api/manage/statics'
import { currentMonth, pickerOptions } from '@/constants/datetime'

export default {
  computed: {
    pickerOptions() {
      return pickerOptions
    }
  },
  components: {
    LineChartComponent,
    CountTo
  },
  name: 'Index',
  data() {
    return {
      chartData: {
        xData: [],
        yData: []
      },
      chartQuery: {},
      onlineOrderCount: {
        orderCount: 0,
        orderProfitCount: 0,
        salesNumberCount: 0,
        salesPriceCount: 0
      },
      queryParamsOnline: {
        orderType: '1',
        params: {}
      },
      offlineOrderCount: {
        orderCount: 0,
        orderProfitCount: 0,
        salesNumberCount: 0,
        salesPriceCount: 0
      },
      queryParamsOffline: {
        orderType: '2',
        params: {}
      },
      daterangePurchaseTime: (() => currentMonth())(),
      user: {},
      // 版本号
      version: '3.8.9'
    }
  },
  created() {
    this.getUser()
    this.getOnlineCount()
    this.getOfflineCount()
    this.getChartData()
  },
  methods: {
    // 日期范围改变时触发
    changeDateRange() {
      this.getStaticData()
    },
    getStaticData() {
      this.getOnlineCount()
      this.getOfflineCount()
      this.getChartData()
    },
    getChartData() {
      if (null != this.queryParamsOnline && '' != this.queryParamsOnline) {
        this.chartQuery.startTime = this.daterangePurchaseTime[0]
        this.chartQuery.endTime = this.daterangePurchaseTime[1]
      }
      getPurchaseOrderStaticData(this.chartQuery).then(res => {
        if (res.code === 200) {
          const data = res.data
          this.chartData = {
            xData: data.names,
            yData: []
          }
          data.totals.forEach((total, index) => {
            var name = '线上订单'
            if (index === 0) {
              name = '线上订单'
            } else {
              name = '线下订单'
            }
            this.chartData.yData.push({
              value: total.totals,
              name: name // 手动设置名称
            })
          })
        } else {
          console.error('获取数据失败:', res.msg)
        }
      }).catch(error => {
        console.error('获取数据失败:', error)
      })
    },

    async getUser() {
      await getUserProfile().then(response => {
        this.user = response.data
        this.roleGroup = response.roleGroup
        this.postGroup = response.postGroup
      })
    }
    ,
    getOnlineCount() {
      if (null != this.queryParamsOnline && '' != this.queryParamsOnline) {
        this.queryParamsOnline.params['beginPurchaseTime'] = this.daterangePurchaseTime[0]
        this.queryParamsOnline.params['endPurchaseTime'] = this.daterangePurchaseTime[1]
      }
      this.queryParamsOnline.orderType = '1'
      getPurchaseOrderInfoCount(this.queryParamsOnline).then(response => {
        this.onlineOrderCount = response.data
      })
    },
    getOfflineCount() {
      if (null != this.queryParamsOffline && '' != this.queryParamsOffline) {
        this.queryParamsOffline.params['beginPurchaseTime'] = this.daterangePurchaseTime[0]
        this.queryParamsOffline.params['endPurchaseTime'] = this.daterangePurchaseTime[1]
      }
      this.queryParamsOffline.orderType = '2'
      getPurchaseOrderInfoCount(this.queryParamsOffline).then(response => {
        this.offlineOrderCount = response.data
      })
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>

