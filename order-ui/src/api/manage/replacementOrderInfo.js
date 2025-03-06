import request from '@/utils/request'

// 查询补单明细列表
export function listReplacementOrderInfo(query) {
  return request({
    url: '/manage/replacementOrderInfo/list',
    method: 'get',
    params: query
  })
}

// 统计总数
export function getReplacementOrderCount(query) {
  return request({
    url: '/manage/replacementOrderInfo/getReplacementOrderCount',
    method: 'get',
    params: query
  })
}

// 查询补单明细详细
export function getReplacementOrderInfo(id) {
  return request({
    url: '/manage/replacementOrderInfo/' + id,
    method: 'get'
  })
}

// 新增补单明细
export function addReplacementOrderInfo(data) {
  return request({
    url: '/manage/replacementOrderInfo',
    method: 'post',
    data: data
  })
}

// 修改补单明细
export function updateReplacementOrderInfo(data) {
  return request({
    url: '/manage/replacementOrderInfo',
    method: 'put',
    data: data
  })
}

// 删除补单明细
export function delReplacementOrderInfo(id) {
  return request({
    url: '/manage/replacementOrderInfo/' + id,
    method: 'delete'
  })
}
