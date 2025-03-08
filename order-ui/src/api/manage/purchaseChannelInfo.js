import request from '@/utils/request'

// 查询采购渠道信息列表
export function listPurchaseChannelInfo(query) {
  return request({
    url: '/manage/purchaseChannelInfo/list',
    method: 'get',
    params: query
  })
}

// 查询采购渠道信息详细
export function getPurchaseChannelInfo(id) {
  return request({
    url: '/manage/purchaseChannelInfo/' + id,
    method: 'get'
  })
}

// 新增采购渠道信息
export function addPurchaseChannelInfo(data) {
  return request({
    url: '/manage/purchaseChannelInfo',
    method: 'post',
    data: data
  })
}

// 修改采购渠道信息
export function updatePurchaseChannelInfo(data) {
  return request({
    url: '/manage/purchaseChannelInfo',
    method: 'put',
    data: data
  })
}

// 删除采购渠道信息
export function delPurchaseChannelInfo(id) {
  return request({
    url: '/manage/purchaseChannelInfo/' + id,
    method: 'delete'
  })
}
