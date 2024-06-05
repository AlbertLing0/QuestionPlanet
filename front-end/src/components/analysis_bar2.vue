<template>
    <div ref="chartRef" style="width: 100%; height: 400px;"></div> <!-- ECharts 容器 -->
  </template>
  
  <script>
  import * as echarts from 'echarts';
  
  export default {
    name: 'MyChartComponent2',
    mounted() {
      this.initChart();
    },
    methods: {
      initChart() {
        const myChart = echarts.init(this.$refs.chartRef); // 使用 ref 引用的 DOM 元素初始化 ECharts 实例
        this.option.yAxis[0].data = ['奉节一中', '奉节一中', '爬行动物', '哺乳动物', '两栖动物'];
        this.option.yAxis[1].data = [1000, 1000, 1000, 1000, 1000];
        this.option.series[0].data = [45, 12, 10, 7, 6];
        // this.option.series[1].data = [55, 88, 90, 93, 94];
        myChart.setOption(this.option); // 设置图表选项
      },
    },
    data() {
      return {

 
        option: {
        backgroundColor: '#fff',
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            top: '2%',
            left: '0%',
            right: '0%',
            bottom: '0%',
            containLabel: true
        },
        xAxis: {
            show: false
        },
        yAxis: [
            {
                type: 'category',
                inverse: true,
                data: [],
                axisLabel: {
                    fontSize: 20,
                    color: '#333'
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false
                }
            },
            {
                type: 'category',
                position: 'right',
                data: [],
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    formatter: '{value}人',
                    fontSize: 14,
                    color: '#333'
                }
            }
        ],
        series: [
            {
                name: '该项总人数',
                type: 'bar',
                stack: 'chart',
                z: 3,
                barWidth: 30,
                itemStyle: {
                    color: function (params) {
                        var colorList1 = ['#F408AB','#4F09F3','#ED2D98','#0055CB','#F408AB','#4F09F3','#ED2D98','#0055CB','#F408AB','#4F09F3','#ED2D98','#0055CB'];
                        var colorList2 = ['#27B7FF','#8CA2FF','#FF793B','#00EAD1','#27B7FF','#8CA2FF','#FF793B','#00EAD1','#27B7FF','#8CA2FF','#FF793B','#00EAD1'];
                        return new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                            { offset: 0, color: colorList1[params.dataIndex] },
                            { offset: 1, color: colorList2[params.dataIndex] }
                        ]);
                    },
                    barBorderRadius: [50, 50, 50, 50] // 统一设置四个角的圆角大小
                },
                label: {
                    normal: {
                        position: 'start',
                        padding: [5, 0, 0, 20],
                        show: true,
                        color: '#fff',
                        fontSize: 14,
                        formatter: function (params) {
                            return params.data + '人';
                        }
                    }
                },
                data: [],
            },
            // {
            //     name: '不占比数量',
            //     type: 'bar',
            //     stack: 'chart',
            //     barWidth: 24,
            //     itemStyle: {
            //         normal: {
            //         color: '#5878A9',
            //         barBorderRadius: [50, 50, 50, 50]
            //         }
            //     },
            //     data: [],
            // }
        ]} 
    }
    },
    beforeDestroy() {
      if (this.chartInstance) {
        this.chartInstance.dispose(); // 销毁图表实例
      }
    },
  };
  </script>
  
  <style scoped>
  /* 你的样式 */
  </style>