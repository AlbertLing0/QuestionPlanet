<template>
    <div>
      <div id="main" style="width: 600px;height:400px;"></div>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
name: 'PieChart',
mounted() {
    this.initChart();
},
methods: {
    initChart() {
    const myChart = echarts.init(document.getElementById('main'));
    this.option.series[0].data = [
        { value: 40, name: '1', Name: 'hhhh' },
        { value: 38, name: '2', Name: '奉节一中' },
        { value: 32, name: '3', Name: '奉节一中' },
        { value: 30, name: '4', Name: '奉节一中' },
    ]
    myChart.setOption(this.option);
    window.addEventListener('resize', () => {
        myChart.resize();
    });
    }
},
data() {
    return {
        option : {
  backgroundColor: '#fff',
  series: [
    {
      type: 'pie',
      radius: [60, 90],
      center: ['50%', '50%'],
      itemStyle: {
        color: function (params) {
          let colorList1 = ['#0055CB', '#27B7FF', '#8CA2FF', '#ED2D98', '#27B7FF', '#ED2D98','#0055CB','#8CA2FF',];
          let colorList2 = ['#00EAD1', '#F408AB', '#4F09F3', '#FF793B', '#F408AB', '#FF793B','#00EAD1','#4F09F3',];
          return new echarts.graphic.LinearGradient(0, 0, 0.8, 1, [
            { offset: 0, color: colorList1[params.dataIndex] },
            { offset: 1, color: colorList2[params.dataIndex] }
          ]);
        },
        barBorderRadius: [50, 50, 0, 0] // 统一设置四个角的圆角大小
      },
      label: {
        normal: {
          show: true,
          color: '#333',
          fontSize: 14,
          formatter: function (params) {
            return params.data.Name + ' ' + params.data.value + '人' + ' ' + params.data.value + '%';
          }
        }
      },
      labelLine: {
        length: 30,
        color: '#333',
        length2: 0
      },
      data: [],
    },
    {
      type: 'pie',
      radius: [50, 51]
    }
  ]
},
    };
}
};
</script>

<style>
/* 可以添加一些样式 */
</style>