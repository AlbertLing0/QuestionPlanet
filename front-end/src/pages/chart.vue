<template>
    <div class="container">
      <div class="content">
        <!-- 使用 v-for 动态生成 el-table-column -->
        <el-table :data="tableData">
          <el-table-column
            v-for="(column, index) in columnList"
            :key="index"
            :prop="column.prop"
            :label="column.label"
            :width="column.width"
            :align="column.align"
          >
          </el-table-column>
        </el-table>
      </div>
    </div>
</template>
  
<script>
export default {
  data() {
    return {
      label: [
        {
          "prop" : "goodsPrice",
          "label" : "hhh",
        },
        {
          "prop" : "goodsNum",
          "label" : "www",
        }
      ],


      tableData: [
        {
          // goodsDetail: {
              goodsImg:'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
              goodsName:'商品名称',
              goodsDesc:'商品详情',
          // },
          goodsPrice: "18188888888888888888888888888888888888888888888",
          goodsNum: 3,
        },
        {
          // goodsDetail: {
              goodsImg:'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
              goodsName:'商品名称',
              goodsDesc:'商品详情',
          // },
          goodsPrice: 188,
          goodsNum: 3,
        },
        {
          // goodsDetail: {
              goodsImg:'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
              goodsName:'商品名称',
              goodsDesc:'商品详情',
          // },
          goodsPrice: 188,
          goodsNum: 3,
        },
        {
          // goodsDetail: {
              goodsImg:'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
              goodsName:'商品名称',
              goodsDesc:'商品详情',
          // },
          goodsPrice: 188,
          goodsNum: 3,
        },
        {
          // goodsDetail: {
              goodsImg:'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
              goodsName:'商品名称',
              goodsDesc:'商品详情',
          // },
          goodsPrice: 188,
          goodsNum: 3,
        },
      ],


      columnList: [
        // { prop: "goodsPrice", label: "单价", width: "120", align: "center" },
        // { prop: "goodsNum", label: "数量", width: "140", align: "center" },
        // { label:"商品" ,width:"270" ,align:"center"},
        // { label:"小计" ,width:"190", align:"center"},
        // { prop:"" ,label:"操作" ,width:"180" ,align:"center"},
      ],
    };
  },

  mounted(){
    for (let i = 0; i < this.label.length; i++) {
      this.columnList[i] = { prop: this.label[i].prop, label: this.label[i].label, width: "180", align: "center"};
    }
    // this.columnList[0] = {prop: "goodsPrice", label: this.label[0], width: "120", align: "center" };
  }
};
</script>
 
<style lang='scss' scoped>
.constainer{
    width: 100vw;//宽等于屏幕的宽
    height: 100vh;//高等于屏幕的高
    background-color: #ccc;
    padding-top: 50px;
}
.content{
    width: 900px;
    height: 600px;
    margin: 0 auto;
    background-color: #fff;
    border-radius: 10px;
    overflow: hidden;
}
</style>