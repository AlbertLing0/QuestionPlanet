<script >
import {inject} from "vue";
import router from "~/router/index.js";

export default {
  name:"blocker",
  setup(){
    const username=inject("Username");
    return{
      username
    }
  },
  mounted() {
    if(this.username.value===null){
      router.push("/");
    }
  }
}
</script>

<template>

</template>

<style scoped>

</style>