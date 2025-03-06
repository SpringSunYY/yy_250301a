import request from '@/utils/request'

// 查询空包/快递充值记录列表
export function listEmptyBagCourierHistoryInfo(query) {
  return request({
    url: '/manage/emptyBagCourierHistoryInfo/list',
    method: 'get',
    params: query
  })
}

export function getEmptyBagCourierHistoryCount(query) {
  return request({
    url: '/manage/emptyBagCourierHistoryInfo/getEmptyBagCourierHistoryCount',
    method: 'get',
    params: query
  })
}

// 查询空包/快递充值记录详细
export function getEmptyBagCourierHistoryInfo(id) {
  return request({
    url: '/manage/emptyBagCourierHistoryInfo/' + id,
    method: 'get'
  })
}

// 新增空包/快递充值记录
export function addEmptyBagCourierHistoryInfo(data) {
  return request({
    url: '/manage/emptyBagCourierHistoryInfo',
    method: 'post',
    data: data
  })
}

// 修改空包/快递充值记录
export function updateEmptyBagCourierHistoryInfo(data) {
  return request({
    url: '/manage/emptyBagCourierHistoryInfo',
    method: 'put',
    data: data
  })
}

// 删除空包/快递充值记录
export function delEmptyBagCourierHistoryInfo(id) {
  return request({
    url: '/manage/emptyBagCourierHistoryInfo/' + id,
    method: 'delete'
  })
}
