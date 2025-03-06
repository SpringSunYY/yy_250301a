<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import * as echarts from 'echarts'

require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
  name: 'LineChartComponent',
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '350px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: false,
      default: () => {
        return {
          xData: ['2024-10-01', '2024-10-02', '2024-10-03', '2024-10-04', '2024-10-05', '2024-10-06', '2024-10-07', '2024-10-08', '2024-10-09', '2024-10-10', '2024-10-11', '2024-10-12', '2024-10-13', '2024-10-14', '2024-10-15', '2024-10-16', '2024-10-17', '2024-10-18', '2024-10-19', '2024-10-20', '2024-10-21', '2024-10-22', '2024-10-23', '2024-10-24', '2024-10-25', '2024-10-26', '2024-10-27', '2024-10-28', '2024-10-29', '2024-10-30', '2024-10-31'],
          yData: [
            {
              value: [509, 917, 2455, 2610, 2719, 3033, 3044, 3085, 2708, 2809, 2117, 2000, 1455, 1210, 719, 733, 944, 2285, 2208, 3372, 3936, 3693, 2962, 2810, 3519, 2455, 2610, 2719, 2484, 2078],
              name: '新车销量'
            }
          ]
        }
      }
    },
    chartName: {
      type: String,
      default: '则线图',
      required: false
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      if (this.chart) {
        this.chart.dispose() // 销毁旧实例
        this.chart = null
      }
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions()
    },
    setOptions() {
      var that = this
      const xData = that.chartData.xData
      const seriesData = function() {
        var data = []
        for (let i = 0; i < that.chartData.yData.length; i++) {
          var yDatum = that.chartData.yData[i]
          data.push(
            {
              name: yDatum.name,
              type: 'line',
              symbolSize: 10,
              symbol: 'circle',
              // itemStyle: {
              //   color: '#6f7de3'
              // },
              markPoint: {
                label: {
                  textStyle: {
                    color: '#fff'
                  }
                }
              },
              data: yDatum.value
            }
          )
        }
        return data
      }()
      this.chart.setOption({
        title: {
          text: that.chartName,
          color: '#ffffff', // 直接设置颜色
          fontSize: 16, // 直接设置字体大小
          left: '5%',
          top: '5%'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
            textStyle: {
              color: '#fff'
            }

          }
        },
        grid: {
          borderWidth: 0,
          top: 110,
          bottom: 95,
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          lift: 'center',
          top: '11%',
          textStyle: {
            color: '#90979c'
          }
        },

        calculable: true,
        xAxis: [{
          type: 'category',
          axisLine: {
            lineStyle: {
              color: 'rgba(0,0,0,0.73)'
            }
          },
          splitLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          data: xData
        }],

        yAxis: [{
          type: 'value',
          splitLine: {
            show: false
          },
          axisLine: {
            lineStyle: {
              color: 'rgb(0,0,0)'
            }
          }

        }],
        dataZoom: [{
          show: true,
          height: 30,
          xAxisIndex: [0],
          bottom: 30,
          'start': 10,
          'end': 80,
          handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
          handleSize: '110%',
          handleStyle: {
            color: '#5B3AAE'
          },
          textStyle: {
            color: 'rgba(204,187,225,0.5)'
          },
          fillerColor: 'rgba(67,55,160,0.4)',
          borderColor: 'rgba(204,187,225,0.5)'

        }, {
          type: 'inside',
          show: true,
          height: 15,
          start: 1,
          end: 35
        }],
        series: seriesData
      })
    }
  }
}
</script>
