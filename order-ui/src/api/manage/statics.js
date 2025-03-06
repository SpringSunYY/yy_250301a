// 查询店铺信息列表
import request from '@/utils/request'

export function getPurchaseOrderStaticData(data) {
  console.log(data)
  return request({
    url: '/manage/statics/day',
    method: 'post',
    data: data
  })
}
