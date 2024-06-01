<template>
  <div :class="['component_root', themeDark && 'theme_dark']">
    <div id="top-bar">
      <topBar :themeDark="themeDark"/>
    </div>
    <div class="bottom-box">
      <div id="side-bar">
        <sideBar @toggle-theme="toggleTheme"/>
      </div>
      <div class="right-box">
        <div class="head-text">
          歡迎回来，{{nowUserName}}。
        </div>
        <div class="mgr_box_container">
          <que_mgr/>
          <ans_mgr/>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import sideBar from "~/components/side-bar.vue";
import topBar from "~/components/top-bar.vue";
import que_mgr from "~/components/que_mgr.vue";
import ans_mgr from "~/components/ans_mgr.vue";
import {inject} from "vue";
export default {
  name:"about",
  components: {sideBar, topBar, que_mgr, ans_mgr},
  setup(){
    const nowUserName = inject('Username') ;
    return {
      nowUserName
    }
  },
  methods: {
    /** @param {boolean} themeDark */
    setThemeDark(themeDark) {
      this.themeDark = themeDark;
      localStorage.setItem("theme-color", themeDark ? "dark" : "light");
    },
    toggleTheme() {
      this.setThemeDark(!this.themeDark);
    },
  },
  data() {
    return {
      themeDark: false,
      mgr_boxes: ["que_mgr", "ans_mgr"],
      mgr_ndx: 0
    };
  },
  mounted() {
    if (localStorage.getItem("theme-color") === "dark") { // Only when `dark` got, toggling is needed.
      this.setThemeDark(true);
    }
    if(localStorage.getItem("username")){
      this.nowUserName=localStorage.getItem("username");
    }
  }
}
</script>

<style lang="scss" scoped>
@import url("~/assets/style/theme_colors.css");

.component_root {
  background-color: var(--bg-color);
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

@font-face {
  font-family: Marmelad_Regular, "yu mincho";
  font-weight: normal;
  src: url(~/assets/font/Marmelad-Regular.ttf) format("truetype");
  text-rendering: optimizeLegibility;
}

body {
  margin: 0;
  padding: 0;
  /* background-size: 100vw 100vh; */


}
.bottom-box{
  display: flex;
  flex-direction: row;
  position: relative;
  #side-bar{
    position: relative;
    flex:0;
    margin-top: 5vh;
    /* top: 50%; */ /* 将顶部定位到页面垂直中點 */
    /* transform: translateY(-50%); */ /* 使用负的自身高度的一半来调整位置，實现垂直居中 */
    z-index: 10;
  }
  .right-box{
    flex:1;
    position: relative;
    margin-left:30px;
    margin-right: 40px;
    margin-top: 5vh;
    z-index:10;
    height: 80vh;
    background-color: var(--theme-text-box-color);
    border-radius: 20px;
    padding: 3% 5%;
    .head-text{
      font-size: 30px;
      font-family: SansSerif;
      color: var(--theme-info-text-color);
      align-items: flex-end;
    }
    .mgr_box_container {
      display: flex;
      flex-direction: row;
    }

  }
}

</style>
