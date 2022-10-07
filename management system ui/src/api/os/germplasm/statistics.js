import request from '@/utils/request'

// 杂交组合整体评价
export function listStatisticsHybrid(query) {
  return request({
    url: '/os/statistics/hybrid',
    method: 'get',
    params: query
  })
}

// 单株评价
export function listStatisticsPlant(query) {
  return request({
    url: '/os/statistics/plant',
    method: 'get',
    params: query
  })
}

// 单株评价
export function listStatisticsYear(query) {
  return request({
    url: '/os/statistics/year',
    method: 'get',
    params: query
  })
}
