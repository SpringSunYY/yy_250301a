export const pickerOptions = {
  shortcuts: [{
    text: '今天',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime())
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '昨天',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24)
      end.setTime(end.getTime() - 3600 * 1000 * 24)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近一周',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近两周',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 14)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '当月',
    onClick(picker) {
      const end = new Date()
      const start = new Date(end.getFullYear(), end.getMonth(), 1)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近一个月',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '上一个月',
    onClick(picker) {
      const end = new Date()
      const start = new Date(end.getFullYear(), end.getMonth() - 1, 1)
      // end 为上一个月最后一天
      end.setTime(new Date(end.getFullYear(), end.getMonth(), 0).getTime())
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近三个月',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近半年',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 180)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '本年',
    onClick(picker) {
      const end = new Date()
      const start = new Date(end.getFullYear(), 0, 1)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '最近一年',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 365)
      picker.$emit('pick', [start, end])
    }
  }, {
    text: '去年',
    onClick(picker) {
      const end = new Date()
      const start = new Date(end.getFullYear() - 1, 0, 1)
      // end 为去年最后一天
      end.setTime(new Date(end.getFullYear() - 1, 11, 31).getTime())
      picker.$emit('pick', [start, end])
    }
  }
  ]
}

// 获取本月时间范围（本地时区）
export const currentMonth = () => {
  const today = new Date();
  // 获取本月第一天（注意月份从0开始，日期从1开始）
  const firstDayOfMonth = new Date(today.getFullYear(), today.getMonth(), 1);

  // 使用本地时间生成日期字符串（避免UTC时区问题）
  const format = (date) =>
    `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;

  return [format(firstDayOfMonth), format(today)];
};
