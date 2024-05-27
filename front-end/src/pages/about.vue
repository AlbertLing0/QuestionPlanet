<template>
  <div id="component-root" :class="[themeDark && 'theme-dark']">
    <div id="top-bar">
      <topBar :themeDark="themeDark"/>
    </div>
    <div id="side-bar">
      <sideBar @toggle-theme="toggleTheme"/>
    </div>
  </div>
</template>

<script>
import sideBar from "~/components/side-bar.vue";
import topBar from "~/components/top-bar.vue";
export default {
  name:"about",
  components: {sideBar, topBar},
  methods: {
    /** @param {boolean} themeDark */
    setThemeDark(themeDark) {
      this.themeDark = themeDark;
      localStorage.setItem("theme-color", themeDark ? "dark" : "light");
    },
    toggleTheme() {
      this.setThemeDark(!this.themeDark);
    }
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
  }
}
</script>

<style lang="css" scoped>
#component-root {
  --theme-text-color: rgb(131, 128, 155);
  --theme-info-text-color: rgb(0, 0, 0);
  --theme-hover-color: rgb(103, 56, 223);
  --theme-hover-menu-color: rgb(248, 247, 255);
  --bg-color: rgb(256, 256, 256,0.8);
  --pane-color: rgb(240, 240, 240);
  --border-color: rgb(26, 22, 19);
  --theme-item-color: rgb(0, 0, 0);
  background-color: var(--bg-color);
  width: 100vw;
  height: 100vh;
}

#component-root.theme-dark {
  --theme-text-color: rgb(141, 139, 164);
  --theme-info-text-color: rgb(255, 255, 255);
  --theme-hover-color: rgb(255, 255, 255);
  --theme-hover-menu-color: rgb(36, 31, 53);
  --bg-color: rgb(31, 30, 38,0.8);
  --pane-color: rgb(64, 64, 64);
  --border-color: rgb(229, 233, 236);
  --theme-item-color: rgb(211, 211, 211);
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
