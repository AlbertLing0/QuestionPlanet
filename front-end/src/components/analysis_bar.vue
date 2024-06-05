<template>
    <div ref="chartRef" style="width: 100%; height: 400px;"></div> <!-- ECharts 容器 -->
  </template>
  
  <script>
  import * as echarts from 'echarts';
  
  export default {
    name: 'MyChartComponent',
    mounted() {
      this.initChart();
    },
    methods: {
      initChart() {
        const myChart = echarts.init(this.$refs.chartRef); // 使用 ref 引用的 DOM 元素初始化 ECharts 实例
        // this.option.xAxis.data = ['奉节一中','奉节一中','奉节一中','奉节一中','aa','hh'];
        // this.option.series[0].data = [50,50,80,80,70,90];
        this.option.xAxis.data = this.xAxisData; // 替换为 this.xAxisData
        this.option.series[0].data = this.seriesData; // 替换为 this.seriesDa
        myChart.setOption(this.option); // 设置图表选项
      },
    },
    data() {
      return {
        option: {
            backgroundColor:'#fff',
            tooltip: {
                trigger: 'item',
                formatter: '{b}<br/>' + '该项占比为' + '{c}%',
                position: 'top',
                backgroundColor: 'rgba(0,0,0,.5)',
                borderColor: 'rgba(255,255,255,0)',
                color: '#fff',
                extraCssText: 'box-shadow: 0 0 0 rgba(255,255,255, .5); color: #fff;',
                zIndex: 400
            },
            xAxis: {
                type: 'category',
                data: [],
                axisLabel: {
                    fontSize: 14,
                    color: '#333',
                    interval: 0,
                    rotate: 0,
                    lineHeight: 15,
                    formatter: function (value) {
                        return value.split('').join('\n')
                    }
                }
            },
            yAxis: {
                type: 'value',
                max: 100,
                min: 0,
                boundaryGap: true,
                axisLabel: {
                    fontSize: 12,
                    color: '#fff'  ////hhh
                },
                axisLine: {
                    show: true
                },
                splitLine: {
                    show: false
                }
            },
            grid: {
                top: '15%',
                left: '5%',
                right: '5%',
                bottom: '5%',
                containLabel: true
            },
            series: [
                {
                    data: [],
                    type: 'bar',
                    barWidth: 30,
                    emphasis: {
                        itemStyle: {
                            borderWidth: 2,
                            borderColor: '#3DE3F5',
                            shadowBlur: 10,
                            shadowColor: '#333'//rgba(255,255,255,255.3)
                        }
                    },
                    itemStyle: {
                        color: function (params) {
                            var colorList1 = ['#F408AB', '#4F09F3', '#ED2D98', '#0055CB', '#F408AB', '#4F09F3', '#ED2D98', '#0055CB', '#F408AB', '#4F09F3', '#ED2D98', '#0055CB'];
                            var colorList2 = ['#27B7FF', '#8CA2FF', '#FF793B', '#00EAD1', '#27B7FF', '#8CA2FF', '#FF793B', '#00EAD1', '#27B7FF', '#8CA2FF', '#FF793B', '#00EAD1'];
                            return new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                { offset: 0, color: colorList1[params.dataIndex] },
                                { offset: 1, color: colorList2[params.dataIndex] }
                            ])
                        },
                        barBorderRadius: [50, 50, 0, 0] // 统一设置四个角的圆角大小
                    },
                    label: {
                        normal: {
                            position: 'top',
                            show: true,
                            color: '#333',
                            fontSize: 14,
                            formatter: function (params) {
                                return params.data + '%'
                            }
                        }
                    }
                }
            ]
        },
      };
    },
    props: {
        xAxisData: {
            type: Array,
            default: () => []
        },
        seriesData: {
            type: Array,
            default: () => []
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