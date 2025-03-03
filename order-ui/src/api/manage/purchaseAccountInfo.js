import request from '@/utils/request'

// 查询采购账号信息列表
export function listPurchaseAccountInfo(query) {
  return request({
    url: '/manage/purchaseAccountInfo/list',
    method: 'get',
    params: query
  })
}

// 查询采购账号信息详细
export function getPurchaseAccountInfo(id) {
  return request({
    url: '/manage/purchaseAccountInfo/' + id,
    method: 'get'
  })
}

// 新增采购账号信息
export function addPurchaseAccountInfo(data) {
  return request({
    url: '/manage/purchaseAccountInfo',
    method: 'post',
    data: data
  })
}

// 修改采购账号信息
export function updatePurchaseAccountInfo(data) {
  return request({
    url: '/manage/purchaseAccountInfo',
    method: 'put',
    data: data
  })
}

// 删除采购账号信息
export function delPurchaseAccountInfo(id) {
  return request({
    url: '/manage/purchaseAccountInfo/' + id,
    method: 'delete'
  })
}
