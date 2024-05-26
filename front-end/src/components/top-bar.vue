<script>
import sideBar from '~/components/side-bar.vue'
export default {
  name:"top-bar",
  components:{sideBar},
  data() {
    return {
      themeDark: false // 父组件中的 themeDark 变量
    };
  },
  computed: {
    logoSrc() {
      console.log(this.themeDark);
      // 使用三目运算符根据条件返回不同的图片路径
      return this.themeDark? 'static/logo/logo-white.svg' : 'static/logo/logo-black.svg';
    }
  },
  methods: {
    handleThemeUpdate(newTheme) {
      this.themeDark = newTheme;
    }
  }
}
</script>

<template>
  <body>
  <div class="top-bar-logo">
    <side-bar  @update-theme="handleThemeUpdate" v-if="false" ></side-bar>
    <img :src="logoSrc" alt="" class="logo">
  </div>
  </body>
</template>

<style scoped>
  .top-bar-logo{
    border-bottom: 1px solid rgb(229, 233, 236);
  }
</style>