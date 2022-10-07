import request from '@/utils/request'

// 新增植物
export function addGaojiePlant(data) {
  return request({
    url: '/os/gaojiePlant/add',
    method: 'post',
    data: data
  })
}