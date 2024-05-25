<script>
import {defineComponent, ref} from 'vue';

import NavBar from "~/components/navigation-bar.vue";

export default {
  components: { NavBar},
  name: 'Index',
};

</script>

<template>
  <div id="app">
    <div id="naviBar">
      <NavBar/>
    </div>
  </div>
</template>

<style scoped>
#app {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}



#naviBar {
  position: absolute;
  width: 100%;
  z-index: 10;
}
</style>
