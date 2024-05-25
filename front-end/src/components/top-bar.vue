<template>
  <body>
  <div class="top-bar-logo">
    <img :src="logoSrc" alt="" class="logo">
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
  border-bottom: 1px solid rgb(--border-color);
  background-color: var(--pane-color);
}
</style>
