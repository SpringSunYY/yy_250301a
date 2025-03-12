import request from '@/utils/request'

// 查询采购发货信息列表
export function listPurchaseOrderInfo(query) {
  return request({
    url: '/manage/purchaseOrderInfo/list',
    method: 'get',
    params: query
  })
}
// 查询采购发货信息列表
export function getPurchaseOrderInfoCount(query) {
  return request({
    url: '/manage/purchaseOrderInfo/getPurchaseOrderInfoCount',
    method: 'get',
    params: query
  })
}
// 查询采购发货信息报表
export function getDeptReport(query) {
  return request({
    url: '/manage/purchaseOrderInfo/dept/getReport',
    method: 'get',
    params: query
  })
}

// 查询采购发货信息报表-客服
export function getServiceReport(query) {
  return request({
    url: '/manage/purchaseOrderInfo/service/getReport',
    method: 'get',
    params: query
  })
}

// 查询采购发货信息详细
export function getPurchaseOrderInfo(id) {
  return request({
    url: '/manage/purchaseOrderInfo/' + id,
    method: 'get'
  })
}

// 新增采购发货信息
export function addPurchaseOrderInfo(data) {
  return request({
    url: '/manage/purchaseOrderInfo',
    method: 'post',
    data: data
  })
}

// 修改采购发货信息
export function updatePurchaseOrderInfo(data) {
  return request({
    url: '/manage/purchaseOrderInfo',
    method: 'put',
    data: data
  })
}

// 删除采购发货信息
export function delPurchaseOrderInfo(id) {
  return request({
    url: '/manage/purchaseOrderInfo/' + id,
    method: 'delete'
  })
}
