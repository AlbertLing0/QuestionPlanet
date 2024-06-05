<template>
  <body>
    <div id="component-root" :class="[themeDark && 'theme-dark']">
      <div id="top-bar">
        <topBar :themeDark="themeDark"/>
      </div>
      <div class="bottom-box">
        <div id="side-bar">
          <sideBar @toggle-theme="toggleTheme"/>
        </div>
        <div class="right-box">
          <div class="head-text">
            欢迎回来，{{nowUserName}}。
          </div>
          <div id="square">
            <quest_square/>
          </div>
        </div>

      </div>
    </div>
  </body>
</template>

<script>
import sideBar from "~/components/side-bar.vue";
import topBar from "~/components/top-bar.vue"
import GlassCard from "~/components/glassCard.vue";
import quest_square from "~/components/quest_square.vue";
import {inject} from "vue";
export default {
  name:"about",
  components: {GlassCard, sideBar, topBar,quest_square},
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
      themeDark: false
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

<style lang="css" scoped>
#component-root {
  --theme-text-color: rgb(131, 128, 155);
  --theme-info-text-color: rgb(0, 0, 0);
  --theme-hover-color: rgb(103, 56, 223);
  --theme-hover-menu-color: rgb(248, 247, 255);
  --bg-color: rgb(256, 256, 256,0.2);
  --pane-color: rgb(240, 240, 240);
  --border-color: rgb(26, 22, 19);
  --theme-item-color: rgb(0, 0, 0);
  --theme-text-box-color:rgb(250,250,250,0.7);
  --glass-head-number-color:rgb(10,10,10,0.1);
  --button-color:rgb(256,256,256);
  --button-hover-color:lightgray;
  background-color: var(--bg-color);
  //background: transparent;
  backdrop-filter: blur(17px);
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

#component-root.theme-dark {
  --theme-text-color: rgb(141, 139, 164);
  --theme-info-text-color: rgb(255, 255, 255);
  --theme-hover-color: rgb(255, 255, 255);
  --theme-hover-menu-color: rgb(36, 31, 53);
  --bg-color: rgb(31, 30, 38,0.2);
  --pane-color: rgb(64, 64, 64);
  --border-color: rgb(229, 233, 236);
  --theme-item-color: rgb(211, 211, 211);
  --theme-text-box-color:rgb(90,90,90,0.7);
  --glass-head-number-color:rgb(210,210,210,0.1);
  --button-color: black;
  --button-hover-color: gray;
}
@font-face {
  font-family: Marmelad_Regular;
  font-weight: normal;
  src: url(~/assets/font/Marmelad-Regular.ttf) format("truetype");
  text-rendering: optimizeLegibility;
}


.bottom-box{
  display: flex;
  flex-direction: row;
  position: relative;
  #side-bar{
    position: relative;
    flex:0;
    margin-top: 5vh;
    //top: 50vh; /* 将顶部定位到页面垂直中点 */
    //transform: translateY(-50%); /* 使用负的自身高度的一半来调整位置，实现垂直居中 */
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
    padding: 2% 2%;
    display: flex;
    flex-direction: column;
    .head-text{
      font-size: 30px;
      font-family: SansSerif;
      color: var(--theme-info-text-color);
      align-items: flex-end;
      margin-bottom: 20px;
      border-bottom: 2px solid var(--border-color);
    }
    #square{
      position: relative;
      //width: 70vw;
      //height: 10vh;
      margin-left: 0;
      margin-right: 0;
      margin-top: 0;
    }

  }
}
body {
  margin: 0;
  padding: 0;
  //background-size: 100vw 100vh;


}

</style>
