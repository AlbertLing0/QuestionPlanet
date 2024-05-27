<template>
  <div :class="['component_root', themeDark && 'theme_dark']">
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
@import url("~/assets/style/theme_colors.css");

.component_root {
  background-color: var(--bg-color);
  width: 100vw;
  height: 100vh;
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
