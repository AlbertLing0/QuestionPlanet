<template>
    <div class="wrapper">
      <!-- 使用 MyChartComponent 组件 -->
      <my-chart-component
        :x-axis-data="customXAxisData"
        :series-data="customSeriesData"
      ></my-chart-component>
    </div>
  </template>
  
  <script>
  import MyChartComponent from '../components/analysis_bar.vue';
  
  export default {
    components: {
      MyChartComponent
    },
    data() {
      return {
        // 自定义 xAxis 数据
        customXAxisData: ['奉节一中', '奉节一中', '奉节一中', '奉节一中', 'aa', 'hh'],
        // 自定义 series 数据
        customSeriesData: [50, 50, 80, 80, 70, 90],
      };
    },
  };
  </script>

<style scoped>
.wrapper {
    padding: 100px;
}
</style>