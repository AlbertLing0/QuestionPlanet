<script>
import sideBar from '~/components/side-bar.vue'
import topBar from "~/components/top-bar.vue";
export default {
  name:"about",
  components:{sideBar,topBar},

}
</script>

<template>
  <div id="top-bar">
    <topBar/>
  </div>
  <div id="side-bar">
    <sideBar/>
  </div>
</template>

<style scoped>
#top-bar{
  margin-left: 30px;
}
#side-bar{
  position: absolute;
  top: 50%; /* 将顶部定位到页面垂直中点 */
  transform: translateY(-50%); /* 使用负的自身高度的一半来调整位置，实现垂直居中 */
  z-index: 10;
}
body {
  margin: 0;
  padding: 0;
  background-size: 100vw 100vh;
}

</style>