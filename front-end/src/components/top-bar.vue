<template>
  <body>
  <div class="top-bar-logo">
    <router-link to="/"><img :src="logoSrc" alt="" class="logo"></router-link>
  </div>
  </body>
</template>

<script>
import sideBar from '~/components/side-bar.vue'
export default {
  name: "top-bar",
  components: {sideBar},
  props: ["themeDark"],
  computed: {
    logoSrc() {
      console.log(this.$props.themeDark);
      // 使用三目运算符根据条件返回不同的图片路径
      return this.$props.themeDark ? 'static/logo/logo-white.svg' : 'static/logo/logo-black.svg';
    }
  },
  methods: {

  },
  data() {
    return {

    };
  },
}
</script>

<style scoped>
.top-bar-logo{
  border-bottom: 3px solid var(--border-color);
  .logo{
    margin-left: 30px;
    margin-top: -15px;
    margin-bottom: -15px;
    &:hover{
      cursor: pointer;
      opacity: 0.8;
    }
  }
  //background-color: var(--pane-color);
}
</style>
