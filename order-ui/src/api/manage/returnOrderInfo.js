import request from '@/utils/request'

// 查询退货订单信息列表
export function listReturnOrderInfo(query) {
  return request({
    url: '/manage/returnOrderInfo/list',
    method: 'get',
    params: query
  })
}

export function getReturnOrderCount(query) {
  return request({
    url: '/manage/returnOrderInfo/getReturnOrderCount',
    method: 'get',
    params: query
  })
}

// 查询退货订单信息详细
export function getReturnOrderInfo(id) {
  return request({
    url: '/manage/returnOrderInfo/' + id,
    method: 'get'
  })
}
// 查询退货订单信息详细 根据订单号
export function getReturnOrderInfoByOrderNumber(orderNumber) {
  return request({
    url: '/manage/returnOrderInfo/orderNumber/' +orderNumber,
    method: 'get'
  })
}

// 新增退货订单信息
export function addOrUpdateReturnOrderInfo(data) {
  return request({
    url: '/manage/returnOrderInfo/addOrUpdate',
    method: 'post',
    data: data
  })
}
// 新增退货订单信息
export function addReturnOrderInfo(data) {
  return request({
    url: '/manage/returnOrderInfo',
    method: 'post',
    data: data
  })
}

// 修改退货订单信息
export function updateReturnOrderInfo(data) {
  return request({
    url: '/manage/returnOrderInfo',
    method: 'put',
    data: data
  })
}

// 删除退货订单信息
export function delReturnOrderInfo(id) {
  return request({
    url: '/manage/returnOrderInfo/' + id,
    method: 'delete'
  })
}
