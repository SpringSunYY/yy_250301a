import request from '@/utils/request'

// 查询售后订单信息列表
export function listAfterSaleOrderInfo(query) {
  return request({
    url: '/manage/afterSaleOrderInfo/list',
    method: 'get',
    params: query
  })
}

export function getAfterSaleOrderCount(query) {
  return request({
    url: '/manage/afterSaleOrderInfo/getAfterSaleOrderCount',
    method: 'get',
    params: query
  })
}

// 查询售后订单信息详细
export function getAfterSaleOrderInfo(id) {
  return request({
    url: '/manage/afterSaleOrderInfo/' + id,
    method: 'get'
  })
}

// 查询售后订单信息详细
export function getAfterSaleOrderInfoByOrderNumber(orderNumber) {
  return request({
    url: '/manage/afterSaleOrderInfo/orderNumber/' + orderNumber,
    method: 'get'
  })
}

export function addOrUpdateAfterSaleOrderInfo(data){
  return request({
    url: '/manage/afterSaleOrderInfo/addOrUpdate',
    method: 'post',
    data: data
  })
}

// 新增售后订单信息
export function addAfterSaleOrderInfo(data) {
  return request({
    url: '/manage/afterSaleOrderInfo',
    method: 'post',
    data: data
  })
}

// 修改售后订单信息
export function updateAfterSaleOrderInfo(data) {
  return request({
    url: '/manage/afterSaleOrderInfo',
    method: 'put',
    data: data
  })
}

// 删除售后订单信息
export function delAfterSaleOrderInfo(id) {
  return request({
    url: '/manage/afterSaleOrderInfo/' + id,
    method: 'delete'
  })
}
