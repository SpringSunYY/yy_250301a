import request from '@/utils/request'

// 查询白嫖订单信息列表
export function listBPOrderInfo(query) {
  return request({
    url: '/manage/bPOrderInfo/list',
    method: 'get',
    params: query
  })
}

// 查询白嫖订单信息详细
export function getBPOrderInfo(id) {
  return request({
    url: '/manage/bPOrderInfo/' + id,
    method: 'get'
  })
}

// 查询白嫖订单信息详细
export function getBPOrderInfoByOrderNumber(orderNumber) {
  return request({
    url: '/manage/bPOrderInfo/orderNumber/' + orderNumber,
    method: 'get'
  })
}

// 新增白嫖订单信息
export function addBPOrderInfo(data) {
  return request({
    url: '/manage/bPOrderInfo',
    method: 'post',
    data: data
  })
}
// 新增或者修改白嫖订单信息
export function addOrUpdateBPOrderInfo(data) {
  return request({
    url: '/manage/bPOrderInfo/addOrUpdate ',
    method: 'post',
    data: data
  })
}

// 修改白嫖订单信息
export function updateBPOrderInfo(data) {
  return request({
    url: '/manage/bPOrderInfo',
    method: 'put',
    data: data
  })
}

// 删除白嫖订单信息
export function delBPOrderInfo(id) {
  return request({
    url: '/manage/bPOrderInfo/' + id,
    method: 'delete'
  })
}
