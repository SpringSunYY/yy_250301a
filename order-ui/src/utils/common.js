/**
 * 将小数转换为百分比字符串
 * @param {number} value - 小数值，例如 0.8 或 1
 * @param {number} [decimalPlaces=0] - 可选参数，保留的小数位数，默认为 0
 * @returns {string} 转换后的百分比字符串，例如 "80%" 或 "100%"
 */
export function toPercentage(value, decimalPlaces = 0) {
  if (typeof value !== 'number') {
    throw new Error('输入值必须是数字')
  }
  const factor = Math.pow(10, decimalPlaces)
  const percentage = Math.round(value * 100 * factor) / factor
  return `${percentage}%`
}
