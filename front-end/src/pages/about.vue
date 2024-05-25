<script>
import sideBar from '~/components/side-bar.vue'
export default {
  name:"about",
  components:{sideBar},
}
</script>

<template>
    <div id="side-bar">
      <side-bar></side-bar>
    </div>
</template>

<style>
#side-bar{
  position: absolute;
  width: 150%;
  z-index: 10;
}
 body {
   margin: 0;
   padding: 0;
   background-size: 100vw 100vh;
 }

</style>