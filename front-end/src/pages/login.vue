<script>
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';
import PlanetBG from '~/components/planetBG.vue';
import NavBar from "~/components/navigation-bar.vue";
import axios from 'axios';

export default defineComponent({
  components: {
    NavBar,axios
  },

  setup() {
    const router = useRouter();
    const tableData = ref([]);
    const username = ref('');
    const password = ref('');

    // 登录提交方法
    const submitLogin = async () => {
      try {
        axios.get('http://localhost:1234/api/login', {
          params: {
            username: username.value,
            password: password.value
          }
        }).then(response => {
          if (response.data === 'success'){
            console.log(response.data);
            router.push('/');
          }
        });
      } catch (error) {
        console.error('Login failed:', error);
      }

    };

    // fetchUserData();

    return {
      tableData,
      username,
      password,
      submitLogin
    };
  }

});
</script>

<template>
  <div id="naviBar">
    <nav-bar/>
  </div>
  <div  class="loginBox">
    <div class="title">
      <div class="title-h1">
        Welcome Back!
      </div>
      <div class="tips">
        问卷星球——一款轻快的问卷
      </div>
    </div>
    <div class="form">
      <div class="input-wrapper">
        <input type="text" placeholder="UserName" v-model="username">
      </div>
      <div class="input-wrapper">
        <input type="password" placeholder="Password" v-model="password">
      </div>
      <div class="forgot-pwd">
        <span>Forgot Password?</span>
      </div>
      <!-- <button class="btn">Login</button> -->
      <button class="btn" @click="submitLogin">Login</button>
    </div>
    <div class="register">
      Not a member? <span><router-link to="/register">Register now</router-link></span>
    </div>
  </div>
</template>

<style scoped>
#BG{
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
@font-face {
  font-family: PlastoTrial_ExtraLight;
  font-weight: 700;
  src: url(src/assets/font/PlastoTrial-ExtraLight.otf) format("truetype");
  text-rendering: optimizeLegibility;
}

@font-face {
  font-family: PlastoTrial_ExtraBold;
  font-weight: 700;
  src: url(src/assets/font/PlastoTrial-ExtraBold.otf) format("truetype");
  text-rendering: optimizeLegibility;
}
@font-face {
  font-family: Marmelad_Regular;
  font-weight: normal;
  src: url(src/assets/font/Marmelad-Regular.ttf) format("truetype");
  text-rendering: optimizeLegibility;
}
.loginBox {
  position: absolute; /* Ensure it can be controlled by z-index */
  z-index: 10; /* Higher than other elements */
  width: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 5% 10%;
  box-sizing: border-box;
  font-family: PlastoTrial_ExtraLight;
  background: transparent;
  border: 2px solid rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(7px);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  .title {
    text-align: center;
    .title-h1 {
      margin: 5% 0;
      text-align: center;
      font-size: 3vw;
      font-family: PlastoTrial_ExtraBold;
      color: white;
    }
    .tips {
      line-height: 30px;
      color: #727272;
    }
  }
  .form {
    width: 100%;
    margin-top: 10%;
    .input-wrapper {
      width: 100%;
      margin: 10px 0;
      input {
        width: 100%;
        height: 6vh;
        border: 1px solid #000;
        border-radius: 50px;
        box-sizing: border-box;
        padding: 10px 20px;
        font-size: 20px;
        outline: none;
        font-family: Marmelad_Regular;
      }
    }
    .forgot-pwd {
      text-align: right;
      margin: 10px 0;
      cursor: pointer;
      color: white;
    }
    .btn {
      width: 100%;
      height: 50px;
      border: 0;
      background-color: #000;
      color: #fff;
      margin: 10px 0;
      border-radius: 50px;
      font-size: 20px;
      cursor: pointer;
      font-family: PlastoTrial_ExtraBold;
    }
  }
  .register {
    width: 100%;
    text-align: center;
    margin-top: 5%;
    color: white;
    span {
      color: rgb(135, 228, 89);
      cursor: pointer;
    }
  }
}
</style>